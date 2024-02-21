package com.pragmatical.c2c_learn.models
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post(var content:String? = "",
                val createdBy:String? = "",
                val createdDate: Long? = 0,
                val imageUrl:String="",
                )
