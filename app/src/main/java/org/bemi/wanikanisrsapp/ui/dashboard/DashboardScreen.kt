package org.bemi.wanikanisrsapp.ui.dashboard

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import org.bemi.wanikanisrsapp.ui.components.PrintScreenName

@Composable
fun DashboardScreen() {
    LazyColumn {
        repeat(5) {
            item { PrintScreenName(name = "Dashboard") }
        }
    }
}
