package com.progress.management.di.module

import android.content.Context
import com.progress.management.base.adapter.ProgressAdapter
import com.progress.management.ui.login.LoginViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Provides
    @Singleton
    fun provideProgressExaminationAdapter(context: Context): ProgressAdapter {
        return ProgressAdapter(context)
    }

}