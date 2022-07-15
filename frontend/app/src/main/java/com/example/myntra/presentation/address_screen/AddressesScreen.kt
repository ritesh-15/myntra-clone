package com.example.myntra.presentation.address_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.R
import com.example.myntra.domain.model.Address
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.Shapes
import com.example.myntra.ui.theme.primary
import com.example.myntra.presentation.bag_screen.BagScreenBottomBar
import com.example.myntra.presentation.bag_screen.BagScreenTopBar
import com.example.myntra.ui.theme.light

@Composable
fun AddressesScreen(navController: NavController, viewModel: AddressViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            AddressScreenTopBar(navController)
        },
        scaffoldState = scaffoldState,
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
        } else if (state.addresses?.isEmpty() == true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(light),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No address found!",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp)
            }
        } else {
            if (state.addresses != null) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(state.addresses) {
                        SingleAddressItem(it, viewModel)
                    }
                }
            }
        }

    }

}

@Composable
fun AddressScreenTopBar(navController: NavController) {
    TopAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp, title = {
            Text(text = "Addresses", fontFamily = Poppins)
        },
        actions = {
            Image(painter = painterResource(id = R.drawable.ic_like),
                contentDescription = null,
                modifier = Modifier
                    .width(22.dp)
                    .height(22.dp),
                contentScale = ContentScale.Fit)
        },
        modifier = Modifier.padding(horizontal = 8.dp),
        navigationIcon = ({
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }))
}

@Composable
fun SingleAddressItem(address: Address, viewModel: AddressViewModel) {
    Card(
        backgroundColor = Color.White,
        border = BorderStroke(width = 1.dp,
            color = Color.LightGray
        ),
        shape = Shapes.small,
        elevation = 0.dp
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AddressItem("Country", address.country)
            AddressItem("State", address.state)
            AddressItem("City", address.city)
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


@Preview(showBackground = true)
@Composable
fun AddressesScreenPreview() {
    AddressesScreen(navController = rememberNavController())
}