package com.shahriyardx.doto.screens.todo.todo_list

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeableTodoWithAction(
    modifier: Modifier,
    isRevealed: Boolean,
    actions: @Composable RowScope.() -> Unit,
    onDelete: () -> Unit,
    onExpanded: (() -> Unit)? = null,
    onCollapsed: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    var contextMenuWidth by remember {
        mutableFloatStateOf(0f)
    }
    var containerWidth by remember {
        mutableFloatStateOf(0f)
    }

    val offset = remember {
        Animatable(0f)
    }

    var removed by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(isRevealed, contextMenuWidth) {
        if (isRevealed) {
            offset.animateTo(contextMenuWidth)
        } else {
            offset.animateTo(0f)
        }
    }

    AnimatedVisibility(visible = !removed) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .onSizeChanged {
                    containerWidth = it.width.toFloat()
                }
        ) {
            Row {
                Row(
                    modifier = Modifier
                        .onSizeChanged {
                            contextMenuWidth = it.width.toFloat()
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    actions()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(1f)
                        .background(Color.Yellow),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                }
            }


            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        IntOffset(offset.value.roundToInt(), 0)
                    }
                    .pointerInput(true) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                scope.launch {
                                    var newOffset = offset.value + dragAmount
                                    if (newOffset > 0) {
                                        newOffset = newOffset.coerceIn(0f, contextMenuWidth)
                                    } else {
                                        newOffset = newOffset.coerceIn(-containerWidth + contextMenuWidth, 0f)
                                    }
                                    offset.snapTo(newOffset)
                                }
                            },
                            onDragEnd = {
                                when {
                                    offset.value >= contextMenuWidth / 2f -> {
                                        scope.launch {
                                            offset.animateTo(contextMenuWidth)
                                            onExpanded?.invoke()
                                        }
                                    }

                                    offset.value <= -containerWidth / 2 -> {
                                        removed = true
                                        onDelete()
                                    }

                                    else -> {
                                        scope.launch {
                                            offset.animateTo(0f)
                                            onCollapsed?.invoke()
                                        }
                                    }
                                }
                            }
                        )
                    }
            ) {
                content()
            }
        }
    }

}