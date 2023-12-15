package com.ch2ps090.equifit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps090.equifit.theme.EquifitTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.loading.value
            }
        }

        setContent {
            EquifitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EquifitApp()
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.onboarding_1),
//                            contentDescription = null,
//                            modifier = Modifier.fillMaxSize(),
//                        )
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(top = 300.dp),
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                        ) {
//                            Text(
//                                text = "ANALYSIS BODY POSTURE,",
//                                style = MaterialTheme.typography.titleLarge,
//                            )
//                            Text(
//                                text = "START YOUR JOURNEY",
//                                style = MaterialTheme.typography.titleLarge,
//                            )
//                        }
//                    }
                }
            }
        }
    }
}

class MyViewModel : ViewModel() {
    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _loading.value = false
        }
    }
}