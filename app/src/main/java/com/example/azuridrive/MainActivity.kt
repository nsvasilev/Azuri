package com.example.azuridrive


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import kotlinx.coroutines.delay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableFloatStateOf

import com.example.azuridrive.ui.theme.AZURIDRIVETheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AZURIDRIVETheme {
                AZURIDriveApp()
            }
        }
    }
}
@Composable
fun AZURIDriveApp() {

    var showSplash by remember {
        mutableStateOf(true)
    }

    if (showSplash) {

        SplashScreen(
            onStart = {
                showSplash = false
            }
        )

    } else {

        MainMenu()
    }
}

@Composable
fun SplashScreen(
    onStart: () -> Unit
) {
    var showFlash by remember {
        mutableStateOf(false)
    }

    var showBackground by remember {
        mutableStateOf(false)
    }

    var showLogo by remember {
        mutableStateOf(false)
    }

    var showHint by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {

        // Небольшая пауза после запуска
        delay(300)

        // Включаем вспышку
        showFlash = true

        // Вспышка держится немного
        delay(250)

        // Выключаем вспышку
        showFlash = false

        // Появляется фотография автомобиля
        showBackground = true

        // Небольшая пауза
        delay(300)

        // Появляется логотип
        showLogo = true

        // Ещё немного ждём
        delay(1700)

        // Появляется подсказка
        showHint = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable {
                onStart()
            }
    ) {

        // ==========================================
        // ФОТОГРАФИЯ AVEO
        // ==========================================

        AnimatedVisibility(
            visible = showBackground,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(
                animationSpec = tween(1000)
            )
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.azuri_drive_background_16x9
                ),
                contentDescription = "AZURI Drive",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }


        // ==========================================
        // ЧЁРНОЙ СЛОЙ ПОВЕРХ ФОТОГРАФИИ
        // ==========================================

        // Пока фотография появляется, она остаётся немного
        // затемнённой за счёт полупрозрачного слоя.


        // ==========================================
        // ВСПЫШКА
        // ==========================================

        AnimatedVisibility(
            visible = showFlash,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(
                animationSpec = tween(100)
            ),
            exit = fadeOut(
                animationSpec = tween(400)
            )
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                )
            }
        }


        // ==========================================
        // ЛОГОТИП AZURI DRIVE
        // ==========================================

        AnimatedVisibility(
            visible = showLogo,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(
                animationSpec = tween(800)
            ) + scaleIn(
                initialScale = 0.85f,
                animationSpec = tween(800)
            )
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {

                Text(
                    text = "AZURI",
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "DRIVE",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 5.sp,
                    color = Color.White
                )
            }
        }


        // ==========================================
        // "КОСНИТЕСЬ ЭКРАНА"
        // ==========================================

        AnimatedVisibility(
            visible = showHint,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(
                animationSpec = tween(800)
            )
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.BottomCenter
            ) {

                Text(
                    text = "КОСНИТЕСЬ ЭКРАНА",
                    modifier = Modifier.padding(
                        bottom = 60.dp
                    ),
                    fontSize = 16.sp,
                    letterSpacing = 2.sp,
                    color = Color.White
                )
            }
        }
    }
}
@Composable
fun MainMenu() {

    var showCarView by remember { mutableStateOf(false) }

    if (showCarView) {
        CarViewScreen(
            modifier = Modifier.fillMaxSize()
        )
        return
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // ==========================================
        // ЛЕВОЕ МЕНЮ
        // ==========================================

        Column(
            modifier = Modifier
                .width(240.dp)
                .fillMaxHeight()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "AZURI",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "DRIVE",
                fontSize = 14.sp,
                letterSpacing = 4.sp,
                color = Color.White
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            MenuButton(
                title = "ВИД",
                onClick = {
                    showCarView = true
                }
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            MenuButton(
                title = "АВТОМОБИЛЬ"
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            MenuButton(
                title = "СТАТИСТИКА"
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            MenuButton(
                title = "НАСТРОЙКИ"
            )
        }

        // ==========================================
        // ПРАВАЯ ЧАСТЬ
        // ==========================================

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "AVEO",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
@Composable
fun MenuButton(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Card(
        modifier = modifier
            .height(70.dp)
            .clickable {
                onClick()
            }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun CarViewScreen(
    modifier: Modifier = Modifier
) {
    val carImages = listOf(
        R.drawable.f01_front,
        R.drawable.f02_front_left,
        R.drawable.f03_left,
        R.drawable.f04_rear_left,
        R.drawable.f05_rear,
        R.drawable.f06_rear_right,
        R.drawable.f07_right,
        R.drawable.f08_front_right
    )

    var currentImage by remember { mutableIntStateOf(0) }

    var totalDrag by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, dragAmount ->
                        totalDrag += dragAmount
                    },
                    onDragEnd = {
                        if (totalDrag > 80f) {
                            // Свайп вправо
                            currentImage =
                                (currentImage - 1) % carImages.size
                        }

                        if (totalDrag < -80f) {
                            // Свайп влево
                            currentImage =
                                (currentImage + 1 + carImages.size) % carImages.size
                        }

                        totalDrag = 0f
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(
                id = carImages[currentImage]
            ),
            contentDescription = "Вид автомобиля",
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun AZURIDriveScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "AZURI",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "DRIVE",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "87",
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "km/h",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            CarParameter(
                title = "RPM",
                value = "2450",
                modifier = Modifier.weight(1f)
            )

            CarParameter(
                title = "TEMP",
                value = "91 °C",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            CarParameter(
                title = "VOLT",
                value = "14.2 V",
                modifier = Modifier.weight(1f)
            )

            CarParameter(
                title = "FUEL",
                value = "63 %",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CarParameter(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AZURIDrivePreview() {
    AZURIDRIVETheme {
        AZURIDriveScreen()
    }
}
