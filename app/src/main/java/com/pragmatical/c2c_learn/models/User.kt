package com.pragmatical.c2c_learn.models
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(var userName: String? = null,
                val email: String? = null,
                var fullName: String? = null,
    )