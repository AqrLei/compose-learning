package com.example.composeone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeone.ui.theme.ComposeOneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsStory()
        }
    }
}
@Composable
fun NewsStory() {
    val countState = remember{ mutableStateOf(0)}

    ComposeOneTheme() {
        Column(modifier = Modifier.fillMaxHeight()) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(16.dp)) {
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
                    style = typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "Davenport, California",
                    style = typography.body2
                )
                Text(
                    text = "December 2021",
                    style = typography.body2
                )
            }

            Counter(count = countState.value, updateCount = { countState.value = it} )
            Divider(modifier = Modifier.padding(top = 10.dp), color = Color.Transparent, thickness = 32.dp)

        }

    }
}


@Composable
fun Counter(count: Int, updateCount: (Int) ->Unit) {
    val isSelected  = remember { mutableStateOf(value = false) }
    val textColor = animateColorAsState(targetValue = if (isSelected.value) Color.Green else Color.White )

    Button(onClick = {
        isSelected.value = !isSelected.value
        updateCount(count+1)
    }) {
        Text(text = "I've been clicked ${count+1} times", color = textColor.value)
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsStory()
}