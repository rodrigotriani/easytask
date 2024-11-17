package com.rtriani.easytask.android.ui.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rtriani.easytask.android.data.AppDBProvider
import com.rtriani.easytask.android.data.AuthorizationRepositoryImpl
import com.rtriani.easytask.android.navigation.ListRoute
import com.rtriani.easytask.android.ui.UIEvent


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current.applicationContext
    val DB = AppDBProvider.provide(context)
    val repository = AuthorizationRepositoryImpl(dal = DB.authorizationDAL)
    val viewModel = viewModel<ProfileViewModel>(){
        ProfileViewModel(
            repository = repository
        )
    }

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

                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile\nAqui vai entrar a tela de configuração do perfil do usuário",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = Color(0xFF7895AB),
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}