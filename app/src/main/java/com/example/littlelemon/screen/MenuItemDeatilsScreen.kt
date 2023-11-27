package com.example.littlelemon.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.data.CartItem
import com.example.littlelemon.data.CartViewModel
import com.example.littlelemon.data.MenuViewModel
import com.example.littlelemon.navigation.Cart

@ExperimentalGlideComposeApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun MenuItemDetilsScreen(navController:NavHostController, id: Int){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Item Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp)},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ){
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        }
    ){
        MenuItemDetilsScreenComponent(id,navController)
    }
}

@ExperimentalGlideComposeApi
@Composable
fun MenuItemDetilsScreenComponent(id:Int,navController: NavHostController ) {
    val cartViewModel : CartViewModel = viewModel()

    val menuItemDetailsviewModel: MenuViewModel = viewModel()
    val selectedDish by menuItemDetailsviewModel.getItemById(id).observeAsState()
    var counter by remember { mutableStateOf(0) }
    //retrives the data and observes the data to update if needed

    LaunchedEffect(Unit) {
        menuItemDetailsviewModel.fetchMenuDataIfNeeded()
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 100.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxSize()
    ) {
        Card(
            elevation = CardDefaults.cardElevation(1.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .size(400.dp, 400.dp)
                        .clip(RoundedCornerShape(0))
                        .background(Color.Transparent) // Ensures the rounded corners are visible

                ) {
                    GlideImage(
                        model = selectedDish?.image ?: "",
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = selectedDish?.title ?: "Title",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(start = 10.dp, top = 8.dp)
                )
                Text(
                    text = selectedDish?.description ?: "Description",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 10.dp,top = 8.dp)
                )
                Text(
                    text = "$ ${selectedDish?.price ?: "price"}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(start = 10.dp,top = 8.dp)
                )
            }
        }
        Row(
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(
                text = "-",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable { if (counter != 0) counter-- }
            )
            Text(
                text = counter.toString(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "+",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable { counter++ }
            )
        }
        Button(
            onClick = {
                val selectedItem = selectedDish?.let {
                    CartItem(
                        it.id, it.title, it.price.toDouble(), counter
                    )
                }
                if (selectedItem != null) {
                    cartViewModel.addItemToCart(selectedItem)
                }
                navController.navigate(Cart.route)

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            shape = RoundedCornerShape(32),
            colors = ButtonDefaults.buttonColors(Color.Yellow)
        ) {
            Text(
                text = "Add to cart",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }

}