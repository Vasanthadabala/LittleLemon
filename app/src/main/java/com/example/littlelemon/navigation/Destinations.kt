package com.example.littlelemon.navigation

interface Destinations
{
    val route:String
}
object Signup: Destinations {
    override val route="Signup"
}
object Home: Destinations {
    override val route="Home"
}
object Profile: Destinations {
    override val route="Profile"
<<<<<<< HEAD
}
object Search: Destinations {
    override val route="Search"
}
object Cart: Destinations {
    override val route="Cart"
}
object Settings: Destinations {
    override val route="Settings"
}
object MenuItemDetails: Destinations {
    override val route="MenuItemDetails"
    const val dishID = "dishId"
=======
>>>>>>> 56d216785f4db1071ef8d8c11d968190a4c3ecd0
}