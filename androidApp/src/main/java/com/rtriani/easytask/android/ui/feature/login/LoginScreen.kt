package com.rtriani.easytask.android.ui.feature.login

import PasswordTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rtriani.easytask.android.data.AppDBProvider
import com.rtriani.easytask.android.data.AuthorizationRepositoryImpl
import com.rtriani.easytask.android.navigation.ListRoute
import com.rtriani.easytask.android.ui.UIEvent
import com.rtriani.easytask.android.ui.components.DefaultButton
import com.rtriani.easytask.android.ui.components.DefaultTextField
import com.rtriani.easytask.android.ui.theme.EasyTaskTheme

@Composable
fun LoginScreen(
    navigateToListScreen: () -> Unit
) {
    val context = LocalContext.current.applicationContext
    val DB = AppDBProvider.provide(context)
    val repository = AuthorizationRepositoryImpl(dal = DB.authorizationDAL)
    val viewModel = viewModel<LoginViewModel>(){
        LoginViewModel(
            repository = repository
        )
    }

    val username = viewModel.username
    val password = viewModel.password
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect{ _UIEvent ->
            when(_UIEvent) {
                is UIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = _UIEvent.message)
                }
                UIEvent.NavigateBack -> {

                }
                is UIEvent.Navigate<*> -> {
                    when(_UIEvent.route){
                        is ListRoute -> {
                            navigateToListScreen()
                        }
                    }
                }
            }
        }
    }

    LoginScreenContent(
        username,
        password,
        snackbarHostState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun LoginScreenContent(
    username: String,
    password: String,
    snackbarHostState: SnackbarHostState,
    onEvent: (LoginEvent) -> Unit
) {
    Scaffold (
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Easy Task",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(70.dp))
            Text(
                text = "Bem-Vindo",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            DefaultTextField(
                placeholder = "Usuário",
                label = "Usuário",
                text = username,
                onValueChange = {
                    onEvent(LoginEvent.UserNameChanged(it))
                },
                modifier = Modifier.padding(horizontal = 50.dp)
                    .fillMaxWidth()
            )

            PasswordTextField(
                placeholder = "Senha",
                label = "Senha",
                password = password,
                onValueChange = {
                    onEvent(LoginEvent.PasswordChanged(it))
                },
                modifier = Modifier.padding(horizontal = 50.dp)
                    .fillMaxWidth()
            )

            DefaultButton(
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth(),
                text = "Entrar",
                onClick = {
                    onEvent(LoginEvent.Enter)
                }
            )
        }
    }
}

@Preview
@Composable
private fun LoginScreenContentPreview() {
    EasyTaskTheme {
        LoginScreenContent(
            username = "",
            password = "",
            snackbarHostState = SnackbarHostState(),
            onEvent = {}
        )
    }
}