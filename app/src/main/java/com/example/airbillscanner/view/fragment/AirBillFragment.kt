package com.example.airbillscanner.view.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.airbillscanner.AirbillScannerApplication
import com.example.airbillscanner.Constants
import com.example.airbillscanner.R
import com.example.airbillscanner.SessionData
import com.example.airbillscanner.common.eventbus.DropdownListChangeBus
import com.example.airbillscanner.common.extension.addOnPropertyChanged
import com.example.airbillscanner.common.extension.getNonNull
import com.example.airbillscanner.common.provider.DriveServiceHelper
import com.example.airbillscanner.common.utility.DateUtility
import com.example.airbillscanner.databinding.FragmentAirbillBinding
import com.example.airbillscanner.model.database.dao.AirbillScannedDao
import com.example.airbillscanner.model.ui.DropdownList
import com.example.airbillscanner.view.activity.BarcodeScanningActivity
import com.example.airbillscanner.viewmodel.AirBillViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.gson.Gson
import com.resmal.smartsales.common.utility.FileUtility
import io.reactivex.disposables.CompositeDisposable
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class AirBillFragment : Fragment() {

    private lateinit var viewModel: AirBillViewModel
    private lateinit var binding: FragmentAirbillBinding

    private val disposables = CompositeDisposable()

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var mDriveServiceHelper: DriveServiceHelper? = null

    @Inject
    lateinit var sessionData : SessionData
    @Inject
    lateinit var airbillScannedDao: AirbillScannedDao

    init {
        AirbillScannerApplication.dataComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(this).get(AirBillViewModel::class.java)

        signInGoogle()

        return if (savedInstanceState == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_airbill, container, false)
            binding.viewModel = viewModel

            subscribeToModel()

            binding.root
        } else {
            view
        }
    }

    private fun signInGoogle()
    {
        val account = GoogleSignIn.getLastSignedInAccount(activity)

        if (account == null) {
            signIn()
        } else {
            val credential = GoogleAccountCredential.usingOAuth2(
                context, Collections.singleton(DriveScopes.DRIVE_FILE)
            )
            credential.selectedAccount = account.account
            val googleDriveService = Drive.Builder(
                AndroidHttp.newCompatibleTransport(),
                GsonFactory(),
                credential
            )
                .setApplicationName("AirbillScanner")
                .build()

            mDriveServiceHelper = DriveServiceHelper(googleDriveService)
        }
    }

    private fun signIn() {
        mGoogleSignInClient = buildGoogleSignInClient()
        startActivityForResult(mGoogleSignInClient?.signInIntent, 100)
    }

    private fun buildGoogleSignInClient(): GoogleSignInClient? {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(DriveScopes.DRIVE_APPDATA))
            .requestScopes(Scope(DriveScopes.DRIVE_FILE))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context!!, signInOptions)
    }

    private fun handleSignInResult(result: Intent) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
            .addOnSuccessListener { googleSignInAccount ->

                Timber.d("Signed in as %s", googleSignInAccount.email)
                mDriveServiceHelper = DriveServiceHelper(
                    DriveServiceHelper.getGoogleDriveService(
                        context!!,
                        googleSignInAccount,
                        "AirbillScanner"
                    )
                )
                Timber.d("handleSignInResult: $mDriveServiceHelper")
            }
            .addOnFailureListener { e ->
                Timber.d("Unable to sign in.")
            }
    }

    private fun subscribeToModel() {
        viewModel.couriers.observe(viewLifecycleOwner, Observer { categories ->
            val data = ArrayList<DropdownList>().apply {
                categories.forEach {
                    this.add(
                        DropdownList(
                            it.Id.toString(),
                            it.description,
                            binding.dropdownCourier.id
                        )
                    )
                }
            }
            binding.dropdownCourier.setDataDropdown(data)

            if (data.size != 0 && viewModel.selectedCourierId.value != 0) {
                binding.dropdownCourier.setSelectedItem(viewModel.selectedCourierId.value.toString())
            }
        })

        viewModel.barcodeChangesClicked.addOnPropertyChanged {
            startBarcodeScanningActivityForResult()
        }

        viewModel.uploadGoogleDrivesClicked.addOnPropertyChanged {
            createExcel()
        }

        disposables.add(DropdownListChangeBus.toObservable().subscribe {
            when (it.layoutId) {
                binding.dropdownCourier.id -> {
                    viewModel.selectedCourierId.value = it.id.toInt()
                }
            }
        })

        viewModel.selectedCourierId.observe(viewLifecycleOwner, {
            if (it != null && it != 0) {
                binding.dropdownCourier.setSelectedItem(viewModel.selectedCourierId.value.toString())
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if (requestCode == BarcodeScanningActivity.BARCODE_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.valueBarcode.set(data?.getStringExtra(BarcodeScanningActivity.BARCODE_KEY))

                val checkAirBillScannedExist = airbillScannedDao.checkAirBillScannedExist(viewModel.valueBarcode.getNonNull())

                if (checkAirBillScannedExist == null) {
                    if (viewModel.areRequiredFieldsFilled()) {
                        viewModel.insertScannedBarcode()
                        viewModel.lastConsignmentScanned.set("Last Scanned : " + viewModel.valueBarcode.getNonNull())
                        clearConsignment()
                    } else {
                        Snackbar.make(
                            binding.root,
                            "Please fill up all mandatory fields.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Snackbar.make(
                        binding.root,
                        "This airbill number already exist. Please edit the number and try again",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
        else if(requestCode == 100)
        {
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                handleSignInResult(data)
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun clearConsignment() {
        binding.etOrderNumber.isFocusableInTouchMode = true
        binding.etOrderNumber.requestFocus()

        viewModel.valueBarcode.set("")
        viewModel.valueOrderNumber.set("")
    }

    override fun onDetach() {
        disposables.clear()
        super.onDetach()
    }

    private fun startBarcodeScanningActivityForResult() {
        val intent = Intent(requireContext(), BarcodeScanningActivity::class.java)
        startActivityForResult(intent, BarcodeScanningActivity.BARCODE_ACTIVITY_CODE)
    }

    private fun createExcel() {

        val workbook = HSSFWorkbook()
        val sheet = workbook.createSheet("Airbill Scanned List")

        val row = sheet.createRow(0)
        val cell1 = row.createCell(0)
        val cell2 = row.createCell(1)
        val cell3 = row.createCell(2)
        val cell4 = row.createCell(3)

        cell1.setCellValue("Datetime")
        cell2.setCellValue("Courier")
        cell3.setCellValue("Order Number")
        cell4.setCellValue("AirBill Number")

        val scannedModel =  airbillScannedDao.loadAirBillScanned(DateUtility.now())

        var cnt  = 1
        scannedModel.forEach {

            val row = sheet.createRow(cnt)

            val cell1 = row.createCell(0)
            val cell2 = row.createCell(1)
            val cell3 = row.createCell(2)
            val cell4 = row.createCell(3)

            cell1.setCellValue(it.createdDate)
            cell2.setCellValue(it.description)
            cell3.setCellValue(it.orderNumber)
            cell4.setCellValue(it.barcode)

            ++cnt
        }


        val directory = FileUtility.getExternalFilesDirectory(context!!, Constants.DIR_GOOGLE_DRIVE)
        val file = File(directory, sessionData.username.plus("_" + DateUtility.now().plus(".xlsx")))

        try {
            if (!file.exists()) {
                file.createNewFile()
            }
            val fileOutputStream = FileOutputStream(file)

            workbook.write(fileOutputStream)

            if (fileOutputStream != null) {
                fileOutputStream.flush()
                fileOutputStream.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        if (mDriveServiceHelper == null) {
            return
        }

        mDriveServiceHelper!!.uploadFile(
            file,
            "application/vnd.ms-excel",
            null
        )
            .addOnSuccessListener(OnSuccessListener<Any?> { googleDriveFileHolder ->
                val gson = Gson()
                Timber.i("onSuccess: %s", gson.toJson(googleDriveFileHolder))
            })
            .addOnFailureListener(OnFailureListener { e ->
                Timber.i("onFailure: %s", e.message)
            })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}