package com.example.littlelemon

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen()
{
    Column {
        UpperScreen()
        LowerScreen()
    }

}

@Composable
fun UpperScreen()
{
    val context = LocalContext.current
    val state by remember {
        mutableStateOf(0)
    }
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = Color(0XFF495E57))
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "LittleLemon",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Yellow,
            modifier = Modifier.padding(10.dp))
        Text(text = "Chicago",
            fontSize = 24.sp,
            fontWeight = FontWeight.W400,
            color = Color.White,
            modifier = Modifier.padding(10.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Start)
        {
            Text(text = stringResource(id = R.string.description),
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(0.6F)
                    .padding(10.dp, end = 20.dp))
            Image(painter = painterResource(id = R.drawable.heroimage),
                contentDescription = "Resturant Image",
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(12.dp)))
        }
        Button(onClick = { Toast.makeText(context,"Order Received", Toast.LENGTH_SHORT).show()},
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.padding(10.dp, bottom = 15.dp)) {
            Text(text = "Reservation",
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(5.dp))
        }

    }
}

@Composable
fun LowerScreen()
{
    Column(
        Modifier.background(Color.White)){
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            shape = RoundedCornerShape(12.dp),
        )
        {
            Text(text = "WeeklySpecial",
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold)
        }
        Row {

        }
    }
}
