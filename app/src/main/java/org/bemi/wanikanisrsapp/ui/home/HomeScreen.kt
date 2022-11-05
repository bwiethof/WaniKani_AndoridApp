package org.bemi.wanikanisrsapp.ui.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import org.bemi.wanikanisrsapp.ui.components.PrintScreenName

@Composable
fun HomeScreen() {
    Surface(modifier = Modifier.semantics { contentDescription = "Home Screen" }) {
        LazyColumn {
            repeat(5) {
                item { PrintScreenName(name = "Home") }
            }
        }
    }
}
