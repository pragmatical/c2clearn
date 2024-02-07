package com.pragmatical.c2c_learn
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val username: String? = null, val email: String? = null,val fullName: String? = null, val profilePic: String? = null)
