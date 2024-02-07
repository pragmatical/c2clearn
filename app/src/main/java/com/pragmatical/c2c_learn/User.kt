package com.pragmatical.c2c_learn
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(var userName: String? = null, val email: String? = null, var fullName: String? = null, var profilePic: String? = null)
