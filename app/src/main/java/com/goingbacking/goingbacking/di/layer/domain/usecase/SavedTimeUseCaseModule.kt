package com.goingbacking.goingbacking.di.layer.domain.usecase

import com.example.domain.repository.SavedTimeRepository
import com.example.domain.usecase.savedTime.common.*
import com.example.domain.usecase.savedTime.my.*
import com.example.domain.usecase.savedTime.other.GetOtherSavedTimeDayUseCase
import com.example.domain.usecase.savedTime.other.GetOtherSavedTimeMonthUseCase
import com.example.domain.usecase.savedTime.other.GetOtherSavedTimeYearUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object SavedTimeUseCaseModule {

    // common
    @Provides
    fun provideGetSavedTimeAboutMonthRankUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : GetSavedTimeAboutMonthRankUseCase {
        return GetSavedTimeAboutMonthRankUseCase(savedTimeRepository)
    }

    @Provides
    fun provideGetSavedTimeAboutYearRankUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : GetSavedTimeAboutYearRankUseCase {
        return GetSavedTimeAboutYearRankUseCase(savedTimeRepository)
    }

    @Provides
    fun provideUpdateSavedTimeAboutMonthRankUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : UpdateSavedTimeAboutMonthRankUseCase {
        return UpdateSavedTimeAboutMonthRankUseCase(savedTimeRepository)
    }

    @Provides
    fun provideUpdateSavedTimeAboutYearRankUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : UpdateSavedTimeAboutYearRankUseCase {
        return UpdateSavedTimeAboutYearRankUseCase(savedTimeRepository)
    }

    // my
    @Provides
    fun provideAddMyInitSavedTimeMonthUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : AddMyInitSavedTimeMonthUseCase {
        return AddMyInitSavedTimeMonthUseCase(savedTimeRepository)
    }

    @Provides
    fun provideAddMyInitSavedTimeYearUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : AddMyInitSavedTimeYearUseCase {
        return AddMyInitSavedTimeYearUseCase(savedTimeRepository)
    }

    @Provides
    fun provideAddMySavedTimeAboutMonthRankUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : AddMySavedTimeAboutMonthRankUseCase {
        return AddMySavedTimeAboutMonthRankUseCase(savedTimeRepository)
    }

    @Provides
    fun provideAddMySavedTimeAboutYearRankUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : AddMySavedTimeAboutYearRankUseCase {
        return AddMySavedTimeAboutYearRankUseCase(savedTimeRepository)
    }

    @Provides
    fun provideAddMySavedTimeDayUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : AddMySavedTimeDayUseCase {
        return AddMySavedTimeDayUseCase(savedTimeRepository)
    }

    @Provides
    fun provideAddMySavedTimeMonthUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : AddMySavedTimeMonthUseCase {
        return AddMySavedTimeMonthUseCase(savedTimeRepository)
    }

    @Provides
    fun provideAddMySavedTimeYearUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : AddMySavedTimeYearUseCase {
        return AddMySavedTimeYearUseCase(savedTimeRepository)
    }

    @Provides
    fun provideGetMySavedTimeDayUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : GetMySavedTimeDayUseCase {
        return GetMySavedTimeDayUseCase(savedTimeRepository)
    }

    @Provides
    fun provideGetMySavedTimeMonthUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : GetMySavedTimeMonthUseCase {
        return GetMySavedTimeMonthUseCase(savedTimeRepository)
    }


    @Provides
    fun provideGetMySavedTimeYearUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : GetMySavedTimeYearUseCase {
        return GetMySavedTimeYearUseCase(savedTimeRepository)
    }

    @Provides
    fun provideGetOtherSavedTimeDayUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : GetOtherSavedTimeDayUseCase {
        return GetOtherSavedTimeDayUseCase(savedTimeRepository)
    }

    // other

    @Provides
    fun provideGetOtherSavedTimeMonthUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : GetOtherSavedTimeMonthUseCase {
        return GetOtherSavedTimeMonthUseCase(savedTimeRepository)
    }

    @Provides
    fun provideGetOtherSavedTimeYearUseCase(
        savedTimeRepository: SavedTimeRepository
    ) : GetOtherSavedTimeYearUseCase {
        return GetOtherSavedTimeYearUseCase(savedTimeRepository)
    }
}