package com.bookmyshow.feature_one

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bookmyshow.common_ui.model.DataSource
import com.bookmyshow.common_ui.model.VenueShowtime
import com.bookmyshow.feature_one.data.Venue
import com.bookmyshow.feature_one.data.Venues
import com.bookmyshow.feature_one.databinding.ActivityFeatureOneBinding
import com.bookmyshow.feature_one.di.FeatureOneDaggerProvider
import com.bookmyshow.feature_one.listener.IShowTimeListener
import com.bookmyshow.feature_one.adapter.VenueAdapter
import com.bookmyshow.feature_one.viewmodel.FeatureOneViewModel
import com.bookmyshow.feature_two.FeatureTwoActivity
import kotlinx.coroutines.launch
import javax.inject.Inject


class FeatureOneActivity : AppCompatActivity(), IShowTimeListener {
    private var isReversed: Boolean = false
    private lateinit var binding: ActivityFeatureOneBinding

    private lateinit var venueAdapter: VenueAdapter

    private lateinit var venues: Venues
    private val venueList = ArrayList<Venue>()

    @Inject
    lateinit var viewModel: FeatureOneViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FeatureOneDaggerProvider.component.inject(this)
        binding = ActivityFeatureOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        observeResponse()
        viewModel.getShowTimes()
        setListener()
    }

    private fun setListener() {
        binding.tvDate.setOnClickListener {
            venueList.clear()
            if (!isReversed) {
                venueList.addAll((venues.venues as ArrayList).reversed())
            } else {
                venueList.addAll((venues.venues))
            }
            isReversed = !isReversed
            venueAdapter.notifyDataSetChanged()

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem? = menu.findItem(R.id.search_bar)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search venue..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                venueList.clear()
                venueList.addAll(venues.venues.filter {
                    it.name.lowercase().contains(newText.lowercase())
                })
                venueAdapter.notifyDataSetChanged()
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Setting adapter
     */
    private fun setAdapter() {
        venueAdapter = VenueAdapter(venueList, this)
        binding.rvVenue.adapter = venueAdapter
    }

    /**
     * observing response
     */
    private fun observeResponse() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showTimesMutableStateFlow.collect {
                    when (it) {
                        is DataSource.DataState.Error -> {
                            val msg = it.error
                            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                        }
                        is DataSource.DataState.Loader -> {
                            if (it.isLoading) {
                                binding.progressBar.visibility = View.VISIBLE
                            } else {
                                binding.progressBar.visibility = View.GONE
                            }
                        }
                        is DataSource.DataState.Success -> {
                            venues = it.data as Venues
                            venueList.clear()
                            venueList.addAll(venues.venues)
                            venueAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    override fun onShowTimeClicked(venueShowtime: VenueShowtime) {
        val intent = Intent(this, FeatureTwoActivity::class.java)
        intent.putExtra("VenueShowTime", venueShowtime)
        startActivity(intent)
    }
}