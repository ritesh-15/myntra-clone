package com.example.myntra.presentation.choose_address_screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.common.Screen
import com.example.myntra.common.bottom_navigation.AppBottomNavigation
import com.example.myntra.domain.model.Address
import com.example.myntra.ui.theme.*

@Composable
fun ChooseAddressScreen(
    navController: NavController,
    viewModel: ChooseAddressViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    val selectedAddressId = remember {
        mutableStateOf("")
    }

    fun changeSelectedId(id: String) {
        selectedAddressId.value = id
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp, title = {
                    Text(text = "Choose Delivery Address", fontFamily = Poppins)
                }, navigationIcon = ({
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }))
        },
        scaffoldState = scaffoldState,
        drawerShape = RectangleShape,
        bottomBar = {
            ChooseAddressBottomBar(navController = navController,
                viewModel,
                selectedAddressId.value,
                state.addresses.isEmpty())
        }
    ) {
        if (state.loading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(light),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = primary,
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .background(Color.White)
                        .padding(4.dp)
                        .clip(CircleShape)
                )
            }
        } else if (state.addresses.isEmpty() || viewModel.addNewAddress.value) {
            NoAddressFound(viewModel, modifier = Modifier.padding(it))
        } else {
            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                items(state.addresses) { address ->
                    SingleAddressItem(address, selectedAddressId.value) { id ->
                        changeSelectedId(id)
                    }
                }

                item {
                    Button(onClick = {
                        viewModel.onAddNewAddress(true)
                    },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = primary,
                            backgroundColor = Color.White
                        ),
                        contentPadding = PaddingValues(12.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = primary
                        ),
                        shape = Shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Add new address".uppercase(),
                            fontFamily = Poppins,
                            fontSize = 16.sp)
                    }
                }

            }
        }

    }
}


// add address 
@Composable
fun NoAddressFound(viewModel: ChooseAddressViewModel, modifier: Modifier = Modifier) {

    val addressFormErrors = viewModel.addressErrors.value

    Column(
        modifier = modifier
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState(), enabled = true)
    ) {

        Text(text = "Add address",
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h5.fontSize,
            fontFamily = Poppins
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = viewModel.address.value, onValueChange = {
            viewModel.onChange(ChangeEvents.OnChangeAddress(it))
        },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Address", fontFamily = Poppins)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = primary,
                focusedBorderColor = primary,
                focusedLabelColor = primary
            ),
            singleLine = true,
            isError = addressFormErrors.address.isNotEmpty()
        )

        if (addressFormErrors.address.isNotEmpty())
            Text(text = addressFormErrors.address, color = red, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = viewModel.country.value, onValueChange = {
            viewModel.onChange(ChangeEvents.OnChangeCountry(it))
        },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Country", fontFamily = Poppins)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = primary,
                focusedBorderColor = primary,
                focusedLabelColor = primary
            ),
            singleLine = true,
            isError = addressFormErrors.country.isNotEmpty()
        )

        if (addressFormErrors.country.isNotEmpty())
            Text(text = addressFormErrors.country, color = red, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = viewModel.countryState.value, onValueChange = {
            viewModel.onChange(ChangeEvents.OnChangeState(it))
        },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "State", fontFamily = Poppins)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = primary,
                focusedBorderColor = primary,
                focusedLabelColor = primary
            ),
            singleLine = true,
            isError = addressFormErrors.state.isNotEmpty()
        )

        if (addressFormErrors.state.isNotEmpty())
            Text(text = addressFormErrors.state, color = red, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = viewModel.city.value, onValueChange = {
            viewModel.onChange(ChangeEvents.OnChangeCity(it))
        },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "City", fontFamily = Poppins)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = primary,
                focusedBorderColor = primary,
                focusedLabelColor = primary
            ),
            singleLine = true,
            isError = addressFormErrors.city.isNotEmpty()
        )

        if (addressFormErrors.city.isNotEmpty())
            Text(text = addressFormErrors.city, color = red, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = viewModel.pinCode.value, onValueChange = {
            viewModel.onChange(ChangeEvents.OnChangePinCode(it))
        },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Pincode", fontFamily = Poppins)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = primary,
                focusedBorderColor = primary,
                focusedLabelColor = primary
            ),
            singleLine = true,
            isError = addressFormErrors.pinCode.isNotEmpty()
        )

        if (addressFormErrors.pinCode.isNotEmpty())
            Text(text = addressFormErrors.pinCode, color = red, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = viewModel.phoneNumber.value, onValueChange = {
            viewModel.onChange(ChangeEvents.OnChangePhoneNumber(it))
        },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Phone Number", fontFamily = Poppins)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = primary,
                focusedBorderColor = primary,
                focusedLabelColor = primary
            ),
            singleLine = true,
            isError = addressFormErrors.phoneNumber.isNotEmpty()
        )

        if (addressFormErrors.phoneNumber.isNotEmpty())
            Text(text = addressFormErrors.phoneNumber, color = red, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = viewModel.nearestLandmark.value, onValueChange = {
            viewModel.onChange(ChangeEvents.OnChangeNearestLandmark(it))
        },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Nearest Landmark", fontFamily = Poppins)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = primary,
                focusedBorderColor = primary,
                focusedLabelColor = primary
            ),
            singleLine = true,
            isError = addressFormErrors.nearestPlace.isNotEmpty()
        )

        if (addressFormErrors.nearestPlace.isNotEmpty())
            Text(text = addressFormErrors.nearestPlace, color = red, fontSize = 12.sp)

    }


}

@Composable
fun SingleAddressItem(address: Address, selectedAddressId: String, onSelect: (id: String) -> Unit) {
    Card(
        backgroundColor = Color.White,
        border = BorderStroke(width = 1.dp,
            color = if (address.id == selectedAddressId) primary else Color.LightGray
        ),
        shape = Shapes.small,
        elevation = 0.dp,
        modifier = Modifier
            .selectable(
                selected = address.id == selectedAddressId,
                role = Role.RadioButton
            ) {
                onSelect(address.id)
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AddressItem("Country", address.country)
            AddressItem("State", address.state)
            AddressItem("City", address.country)
            AddressItem("Address", address.address)
            AddressItem("Pin Code", address.pincode.toString())
            AddressItem("Phone Number", address.phoneNumber ?: "NA")
            AddressItem("Nearest Landmark", address.nearestLandmark ?: "NA")
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun AddressItem(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title,
            fontFamily = Poppins,
            fontSize = 14.sp)

        Text(text = value,
            fontFamily = Poppins,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun ChooseAddressBottomBar(
    navController: NavController,
    viewModel: ChooseAddressViewModel,
    selectedAddressId: String,
    save: Boolean = false,
) {
    val context = LocalContext.current

    BottomNavigation(
        modifier = Modifier
            .background(Color.White)
            .padding(12.dp)
    ) {

        Button(
            modifier = Modifier
                .background(primary)
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = primary,
                contentColor = Color.White
            ),
            onClick = {
                if (save || viewModel.addNewAddress.value) {
                    viewModel.addAddress()
                } else {
                    if (selectedAddressId.isEmpty()) {
                        Toast.makeText(context,
                            "You have not selected address for delivery please choose address!",
                            Toast.LENGTH_LONG).show()
                    } else {
                        navController.navigate(Screen.CheckoutScreen.passAddressId(selectedAddressId))
                    }
                }
            },
            elevation = ButtonDefaults.elevation(0.dp),
            enabled = !viewModel.state.value.loading
        ) {

            if (viewModel.state.value.loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .height(20.dp)
                        .width(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )

            }

            if (save || viewModel.addNewAddress.value) {
                Text(text = "Save".uppercase(), fontFamily = Poppins)
            } else {
                Text(text = "continue".uppercase(), fontFamily = Poppins)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChooseAddressScreenPreview() {
    ChooseAddressScreen(navController = rememberNavController())
}