package com.example.composeone

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Page()
        }
    }
}

@Composable
fun Page() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Compose-Learning")
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Filled.Favorite, contentDescription =null )

                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        NewsStory(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )
        PhotographerCard()
        Divider(
            modifier = Modifier.padding(top = 10.dp),
            color = Color.Transparent,
            thickness = 16.dp
        )
        CounterWrapper()
    }

}

@Composable
fun NewsStory(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, state = rememberLazyListState()) {
        items(100) {
            Image(
                painter = painterResource(
                    id = R.drawable.header
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "A day wandering through the sandhills " +
                        "in Shark Fin Cove, and a few of the " +
                        "sights I saw",
                style = MaterialTheme.typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "Davenport, California",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "December 2021",
                style = MaterialTheme.typography.body2
            )
        }
    }
}


@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(
        modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
            .clickable { }) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2F)
        ) {
            // Image GOES HERE
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {

            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun CounterWrapper() {
    val countState = remember { mutableStateOf(0) }

    Counter(count = countState.value, updateCount = { countState.value = it })
    Divider(
        modifier = Modifier.padding(top = 10.dp),
        color = Color.Transparent,
        thickness = 32.dp
    )
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    val isSelected = remember { mutableStateOf(value = false) }
    val textColor =
        animateColorAsState(targetValue = if (isSelected.value) Color.Green else Color.White)

    Button(onClick = {
        isSelected.value = !isSelected.value
        updateCount(count + 1)
    }) {
        Text(text = "I've been clicked ${count + 1} times", color = textColor.value)
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Page()
}