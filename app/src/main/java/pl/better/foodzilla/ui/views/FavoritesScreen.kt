package pl.better.foodzilla.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.login.Login
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph

@OptIn(ExperimentalFoundationApi::class)
@BottomBarNavGraph
@Destination
@Composable
fun FavoritesScreen(
    navigator: DestinationsNavigator,
    user: Login?
) {
    val tabRowItems = listOf(
        "Favorite recipes",
        "Favorite searches"
    )
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            backgroundColor = Color.White,
            contentColor = Color.Black,
            selectedTabIndex = pagerState.currentPage,
        ) {
            tabRowItems.forEachIndexed { index, item ->
                Tab(
                    text = { Text(item) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }
        HorizontalPager(
            verticalAlignment = Alignment.CenterVertically,
            pageCount = tabRowItems.size,
            state = pagerState
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = tabRowItems[it])
            }
        }
    }
}