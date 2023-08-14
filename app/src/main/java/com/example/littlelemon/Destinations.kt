package com.example.littlelemon

interface Destinations
{
    val route:String
}
object Login:Destinations{
    override val route="Login"
}
object Signup:Destinations{
    override val route="Signup"
}
object Home:Destinations{
    override val route="Home"
}