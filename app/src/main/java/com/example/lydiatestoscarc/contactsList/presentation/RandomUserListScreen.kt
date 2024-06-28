package com.example.lydiatestoscarc.contactsList.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.lydiatestoscarc.R
import com.example.lydiatestoscarc.contactsList.domain.Name
import com.example.lydiatestoscarc.contactsList.domain.Picture
import com.example.lydiatestoscarc.contactsList.domain.RandomUser

@Composable
fun RandomUserListScreen(
    randomUserViewModel: RandomUserViewModel= hiltViewModel(),
    onNavigateToDetails: (RandomUser) -> Unit
) {

    val uiState by randomUserViewModel.uiState.collectAsState()
    val randomUserListList by randomUserViewModel.randomUserList.collectAsStateWithLifecycle()

    RandomUserListContent(
        uiState,
        randomUserListList,
        onNavigateToDetails,
        randomUserViewModel::getNextPage
    )

}

@Composable
fun RandomUserListContent(
    uiState: RandomUserListUiState,
    randomUserListList: List<RandomUser>,
    onNavigateToDetails: (RandomUser) -> Unit,
    getNextPage: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val threadHold = 8
        Text(
            text = stringResource(id = R.string.random_user_list_title),
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(6.dp),
        ) {
            itemsIndexed(
                items = randomUserListList, key = { _, user -> user.email }
            ) { index, randomUser ->
                if ((index + threadHold) >= randomUserListList.size && uiState != RandomUserListUiState.Loading) {
                    getNextPage()
                }

                RandomUserCard(
                    randomUser = randomUser,
                    onNavigateToDetails = onNavigateToDetails
                )
            }
        }

        if (uiState == RandomUserListUiState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colorResource(id = R.color.purple_200),
            )
        }
    }

}

@Composable
private fun RandomUserCard(
    randomUser: RandomUser,
    onNavigateToDetails: (RandomUser) -> Unit,
) {

    Card(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .clickable {
                onNavigateToDetails(randomUser)
            },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Image(
            painter = rememberAsyncImagePainter(randomUser.picture.medium),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .padding(8.dp)
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(12.dp),
            text = "${randomUser.name.first} ${randomUser.name.first}",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun RandomUserListContentPreview() {

    RandomUserListContent(
        uiState = RandomUserListUiState.Idle,
        randomUserListList = listOf(
            RandomUser("Non Binary", Name("John", "Doe"), "a@a.com",  0, Picture("https://randomuser.me/api/portraits/men/88.jpg")),
        ),
        onNavigateToDetails = { },
        {}
    )

}