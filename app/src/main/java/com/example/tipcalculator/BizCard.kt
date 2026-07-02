package com.example.tipcalculator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun bizCard() {
    var cardShown by remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .clip(
                    shape = RoundedCornerShape(
                        50.dp
                    )
                ),
            elevation = cardElevation(
                13.dp
            ),
            colors = CardDefaults.cardColors(
                Color(
                    0xFFDAE3EC
                )
            ),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageSection(
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp)
                )
                Divider(
                    modifier = Modifier
                        .height(2.dp)
                )
                UserInfo()
                Button(
                    onClick = { cardShown = !cardShown },
                ) {
                    Text(text = "Portfolio")
                }
            }
            Column() {
                if (cardShown) {
                    Card(
                        colors = CardDefaults.cardColors(
                            Color(
                                0xFF123456
                            )
                        ),
                        elevation = cardElevation(
                            10.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .height(400.dp)
                    ) {

                        CardRow(
                            listOf(
                                "Project 1",
                                "Project 2 ",
                                "Project 3 ",
                                "Project 4",
                                "Project 5"
                            )
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun UserInfo() {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = "Mohit Jangra",
            fontSize = 30.sp
        )
        Text(text = "Android developer")
        Text(text = "@Google")
    }
}

@Composable
fun ImageSection(modifier: Modifier = Modifier) {
    Surface(
        color = Color.White,
        border = BorderStroke(
            width = 1.dp,
            color = Color.Black
        ),
        tonalElevation = 3.dp,
        shape = CircleShape,
        modifier = modifier
            .padding(5.dp)

    ) {
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = "Person Icon",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun CardRow(data: List<String>) {
    LazyColumn() {
        items(data) { item ->
            Card(
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ) {
                Row() {
                    Column(

                        modifier = Modifier
                            .background(color = Color.Gray)
                            .padding(5.dp)
                    ) {
                        ImageSection(
                            modifier = Modifier
                                .height(80.dp)
                                .width(80.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(5.dp),
                    ) {
                        Text(text = item)
                        Text(text = "A Great project indeed")
                    }

                }
            }
        }
    }
}