package com.example.airbillscanner.view.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.airbillscanner.R
import com.example.airbillscanner.common.eventbus.DropdownListChangeBus
import com.example.airbillscanner.common.extension.addOnPropertyChanged
import com.example.airbillscanner.common.extension.getNonNull
import com.example.airbillscanner.databinding.FragmentAirbillBinding
import com.example.airbillscanner.model.ui.DropdownList
import com.example.airbillscanner.view.activity.BarcodeScanningActivity
import com.example.airbillscanner.viewmodel.AirBillViewModel
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.FileOutputStream

class AirBillFragment : Fragment() {

    private lateinit var viewModel: AirBillViewModel
    private lateinit var binding: FragmentAirbillBinding


    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AirBillViewModel::class.java)

        if (savedInstanceState == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_airbill, container, false)
            binding.viewModel = viewModel

            subscribeToModel()

            return binding.root
        } else {
            return view
        }
    }

    private fun subscribeToModel() {
        viewModel.couriers.observe(this, Observer { categories ->
            val data = ArrayList<DropdownList>().apply {
                categories.forEach {
                    this.add(DropdownList(it.Id.toString(), it.description, binding.dropdownCourier.id))
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

        disposables.add(DropdownListChangeBus.toObservable().subscribe {
            when (it.layoutId) {
                binding.dropdownCourier.id -> {
                    viewModel.selectedCourierId.value = it.id.toInt()
                }
            }
        })

        viewModel.selectedCourierId.observe(viewLifecycleOwner, Observer {
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

                if (viewModel.areRequiredFieldsFilled()) {
                    viewModel.insertScannedBarcode()
                    viewModel.lastConsignmentScanned.set("Last Scanned : " + viewModel.valueBarcode.getNonNull())
                    clearConsignment()
                } else {
                    Snackbar.make(binding.root, "Please fill up all mandatory fields.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }



    private fun clearConsignment() {

        binding.etOrderNumber.isFocusableInTouchMode = true;
        binding.etOrderNumber.requestFocus()

        viewModel.valueBarcode.set("")
        viewModel.valueOrderNumber.set("")

    }

    override fun onDetach() {
        disposables.clear()
        super.onDetach()
    }


    private fun startBarcodeScanningActivityForResult()
    {
        val intent = Intent(requireContext(), BarcodeScanningActivity::class.java)
        startActivityForResult(intent, BarcodeScanningActivity.BARCODE_ACTIVITY_CODE)
    }



//    fun buttonCreateExcel(view: View?) {
//        val hssfWorkbook = HSSFWorkbook()
//        val hssfSheet = hssfWorkbook.createSheet("Custom Sheet")
//        val hssfRow = hssfSheet.createRow(0)
//        val hssfCell = hssfRow.createCell(0)
//        hssfCell.setCellValue(editTextExcel.getText().toString())
//        try {
//            if (!filePath.exists()) {
//                filePath.createNewFile()
//            }
//            val fileOutputStream: FileOutputStream = FileOutputStream(filePath)
//            hssfWorkbook.write(fileOutputStream)
//            if (fileOutputStream != null) {
//                fileOutputStream.flush()
//                fileOutputStream.close()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}