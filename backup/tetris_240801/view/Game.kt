package com.ohaddinur.tetris.view

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import com.ohaddinur.tetris.data.CellType
import kotlin.math.roundToInt

import com.ohaddinur.tetris.model.GameViewModel
import com.ohaddinur.tetris.ui.theme.Digital


@Composable
fun Game() {
    val displayMetrics = remember {Resources.getSystem().displayMetrics}
    val scale = remember {displayMetrics.density}
    val cellSize: Int = remember {((displayMetrics.widthPixels / scale - 20) / 10.0 ).roundToInt()}
    val rows: Int = remember {((displayMetrics.heightPixels / scale - 160.0) / cellSize).roundToInt()}
    val viewModel = remember {GameViewModel(rows, 10)}
    val gameUiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp)
    ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = padZero(gameUiState.score),
                    color = Color.Blue,
                    fontSize = 30.sp,
                    style = TextStyle(
                            fontFamily = Digital
                            )
                )
                if (gameUiState.gameOver) {
                    Text(
                        text = "Game Over",
                        color = Color.Red,
                        fontSize = 30.sp,
                        style = TextStyle(
                            fontFamily = Digital
                        )
                    )
                }
                IconButton(
                    onClick = {
                        viewModel.resetGame()
                    }
                ) {
                    Box (Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Blue.copy(alpha = 0.2f))){
                        Box(Modifier.align(Alignment.Center)) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                }
            }

        BoardView(
            cellSize = cellSize,
            cells = gameUiState.boardCells,
            rowsToRemove = gameUiState.rowsToRemove,
            onTap = {position -> viewModel.rotateIfInShape(position) },
            onDrag = { position, offset -> viewModel.moveIfInShape(position, offset) }
        )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.moveShape(-1, 0) }
                ) {
                    Box (Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Blue.copy(alpha = 0.2f))){
                        Box (Modifier.align(Alignment.Center)) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                }
                IconButton(
                    onClick = { viewModel.rotateShape() }
                ) {
                    Box (Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Blue.copy(alpha = 0.2f))){
                        Box (Modifier.align(Alignment.Center)) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                }
                IconButton(
                    onClick = { viewModel.moveShape(1, 0) }
                ) {
                    Box (Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Blue.copy(alpha = 0.2f))){
                        Box (Modifier.align(Alignment.Center)) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }

    }

fun padZero(score: Int): String {
    return score.toString().padStart(6, '0')
}