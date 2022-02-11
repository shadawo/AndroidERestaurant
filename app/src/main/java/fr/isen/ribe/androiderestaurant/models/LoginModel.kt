package fr.isen.ribe.androiderestaurant.models

import com.google.gson.annotations.SerializedName

data class LoginModel(@SerializedName("id") val userId: String)