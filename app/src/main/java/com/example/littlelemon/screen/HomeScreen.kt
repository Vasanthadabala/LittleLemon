@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.littlelemon.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.R
import com.example.littlelemon.data.Categories
import com.example.littlelemon.data.MenuItemEntity
import com.example.littlelemon.data.MenuViewModel
import com.example.littlelemon.navigation.TopAppBar

@ExperimentalComposeUiApi
@ExperimentalGlideComposeApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController:NavHostController)
{
    Scaffold(
        topBar = { TopAppBar(navController = navController) }
    ) {
        Column(Modifier.padding(top = 70.dp)) {
            Screen()
        }
    }
}
@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun Screen() {
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewModel: MenuViewModel = viewModel()
    val menuItemsDatabase = viewModel.getAllDatabaseMenuItems().observeAsState(emptyList()).value
    val categories = Categories

    var selectedCategory by remember { mutableStateOf("") }

    LaunchedEffect(Unit){
        viewModel.fetchMenuDataIfNeeded()
    }

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
            fontSize = 40.sp,
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
        OutlinedTextField(
            singleLine = true,
            value = searchText, onValueChange = { newText -> searchText = newText },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
            placeholder = { Text(text = "Enter the search phrase")},
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()}),
            shape = RoundedCornerShape(20),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 12.dp)
                .background(Color.White, shape = RoundedCornerShape(20))
        )
    }

    val filteredMenuItems = menuItemsDatabase.filter { item ->
        (selectedCategory.isEmpty() || item.category == selectedCategory) &&
                (searchText.isEmpty() || item.title.contains(searchText, ignoreCase = true))
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Text(
            text = "Order For Delivery!",
            fontWeight = FontWeight.W800,
            fontSize = 28.sp,
            modifier = Modifier.padding(top = 16.dp, start = 8.dp, bottom = 10.dp)
        )
        LazyRow {
            items(categories) { category ->
                MenuCategory(category = category ,isSelected = selectedCategory == category, onCategorySelected = {selectedCategory = category})
            }
        }

        Divider(modifier = Modifier.padding(vertical = 18.dp, horizontal = 2.dp), color = Color.Gray, thickness = 1.dp)
        LazyColumn{
            items(filteredMenuItems) { item ->
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
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10))
                    .background(Color.Transparent) // Ensures the rounded corners are visible
                    .align(Alignment.CenterVertically)
            ) {
                GlideImage(
                    model = dish.image,
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
