package org.bemi.wanikanisrsapp.ui.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import org.bemi.wanikanisrsapp.ui.components.PrintScreenName

@Composable
fun HomeScreen() {
    LazyColumn {
        repeat(5) {
            item { PrintScreenName(name = "Home") }
        }
    }
}
