package com.yuoyama12.bbsapp.firebaseerror

enum class AuthenticationError(val code: String) {
    UserNotFound("ERROR_USER_NOT_FOUND"),
    WrongPassword("ERROR_WRONG_PASSWORD"),
    TooManyRequests("ERROR_TOO_MANY_REQUESTS")
}