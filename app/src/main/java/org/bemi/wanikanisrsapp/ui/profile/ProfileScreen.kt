package org.bemi.wanikanisrsapp.ui.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.bemi.wanikanisrsapp.ui.theme.Typography


@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {
    var hasToken by rememberSaveable {
        mutableStateOf(profileViewModel.tokenExists())
    }
    Surface(modifier = Modifier.semantics { contentDescription = "Profile Screen" }) {
        if (hasToken) {
            ProfileDataScreen(profileViewModel)
        } else {
            EnterTokenScreen(onContinueClicked = { hasToken = true }, profileViewModel)
        }
    }
}

@Composable
fun ProfileDataScreen(viewModel: ProfileViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start
    ) {

        if (viewModel.isUserDataAvailable()) {

            item { DataSection(title = "User Info", viewModel.getUserProfile()) }
            item { Divider() }
            item { DataSection(title = "Subscription", viewModel.getUserSubscription()) }

        } else {
            item {
                Text(
                    modifier = Modifier.padding(8.dp), text = "Failed to get User data :("
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterTokenScreen(onContinueClicked: () -> Unit, viewModel: ProfileViewModel) {
    var text by rememberSaveable { mutableStateOf("") }
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
                        if (viewModel.isTokenValid(text)) viewModel.updateToken(text)
                        onContinueClicked()
                    }, enabled = viewModel.isTokenValid(text)

                ) {
                    Text("Submit Token")
                }
            }
        }
    }
}


@Composable
fun DataSection(title: String, userInfo: UserInfoItems) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
    ) {
        Column {
            Row(
                modifier = Modifier.padding(8.dp, 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(style = Typography.titleLarge, text = title, modifier = Modifier.padding(8.dp))
            }
            Row(
                modifier = Modifier
                    .padding(8.dp, 4.dp)
                    .fillMaxWidth()
                    .animateContentSize(
                    ), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    userInfo.forEach { entry ->
                        run {
                            Text(
                                modifier = Modifier.padding(4.dp), text = entry.key + ":"
                            )
                        }

                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    userInfo.forEach { entry ->
                        run {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = entry.value ?: "",
                                textAlign = TextAlign.End
                            )
                        }
                    }
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
