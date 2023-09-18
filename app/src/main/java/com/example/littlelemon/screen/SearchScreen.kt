package com.example.littlelemon.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.navigation.BottomBar

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController:NavHostController){
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        Screen()
    }
}
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun Screen(){
    var searchText by remember { mutableStateOf(TextFieldValue(""))}
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        Row {
            OutlinedTextField(
                singleLine = true,
                value = searchText, onValueChange = { newText -> searchText = newText },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
                placeholder = { Text(text = "Search") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()}),
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(.78f)
                    .padding(start = 10.dp, top = 20.dp, end = 5.dp, bottom = 10.dp)
            )
            Text(
                text = "Cancel",
                fontWeight = FontWeight.W700,
                fontSize = 18.sp,
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
    }
}