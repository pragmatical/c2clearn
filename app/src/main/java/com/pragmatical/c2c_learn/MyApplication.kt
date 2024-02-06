package com.pragmatical.c2c_learn

import android.app.Application

class MyApplication:Application() {
    companion object{
        @JvmField
        var loggedInUserId = ""
        @JvmField
        var loggedInUserEmail = ""
    }

}