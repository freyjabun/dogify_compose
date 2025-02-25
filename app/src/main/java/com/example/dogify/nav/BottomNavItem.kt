package com.example.dogify.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Favorite

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.dogify.breedlist.view.BreedList
import com.example.dogify.breedpics.view.BreedPics
import com.example.dogify.favorites.view.Favorites

data class BottomNavigationItem<T : Any>(
    val title: String,
    val route: T,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

val navItems = listOf(
    BottomNavigationItem(
        title = "Breeds",
        route = BreedList,
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange,
        hasNews = false,
    ),
    BottomNavigationItem(
        title = "Favorites",
        route = Favorites,
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.Favorite,
        hasNews = false,
    )
)