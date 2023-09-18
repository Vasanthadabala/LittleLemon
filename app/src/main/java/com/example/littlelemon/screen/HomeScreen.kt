@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.littlelemon.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.navigation.BottomBar
import com.example.littlelemon.data.MenuItemEntity
import com.example.littlelemon.data.MenuViewModel
import com.example.littlelemon.R
import com.example.littlelemon.navigation.TopAppBar
import com.example.littlelemon.data.Categories

@ExperimentalGlideComposeApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController:NavHostController)
{
    Scaffold(
        bottomBar = { BottomBar(navController = navController ) },
        topBar = { TopAppBar(navController = navController) }
    ) {
        Column(Modifier.padding(top = 50.dp)) {
            UpperScreen()
            LowerScreen()
        }
    }
}

@Composable
fun UpperScreen() {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = Color(0XFF41544E))
            .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Little lemon",
                fontSize = 34.sp,
                fontWeight = FontWeight.W800,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(8f, 8f),
                        blurRadius = 5f
                    )
                ),
                fontFamily = FontFamily.Serif,
                color = Color.Yellow,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 2.dp)
            )
            Text(
                text = "Chicago",
                fontSize = 25.sp,
                fontWeight = FontWeight.W600,
                fontFamily = FontFamily.Serif,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp, top = 2.dp, bottom = 5.dp)
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(
                    text = stringResource(id = R.string.description),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth(0.6F)
                        .padding(10.dp, end = 12.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.heroimage),
                    contentDescription = "Restaurant Image", contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .padding(bottom = 10.dp, end = 15.dp)
                        .size(135.dp)
                        .clip(RoundedCornerShape(15.dp))
                )
            }
            Button(
                onClick = { Toast.makeText(context, "Order Received", Toast.LENGTH_SHORT).show() },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.padding(10.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "Reservation",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(5.dp)
                )
            }

        }
    }
@ExperimentalGlideComposeApi
@Composable
fun LowerScreen() {
    val viewModel: MenuViewModel = viewModel()
    val menuItemsDatabase = viewModel.getAllDatabaseMenuItems().observeAsState(emptyList()).value
    val categories = Categories

    var selectedCategory by remember { mutableStateOf(categories.first()) }

    LaunchedEffect(Unit){
        viewModel.fetchMenuDataIfNeeded()
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(.9f)
            .background(Color.White)
    ) {
        Text(
            text = "Order Delivery",
            fontWeight = FontWeight.W600,
            fontSize = 30.sp,
            modifier = Modifier.padding(5.dp)
        )
        LazyRow {
            items(categories) { category ->
                MenuCategory(category = category ,isSelected = selectedCategory == category, onCategorySelected = {selectedCategory = category})
            }
        }

        Divider(modifier = Modifier.padding(8.dp), color = Color.Gray, thickness = 1.dp)
        LazyColumn{
            items(menuItemsDatabase.filter { it.category == selectedCategory }) { item ->
                MenuDish(item)
            }
        }
    }
}
@Composable
fun MenuCategory(category: String,isSelected:Boolean,onCategorySelected:()-> Unit) {
    Button(
        onClick = { onCategorySelected() },
        colors = ButtonDefaults.buttonColors(if (isSelected) Color.DarkGray else Color.Gray),
        shape = RoundedCornerShape(48),
        modifier = Modifier.padding(5.dp)
    ) {
        Text(text = category)
    }
}
@ExperimentalGlideComposeApi
@Composable
fun MenuDish(dish: MenuItemEntity)
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
                    text = dish.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W800,
                    modifier = Modifier.padding(5.dp))
                Text(
                    text = dish.description,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(.75f)
                )
                Text(text = "$ ${dish.price}",
                    fontWeight = FontWeight.W800,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(5.dp))
            }
            GlideImage(model = dish.image, contentDescription = "Image" ,Modifier.size(100.dp) )
        }
    }
}
