package com.seng440.jeh128.seng440assignment2.data.repository

import com.seng440.jeh128.seng440assignment2.data.network.ExerciseDao
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import com.seng440.jeh128.seng440assignment2.domain.model.PersonalBest
import com.seng440.jeh128.seng440assignment2.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow

class ExerciseRepositoryImpl (private val exerciseDao: ExerciseDao) : ExerciseRepository {
    override suspend fun getExercisesFromRoom() = exerciseDao.getExercises()

    override suspend fun getExerciseFromRoom(id: Int) = exerciseDao.getExercise(id)

    override suspend fun getPersonalBestFromRoom(id: Int): Flow<PersonalBest> = exerciseDao.getPersonalBest(id)

    override suspend fun addExerciseToRoom(exercise: Exercise) = exerciseDao.addExercise(exercise)

    override suspend fun updateExerciseInRoom(exercise: Exercise) = exerciseDao.updateExercise(exercise)

    override suspend fun deleteExerciseFromRoom(exercise: Exercise) = exerciseDao.deleteExercise(exercise)

    override suspend fun addPersonalBestToRoom(personalBest: PersonalBest) = exerciseDao.addNewPersonalBest(personalBest)

    override suspend fun deletePersonalBestFromRoom(personalBest: PersonalBest) = exerciseDao.deletePersonalBest(personalBest)

    override suspend fun getExerciseWithPersonalBests(id: Int) = exerciseDao.getExercisesWithPersonalBests(id)
}