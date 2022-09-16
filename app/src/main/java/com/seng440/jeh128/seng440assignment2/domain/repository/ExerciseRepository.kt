package com.seng440.jeh128.seng440assignment2.domain.repository

import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getExercisesFromRoom(): Flow<List<Exercise>>

    suspend fun getExerciseFromRoom(id: Int): Flow<Exercise>

    suspend fun addExerciseToRoom(exercise: Exercise)

    suspend fun updateExerciseInRoom(exercise: Exercise)

    suspend fun deleteExerciseFromRoom(exercise: Exercise)
}