package com.example.littlelemon.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.R

@Composable
fun BottomBar(navController: NavHostController) {
    var selectedItemIndex by rememberSaveable {mutableStateOf(0)}

    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = R.drawable.home,
            route = Home.route
        ),
        BottomNavigationItem(
            title = "Search",
            selectedIcon = R.drawable.search,
            route = Search.route
        ),
        BottomNavigationItem(
            title = "Settings",
            selectedIcon = R.drawable.settings,
            route = Cart.route
        ),
        BottomNavigationItem(
            title = "Cart",
            selectedIcon = R.drawable.cart,
            route = Cart.route
        ),
    )

    Card(
        elevation = CardDefaults.elevatedCardElevation(20.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        NavigationBar(
            tonalElevation = 5.dp,
            containerColor = Color.White
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                        navController.navigate(item.route){
                            popUpTo(item.route){
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold ,
                            color = Color(0xff0C2D48))
                    },
                    alwaysShowLabel = true,
                    icon = {
                        Box(
                            modifier = Modifier.size(28.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painterResource(id = item.selectedIcon),
                                contentDescription = item.title
                            )
                        }
                    }
                )
            }
        }
    }
}
data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val route:String
)