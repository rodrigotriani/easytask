package com.rtriani.easytask.android.ui.feature.list

import com.rtriani.easytask.android.data.TodoRepository
import com.rtriani.easytask.android.domain.StatusEnum
import com.rtriani.easytask.android.domain.Todo
import com.rtriani.easytask.android.navigation.AddEditRoute
import com.rtriani.easytask.android.navigation.ProfileRoute
import com.rtriani.easytask.android.ui.UIEvent
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class ListScreenTest {

    private lateinit var viewModel: ListViewModel
    private lateinit var repository: TodoRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mock()
        viewModel = ListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test ListScreen calls navigateToAddEditScreen on AddEditEvent`() = runTest {
        viewModel.onEvent(ListEvent.AddEdit(null))

        advanceUntilIdle()

        val uiEvent = viewModel.uiEvent.first()

        assertEquals(UIEvent.Navigate(AddEditRoute(null)), uiEvent)
    }

    @Test
    fun `test ListScreen calls navigateToProfileScreen on ProfileEvent`() = runTest {
        viewModel.onEvent(ListEvent.Profile(1))

        advanceUntilIdle()

        val uiEvent = viewModel.uiEvent.first()

        assertEquals(UIEvent.Navigate(ProfileRoute), uiEvent)
    }
}