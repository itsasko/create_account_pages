package com.example.create_account

import android.app.Application

class MyApplication : Application() {
    val credentialsManager: CredentialsManager by lazy {
        CredentialsManager()
    }
}