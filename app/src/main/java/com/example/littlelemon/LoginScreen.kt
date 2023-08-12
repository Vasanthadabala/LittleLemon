@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen()
{
    var firstname by remember { mutableStateOf(TextFieldValue("")) }
    var lastname by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }

    Column(Modifier.padding(0.dp)) {
        Image(painter = painterResource(id = R.drawable.logo ),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp)
                .padding(vertical = 20.dp, horizontal = 25.dp)
                .align(Alignment.CenterHorizontally))
        Text(text = "Let's get to know you",
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            textAlign= TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495E57))
                .padding(40.dp))
        Text(text = "Personal information", fontSize = 20.sp, fontWeight = FontWeight.W500,
            modifier=Modifier.padding(20.dp)
        )
        OutlinedTextField(value = firstname, onValueChange ={newText->firstname=newText},
            label = { Text(text = "Firstname")},
            placeholder = { Text(text = "Firstname")},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 14.dp),
            shape = RoundedCornerShape(16)
        )
        OutlinedTextField(value = lastname , onValueChange ={newText->lastname=newText},
            label = { Text(text = "Lastname")},
            placeholder = { Text(text = "Lastname")},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 14.dp),
            shape = RoundedCornerShape(16)
        )
        OutlinedTextField(value = email, onValueChange = {newText->email=newText},
            placeholder = { Text(text = "Email")},
            label = { Text(text = "Email")},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 16.dp),
            shape = RoundedCornerShape(16)
        )
        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 80.dp),
            shape = RoundedCornerShape(18),
            colors = ButtonDefaults.buttonColors(Color.Yellow)
        ) {
            Text(text = "Register", textAlign = TextAlign.Center, fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(2.dp))
        }
    }
}