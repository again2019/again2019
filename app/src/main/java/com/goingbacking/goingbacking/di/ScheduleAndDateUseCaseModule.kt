package com.goingbacking.goingbacking.di

import com.example.domain.repository.ScheduleAndDateRepository

import com.example.domain.usecase.scheduleAndDate.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ScheduleAndDateUseCaseModule {

    @Provides
    fun provideAddDateUseCase(
        scheduleAndDateRepository: ScheduleAndDateRepository
    ) : AddDateUseCase {
        return AddDateUseCase(scheduleAndDateRepository)
    }

    @Provides
    fun provideAddScheduleUseCase(
        scheduleAndDateRepository: ScheduleAndDateRepository
    ) : AddScheduleUseCase {
        return AddScheduleUseCase(scheduleAndDateRepository)
    }

    @Provides
    fun provideDeleteSchedulesUseCase(
        scheduleAndDateRepository: ScheduleAndDateRepository
    ) : DeleteSchedulesUseCase {
        return DeleteSchedulesUseCase(scheduleAndDateRepository)
    }

    @Provides
    fun provideGetAllSchedulesUseCase(
        scheduleAndDateRepository: ScheduleAndDateRepository
    ) : GetAllSchedulesUseCase {
        return GetAllSchedulesUseCase(scheduleAndDateRepository)
    }

    @Provides
    fun provideGetDateListUseCase(
        scheduleAndDateRepository: ScheduleAndDateRepository
    ) : GetDateListUseCase {
        return GetDateListUseCase(scheduleAndDateRepository)
    }

    @Provides
    fun provideGetDateUseCase(
        scheduleAndDateRepository: ScheduleAndDateRepository
    ) : GetDateUseCase {
        return GetDateUseCase(scheduleAndDateRepository)
    }

    @Provides
    fun provideGetSelectedSchedulesUseCase(
        scheduleAndDateRepository: ScheduleAndDateRepository
    ) : GetSelectedSchedulesUseCase {
        return GetSelectedSchedulesUseCase(scheduleAndDateRepository)
    }
}