package com.solie.mansworld.util

sealed class AuthenticationState {
    class Authenticated(val userId : String) : AuthenticationState()
    object Unauthenticated : AuthenticationState()
    object AlreadyAuthenticated : AuthenticationState()
}