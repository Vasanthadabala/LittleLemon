package com.example.littlelemon.navigation

interface Destinations
{
    val route:String
}
object Login: Destinations {
    override val route="Login"
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
object Search: Destinations {
    override val route="Search"
}