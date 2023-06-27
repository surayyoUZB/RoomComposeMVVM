package com.yoo.roomcomposemvvm.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yoo.roomcomposemvvm.R

@Composable
fun Lottie() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty))
        val progress by animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            composition = composition,
            progress = progress)
    }
    
}