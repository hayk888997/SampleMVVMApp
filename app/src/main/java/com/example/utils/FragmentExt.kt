package com.example.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

val Fragment.navController
    get() = findNavController()

fun Fragment.navigate(directions: NavDirections) = navController.navigate(directions)