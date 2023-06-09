package org.bemi.wanikanisrsapp.ui.startUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EnterTokenScreen(onContinueClicked: () -> Unit, viewModel: TokenViewModel) {
    var text by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Surface {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Please enter your user token for WaniKani."
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = (if (text.isEmpty()) "" else text),
                        onValueChange = {
                            text = it
                        },
                        label = { Text("User Token") },
                        singleLine = true
                    )
                }
            }
            item {
                OutlinedButton(
                    modifier = Modifier.padding(vertical = 24.dp), onClick = {
                        if (viewModel.isTokenValid(text)) scope.launch {
                            println("Valid Token Submitted")
                            viewModel.updateToken(text)
                        }
                        onContinueClicked()
                    }, enabled = viewModel.isTokenValid(text)

                ) {
                    Text("Submit Token")
                }
            }
        }
    }
}
