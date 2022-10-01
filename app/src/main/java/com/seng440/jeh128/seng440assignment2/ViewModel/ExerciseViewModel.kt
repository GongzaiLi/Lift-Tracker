package com.seng440.jeh128.seng440assignment2.ViewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seng440.jeh128.seng440assignment2.data.local.LocationHandler
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import com.seng440.jeh128.seng440assignment2.domain.model.ExerciseWithPersonalBests
import com.seng440.jeh128.seng440assignment2.domain.model.PersonalBest
import com.seng440.jeh128.seng440assignment2.domain.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val repo: ExerciseRepository,
    private val locationHandler: LocationHandler
) : ViewModel() {
    var exercises by mutableStateOf(emptyList<Exercise>())
    var exercise by mutableStateOf(Exercise(0, "", ""))
    var exerciseWithPersonalBests by mutableStateOf(ExerciseWithPersonalBests(Exercise(0, "", ""), emptyList<PersonalBest>()))
    var openDialog by mutableStateOf(false)
    var personalBest by mutableStateOf(PersonalBest(0, 0, 0.0, "" , LocalDateTime.now(), Uri.EMPTY))
    var deletingExercises by mutableStateOf(false)

    var currentLocation by mutableStateOf<Pair<Double, Double>?>(null)

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

    fun getPersonalBest(id: Int) {
        viewModelScope.launch {
            repo.getPersonalBestFromRoom(id).collect { response ->
                personalBest = response
            }
        }
    }

    fun getExerciseWithPersonalBests(id: Int) {
        viewModelScope.launch {
            repo.getExerciseWithPersonalBests(id).collect { response ->
                exerciseWithPersonalBests = response
            }
        }
    }

    fun updateName(name: String) {
        exercise = exercise.copy(name = name)
    }


//    fun updatePBWeight(pbWeight: Double) {
//        exercise = exercise.copy(pbWeight = pbWeight)
//    }
//
//    fun updatePBLocation(location: String) {
//        exercise = exercise.copy(pbLocation = location)
//    }
//
//    fun updatePBDate(pbDate: LocalDateTime) {
//        exercise = exercise.copy(pbDate = pbDate)
//    }

    fun updateExerciseNotes(notes: String) {
        exercise = exercise.copy(exerciseNotes = notes)
    }


//    fun updateVideoURI(uri: Uri){
//        exercise = exercise.copy(videoUri = uri)
//    }

    fun addExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addExerciseToRoom(exercise)
        }
    }

    fun addPersonalBest(personalBest: PersonalBest) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addPersonalBestToRoom(personalBest)
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

    fun openDialog() {
        openDialog = true
    }

    fun closeDialog() {
        openDialog = false
    }

    fun getCurrentLocation() {
        viewModelScope.launch {
            currentLocation = locationHandler.getCurrentLocation()
        }
    }
}