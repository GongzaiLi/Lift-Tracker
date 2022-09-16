package com.seng440.jeh128.seng440assignment2.ViewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seng440.jeh128.seng440assignment2.core.Constants.Companion.EMPTY_URI
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import com.seng440.jeh128.seng440assignment2.domain.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val repo: ExerciseRepository
) : ViewModel() {
    var exercises by mutableStateOf(emptyList<Exercise>())
    var exercise by mutableStateOf(Exercise(0, "", 0.0, "", LocalDateTime.now(), "", EMPTY_URI))
    var openDialog by mutableStateOf(false)
    var deletingExercises by mutableStateOf(false)

    fun getExercises() {
        viewModelScope.launch {
            repo.getExercisesFromRoom().collect { response ->
                exercises = response
            }
        }
    }

    fun getExercise(id: Int) {
        viewModelScope.launch {
            repo.getExerciseFromRoom(id).collect { response ->
                exercise = response
            }
        }
    }

    fun updateName(name: String) {
        exercise = exercise.copy(name = name)
    }

    fun updatePBWeight(pbWeight: Double) {
        exercise = exercise.copy(pbWeight = pbWeight)
    }

    fun updatePBLocation(location: String) {
        exercise = exercise.copy(pbLocation = location)
    }

    fun updatePBDate(pbDate: LocalDateTime) {
        exercise = exercise.copy(pbDate = pbDate)
    }

    fun updateExerciseNotes(notes: String) {
        exercise = exercise.copy(exerciseNotes = notes)
    }

    fun updateVideoURI(uri: Uri){
        exercise = exercise.copy(videoUri = uri)
    }

    fun addExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addExerciseToRoom(exercise)
        }
    }

    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateExerciseInRoom(exercise)
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteExerciseFromRoom(exercise)
        }
    }

    fun toggleDeletingExercises() {
        deletingExercises = !deletingExercises
    }

    fun openDialog() {
        openDialog = true
    }

    fun closeDialog() {
        openDialog = false
    }
}