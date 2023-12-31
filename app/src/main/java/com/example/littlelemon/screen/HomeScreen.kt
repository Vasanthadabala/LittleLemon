@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.littlelemon.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.example.littlelemon.R
import com.example.littlelemon.data.Categories
import com.example.littlelemon.data.Items
import com.example.littlelemon.data.MenuItemEntity
import com.example.littlelemon.data.MenuViewModel
import com.example.littlelemon.navigation.BottomBar
import com.example.littlelemon.navigation.HomeTopBar
import com.example.littlelemon.navigation.MenuItemDetails
import com.example.littlelemon.navigation.items
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalGlideComposeApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController:NavHostController)
{
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: ""
    selectedItemIndex = items.indexOfFirst { it.title == currentRoute }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(40.dp))
                Items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 20.sp, fontWeight = FontWeight.W600,
                                modifier = Modifier.padding(5.dp)
                            )
                        },
                        selected = selectedItemIndex == index,
                        onClick = {
                            navController.navigate(item.title)
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(5.dp)
                            ) {
                                Image(
                                    painterResource(
                                        id = if (index == selectedItemIndex) {
                                            item.selectedIcon
                                        } else {
                                            item.unselectedIcon
                                        }
                                    ),
                                    contentDescription = item.title
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .size(60.dp)
                    )
                }
            }
        },
        drawerState = drawerState
    ){
        Scaffold(
            topBar = { HomeTopBar(scope,drawerState,navController = navController)},
            bottomBar = { BottomBar(navController = navController)}
        ){
            Column(Modifier.padding(top = 50.dp, bottom = 80.dp)) {
                HomeScreenComponent(navController)
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalGlideComposeApi
@Composable
fun HomeScreenComponent(navController: NavHostController) {
    val context = LocalContext.current

    val viewModel: MenuViewModel = viewModel()
    val menuItemsDatabase = viewModel.getAllDatabaseMenuItems().observeAsState(emptyList()).value

    val categories = Categories

    var selectedCategory by remember { mutableStateOf(categories.first()) }


    LaunchedEffect(Unit) {
        viewModel.fetchMenuDataIfNeeded()
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(color = Color(0XFF41544E))
                .padding(5.dp),
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
                onClick = { Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show() },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.padding(top= 10.dp, bottom = 10.dp, start = 10.dp)
            ) {
                Text(
                    text = "Reservation",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(5.dp)
                )
            }

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp)
                .background(Color.White)
        ) {
            Text(
                text = "Order Delivery",
                fontWeight = FontWeight.W600,
                fontSize = 28.sp,
                modifier = Modifier.padding(8.dp )
            )
            LazyRow{
                items(categories) { category ->
                    MenuCategory(
                        category = category,
                        isSelected = selectedCategory == category,
                        onCategorySelected = { selectedCategory = category })
                }
            }

            val filteredMenuItems = if(selectedCategory == "Menu"){
                menuItemsDatabase
            }else{
                menuItemsDatabase.filter { item ->
                    item.category == selectedCategory
                }
            }

            Divider(modifier = Modifier.padding(5.dp), color = Color.Gray, thickness = 1.dp)

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ){
                items(filteredMenuItems) {item ->
                    MenuDish(item,navController)
                }
            }

            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}
@Composable
fun MenuCategory(category: String,isSelected:Boolean,onCategorySelected:()-> Unit) {
    Button(
        onClick = { onCategorySelected() },
        colors = ButtonDefaults.buttonColors(if (isSelected) Color.DarkGray else Color.LightGray),
        shape = RoundedCornerShape(48),
        modifier = Modifier.padding(5.dp)
    ) {
        Text(
            text = category,
            fontSize = 16.sp,
            color = (if (isSelected) Color.White else Color(0XFF41544E)),
            fontWeight = FontWeight.W900
            )
    }
}
@ExperimentalGlideComposeApi
@Composable
fun MenuDish(dish: MenuItemEntity,navController: NavHostController)
{
    Card(
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .clickable {
                navController.navigate(MenuItemDetails.route + "/${dish.id}")
            }
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
                    fontWeight = FontWeight.W700,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(.75f)
                )
                Text(text = "$ ${dish.price}",
                    fontWeight = FontWeight.W900,
                    fontSize = 16.sp,
                    color = Color(0XFF41544E),
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
