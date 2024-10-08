package com.oguz.wallpaperaplication.presentation.WallpaperDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguz.wallpaperaplication.domain.use_case.GetWallpaperDetail.GetWallpaperDetailUseCase
import com.oguz.wallpaperaplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WallpaperDetailViewModel @Inject constructor(

    private val getWallpaperDetailUseCase: GetWallpaperDetailUseCase

) : ViewModel() {

    private val _state = MutableStateFlow<WallpaperDetailState>(WallpaperDetailState())
    val state : StateFlow<WallpaperDetailState> = _state


    private var job: Job? = null

    private fun getWallpaper(id:String){

        job?.cancel()

        getWallpaperDetailUseCase.executeGetWallpaperDetail(id = id).onEach {

            when(it){

                is Resource.Success -> {
                    _state.value = WallpaperDetailState(wallpaperDetail = it.data)
                }

                is Resource.Error -> {

                    _state.value = WallpaperDetailState(error = it.message ?: "Error")

                }
                is Resource.Loading -> {

                    _state.value = WallpaperDetailState(isLoading = true)

                }

            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event : WallpaperDetailEvent){

        when(event){
            is WallpaperDetailEvent.GetbyId -> {
                getWallpaper(id = event.id)
            }
        }
    }

}