package com.example.lepaking.view.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lepaking.Constants
import com.example.lepaking.LepakingApplication
import com.example.lepaking.R
import com.example.lepaking.SessionData
import com.example.lepaking.common.eventbus.OrderItemClickBus
import com.example.lepaking.common.extension.getViewModelNew
import com.example.lepaking.databinding.FragmentOrderBinding
import com.example.lepaking.model.database.dao.OrderDetailDao
import com.example.lepaking.view.activity.MainActivity
import com.example.lepaking.view.activity.OrderDetailActivity
import com.example.lepaking.view.adapter.OrderAdapter
import com.example.lepaking.viewmodel.OrderViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OrderFragment : Fragment() {

    //private lateinit var viewModel: OrderViewModel
    private lateinit var binding: FragmentOrderBinding

    lateinit var adapter: OrderAdapter
    private val disposables = CompositeDisposable()

    @Inject
    lateinit var sessionData: SessionData

    @Inject
    lateinit var orderDetailDao: OrderDetailDao

    init {
        LepakingApplication.dataComponent.inject(this)
    }

    private val viewModel : OrderViewModel by lazy {
        getViewModelNew { OrderViewModel(arguments?.getString(Constants.FRAGMENT_TYPE)) }
    }


    companion object {
        fun newInstance(fragmentType: String? = null) = OrderFragment().apply {
            this.arguments = Bundle().apply {
                fragmentType?.let { putString(Constants.FRAGMENT_TYPE, it) }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (savedInstanceState == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
            binding.viewModel = viewModel

            adapter = OrderAdapter()

            subscribeToModel()
            initializeRecyclerView()

            binding.root
        } else {
            view
        }
    }

    private fun initializeRecyclerView(){
        val recyclerView = binding.recyclerItemOrder
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }


    private fun subscribeToModel() {
        viewModel.orderLiveData.observe(viewLifecycleOwner, { orderDetails ->
            if(orderDetails != null) {
                adapter.setDataList(orderDetails)
                adapter.notifyDataSetChanged()
            }
        })

        disposables.add(OrderItemClickBus.toObservable().subscribe {
            val intent = Intent(activity,OrderDetailActivity::class.java)
            intent.putExtra(Constants.ORDER_ID,it.orderID)
            intent.putExtra(Constants.FRAGMENT_TYPE,arguments?.getString(Constants.FRAGMENT_TYPE))

            startActivity(intent)

        })
    }



    override fun onDetach() {
        disposables.clear()
        super.onDetach()
    }
}