package com.hw18.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hw18.data.Photo
import com.hw18.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

class MakingPhotosViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _photos = MutableStateFlow<List<Photo>>(emptyList())

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            repository.getAllPhoto().collect { photos ->
                _photos.value = photos
            }
        }
    }

    fun addPhoto(context: Context) {
        viewModelScope.launch {
            repository.addPhoto(context)
        }
    }
}