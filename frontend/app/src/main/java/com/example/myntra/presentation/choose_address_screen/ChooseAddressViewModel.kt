package com.example.myntra.presentation.choose_address_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.user.request.AddAddressRequestBody
import com.example.myntra.domain.model.Address
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.user.AddAddressUseCase
import com.example.myntra.domain.usecases.user.GetAllAddressesUseCase
import com.example.myntra.presentation.categories_screen.CategoriesViewModelState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseAddressViewModel @Inject constructor(
    private val getAllAddressesUseCase: GetAllAddressesUseCase,
    private val addAddressUseCase: AddAddressUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(ChooseAddressViewModelState())
    val state: State<ChooseAddressViewModelState> = _state

    val addressErrors = mutableStateOf(AddressDetailError())
    val addNewAddress = mutableStateOf(false)

    val address = mutableStateOf("")
    val country = mutableStateOf("")
    val pinCode = mutableStateOf("")
    val city = mutableStateOf("")
    val countryState = mutableStateOf("")
    val phoneNumber = mutableStateOf("")
    val nearestLandmark = mutableStateOf("")

    fun onAddNewAddress(value:Boolean) {
        addNewAddress.value = value

        ChangeEvents.OnChangeAddress("")
        ChangeEvents.OnChangeCity("")
        ChangeEvents.OnChangeState("")
        ChangeEvents.OnChangeCountry("")
        ChangeEvents.OnChangePinCode("")
        ChangeEvents.OnChangeNearestLandmark("")
        ChangeEvents.OnChangePhoneNumber("")
    }

    fun onChange(event: ChangeEvents) {
        when (event) {
            is ChangeEvents.OnChangeAddress -> {
                address.value = event.value
            }

            is ChangeEvents.OnChangeCountry -> {
                country.value = event.value
            }

            is ChangeEvents.OnChangeCity -> {
                city.value = event.value
            }

            is ChangeEvents.OnChangeState -> {
                countryState.value = event.value
            }

            is ChangeEvents.OnChangePinCode -> {
                pinCode.value = event.value
            }

            is ChangeEvents.OnChangePhoneNumber -> {
                phoneNumber.value = event.value
            }

            is ChangeEvents.OnChangeNearestLandmark -> {
                nearestLandmark.value = event.value
            }
        }
    }

    init {
        getAllAddresses()
    }

    fun addAddress() {
        if (!validateAddressDetails()) {

            viewModelScope.launch {
                addAddressUseCase.invoke(AddAddressRequestBody(
                    address = address.value,
                    city = city.value,
                    country = country.value,
                    pinCode = pinCode.value.toInt(),
                    phoneNumber = phoneNumber.value,
                    nearestLandmark = nearestLandmark.value,
                    state = countryState.value
                )).collect {
                    when (it) {
                        is Resource.Loading -> {
                            if (it.data != null) {
                                _state.value =
                                    ChooseAddressViewModelState(loading = false, address = it.data)
                            } else {
                                _state.value = ChooseAddressViewModelState(loading = true)
                            }
                        }

                        is Resource.Success -> {
                            _state.value =
                                ChooseAddressViewModelState(loading = false, address = it.data)
                            addNewAddress.value = false
                            getAllAddresses()
                        }

                        is Resource.Error -> {
                            if (it.errorBody != null) {
                                val error = Gson().fromJson(it.errorBody.string(),
                                    ApiError::class.java)
                                _state.value =
                                    ChooseAddressViewModelState(error = error.message,
                                        loading = false)
                            } else {
                                _state.value =
                                    ChooseAddressViewModelState(loading = false, error = it.message)
                            }
                        }
                    }
                }
            }

        }
    }

    private fun validateAddressDetails(): Boolean {
        val errors = AddressDetailError()

        var isError = false;

        if (address.value.isEmpty()) {
            errors.address = "Required!"
            isError = true
        }

        if (country.value.isEmpty()) {
            errors.country = "Required!"
            isError = true
        }

        if (city.value.isEmpty()) {
            errors.city = "Required!"
            isError = true
        }

        if (countryState.value.isEmpty()) {
            errors.state = "Required!"
            isError = true
        }

        if (pinCode.value.isEmpty()) {
            errors.pinCode = "Required!"
            isError = true
        }

        if (phoneNumber.value.isEmpty()){
            errors.phoneNumber = "Required!"
            isError = true
        }

        if (nearestLandmark.value.isEmpty()){
            errors.nearestPlace = "Required!"
            isError = true
        }


        addressErrors.value = errors

        return isError
    }

    fun getAllAddresses() {

        val response = getAllAddressesUseCase.invoke()

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        if (it.data != null) {
                            _state.value =
                                ChooseAddressViewModelState(loading = false,
                                    addresses = it.data)
                        } else {
                            _state.value = ChooseAddressViewModelState(loading = true)
                        }
                    }

                    is Resource.Success -> {
                        if (it.data != null) {
                            _state.value =
                                ChooseAddressViewModelState(loading = false,
                                    addresses = it.data)
                        }
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value =
                                ChooseAddressViewModelState(error = error.message,
                                    loading = false)
                        } else {
                            _state.value =
                                ChooseAddressViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }
}
