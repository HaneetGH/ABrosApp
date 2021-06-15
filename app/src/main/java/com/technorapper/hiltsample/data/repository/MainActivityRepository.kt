package com.technorapper.hiltsample.data.repository

import android.content.Context
import android.util.Log

import com.technorapper.hiltsample.data.SynchronousApi


import com.technorapper.hiltsample.data.UserPreferences
import com.technorapper.hiltsample.data.model.RequestPost
import com.technorapper.hiltsample.domain.DataState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainActivityRepository @Inject constructor(
    private val userPreferences: UserPreferences,
    @ApplicationContext context: Context, private val asyncApi: SynchronousApi
) : BaseRepository() {
    private val appContext = context.applicationContext

    fun saveDataInDataStore(s: String, s1: String) {
        runBlocking {
            userPreferences.saveAGENAME(s, s1)

        }
    }


}

