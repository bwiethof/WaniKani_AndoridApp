package org.bemi.wanikanisrsapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun PrintScreenName(name: String) {
    Text(modifier = Modifier.padding(10.dp), text = "Hello $name!")
}