package com.example.littlelemon.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.littlelemon.data.CartItem
import com.example.littlelemon.data.CartViewModel
import com.example.littlelemon.navigation.BottomBar
import androidx.compose.foundation.lazy.items

@ExperimentalGlideComposeApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun CartScreen(navController: NavHostController) {
    val cartviewmodel: CartViewModel = viewModel()
    val cartItems = cartviewmodel.getCartItems()

    Scaffold(
        topBar = { CartTopBar(navController = navController) },
        bottomBar = { BottomBar(navController = navController) }
    ) {
        Column(Modifier.padding(top = 80.dp)) {

            LazyColumn {
                items(cartItems){cartItem ->
                    CartDishItems(cartItem)
                }
            }
        }
    }
}

@ExperimentalGlideComposeApi
@Composable
fun CartDishItems(cartDish:CartItem)
{
    Card(
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column{
                Text(
                    text = cartDish?.name ?: "name",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W800,
                    modifier = Modifier.padding(5.dp))
                Text(text = "$ ${cartDish?.price ?: "Price" }",
                    fontWeight = FontWeight.W900,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun CartTopBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(text = "Cart",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )},
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(26.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
        )
    )
}