package com.example.gym_coleman_application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym_coleman_application.data.model.api.ExerciseInfoItem
import com.example.gym_coleman_application.data.repository.api.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExerciseViewModel : ViewModel() {

    private val repo = ExerciseRepository()

    private val _uiState = MutableStateFlow<List<ExerciseInfoItem>>(emptyList())
    val uiState = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = repo.getExercises()
        }
    }
}
