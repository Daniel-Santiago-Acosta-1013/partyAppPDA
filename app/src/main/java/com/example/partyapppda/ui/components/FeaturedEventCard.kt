package com.example.partyapppda.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.partyapppda.R

@Composable
fun FeaturedEventCard(
    eventImage: String? = null,
    eventTime: String,
    eventDate: String,
    eventTitle: String,
    eventSubtitle: String,
    onClick: () -> Unit = {}
) {
    val defaultImage = R.drawable.main_banner

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(15.dp))
            .background(Color(0xFF1c003e))
    ) {
        Image(
            painter = if (eventImage != null) {
                rememberAsyncImagePainter(eventImage)
            } else {
                painterResource(id = defaultImage)
            },
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop
        )

        // Overlay content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Gradient time badge
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFD90F96), Color(0xFF5503AE))
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                Text(
                    text = eventTime,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 5.dp)
                )
            }

            Column {
                Text(
                    text = eventDate,
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = eventTitle,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = eventSubtitle,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 5.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
