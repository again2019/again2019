package com.goingbacking.goingbacking.di

import com.example.domain.repository.AccountRepository
import com.example.domain.usecase.myAccount.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object MyAccountUseCaseModule {

    @Provides
    fun provideFindEmailPasswordUseCase(
        accountRepository: AccountRepository
    ) : FindEmailPasswordUseCase {
        return FindEmailPasswordUseCase(accountRepository)
    }

    @Provides
    fun provideGetCurrentSessionUseCase(
        accountRepository: AccountRepository
    ) : GetCurrentSessionUseCase {
        return GetCurrentSessionUseCase(accountRepository)
    }

    @Provides
    fun provideGetGsoUseCase(
        accountRepository: AccountRepository
    ) : GetGsoUseCase {
        return GetGsoUseCase(accountRepository)
    }

    @Provides
    fun provideLoginEmailUseCase(
        accountRepository: AccountRepository
    ) : LoginEmailUseCase {
        return LoginEmailUseCase(accountRepository)
    }

    @Provides
    fun provideLogOutUseCase(
        accountRepository: AccountRepository
    ) : LogOutUseCase {
        return LogOutUseCase(accountRepository)
    }

    @Provides
    fun provideRegisterEmailUseCase(
        accountRepository: AccountRepository
    ) : RegisterEmailUseCase {
        return RegisterEmailUseCase(accountRepository)
    }

    @Provides
    fun provideSignInWithCredentialUseCase(
        accountRepository: AccountRepository
    ) : SignInWithCredentialUseCase {
        return SignInWithCredentialUseCase(accountRepository)
    }

    @Provides
    fun provideSignOutUseCase(
        accountRepository: AccountRepository
    ) : SignOutUseCase {
        return SignOutUseCase(accountRepository)
    }
}