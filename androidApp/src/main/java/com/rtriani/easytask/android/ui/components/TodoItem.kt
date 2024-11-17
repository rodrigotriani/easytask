package com.rtriani.easytask.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rtriani.easytask.android.R
import com.rtriani.easytask.android.domain.Todo
import com.rtriani.easytask.android.domain.todo1
import com.rtriani.easytask.android.domain.todo2
import com.rtriani.easytask.android.ui.theme.EasyTaskTheme

@Composable
fun TodoItem(
    todo: Todo,
    onCompletedChange: (Boolean) -> Unit = {},
    onItemClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(16.dp)
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = onCompletedChange
            )
            Text(
                modifier = Modifier.width(60.dp),
                style = TextStyle(
                    fontSize = 10.sp
                ),
                textAlign = TextAlign.Center,
                text = if(todo.isCompleted) "Finalizado" else "Em andamento"
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(.65f)
                .align(
                    Alignment.CenterVertically
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = todo.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = Color(0xFF7895AB),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column (
            modifier = Modifier.width(100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row (){
                Column {
                    IconButton(
                        onClick = onItemClick
                    ) {
                        Icon(
                            tint = Color.Unspecified,
                            painter = painterResource(id = R.drawable.edit_icon),
                            modifier = Modifier.size(
                                25.dp
                            ),
                            contentDescription = "Alterar tarefa"
                        )
                    }
                    Text(
                        modifier = Modifier.width(50.dp),
                        style = TextStyle(
                            fontSize = 10.sp
                        ),
                        textAlign = TextAlign.Center,
                        text = "Alterar",
                        maxLines = 1
                    )
                }

                Column() {
                    IconButton(
                        onClick = onDeleteClick
                    ) {
                        Icon(
                            tint = Color.Unspecified,
                            painter = painterResource(id = R.drawable.remove_icon),
                            modifier = Modifier.size(
                                25.dp
                            ),
                            contentDescription = "Remover tarefa"
                        )
                    }
                    Text(
                        modifier = Modifier.width(50.dp),
                        style = TextStyle(
                            fontSize = 10.sp
                        ),
                        textAlign = TextAlign.Center,
                        text = "Remover",
                        maxLines = 1
                    )
                }

            }
        }

    }
    HorizontalDivider(
        color = Color.Gray,
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
    )
}

@Preview
@Composable
private fun TodoItemPreview() {
    EasyTaskTheme {
        TodoItem(
            todo = todo1,
            onCompletedChange = {},
            onItemClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview
@Composable
private fun TodoItemCompletedPreview() {
    EasyTaskTheme {
        TodoItem(
            todo = todo2,
            onCompletedChange = {},
            onItemClick = {},
            onDeleteClick = {}
        )
    }
}