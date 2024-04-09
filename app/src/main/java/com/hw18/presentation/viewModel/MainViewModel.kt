package com.hw18.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hw18.data.Photo
import com.hw18.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _allPhotos = MutableStateFlow<List<Photo>>(emptyList())
    val allPhotos: StateFlow<List<Photo>> = _allPhotos

    init {
        loadAllPhotos()
    }

    private fun loadAllPhotos() {
        viewModelScope.launch {
            repository.getAllPhoto().collect {
                _allPhotos.value = it
            }
        }
    }

    fun deleteAllPhotos() {
        viewModelScope.launch {
            repository.deleteAllPhotos()
        }
    }
}