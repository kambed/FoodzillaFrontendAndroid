package pl.better.foodzilla.ui.views.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.better.foodzilla.ui.components.TextFieldSearch

@Composable
fun FavouriteSearchesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextFieldSearch(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 11.dp
                )
                .shadow(2.dp),
            value = "",
            label = "Search favourite searches",
            icon = Icons.Default.SwapHoriz,
            textColor = MaterialTheme.colors.onBackground,
            onTextChanged = { /*TODO*/ },
            onSearch = { /*TODO*/ }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Saved searches", fontWeight = FontWeight.Bold)
        }
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 6.dp)
        ) {
            items(
                listOf(
                    "eggs, dog, 10 min, 0 cal",
                    "butter, sugar, vanilla, eggs, sweet",
                    "30 min, giga chad recipe",
                    "hehe",
                    "what to eat?",
                    "eggs, dog, 10 min, 0 cal",
                    "butter, sugar, vanilla, eggs, sweet",
                    "30 min, giga chad recipe",
                    "hehe",
                    "gasgasg",
                    "eggs, dog, 10 min, 0 cal",
                    "butter, sugar, vanilla, eggs, sweet",
                    "30 min, giga chad recipe",
                    "hehe",
                    "gasgasg"
                )
            ) { search ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 35.dp)
                        .padding(horizontal = 18.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(224, 224, 224))
                        .clickable {
                            //TODO: COPY TEXT TO SEARCH TEXT FIELD
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 12.dp),
                        text = search,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}