package com.example.littlelemon.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.data.MenuItemEntity
import com.example.littlelemon.data.MenuViewModel
import com.example.littlelemon.navigation.MenuItemDetails

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun SearchScreen(navController:NavHostController){

    var searchText by remember { mutableStateOf(TextFieldValue(""))}
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val searchViewModel: MenuViewModel = viewModel()
    val menuItemsDatabase by searchViewModel.getAllDatabaseMenuItems().observeAsState(emptyList())

    LaunchedEffect(Unit) {
        searchViewModel.fetchMenuDataIfNeeded()
        keyboardController?.show()
        focusManager.clearFocus()
        focusRequester.requestFocus()
    }

    Column {
        Row {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(top = 32.dp, start = 10.dp, end = 8.dp)
                    .size(30.dp)
                    .clickable { navController.navigateUp() }
            )
            OutlinedTextField(
                singleLine = true,
                value = searchText,
                onValueChange = { newText -> searchText = newText },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
                placeholder = { Text(text = "Enter the search phrase") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                shape = RoundedCornerShape(30),
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .padding(vertical = 20.dp, horizontal = 12.dp)
                    .size(5.dp, 52.dp)
                    .focusRequester(focusRequester),
                textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W400)
            )
            Text(
                text = "Cancel",
                fontWeight = FontWeight.W700,
                fontSize = 20.sp,
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 35.dp, horizontal = 5.dp)
                    .clickable {
                        searchText = TextFieldValue("")
                        keyboardController?.hide()

                    }
            )
        }

        val filteredMenuItems = menuItemsDatabase.filter { item ->
            item.title.contains(searchText.text, ignoreCase = true)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 5.dp, bottom = 5.dp)
        ) {
            if (searchText.text.isNotEmpty()) {
                items(filteredMenuItems) { item ->
                    SearchMenuDish(item,navController)
                }
            }
        }
    }
}

@ExperimentalGlideComposeApi
@Composable
fun SearchMenuDish(dish: MenuItemEntity,navController: NavHostController)
{
    Card(
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .clickable{
                navController.navigate(MenuItemDetails.route+"/${dish.id}")
            }
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
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
                    color = Color.DarkGray,
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