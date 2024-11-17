package com.rtriani.easytask.android.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultTextField(
    placeholder: String = "",
    label: String = "",
    text: String = "",
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 0.dp, color = Color.Unspecified),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        TextField(
            modifier = modifier,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent
            ),
            value = text,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = placeholder)
            },
            label = {
                Text(text = label)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun DefaultTextFieldPreview(){
    DefaultTextField(
        placeholder = "Usuário",
        label = "Usuário",
        text = "",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth()
    )
}