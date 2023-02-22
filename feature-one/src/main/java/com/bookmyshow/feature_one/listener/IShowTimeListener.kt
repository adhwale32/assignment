package com.bookmyshow.feature_one.listener

import com.bookmyshow.common_ui.model.VenueShowtime

/**
 * Show time listener
 */
interface IShowTimeListener {
    fun onShowTimeClicked(venueShowtime: VenueShowtime)
}