package com.example.a6_uaspam.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.a6_uaspam.R

@Composable
fun ViewAwal(
    onMulaiButton: () -> Unit
) {
    // Lottie animation state untuk background
    val compositionBackground by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.backgroundawal))
    val progressBackground by animateLottieCompositionAsState(
        composition = compositionBackground,
        iterations = LottieConstants.IterateForever, // Animasi berulang selamanya
        speed = 1.0f, // Kecepatan animasi
    )

    // Lottie animation untuk ikon yang bergerak
    val compositionIcon by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.hello)) // Gunakan animasi ikon
    val progressIcon by animateLottieCompositionAsState(
        composition = compositionIcon,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f,
    )

    Box(
        modifier = Modifier
            .fillMaxSize() // Pastikan Box mengambil seluruh layar
    ) {
        // Lottie animation sebagai background, pastikan mengisi seluruh layar
        LottieAnimation(
            composition = compositionBackground,
            progress = progressBackground,
            modifier = Modifier
                .fillMaxSize() // Memastikan background mengisi seluruh layar
                .graphicsLayer(
                    scaleX = 1.1f, // Mengatur skala horizontal
                    scaleY = 1.27f, // Mengatur skala vertikal
                    translationX = 0f,
                    translationY = 0f
                )
        )

        // Konten utama di atas background
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center), // Menyelaraskan isi secara vertikal dan horizontal
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ikon atau gambar yang bergerak menggunakan Lottie
            LottieAnimation(
                composition = compositionIcon,
                progress = progressIcon,
                modifier = Modifier
                    .size(200.dp) // Ukuran ikon
            )

            // Tombol "Get Started"
            Button(
                onClick = { onMulaiButton() },
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 14.sp

                )
            }
        }
    }
}
