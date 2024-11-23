package com.rtriani.easytask.android.ui.feature.addedit

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rtriani.easytask.android.data.AppDBProvider
import com.rtriani.easytask.android.data.TodoRepositoryImpl
import com.rtriani.easytask.android.domain.StatusEnum
import com.rtriani.easytask.android.ui.UIEvent
import com.rtriani.easytask.android.ui.components.DefaultButton
import com.rtriani.easytask.android.ui.components.DefaultTextField
import com.rtriani.easytask.android.ui.theme.EasyTaskTheme

@Composable
fun AddEditScreen(
    id: Long? = null,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current.applicationContext
    val DB = AppDBProvider.provide(context)
    val repository = TodoRepositoryImpl(dal = DB.todoDAL)
    val viewModel = viewModel<AddEditViewModel> {
        AddEditViewModel(
            id = id,
            repository = repository
        )
    }
    val title = viewModel.title
    val description = viewModel.description
    val status = viewModel.status
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { _UIEvent ->
            when(_UIEvent) {
                is UIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = _UIEvent.message)
                }
                UIEvent.NavigateBack -> {
                    navigateBack()
                }
                is UIEvent.Navigate<*> -> {
                    TODO("Not yet implemented")
                }
            }
        }
    }

    AddEditContent(
        id,
        title,
        description,
        status,
        navigateBack,
        snackbarHostState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun AddEditContent(
    id: Long? = null,
    title: String,
    description: String?,
    status: StatusEnum,
    navigateBack: () -> Unit,
    snackbarHostState: SnackbarHostState,
    onEvent: (AddEditEvent) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(status.ordinal) }
    val options = StatusEnum.entries.map {
        StatusEnum.valueOf(it.toString()).status
    }
    Scaffold (
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier.consumeWindowInsets(paddingValues)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Easy Task",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(70.dp))
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){

                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth(1f)
                        .padding(horizontal = 16.dp),
                ) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size,
                                baseShape = MaterialTheme.shapes.large
                            ),
                            onClick = {
                                selectedIndex = index
                                 onEvent(AddEditEvent.StatusChanged(StatusEnum.entries.get(selectedIndex)))
                            },
                            selected = index == status.ordinal,
                            border = SegmentedButtonDefaults.borderStroke(
                                color = Color.Unspecified
                            ),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = Color.White,
                                inactiveContentColor = Color.White,
                                inactiveBorderColor = Color.Unspecified,
                                inactiveContainerColor = Color(0x177895AB)
                            )
                        ) {
                            Text(
                                text = label,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            DefaultTextField(
                placeholder = "Título da Tarefa",
                label = "Título da Tarefa",
                text = title,
                onValueChange = {
                    onEvent(AddEditEvent.TitleChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            DefaultTextField(
                placeholder = "Descrição da Tarefa (opcional)",
                label = "Descrição da Tarefa (opcional)",
                text = description?: "",
                onValueChange = {
                    onEvent(AddEditEvent.DescriptionChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                DefaultButton(
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    text = if (id != null) "Editar Tarefa" else "Inserir Tarefa",
                    onClick = {
                        onEvent(AddEditEvent.Save)
                    }
                )

                Spacer(Modifier.width(16.dp))

                DefaultButton(
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    text = "Cancelar",
                    onClick = {
                        navigateBack()
                    }
                )

            }
        }
    }
}

@Preview
@Composable
private fun AddEditContentPreview() {
    EasyTaskTheme {
        AddEditContent(
            id = null,
            title = "",
            description = null,
            status = StatusEnum.PENDENTE,
            navigateBack = {},
            snackbarHostState = SnackbarHostState(),
            onEvent = {}
        )
    }
}