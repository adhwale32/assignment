package com.bookmyshow.feature_one.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bookmyshow.common_ui.model.VenueShowtime
import com.bookmyshow.feature_one.databinding.ItemShowTimesBinding
import com.bookmyshow.feature_one.listener.IShowTimeListener


/**
 * ShowTime Adapter
 */
class ShowTimeAdapter(private val dataList: ArrayList<VenueShowtime>, private val showTimeListener: IShowTimeListener) :
    RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemShowTimesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowTimeViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder as ShowTimeViewHolder
        holder.bind(data)
    }

    override fun getItemCount(): Int = dataList.size


    /**
     * ShowTimeViewHolder
     * @param binding adapter view.
     */
    inner class ShowTimeViewHolder(private val binding: ItemShowTimesBinding) : ViewHolder(binding.root) {
        /**
         * bind data with view
         */
        fun bind(itemModel: VenueShowtime) {
            binding.showTime = itemModel
            binding.root.setOnClickListener {
                showTimeListener.onShowTimeClicked(itemModel)
            }

        }
    }
}

