package com.masliaiev.feature.main.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masliaiev.core.theme.Pink80
import com.masliaiev.core.theme.Purple80
import com.masliaiev.core.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerBottomSheet(
    bottomSheetState: SheetState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Pink80),
        contentAlignment = Alignment.TopCenter
    ) {

        AnimatedVisibility(
            visible = bottomSheetState.currentValue != SheetValue.Expanded,
            enter = slideInVertically(),
            exit = fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Blue)
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Title",
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = "Kirill Masliaiev",
                        modifier = Modifier,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null
                )
            }
        }

        AnimatedVisibility(
            visible = bottomSheetState.currentValue == SheetValue.Expanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .padding(top = 60.dp)
                            .aspectRatio(1f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Blue)
                        )
                    }


                    Text(
                        text = "Title",
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = "Kirill Masliaiev",
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Card(
                    modifier = Modifier
                        .padding(top = 60.dp),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Purple80)
                            .navigationBarsPadding()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 32.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            PlayerButton(
                                icon = painterResource(id = RCore.drawable.ic_skip_back),
                                onClick = {

                                }
                            )
                            Spacer(modifier = Modifier.size(32.dp))
                            PlayerButton(
                                icon = painterResource(id = RCore.drawable.ic_play),
                                enableCircleShape = true,
                                onClick = {

                                }
                            )
                            Spacer(modifier = Modifier.size(32.dp))
                            PlayerButton(
                                icon = painterResource(id = RCore.drawable.ic_skip_forward),
                                onClick = {

                                }
                            )
                        }
                    }
                }
            }


        }


    }
}

@Composable
private fun PlayerButton(
    icon: Painter,
    enableCircleShape: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    IconButton(
        modifier = if (enableCircleShape) {
            Modifier
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
        } else {
            Modifier
        }
            .size(60.dp),
        enabled = enabled,
        onClick = { /*TODO*/ }
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            painter = icon,
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PlayerBottomSheetPreview() {
    PlayerBottomSheet(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            initialValue = SheetValue.Expanded
        )
    )
}