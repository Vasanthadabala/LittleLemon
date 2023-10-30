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
}