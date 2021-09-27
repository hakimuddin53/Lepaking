package com.example.lepaking.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lepaking.LepakingApplication
import com.example.lepaking.R
import com.example.lepaking.SessionData
import com.example.lepaking.common.eventbus.OrderItemClickBus
import com.example.lepaking.databinding.FragmentOrderBinding
import com.example.lepaking.model.database.dao.OrderDetailDao
import com.example.lepaking.view.activity.MainActivity
import com.example.lepaking.view.adapter.OrderAdapter
import com.example.lepaking.viewmodel.OrderViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OrderFragment : Fragment() {

    private lateinit var viewModel: OrderViewModel
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

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
        viewModel.distinctOrderDetailLiveData.observe(viewLifecycleOwner, Observer { orderDetails ->
            if(orderDetails != null) {
                adapter.setDataList(orderDetails)
                adapter.notifyDataSetChanged()
            }
        })

        disposables.add(OrderItemClickBus.toObservable().subscribe {
            navigateToOrderDetailFragment(OrderDetailFragment.newInstance(it.code))
        })
    }

    private fun <T: Fragment> navigateToOrderDetailFragment(fragment : T) {
        val activity = activity as MainActivity?
        activity?.showFragment(fragment)
    }

    override fun onDetach() {
        disposables.clear()
        super.onDetach()
    }
}