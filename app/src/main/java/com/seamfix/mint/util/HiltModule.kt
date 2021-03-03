package com.seamfix.mint.util

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(ActivityComponent::class)
object HiltModule {

    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context{
        return appContext
    }
}