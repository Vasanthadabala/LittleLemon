package com.example.littlelemon.navigation

interface Destinations
{
    val route:String
}
object Signin: Destinations {
    override val route="Signin"
}
object Signup: Destinations {
    override val route="Signup"
}
object Home: Destinations {
    override val route="Home"
}
object Profile: Destinations {
    override val route="Profile"
}
object About: Destinations {
    override val route="About"
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
}