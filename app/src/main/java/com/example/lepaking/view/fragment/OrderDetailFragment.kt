package com.example.lepaking.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lepaking.Constants
import com.example.lepaking.LepakingApplication
import com.example.lepaking.R
import com.example.lepaking.SessionData
import com.example.lepaking.common.extension.getViewModelNew
import com.example.lepaking.databinding.FragmentOrderDetailBinding
import com.example.lepaking.model.database.dao.OrderDetailDao
import com.example.lepaking.view.adapter.OrderDetailAdapter
import com.example.lepaking.viewmodel.OrderDetailViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OrderDetailFragment : Fragment() {

    private lateinit var binding: FragmentOrderDetailBinding
    lateinit var adapter: OrderDetailAdapter

    private val disposables = CompositeDisposable()

    private val viewModel : OrderDetailViewModel by lazy {
        getViewModelNew { OrderDetailViewModel(arguments?.getString(Constants.ORDER_ID)) }
    }

    @Inject
    lateinit var sessionData: SessionData
    @Inject
    lateinit var orderDetailDao: OrderDetailDao

    init {
        LepakingApplication.dataComponent.inject(this)
    }

    companion object {
        fun newInstance(orderID: String? = null) = OrderDetailFragment().apply {
            this.arguments = Bundle().apply {
                orderID?.let { putString(Constants.ORDER_ID, it) }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return if (savedInstanceState == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false)
            binding.viewModel = viewModel

            adapter = OrderDetailAdapter()
            subscribeToModel()
            initializeRecyclerView()

            binding.root
        } else {
            view
        }
    }

    private fun initializeRecyclerView(){
        val recyclerView = binding.recyclerItemOrderDetail
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun subscribeToModel() {

        viewModel.orderDetailLiveData.observe(viewLifecycleOwner, Observer { orderDetails ->
            if(orderDetails != null) {
                adapter.setDataList(orderDetails)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onDetach() {
        disposables.clear()
        super.onDetach()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}