package com.seng440.jeh128.seng440assignment2.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seng440.jeh128.seng440assignment2.core.Constants.Companion.EXERCISE_TABLE

@Entity(tableName = EXERCISE_TABLE)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Int,

    val name: String,
    val exerciseNotes: String,
)