package com.progress.management.di.module

import com.progress.management.share_preference.HSBASharePref
import com.progress.management.share_preference.HSBASharePrefImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefModule {
    @Provides
    @Singleton
    fun provideSharePref(sharedPref: HSBASharePrefImpl): HSBASharePref {
        return sharedPref
    }
}