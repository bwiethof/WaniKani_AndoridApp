package org.bemi.wanikanisrsapp.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.bemi.wanikanisrsapp.data.TokenStore


@Composable
fun ProfileScreen() {
    var tokenExists by rememberSaveable {
        mutableStateOf(TokenStore.tokenExists())
    }
    Surface(modifier = Modifier.semantics { contentDescription = "Profile Screen" }) {
        if (tokenExists) {
            ProfileDataScreen()
        } else {
            EnterTokenScreen(onContinueClicked = { tokenExists = true })
        }
    }
}

@Composable
fun ProfileDataScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(5) {
            item {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Hello, your Token is: ${TokenStore.token!!}"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterTokenScreen(onContinueClicked: () -> Unit) {
    var text by rememberSaveable { mutableStateOf(TokenStore.token) }
    Surface() {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
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
                        value = (if (text.isNullOrEmpty()) "" else text)!!,
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
                        if (TokenStore.isTokenValid(text)) TokenStore.token = text
                        onContinueClicked()
                    }, enabled = TokenStore.isTokenValid(text)

                ) {
                    Text("Submit Token")
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 1280)
@Composable
fun ProfilePreview() {
    Surface {
        ProfileScreen()
    }
}