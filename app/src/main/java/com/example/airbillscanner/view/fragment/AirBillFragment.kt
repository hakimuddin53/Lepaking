package com.example.airbillscanner.view.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.airbillscanner.R
import com.example.airbillscanner.databinding.FragmentAirbillBinding
import com.example.airbillscanner.view.activity.BarcodeScanningActivity
import com.example.airbillscanner.viewmodel.AirBillViewModel

class AirBillFragment : Fragment() {

    private lateinit var viewModel: AirBillViewModel
    private lateinit var binding: FragmentAirbillBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(AirBillViewModel::class.java)

        if (savedInstanceState == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_airbill, container, false)
            binding.viewModel = viewModel

//            val activity = activity as AppCompatActivity
//            val toolbar = binding.root.findViewById<Toolbar>(R.id.toolbar)
//            activity.setSupportActionBar(toolbar)
//            activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//            activity.supportActionBar!!.setTitle(R.string.title_price_checks)

            binding.etBarcode.setRawInputType(InputType.TYPE_CLASS_TEXT);

            getEditTextBarcodeFocus()

            startBarcodeScanningActivityForResult()

//            binding.etBarcode.setOnEditorActionListener { view, actionId, keyEvent ->
//                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
//                    viewModel.verifyBarcode()
//                    true
//                } else{
//                    false
//                }
//            }

//            subscribeToModel()

            return binding.root
        } else {
            return view
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if (requestCode == BarcodeScanningActivity.BARCODE_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.valueBarcode.set(data?.getStringExtra(BarcodeScanningActivity.BARCODE_KEY))
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    private fun startBarcodeScanningActivityForResult()
    {
        val intent = Intent(requireContext(), BarcodeScanningActivity::class.java)
        startActivityForResult(intent, BarcodeScanningActivity.BARCODE_ACTIVITY_CODE)
    }

    private fun getEditTextBarcodeFocus(){
        binding.etBarcode.isFocusableInTouchMode = true;
        binding.etBarcode.requestFocus()
        binding.etBarcode.selectAll()
    }

//    private fun subscribeToModel() {
//        viewModel.isBarcodeVerify.observe(activity!!, Observer<Boolean>
//        { isBarcodeVerify ->
//            if (isBarcodeVerify != null) {
//                getEditTextBarcodeFocus()
//
//                if (!isBarcodeVerify) {
//                    SystemUtility.playErrorSound(context!!)
//                    Toast.makeText(context, context!!.getString(R.string.error_scan_barcode), Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}