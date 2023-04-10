package pl.better.foodzilla.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import pl.better.foodzilla.ui.navigation.TabItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout(
    tabRowItems: List<TabItem>
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        selectedTabIndex = pagerState.currentPage,
    ) {
        tabRowItems.forEachIndexed { index, item ->
            Tab(
                text = { Text(item.title) },
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
            tabRowItems[it].screen()
        }
    }
}