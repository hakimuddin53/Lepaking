package com.example.lepaking.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lepaking.R
import com.example.lepaking.databinding.ItemOrderBinding
import com.example.lepaking.model.ui.OrderDetailModel
import com.example.lepaking.viewmodel.ItemOrderViewModel


class OrderAdapter: RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private var list = mutableListOf<OrderDetailModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding = DataBindingUtil.inflate<ItemOrderBinding>(
                LayoutInflater.from(parent.context), R.layout.item_order,
                parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val orderDetail = list[position]
        holder.bindView(orderDetail)
    }

    override fun getItemCount(): Int
    {
        return list.size
    }

    class ViewHolder(internal var binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)
    {
        internal fun bindView(data: OrderDetailModel)
        {
            binding.viewModel = ItemOrderViewModel(data)
        }
    }

    fun setDataList(list: List<OrderDetailModel>)
    {
        this.list.clear()
        this.list.addAll(list)
    }
}
