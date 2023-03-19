package com.goingbacking.goingbacking.di.layer.domain.usecase

import com.example.domain.repository.TmpTimeRepository
import com.example.domain.repository.WhatToDoRepository
import com.example.domain.usecase.myTmpTime.AddTmpTimeUseCase
import com.example.domain.usecase.whatToDo.my.*
import com.example.domain.usecase.whatToDo.other.GetOtherWhatToDoMonthUseCase
import com.example.domain.usecase.whatToDo.other.GetOtherWhatToDoYearUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object WhatToDoUseCaseModule {

    // my

    @Provides
    fun provideAddWhatToDoMonthUseCase(
        whatToDoRepository: WhatToDoRepository
    ) : AddWhatToDoMonthUseCase {
        return AddWhatToDoMonthUseCase(whatToDoRepository)
    }

    @Provides
    fun provideAddWhatToDoYearUseCase(
        whatToDoRepository: WhatToDoRepository
    ) : AddWhatToDoYearUseCase {
        return AddWhatToDoYearUseCase(whatToDoRepository)
    }

    @Provides
    fun provideGetMyWhatToDoMonthUseCase(
        whatToDoRepository: WhatToDoRepository
    ) : GetMyWhatToDoMonthUseCase {
        return GetMyWhatToDoMonthUseCase(whatToDoRepository)
    }

    @Provides
    fun provideGetMyWhatToDoYearUseCase(
        whatToDoRepository: WhatToDoRepository
    ) : GetMyWhatToDoYearUseCase {
        return GetMyWhatToDoYearUseCase(whatToDoRepository)
    }

    @Provides
    fun provideUpdateWhatToDoMonthUseCase(
        whatToDoRepository: WhatToDoRepository
    ) : UpdateWhatToDoMonthUseCase {
        return UpdateWhatToDoMonthUseCase(whatToDoRepository)
    }

    @Provides
    fun provideUpdateWhatToDoYearUseCase(
        whatToDoRepository: WhatToDoRepository
    ) : UpdateWhatToDoYearUseCase {
        return UpdateWhatToDoYearUseCase(whatToDoRepository)
    }

    //other

    @Provides
    fun provideGetOtherWhatToDoMonthUseCase(
        whatToDoRepository: WhatToDoRepository
    ) : GetOtherWhatToDoMonthUseCase {
        return GetOtherWhatToDoMonthUseCase(whatToDoRepository)
    }

    @Provides
    fun provideGetOtherWhatToDoYearUseCase(
        whatToDoRepository: WhatToDoRepository
    ) : GetOtherWhatToDoYearUseCase {
        return GetOtherWhatToDoYearUseCase(whatToDoRepository)
    }

}