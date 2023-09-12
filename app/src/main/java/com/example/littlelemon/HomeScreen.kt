@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.littlelemon

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.navigation.NavHostController
import com.example.littlelemon.data.Categories

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController:NavHostController,database:AppDataBase)
{
    val menuItemsDatabase by database.menuDao().getAllMenuItems().observeAsState(emptyList())
    Scaffold(
        bottomBar = { BottomBar(navController = navController )},
        topBar = { TopAppBar(navController = navController)}
    ) {
        Column(Modifier.padding(top = 50.dp)) {
            UpperScreen()
            LowerScreen(menuItemsDatabase)
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
@Composable
fun LowerScreen(menuItemsDatabase:List<MenuItemEntity>) {
    Column(
        Modifier.background(Color.White)
    ) {
        Text(
            text = "Order Delivery",
            fontWeight = FontWeight.W600,
            fontSize = 30.sp,
            modifier = Modifier.padding(5.dp)
        )
        LazyRow {
            items(Categories) { item ->
                MenuCategory(item)
            }
        }
        Divider(modifier = Modifier.padding(8.dp), color = Color.Gray, thickness = 1.dp)
        LazyColumn{
            items(menuItemsDatabase) { item ->
                MenuDish(item)
            }
        }
    }
}
@Composable
fun MenuCategory(category: String) {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(Color.Gray),
        shape = RoundedCornerShape(40),
        modifier = Modifier.padding(5.dp)
    ) {
        Text(text = category)
    }
}
@Composable
fun MenuDish(dish:MenuItemEntity)
{
    Card {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = dish.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
                Text(
                    text = dish.description,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth(.75f)
                )
                Text(text = "$ ${dish.price}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = Color.Black)
            }
        }
    }
}