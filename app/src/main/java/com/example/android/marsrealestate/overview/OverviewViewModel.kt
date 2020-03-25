
package com.example.android.marsrealestate.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MarsApiStatus{LOADING , ERROR , DONE}
/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties : LiveData<List<MarsProperty>>
        get() = _properties

    // The external immutable LiveData for the request status String
    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _navigate = MutableLiveData<MarsProperty>()
    val navigate : LiveData<MarsProperty>
        get() = _navigate

    fun doneNavigating(){
        _navigate.value = null
    }

    fun displayProperty(marsProperty: MarsProperty){
        _navigate.value = marsProperty

    }
    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */

    private var viewModelJob = Job()

    // we are using the main thread because the retrofit will create the background
    // thread for us
    private val coroutineScope = CoroutineScope(viewModelJob
            + Dispatchers.Main)

    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }



    fun getMarsRealEstateProperties(filter: MarsApiFilter) {
        _status.value = MarsApiStatus.LOADING
        coroutineScope.launch {
            val getProperty = MarsApi.retrofitService.getProperties(filter.value)
            try {
                // await will wait for the response from the server
                val list = getProperty.await()
                _status.value = MarsApiStatus.DONE
                if(list.isNotEmpty()){
                    _properties.value = list
                }
            }catch(t:Throwable){
                _status.value = MarsApiStatus.ERROR
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
