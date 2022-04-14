package com.example.composesample.compositionLocal

import androidx.compose.runtime.compositionLocalOf
import com.example.composesample.viewmodel.UserViewModel

val LocalUserViewModel =
    compositionLocalOf<UserViewModel> { error("User View Model Context Not Found") }