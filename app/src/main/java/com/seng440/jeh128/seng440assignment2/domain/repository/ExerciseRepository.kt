package com.seng440.jeh128.seng440assignment2.domain.repository

import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import com.seng440.jeh128.seng440assignment2.domain.model.ExerciseWithPersonalBests
import com.seng440.jeh128.seng440assignment2.domain.model.PersonalBest
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getExercisesFromRoom(): Flow<List<Exercise>>

    suspend fun getExerciseFromRoom(id: Int): Flow<Exercise>

    suspend fun getPersonalBestFromRoom(id: Int): Flow<PersonalBest>

    suspend fun addExerciseToRoom(exercise: Exercise)

    suspend fun updateExerciseInRoom(exercise: Exercise)

    suspend fun deleteExerciseFromRoom(exercise: Exercise)

    suspend fun addPersonalBestToRoom(personalBest: PersonalBest)

    suspend fun getExerciseWithPersonalBests(id: Int):  Flow<ExerciseWithPersonalBests>
}