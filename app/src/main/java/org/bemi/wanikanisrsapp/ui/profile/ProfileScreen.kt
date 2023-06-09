package org.bemi.wanikanisrsapp.ui.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    Surface(modifier = Modifier.semantics { contentDescription = "Profile Screen" }) {
        ProfileDataScreen(profileViewModel)
    }
}

@Composable
fun ProfileDataScreen(viewModel: ProfileViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            DataSection(
                title = "User Info",
                viewModel.getUserProfile(uiState.userData.profile)
            )
        }
        item { Divider() }
        item {
            DataSection(
                title = "Subscription",
                viewModel.getUserSubscription(uiState.userData.subscription)
            )
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
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(8.dp, 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(style = Typography.titleLarge, text = title, modifier = Modifier.padding(8.dp))
            }

            userInfo.forEach { entry ->

                run {
                    Row(
                        modifier = Modifier
                            .padding(8.dp, 4.dp)
                            .fillMaxWidth()
                            .animateContentSize(
                            ), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.weight(0.5f)
                        ) {

                            Text(
                                modifier = Modifier.padding(4.dp), text = entry.key + ":"
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.weight(0.5f)
                        ) {

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
