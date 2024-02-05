package com.thoughtctl.codingchallenge.imagebrowser.ui.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.thoughtctl.codingchallenge.imagebrowser.R

/**
 * Composable to show search bar in the top of the screen.
 * TODO: Implement debouncing to handle search query in efficient manner
 */
@Composable
fun SearchTextField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    query: String,
    onQueryChange: (String) -> Unit,
    onTextClear:() -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = query,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = stringResource(R.string.search_icon)
            )
        },
        trailingIcon = {
            if(query.isNotBlank()) {
                IconButton(onClick = onTextClear) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = stringResource(R.string.clear_icon)
                    )
                }
            }
        },
        onValueChange = onQueryChange,
        placeholder = { Text(text = stringResource(label)) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus(true)
            }
        ),
        modifier = modifier
    )
}
