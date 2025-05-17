package com.example.proincome.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proincome.data.local.dao.GroupDao
import com.example.proincome.data.local.entity.Group
import kotlinx.coroutines.launch

class GreetingViewModel(private val groupDao: GroupDao) : ViewModel() {
    private val _groups = MutableLiveData<List<Group>>()
    val allGroups: LiveData<List<Group>> = _groups

    private val _operationStatus = MutableLiveData<String>()
    val operationStatus: LiveData<String> = _operationStatus

    init {
        loadAllItems()
    }

    private fun loadAllItems() {
        viewModelScope.launch {
            try {
                _groups.value = groupDao.getAllGroups()
                _operationStatus.value = "Success"
            } catch (e: Exception) {
                _operationStatus.value = "Error loading items: ${e.message}"
                Log.e("GreetingViewModel", "Error loading items", e)
            }
        }
    }
}