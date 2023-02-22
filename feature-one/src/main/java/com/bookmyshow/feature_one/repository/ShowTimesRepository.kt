package com.bookmyshow.feature_one.repository

import com.bookmyshow.core.NetworkManager
import com.bookmyshow.core.NetworkProvider
import com.bookmyshow.feature_one.data.Venues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Created by Akshansh Dhing on 12/10/22.
 */
class ShowTimesRepository @Inject constructor(
    private val networkProvider: NetworkProvider,
    private val networkManager: NetworkManager
): com.bookmyshow.common_ui.model.DataSource() {

    private val api: ShowTimesAPI
        get() = networkProvider.getApi(
            apiClass = ShowTimesAPI::class.java,
            baseUrl = "https://demo2782755.mockable.io"
        )

    /**
     * get the show times from api
     */
    suspend fun getShowTimes(): Flow<DataState<Venues>> {
        return flow {
            if (!networkManager.isNetworkConnected){
                emit(DataState.Error("No internet connection."))
            }else {
                emit(processResponse { api.getShowTimes() })
            }
        }.onStart { emit(DataState.Loader(true)) }.onCompletion { emit(DataState.Loader(false)) }
            .flowOn(Dispatchers.IO)
    }
}