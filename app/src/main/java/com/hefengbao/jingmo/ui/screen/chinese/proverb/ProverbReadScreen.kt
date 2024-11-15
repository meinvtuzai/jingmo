/*
 *  This file is part of the 京墨（jingmo）APP.
 *
 * (c) 贺丰宝（hefengbao） <hefengbao@foxmail.com>
 *
 *  For the full copyright and license information, please view the LICENSE
 *  file that was distributed with this source code.
 */

package com.hefengbao.jingmo.ui.screen.chinese.proverb

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hefengbao.jingmo.data.database.entity.chinese.ProverbCollectionEntity
import com.hefengbao.jingmo.data.database.entity.chinese.ProverbEntity
import com.hefengbao.jingmo.ui.component.SimpleScaffold
import com.hefengbao.jingmo.ui.screen.chinese.proverb.components.ProverbPanel
import kotlin.math.abs

@Composable
fun ProverbReadRoute(
    viewModel: ProverbReadViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val proverb by viewModel.proverb.collectAsState()
    val proverbCollection by viewModel.proverbCollection.collectAsState()
    val nextId by viewModel.nextId.collectAsState()
    val prevId by viewModel.prevId.collectAsState()

    ProverbReadScreen(
        onBackClick = onBackClick,
        proverb = proverb,
        proverbCollection = proverbCollection,
        nextId = nextId,
        prevId = prevId,
        setCurrentId = viewModel::setCurrentId,
        setCollect = viewModel::collect,
        setUncollect = viewModel::uncollect
    )
}

@Composable
private fun ProverbReadScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    proverb: ProverbEntity?,
    proverbCollection: ProverbCollectionEntity?,
    nextId: Int?,
    prevId: Int?,
    setCurrentId: (Int) -> Unit,
    setCollect: (Int) -> Unit,
    setUncollect: (Int) -> Unit,
) {
    SimpleScaffold(
        onBackClick = onBackClick,
        title = "谚语",
        bottomBar = {
            proverb?.let {
                BottomAppBar(
                    actions = {
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { prevId?.let(setCurrentId) },
                                enabled = prevId != null
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                            if (proverbCollection != null) {
                                IconButton(onClick = { setUncollect(proverb.id) }) {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            } else {
                                IconButton(onClick = { setCollect(proverb.id) }) {
                                    Icon(
                                        imageVector = Icons.Default.BookmarkBorder,
                                        contentDescription = null
                                    )
                                }
                            }
                            IconButton(
                                onClick = { nextId?.let(setCurrentId) },
                                enabled = nextId != null
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
            }
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .draggable(
                    state = rememberDraggableState {},
                    orientation = Orientation.Horizontal,
                    onDragStarted = {},
                    onDragStopped = { velocity ->
                        if (velocity < 0 && abs(velocity) > 500f) {
                            nextId?.let(setCurrentId)
                        } else if (velocity > 0 && abs(velocity) > 500f) {
                            prevId?.let(setCurrentId)
                        }
                    }
                )
        ) {
            proverb?.let { entity -> ProverbPanel(entity = entity) }
        }
    }
}