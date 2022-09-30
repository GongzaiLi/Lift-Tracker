package com.seng440.jeh128.seng440assignment2.data.network

import androidx.room.*
import com.seng440.jeh128.seng440assignment2.core.Constants.Companion.EXERCISE_TABLE
import com.seng440.jeh128.seng440assignment2.core.Constants.Companion.PERSONAL_BEST_TABLE
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import com.seng440.jeh128.seng440assignment2.domain.model.ExerciseWithPersonalBests
import com.seng440.jeh128.seng440assignment2.domain.model.PersonalBest
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM $EXERCISE_TABLE ORDER BY exerciseId ASC")
    fun getExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM $EXERCISE_TABLE WHERE exerciseId = :id")
    fun getExercise(id: Int): Flow<Exercise>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addExercise(exercise: Exercise)

    @Insert
    fun addNewPersonalBest(personalBest: PersonalBest)

    @Update
    fun updateExercise(exercise: Exercise)

    @Delete
    fun deleteExercise(exercise: Exercise)

    @Transaction
    @Query("Select * From $EXERCISE_TABLE ORDER BY exerciseId ASC")
    fun getExercisesWithPersonalBests(): List<ExerciseWithPersonalBests>

    @Transaction
    @Query("Select * From $EXERCISE_TABLE WHERE exerciseId = :id")
    fun getExercisesWithPersonalBests(id: Int): Flow<ExerciseWithPersonalBests>

    @Query("SELECT * FROM $PERSONAL_BEST_TABLE WHERE personalBestId = :id")
    fun getPersonalBest(id: Int): Flow<PersonalBest>
}