package com.bookmyshow.feature_two

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bookmyshow.common_ui.model.VenueShowtime
import com.bookmyshow.feature_two.databinding.ActivityFeatureTwoBinding

class FeatureTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFeatureTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val venueShowTime: VenueShowtime = intent.getSerializableExtra("VenueShowTime") as VenueShowtime
        binding.venueShowTime =
            "Please check the show details\nVenue - " + venueShowTime.name + "\n\nDate - " + venueShowTime.showDate +
                    "\nTime - " + venueShowTime.showTime

    }
}