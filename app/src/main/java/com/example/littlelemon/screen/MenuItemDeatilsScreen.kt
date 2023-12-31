package com.example.littlelemon.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.data.MenuViewModel
import com.example.littlelemon.navigation.TopBar

@ExperimentalGlideComposeApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun MenuItemDetilsScreen(navController:NavHostController, id: Int){
    Scaffold(
        topBar = { TopBar(name = "Item Details", navController = navController)}
    ){
        Column(Modifier.padding(top = 50.dp, bottom = 10.dp)) {
            MenuItemDetilsScreenComponent(id)
        }
    }
}

@ExperimentalGlideComposeApi
@Composable
fun MenuItemDetilsScreenComponent(id:Int ) {

    val context = LocalContext.current
    val menuItemDetailsviewModel: MenuViewModel = viewModel()
    val selectedDish by menuItemDetailsviewModel.getItemById(id).observeAsState()
    var counter by remember { mutableStateOf(0) }
    //retrives the data and observes the data to update if needed

    LaunchedEffect(Unit) {
        menuItemDetailsviewModel.fetchMenuDataIfNeeded()
    }


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Card(
            elevation = CardDefaults.cardElevation(1.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column{
                Box(
                    modifier = Modifier
                        .size(400.dp, 300.dp)
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
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                )
                Text(
                    text = selectedDish?.description ?: "Description",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                )
                Text(
                    text = "$ ${selectedDish?.price ?: "price"}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                )
            }
        }
        Row(
            modifier = Modifier.padding(top = 10.dp)
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

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
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