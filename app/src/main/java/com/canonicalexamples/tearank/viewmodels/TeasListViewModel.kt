package com.canonicalexamples.tearank.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.canonicalexamples.tearank.util.Event

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
class TeasListViewModel: ViewModel() {
    private val _navigate: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val navigate: LiveData<Event<Boolean>> = _navigate
    private val teaList = listOf("Earl Gray", "Oolong", "Black Tea")
    data class Item(val name: String)
    val numberOfItems: Int
        get() = teaList.count()

    fun addButtonClicked() {
        _navigate.value = Event(true)
    }

    fun getItem(n: Int) = Item(name = teaList[n])

}