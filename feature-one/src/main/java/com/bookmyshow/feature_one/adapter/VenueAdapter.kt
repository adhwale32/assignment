package com.bookmyshow.feature_one.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bookmyshow.common_ui.model.VenueShowtime
import com.bookmyshow.feature_one.data.Venue
import com.bookmyshow.feature_one.databinding.ItemVenueBinding
import com.bookmyshow.feature_one.listener.IShowTimeListener


/**
 * VenueAdapter
 */
class VenueAdapter(private val dataList: ArrayList<Venue>, private val showTimeListener: IShowTimeListener) :
    RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVenueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VenueViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder as VenueViewHolder
        holder.bind(data)
    }

    override fun getItemCount(): Int = dataList.size


    /**
     * VenueViewHolder
     * @param binding adapter view.
     */
    inner class VenueViewHolder(private val binding: ItemVenueBinding) : ViewHolder(binding.root) {
        /**
         * bind data with view
         */
        fun bind(itemModel: Venue) {
            val listVenueShowtime = ArrayList<VenueShowtime>()
            itemModel.showtimes.map {
                VenueShowtime(showDate = itemModel.showDate, name = itemModel.name, showDateCode = it.showDateCode,
                        showTime = it.showTime)
            }.let { listVenueShowtime.addAll(it) }
            val showTimeAdapter = ShowTimeAdapter(listVenueShowtime, showTimeListener)
            with(binding) {
                venue = itemModel
                rvShowTime.adapter = showTimeAdapter
                showTimeAdapter.notifyDataSetChanged()
            }


        }
    }
}

