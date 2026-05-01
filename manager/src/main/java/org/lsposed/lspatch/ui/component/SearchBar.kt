package org.lsposed.lspatch.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.lsposed.lspatch.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    title: @Composable () -> Unit,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onBackClick: () -> Unit,
    onConfirm: (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var onSearch by remember { mutableStateOf(false) }

    if (onSearch) LaunchedEffect(Unit) { focusRequester.requestFocus() }

    DisposableEffect(Unit) {
        onDispose { keyboardController?.hide() }
    }

    TopAppBar(
        title = {
            Box(Modifier.fillMaxWidth()) {
                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterStart),
                    visible = !onSearch,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) { title() }

                AnimatedVisibility(visible = onSearch, enter = fadeIn(), exit = fadeOut()) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                            .focusRequester(focusRequester)
                            .onFocusChanged { if (it.isFocused) onSearch = true },
                        value = searchText,
                        onValueChange = onSearchTextChange,
                        maxLines = 1,
                        singleLine = true,
                        textStyle = TextStyle(color = AppleText, fontSize = AppleDesign.BodySize.sp),
                        shape = RoundedCornerShape(AppleDesign.CornerM),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = AppleText,
                            unfocusedTextColor = AppleText,
                            focusedContainerColor = AppleSurface2,
                            unfocusedContainerColor = AppleSurface2,
                            focusedBorderColor = AppleAccent,
                            unfocusedBorderColor = AppleSeparator,
                            cursorColor = AppleAccent
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                onSearch = false
                                keyboardController?.hide()
                                onClearClick()
                            }) {
                                Icon(Icons.Filled.Close, null, tint = AppleText2)
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                            onConfirm?.invoke()
                        })
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Outlined.ArrowBack, null, tint = AppleText)
            }
        },
        actions = {
            AnimatedVisibility(visible = !onSearch) {
                IconButton(onClick = { onSearch = true }) {
                    Icon(Icons.Filled.Search, null, tint = AppleText)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = AppleBackground)
    )
}

@Preview
@Composable
private fun SearchAppBarPreview() {
    var searchText by remember { mutableStateOf("") }
    SearchAppBar(
        title = { Text("Search", color = AppleText, fontWeight = FontWeight.Bold) },
        searchText = searchText,
        onSearchTextChange = { searchText = it },
        onClearClick = { searchText = "" },
        onBackClick = {}
    )
}
