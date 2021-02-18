package com.canonicalexamples.tearank.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.canonicalexamples.tearank.model.Tea
import com.canonicalexamples.tearank.model.TeaDatabase
import com.canonicalexamples.tearank.model.TodoService
import com.canonicalexamples.tearank.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

/**
 * 20210210. Initial version created by jorge
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
class TeasListViewModel(private val database: TeaDatabase, private val webservice: TodoService): ViewModel() {
    private val _navigate: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val navigate: LiveData<Event<Boolean>> = _navigate
    private var teasList = listOf<Tea>()
    data class Item(val name: String)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            teasList = database.teaDao.fetchTeas()
        }
    }

    val numberOfItems: Int
        get() = teasList.count()

    fun addButtonClicked() {
        _navigate.value = Event(true)
    }

    fun getItem(n: Int) = Item(name = teasList[n].name)

    fun onClickItem(n: Int) {
        println("Item $n clicked")
        viewModelScope.launch(Dispatchers.IO) {
            val todo = webservice.getTodo(n).await()
            println("todo: ${todo.title}")
        }
    }
}

class TeasListViewModelFactory(private val database: TeaDatabase, private val webservice: TodoService): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeasListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeasListViewModel(database, webservice) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
