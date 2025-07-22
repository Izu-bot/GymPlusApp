package com.example.gym.screens.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gym.R
import com.example.gym.ui.theme.GymTheme

@Composable
fun TimerScreen(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 110.dp))
        Icon(
            painter = painterResource(id = R.drawable.exercise_150dp),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "Icon",
        )
        Spacer(modifier = Modifier.padding(top = 15.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(top = 60.dp))
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Text(
            text = stringResource(id = R.string.bem_vindo),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W500
        )
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        Text(
            text = "${stringResource(id = R.string.preparando_ambiente)}...",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)) {
                GetAppVersion()
            }
        }
    }
}

@Preview( showSystemUi = true, showBackground = true)
@Composable
private fun PreviewTimerScreen() {
    GymTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TimerScreen()
        }
    }
}