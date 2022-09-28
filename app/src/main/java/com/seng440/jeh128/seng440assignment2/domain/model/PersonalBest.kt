package com.seng440.jeh128.seng440assignment2.domain.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seng440.jeh128.seng440assignment2.core.Constants
import java.time.LocalDateTime

@Entity(tableName = Constants.PERSONAL_BEST_TABLE)
data class PersonalBest(
    @PrimaryKey(autoGenerate = true)
    val personalBestId: Int,
    val correspondingExerciseId: Int,
    val pbWeight: Double,
    val pbLocation: String,
    val pbDate: LocalDateTime,
    val videoUri: Uri
)