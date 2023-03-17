package com.goingbacking.goingbacking.di.layer.domain.usecase

import com.example.domain.repository.TmpTimeRepository
import com.example.domain.usecase.myTmpTime.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object MyTmpTimeUseCaseModule {

    @Provides
    fun provideAddTmpTimeUseCase(
        tmpTimeRepository: TmpTimeRepository
    ) : AddTmpTimeUseCase {
        return AddTmpTimeUseCase(tmpTimeRepository)
    }

    @Provides
    fun provideDeleteTmpTimeUseCase(
        tmpTimeRepository: TmpTimeRepository
    ) : DeleteTmpTimeUseCase {
        return DeleteTmpTimeUseCase(tmpTimeRepository)
    }

    @Provides
    fun provideGetTmpTimeUseCase(
        tmpTimeRepository: TmpTimeRepository
    ) : GetTmpTimeUseCase {
        return GetTmpTimeUseCase(tmpTimeRepository)
    }

    @Provides
    fun provideUpdateTmpTimeDayUseCase(
        tmpTimeRepository: TmpTimeRepository
    ) : UpdateTmpTimeDayUseCase {
        return UpdateTmpTimeDayUseCase(tmpTimeRepository)
    }

    @Provides
    fun provideUpdateTmpTimeMonthUseCase(
        tmpTimeRepository: TmpTimeRepository
    ) : UpdateTmpTimeMonthUseCase {
        return UpdateTmpTimeMonthUseCase(tmpTimeRepository)
    }

    @Provides
    fun provideUpdateTmpTimeYearUseCase(
        tmpTimeRepository: TmpTimeRepository
    ) : UpdateTmpTimeYearUseCase {
        return UpdateTmpTimeYearUseCase(tmpTimeRepository)
    }


}