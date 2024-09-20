package com.oguz.wallpaperaplication.presentation.Wallpapers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguz.wallpaperaplication.domain.use_case.get_wallpaper.GetWallPapersUseCase
import com.oguz.wallpaperaplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class WallpaperViewModel @Inject constructor(
    private val getWallPaperUseCase : GetWallPapersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<WallpaperState>(WallpaperState())
    val state : StateFlow<WallpaperState> = _state



    private var job : Job? = null

    init {
        getWallpapers(_state.value.query)
    }

    private fun getWallpapers(query :String){

        job?.cancel()

        job = getWallPaperUseCase.executeGetWallPaper(query = query).onEach{

            when(it){
                is Resource.Success -> {

                    _state.value = WallpaperState(wallpapers = it.data ?: emptyList())
                }

                is Resource.Loading -> {

                    _state.value = WallpaperState(isLoading = true)
                }

                is Resource.Error -> {

                    _state.value = WallpaperState(error = it.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)

    }


    fun onEvent(event : WallpaperEvent){
        when(event){
            is WallpaperEvent.Search -> {
                getWallpapers(event.searchQuery)
            }
        }
    }

}