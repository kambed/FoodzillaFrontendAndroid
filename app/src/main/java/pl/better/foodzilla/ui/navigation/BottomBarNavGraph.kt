package pl.better.foodzilla.ui.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@NavGraph
annotation class BottomBarNavGraph(
    val start: Boolean = false
)