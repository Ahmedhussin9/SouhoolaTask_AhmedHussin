package com.souhoolatask_ahmedhussin.di

import com.souhoolatask_ahmedhussin.data.remote.WebServices
import com.souhoolatask_ahmedhussin.data.repository.NewsRepositoryImpl
import com.souhoolatask_ahmedhussin.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideNewsRepository(webServices: WebServices): NewsRepository {
        return NewsRepositoryImpl(webServices)
    }

}