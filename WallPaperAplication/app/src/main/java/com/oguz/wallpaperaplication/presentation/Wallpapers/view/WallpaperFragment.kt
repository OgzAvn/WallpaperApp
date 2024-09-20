package com.oguz.wallpaperaplication.presentation.Wallpapers.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.oguz.wallpaperaplication.databinding.FragmentWallpaperBinding
import com.oguz.wallpaperaplication.presentation.Wallpapers.WallpaperEvent
import com.oguz.wallpaperaplication.presentation.Wallpapers.WallpaperState
import com.oguz.wallpaperaplication.presentation.Wallpapers.WallpaperViewModel
import com.oguz.wallpaperaplication.presentation.Wallpapers.adapter.WallpaperRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class WallpaperFragment @Inject constructor(
    private val adapter: WallpaperRecyclerAdapter
) : Fragment() {

    private var _binding: FragmentWallpaperBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WallpaperViewModel


    //TODO: Hata alırsak hatayı burada alabiliyoruz coroutine de
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error : ${throwable.localizedMessage}")
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[WallpaperViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWallpaperBinding.inflate(LayoutInflater.from(context))
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wallpapersRecyclerview.adapter = adapter
        binding.wallpapersRecyclerview.layoutManager = GridLayoutManager(requireContext(), 4)

        TextListener()


        lifecycleScope.launch {

           viewModel.state.collect() {

                val wallpapers = it.wallpapers

                if (wallpapers.isNotEmpty()) {
                    adapter.wallpaperList = wallpapers

                }

            }

        }



    }

    private fun TextListener(){

        binding.searchEditText.addTextChangedListener {

            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

                delay(1000)
                viewModel.onEvent(WallpaperEvent.Search(it.toString()))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}