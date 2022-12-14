package com.seng440.jeh128.seng440assignment2.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Transaction
import androidx.room.TypeConverters
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import com.seng440.jeh128.seng440assignment2.domain.model.PersonalBest

@Database(entities = [Exercise::class, PersonalBest::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ExerciseDb : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}