package com.example.myntra.common.utils

import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*

@Composable
fun InfiniteScroll(lazyListScope: LazyListScope) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        lazyListScope
    }
}

@Composable
fun LazyListState.OnBottomReached(
    loadMore: () -> Unit,
) {
    val shouldLoadMore = remember {
        derivedStateOf {

            // get last visible item
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                // list is empty
                // return false here if loadMore should not be invoked if the list is empty
                return@derivedStateOf true

            // Check if last visible item is the last item in the list
            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}