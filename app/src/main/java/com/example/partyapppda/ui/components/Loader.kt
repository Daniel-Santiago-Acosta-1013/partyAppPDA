package com.example.partyapppda.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.partyapppda.R

@Composable
fun Loader(visible: Boolean) {
    if (!visible) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xE61C0A36)),
        contentAlignment = Alignment.Center
    ) {
        val compositionResult = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))
        val composition = compositionResult.value
        val animationState = animateLottieCompositionAsState(composition = composition)

        LottieAnimation(
            composition = composition,
            progress = animationState.progress,
            modifier = Modifier.size(120.dp)
        )
    }
}
