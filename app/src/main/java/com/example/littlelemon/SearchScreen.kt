package com.example.littlelemon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@ExperimentalMaterial3Api
@Composable
fun SearchScreen(navController: NavHostController){
    var text by remember { mutableStateOf(TextFieldValue(""))}
    Column {
        Row {
            Icon(imageVector = Icons.TwoTone.ArrowBack,contentDescription ="",
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 5.dp)
                    .clickable { navController.navigate(Home.route) }
                    .size(30.dp)
            )
            OutlinedTextField(value = text, onValueChange ={ newText -> text=newText },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "")},
                placeholder ={ Text(text = "Search")},
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp))
            }
    }
}