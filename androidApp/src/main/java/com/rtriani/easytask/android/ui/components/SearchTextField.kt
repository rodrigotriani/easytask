package com.rtriani.easytask.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    placeholder: String = "",
    label: String = "",
    search: String = "",
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = modifier,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent
            ),
            value = search,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = placeholder)
            },
            label = {
                Text(text = label)
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Sharp.Search,
                    contentDescription = ""
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = VisualTransformation.None
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
@Preview
fun SearchTextFieldPreview(){
    SearchTextField(
        placeholder = "Pesquisar",
        label = "Pesquisar",
        search = "Teste",
        onValueChange = {}
    )
}