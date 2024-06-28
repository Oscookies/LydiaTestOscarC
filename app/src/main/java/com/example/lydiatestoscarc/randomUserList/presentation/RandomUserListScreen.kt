@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.lydiatestoscarc.randomUserList.presentation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.lydiatestoscarc.R
import com.example.lydiatestoscarc.randomUserList.domain.RandomUser

@Composable
fun SharedTransitionScope.RandomUserListScreen(
    randomUserViewModel: RandomUserViewModel= hiltViewModel(),
    onNavigateToDetails: (RandomUser) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {

    val uiState by randomUserViewModel.uiState.collectAsState()
    val randomUserListList by randomUserViewModel.randomUserList.collectAsStateWithLifecycle()

    RandomUserListContent(
        uiState,
        randomUserListList,
        onNavigateToDetails,
        randomUserViewModel::getNextPage,
        animatedVisibilityScope,
    )

}

@Composable
fun SharedTransitionScope.RandomUserListContent(
    uiState: RandomUserListUiState,
    randomUserList: List<RandomUser>,
    onNavigateToDetails: (RandomUser) -> Unit,
    getNextPage: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val threadHold = 8
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(6.dp),
        ) {
            itemsIndexed(
                items = randomUserList, key = { _, user -> user.email }
            ) { index, randomUser ->
                if ((index + threadHold) >= randomUserList.size && uiState != RandomUserListUiState.Loading) {
                    getNextPage()
                }

                RandomUserCard(
                    randomUser = randomUser,
                    onNavigateToDetails = onNavigateToDetails,
                    animatedVisibilityScope
                )
            }
        }

        when(uiState) {
            RandomUserListUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = colorResource(id = R.color.purple_200),
                )
            }
            RandomUserListUiState.Idle -> { }
            is RandomUserListUiState.Error -> {
                if(uiState.message!!.contains("Unable to resolve host") && randomUserList.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_internet),
                            color = colorResource(id = R.color.black)
                        )
                        Text(
                            text = stringResource(id = R.string.try_again),
                            color = colorResource(id = R.color.black)
                        )
                    }
                }
            }
        }
    }

}


@Composable
private fun SharedTransitionScope.RandomUserCard(
    randomUser: RandomUser,
    onNavigateToDetails: (RandomUser) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
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
                .align(Alignment.CenterHorizontally)
                .size(128.dp)
                .padding(8.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = "image/${randomUser.picture.medium}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }
                )
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