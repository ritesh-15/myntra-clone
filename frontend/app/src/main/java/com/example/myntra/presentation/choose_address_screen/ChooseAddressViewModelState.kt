package com.example.myntra.presentation.choose_address_screen


import com.example.myntra.domain.model.Address
import com.example.myntra.domain.model.Catagory

data class ChooseAddressViewModelState(
    val loading: Boolean = false,
    val addresses: List<Address> = emptyList(),
    val error: String? = null,
    val address: Address? = null,
)

data class AddressDetailError(
    var address: String = "",
    var country: String = "",
    var state: String = "",
    var pinCode: String = "",
    var city: String = "",
    var phoneNumber: String = "",
    var nearestPlace: String = "",
)

sealed class ChangeEvents(val value: String) {
    class OnChangeAddress(value: String) : ChangeEvents(value)
    class OnChangeCountry(value: String) : ChangeEvents(value)
    class OnChangeState(value: String) : ChangeEvents(value)
    class OnChangePinCode(value: String) : ChangeEvents(value)
    class OnChangeCity(value: String) : ChangeEvents(value)
    class OnChangePhoneNumber(value: String) : ChangeEvents(value)
    class OnChangeNearestLandmark(value: String) : ChangeEvents(value)
}