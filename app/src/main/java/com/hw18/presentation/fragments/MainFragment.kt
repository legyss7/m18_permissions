package com.hw18.presentation.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.hw18.presentation.viewModel.MainViewModel
import com.hw18.R
import com.hw18.databinding.FragmentMainBinding
import com.hw18.presentation.adapter.ListPhotoAdapter
import com.hw18.presentation.viewModel.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private val viewModel: MainViewModel by viewModels { mainViewModelFactory }

    private val photoAdapter: ListPhotoAdapter by lazy { ListPhotoAdapter(emptyList()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonTakePhoto.setOnClickListener {
            takePhoto()
        }

        binding.buttonDeletePhotos.setOnClickListener {
            viewModel.deleteAllPhotos()
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = photoAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allPhotos.collect { photos ->
                photoAdapter.photos = photos
                if (photos.isNotEmpty()) {
                    val newPosition = photos.size - 1
                    photoAdapter.notifyItemInserted(newPosition)
                } else {
                    photoAdapter.notifyDataSetChanged()
                }
            }
        }

    }

    private fun takePhoto() {
        val fragment = MakingPhotosFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_main_tag, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}