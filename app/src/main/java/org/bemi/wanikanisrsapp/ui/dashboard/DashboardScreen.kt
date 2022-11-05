package org.bemi.wanikanisrsapp.ui.dashboard

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import org.bemi.wanikanisrsapp.ui.components.PrintScreenName

@Composable
fun DashboardScreen() {
    Surface(modifier = Modifier.semantics { contentDescription = "Dashboard Screen" }) {
        LazyColumn {
            repeat(5) {
                item { PrintScreenName(name = "Dashboard") }
            }
        }
    }

}
