package com.seng440.jeh128.seng440assignment2.di

import android.content.Context
import androidx.room.Room
import com.seng440.jeh128.seng440assignment2.core.Constants.Companion.EXERCISE_TABLE
import com.seng440.jeh128.seng440assignment2.core.NotificationService
import com.seng440.jeh128.seng440assignment2.data.local.LocationHandler
import com.seng440.jeh128.seng440assignment2.data.network.ExerciseDao
import com.seng440.jeh128.seng440assignment2.data.network.ExerciseDb
import com.seng440.jeh128.seng440assignment2.data.repository.ExerciseRepositoryImpl
import com.seng440.jeh128.seng440assignment2.domain.repository.ExerciseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideExerciseDb(
        @ApplicationContext
        context : Context
    ) = Room.databaseBuilder(
        context,
        ExerciseDb::class.java,
        EXERCISE_TABLE
    ).build()

    @Provides
    fun provideExerciseDao(
        exerciseDb: ExerciseDb
    ) = exerciseDb.exerciseDao()

    @Provides
    fun provideExerciseRepository(
        exerciseDao: ExerciseDao
    ): ExerciseRepository = ExerciseRepositoryImpl(
        exerciseDao = exerciseDao
    )

    @Provides
    fun provideLocationHandler(
        @ApplicationContext
        context: Context
    ): LocationHandler = LocationHandler(context)

    @Provides
    fun provideNotificationService(
        @ApplicationContext
        context: Context
    ): NotificationService = NotificationService(context)
}