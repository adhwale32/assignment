package com.bookmyshow.feature_one.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookmyshow.common_ui.model.DataSource
import com.bookmyshow.feature_one.repository.ShowTimesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Akshansh Dhing on 12/10/22.
 */
class FeatureOneViewModel @Inject constructor(private val showTimesRepository: ShowTimesRepository)  : ViewModel() {
    private val _showTimesMutableStateFlow= MutableSharedFlow<DataSource.DataState<*>>()
    val showTimesMutableStateFlow = _showTimesMutableStateFlow.asSharedFlow()

    /**
     * get showtimes
     */
    fun getShowTimes() = viewModelScope.launch{
        showTimesRepository.getShowTimes().collect{
            _showTimesMutableStateFlow.emit(it)
        }
    }
}