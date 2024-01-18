package com.example.kotlinmultiplatformsandbox.android.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmultiplatformsandbox.Greeting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The view model will manage the data from Activity and won't disappear when Activity undergoes a lifecycle change.
 * StateFlow here extends the Flow interface but has a single value or state.
 * The private backing property _greetingList ensures that only clients of this class can access the
 * read-only greetingList property.
 *
 */
class MainViewModel : ViewModel() {

    private val _greetingList = MutableStateFlow<List<String>>(listOf())
    val greetingList : StateFlow<List<String>> get() = _greetingList

    /**
     * - 'init' function of the View Model, collect all the strings from the Greeting().greet() flow.
     * - Since the collect() function is suspended, the launch coroutine is used within the view
     *   model's scope. This means that the launch coroutine will run only during the correct phases
     *   of the view model's lifecycle.
     *
     * - The update() function will update the value automatically.
     */
    init {
        viewModelScope.launch {
            Greeting().greet().collect { phrase ->
                _greetingList.update { list -> list + phrase }
            }
        }
    }
}