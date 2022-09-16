package com.seng440.jeh128.seng440assignment2.data.repository

import com.seng440.jeh128.seng440assignment2.data.network.ExerciseDao
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import com.seng440.jeh128.seng440assignment2.domain.repository.ExerciseRepository

class ExerciseRepositoryImpl (private val exerciseDao: ExerciseDao) : ExerciseRepository {
    override suspend fun getExercisesFromRoom() = exerciseDao.getExercises()

    override suspend fun getExerciseFromRoom(id: Int) = exerciseDao.getExercise(id)

    override suspend fun addExerciseToRoom(exercise: Exercise) = exerciseDao.addExercise(exercise)

    override suspend fun updateExerciseInRoom(exercise: Exercise) = exerciseDao.updateExercise(exercise)

    override suspend fun deleteExerciseFromRoom(exercise: Exercise) = exerciseDao.deleteExercise(exercise)
}