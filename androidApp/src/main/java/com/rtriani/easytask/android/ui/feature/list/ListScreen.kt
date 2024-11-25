package com.rtriani.easytask.android.ui.feature.list

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rtriani.easytask.android.R.drawable
import com.rtriani.easytask.android.data.AppDBProvider
import com.rtriani.easytask.android.data.TodoRepositoryImpl
import com.rtriani.easytask.android.domain.StatusEnum
import com.rtriani.easytask.android.domain.Todo
import com.rtriani.easytask.android.domain.todo1
import com.rtriani.easytask.android.domain.todo2
import com.rtriani.easytask.android.navigation.AddEditRoute
import com.rtriani.easytask.android.navigation.ProfileRoute
import com.rtriani.easytask.android.ui.UIEvent
import com.rtriani.easytask.android.ui.components.SearchTextField
import com.rtriani.easytask.android.ui.components.TodoItem
import com.rtriani.easytask.android.ui.theme.EasyTaskTheme

@Composable
fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Erro ao obter a ActivityMain")
}

@Composable
fun ListScreen(
    navigateToAddEditScreen: (id: Long?) -> Unit,
    navigateToProfileScreen: () -> Unit,
) {
    val activity = LocalContext.current.findActivity()
    val context = LocalContext.current.applicationContext
    val DB = AppDBProvider.provide(context)
    val repository = TodoRepositoryImpl(dal = DB.todoDAL)
    val viewModel = viewModel<ListViewModel>{
        ListViewModel(
            repository = repository
        )
    }

    val todos by viewModel.todos.collectAsState()

    val search = viewModel.search
    val status = viewModel.status

    BackHandler {
        activity.finish()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect{ _UIEvent ->
            when(_UIEvent) {
                is UIEvent.ShowSnackbar -> {}
                UIEvent.NavigateBack -> {}
                is UIEvent.Navigate<*> -> {
                    when(_UIEvent.route){
                        is AddEditRoute -> {
                            navigateToAddEditScreen(_UIEvent.route.id)
                        }
                        is ProfileRoute -> {
                            navigateToProfileScreen()
                        }
                    }
                }
            }
        }
    }

    ListContent(
        todos = todos,
        search = search ?: "",
        status = status,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ListContent(
    todos: List<Todo>,
    search: String = "",
    status: StatusEnum = StatusEnum.PENDENTE,
    onEvent: (ListEvent) -> Unit
) {

    val tabs = StatusEnum.entries.map {
        StatusEnum.valueOf(it.toString()).status
    }

    var tabIndex by remember { mutableIntStateOf(status.ordinal) }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color(0xFF7895AB),
                contentColor = Color.White
            ) {
                BottomNavigationItem(
                    selected = false,
                    onClick = {
                        onEvent(ListEvent.Profile(1))
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = drawable.profile_icon),
                            tint = Color.Unspecified,
                            modifier = Modifier.size(
                                20.dp
                            ),
                            contentDescription = "Visualizar/Editar Perfil"
                        )
                    },
                    label = {
                        Text(
                            "Perfil",
                            fontWeight = FontWeight.Bold
                        )
                    }
                )

                BottomNavigationItem(
                    selected = false,
                    onClick = {
                        onEvent(ListEvent.AddEdit(null))
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = drawable.add_icon),
                            tint = Color.Unspecified,
                            modifier = Modifier.size(
                                20.dp
                            ),
                            contentDescription = "Adicionar tarefa"
                        )
                    },
                    label = {
                        Text(
                            "Adicionar",
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        }

    ){ paddingValues ->
        Box(
            contentAlignment = Alignment.TopCenter
        ){
            Column (
                modifier = Modifier.fillMaxSize()
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

                Row {
                    SearchTextField(
                        placeholder = "Pesquisar",
                        label = "Pesquisar",
                        search = search,
                        onValueChange = {
                            onEvent(
                                ListEvent.SearchChanged(
                                    search = it,
                                    status = StatusEnum.entries[tabIndex]
                                )
                            )
                        }
                    )
                }

                Column{
                    TabRow(
                        selectedTabIndex = tabIndex,
                        backgroundColor = Color(0xFF7895AB),
                        contentColor = Color.White
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                text = {
                                    Text(title)
                                },
                                selected = tabIndex == index,
                                onClick = {
                                    tabIndex = index
                                    onEvent(
                                        ListEvent.SearchChanged(
                                            search = search,
                                            status = StatusEnum.entries.get(tabIndex)
                                        )
                                    )
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.consumeWindowInsets(paddingValues)
                ) {
                    itemsIndexed(todos) { index, todo ->
                        TodoItem(
                            modifier = Modifier.fillParentMaxWidth()
                                .background(
                                    if (index % 2 == 0) {
                                        Color.Unspecified
                                    } else {
                                        Color(0x177895AB)
                                    }
                                ),
                            todo = todo,
                            onItemClick = {
                                onEvent(ListEvent.AddEdit(todo.id))
                            },
                            onDeleteClick = {
                                onEvent(ListEvent.Delete(todo.id))
                            }
                        )

                        if(index == todos.lastIndex){
                            Spacer(modifier = Modifier.height(70.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListContentPreview() {
    EasyTaskTheme {
        ListContent(
            todos = listOf(
                todo1,
                todo2
            ),
            search = "Todo 1",
            status = StatusEnum.PENDENTE,
            onEvent = {}
        )
    }
}