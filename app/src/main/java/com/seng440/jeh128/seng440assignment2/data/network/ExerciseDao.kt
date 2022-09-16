package com.seng440.jeh128.seng440assignment2.data.network

import androidx.room.*
import com.seng440.jeh128.seng440assignment2.core.Constants.Companion.EXERCISE_TABLE
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM $EXERCISE_TABLE ORDER BY id ASC")
    fun getExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM $EXERCISE_TABLE WHERE id = :id")
    fun getExercise(id: Int): Flow<Exercise>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addExercise(exercise: Exercise)

    @Update
    fun updateExercise(exercise: Exercise)

    @Delete
    fun deleteExercise(exercise: Exercise)
}