package com.rtriani.easytask.android.ui.feature.login

import com.rtriani.easytask.android.data.AuthorizationRepository
import com.rtriani.easytask.android.domain.Authorization
import com.rtriani.easytask.android.navigation.ListRoute
import com.rtriani.easytask.android.ui.UIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class LoginScreenTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: AuthorizationRepository
    private val testDispatcher = StandardTestDispatcher()

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun setup() {
        // Mockando a dependência externa
        repository = mock()
        viewModel = LoginViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test LoginScreen calls navigateToListScreen on navigation event`() = runTest {
        // Configurar o mock para retornar autorização válida
        val mockAuthorization = Authorization(
            id = 123,
            username = "validUser",
            password = "validPass"
        )
        whenever(repository.GetAuthorization("validUser", "validPass"))
            .thenReturn(mockAuthorization) // Retorna uma instância válida de Authorization

        // Configurar valores de entrada
        viewModel.onEvent(LoginEvent.UserNameChanged("validUser"))
        viewModel.onEvent(LoginEvent.PasswordChanged("validPass"))

        // Simular o clique no botão de login
        viewModel.onEvent(LoginEvent.Enter)

        // Capturar o primeiro evento de navegação emitido
        val uiEvent = viewModel.uiEvent.first()

        // Verificar se o evento de navegação foi disparado
        assertEquals(UIEvent.Navigate(ListRoute), uiEvent)
    }


    @Test
    fun `test LoginScreen shows error when username is blank`() = runTest {
        // Configurar valores de entrada
        viewModel.onEvent(LoginEvent.PasswordChanged("validPass"))

        // Simular o clique no botão de login
        viewModel.onEvent(LoginEvent.Enter)

        // Capturar o primeiro evento emitido
        val uiEvent = viewModel.uiEvent.first()

        // Verificar se a mensagem de erro foi emitida
        assertEquals(UIEvent.ShowSnackbar("O usuário não pode estar em branco"), uiEvent)
    }

    @Test
    fun `test LoginScreen shows error when password is blank`() = runTest {
        // Configurar valores de entrada
        viewModel.onEvent(LoginEvent.UserNameChanged("validUser"))

        // Simular o clique no botão de login
        viewModel.onEvent(LoginEvent.Enter)

        // Capturar o primeiro evento emitido
        val uiEvent = viewModel.uiEvent.first()

        // Verificar se a mensagem de erro foi emitida
        assertEquals(UIEvent.ShowSnackbar("a senha não pode estar em branco"), uiEvent)
    }
}
