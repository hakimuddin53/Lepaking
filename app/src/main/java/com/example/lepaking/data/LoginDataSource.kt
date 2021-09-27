package com.example.lepaking.data

import com.example.lepaking.data.model.LoggedInUser
import java.io.IOException
import java.lang.Exception

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        return try {

            if(username.trim() == "test" && password.trim() == "password")
                Result.Success(LoggedInUser(java.util.UUID.randomUUID().toString(), "test"))
            else
                Result.Error(Exception("Error logging in"))

        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}