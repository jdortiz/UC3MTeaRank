package com.canonicalexamples.tearank.app

import android.app.Application
import com.canonicalexamples.tearank.model.Tea
import com.canonicalexamples.tearank.model.TeaDatabase
import com.canonicalexamples.tearank.model.TodoService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 20210211. Initial version created by jorge
 * for a Canonical Examples training.
 *
 * Copyright 2021 Jorge D. Ortiz Fuentes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class TeaRankApp: Application() {
    val database by lazy { TeaDatabase.getInstance(this) }
    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(TodoService::class.java)
    }
    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            database.clearAllTables()
            database.teaDao.apply {
                this.create(tea = Tea(id = 0, name = "Oolong", rating = 1))
                this.create(tea = Tea(id = 1, name = "Pu erh", rating = 1))
                this.create(tea = Tea(id = 2, name = "Green tea", rating = 1))
            }
        }
    }
}
