package com.seng440.jeh128.seng440assignment2.domain.model

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseWithPersonalBests (
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "correspondingExerciseId"
    )
    val personalBests: List<PersonalBest>
)