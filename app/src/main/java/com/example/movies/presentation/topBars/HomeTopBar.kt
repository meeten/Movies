package com.example.movies.presentation.topBars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.movies.R
import com.example.movies.ui.theme.blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onMoreVertClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    var isShowMoreMenu by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
            actions = {
                IconButton(onClick = {
                    isShowMoreMenu = true
                }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }

                DropdownMenu(
                    expanded = isShowMoreMenu,
                    onDismissRequest = { isShowMoreMenu = false },
                    offset = DpOffset(x = 0.dp, y = (-45).dp),
                    containerColor = Color.White,
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Favorite movies", fontSize = 20.sp) },
                        onClick = { onMoreVertClick() },
                        colors = MenuDefaults.itemColors(textColor = Color.Black),
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = blue,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )
    }) {
        Column(modifier = Modifier.padding(it)) {
            content()
        }
    }
}