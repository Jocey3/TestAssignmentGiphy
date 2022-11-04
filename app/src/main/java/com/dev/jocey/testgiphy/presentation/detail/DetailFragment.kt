package com.dev.jocey.testgiphy.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.dev.jocey.testgiphy.R
import com.dev.jocey.testgiphy.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var binding: FragmentDetailBinding
    private val viewPagerGifAdapter = ViewPagerGifAdapter()
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.viewPager.adapter = viewPagerGifAdapter
        viewModel.launchSearch(args.gif.searchQuery)
        collectFromViewModel()
        viewModel.chan.observe(viewLifecycleOwner) {
            binding.viewPager.setCurrentItem(args.position, false)
        }
        return binding.root
    }

    private fun collectFromViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gifs
                    .collectLatest {
                        viewPagerGifAdapter.submitData(it)
                    }
            }
        }
    }
}