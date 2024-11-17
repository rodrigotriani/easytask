package com.rtriani.easytask.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DefaultButton(
    text: String = "",
    onClick: () -> Unit = {},
    modifier: Modifier
){
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Button(
            onClick = onClick
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun DefaultButtonPreview(){
    DefaultButton(
        text = "Entrar",
        onClick = {},
        Modifier.fillMaxWidth()
    )
}