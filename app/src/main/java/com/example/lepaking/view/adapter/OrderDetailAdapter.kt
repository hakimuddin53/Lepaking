package com.example.lepaking.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lepaking.R
import com.example.lepaking.databinding.ItemOrderDetailBinding
import com.example.lepaking.model.database.entity.OrderDetailEntity
import com.example.lepaking.viewmodel.ItemOrderDetailViewModel


class OrderDetailAdapter: RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {
    private var list = mutableListOf<OrderDetailEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding = DataBindingUtil.inflate<ItemOrderDetailBinding>(
                LayoutInflater.from(parent.context), R.layout.item_order_detail,
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

    class ViewHolder(internal var binding: ItemOrderDetailBinding) : RecyclerView.ViewHolder(binding.root)
    {
        internal fun bindView(data: OrderDetailEntity)
        {
            binding.viewModel = ItemOrderDetailViewModel(data)
        }
    }

    fun setDataList(list: List<OrderDetailEntity>)
    {
        this.list.clear()
        this.list.addAll(list)
    }
}
