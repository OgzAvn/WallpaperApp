package com.oguz.wallpaperaplication.presentation.WallpaperDetail.view


import android.Manifest
import android.app.WallpaperManager
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.oguz.wallpaperaplication.R
import com.oguz.wallpaperaplication.databinding.FragmentWallpaperDetailBinding
import com.oguz.wallpaperaplication.presentation.WallpaperDetail.WallpaperDetailEvent
import com.oguz.wallpaperaplication.presentation.WallpaperDetail.WallpaperDetailViewModel
import com.oguz.wallpaperaplication.util.scaleAndSetWallpaper
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject



class WallpaperDetailFragment @Inject constructor(
    private val glide: RequestManager,
    private val wallpaperManager: WallpaperManager
) : Fragment() {

    private var _binding: FragmentWallpaperDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewmodel : WallpaperDetailViewModel

    lateinit var imageUrl : String
    private lateinit var bottomSheetDialog: BottomSheetDialog

    private lateinit var imageBitmap: Bitmap



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    // Inflate the layout for this fragment
        _binding = FragmentWallpaperDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(requireActivity())[WallpaperDetailViewModel::class.java]

        val args = arguments?.getString("wId")
        if (args != null){
            Log.d("id" , args)
            viewmodel.onEvent(WallpaperDetailEvent.GetbyId(args))
        }

        getData()

        bottomSheetDialog = BottomSheetDialog(requireContext())


        binding.downloadButton.setOnClickListener {

            openDialog()

        }


        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


    }

    private fun getData(){

        val state = viewmodel.state

        lifecycleScope.launch {

            state.collect {

                imageUrl = it.wallpaperDetail?.largeImageURL.toString()

                glide.asBitmap()
                    .load(imageUrl)
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            binding.imageView.setImageBitmap(resource)
                            imageBitmap = resource
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            
                        }


                    })


            }
        }
    }

    private fun openDialog(){

        val dialogInflater = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet,null)

        bottomSheetDialog.setContentView(dialogInflater)
        bottomSheetDialog.show()

        dialogInflater.findViewById<View>(R.id.set_wallpaper).setOnClickListener {
            bottomSheetDialog.dismiss()

            activity?.window?.decorView.let {

                it?.let {
                    Snackbar.make(it.findViewById(R.id.constraint_layoutt),
                        resources.getString(R.string.wallpaper_set_success),
                        Snackbar.LENGTH_SHORT).show()
                }

            }

            getMetrics(imageBitmap)

        }

        dialogInflater.findViewById<View>(R.id.save_gallery).setOnClickListener {

            it?.let {
                bottomSheetDialog.dismiss()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //Save image to MediaStore
                    saveImageToMediaStore(imageBitmap)
                }else {
                    //save image to File
                    saveImageToFile(imageBitmap)
                }
            }
        }

    }

    private fun saveImageToMediaStore(imageBitmap: Bitmap) {
        Snackbar.make(
            requireActivity().findViewById(R.id.constraint_layoutt),
            resources.getString(R.string.wallpaper_save_success),
            Snackbar.LENGTH_SHORT
        ).show()

        val imageCollections = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        }else{
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val uuid = UUID.randomUUID()

        val displayName = uuid.toString()

        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val resolver = context?.applicationContext?.contentResolver
        val imageContentUri = resolver?.insert(imageCollections, imageDetails)

        if (imageContentUri != null) {
            resolver.openOutputStream(imageContentUri, "w").use { outStream->

                if (outStream != null) {
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
                }

            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                imageDetails.clear()
                imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(imageContentUri, imageDetails, null, null)
            }

        }
    }

    private fun saveImageToFile(imageBitmap: Bitmap) {

        if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                Snackbar.make(requireActivity().findViewById(R.id.constraint_layoutt),
                    resources.getString(R.string.AllowPermission),
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(resources.getString(R.string.GiveMePermission)){
                        //Permission request
                        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }.show()
            }else{
                //Permission request
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }else{
            //write file
            Snackbar.make(
                requireActivity().findViewById(R.id.constraint_layoutt),
                resources.getString(R.string.wallpaper_save_success),
                Snackbar.LENGTH_SHORT
            ).show()

            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

            val uuid = UUID.randomUUID()
            val displayName = uuid.toString()

            val imageFile = File(directory,"$displayName.png")

            if (!directory.isDirectory) directory.mkdir()

            if (directory.isDirectory) imageFile.outputStream().use {
                imageBitmap.compress(Bitmap.CompressFormat.PNG,100,it)
            }


        }

    }

    private fun getMetrics(cBitmap : Bitmap){

        cBitmap.scaleAndSetWallpaper(requireActivity(),wallpaperManager)

    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){result->


        if (result){

            Toast.makeText(
                requireContext(),
                getString(R.string.PermissionGranted),
                Toast.LENGTH_LONG
            ).show()

        }else{
            Toast.makeText(
                requireContext(),
                getString(R.string.PermissionDenied),
                Toast.LENGTH_LONG
            ).show()
        }

    }






    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}