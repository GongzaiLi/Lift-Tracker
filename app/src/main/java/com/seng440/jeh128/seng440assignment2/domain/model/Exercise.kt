package com.seng440.jeh128.seng440assignment2.domain.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seng440.jeh128.seng440assignment2.core.Constants.Companion.EXERCISE_TABLE
import java.time.LocalDateTime

@Entity(tableName = EXERCISE_TABLE)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val pbWeight: Double,
    val pbLocation: String,
    val pbDate: LocalDateTime,
    val exerciseNotes: String,
    val videoUri: Uri
)