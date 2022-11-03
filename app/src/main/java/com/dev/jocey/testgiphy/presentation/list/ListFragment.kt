package com.dev.jocey.testgiphy.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.dev.jocey.testgiphy.R
import com.dev.jocey.testgiphy.databinding.FragmentListBinding
import com.dev.jocey.testgiphy.core.ext.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()
    val adapter = GifsPagingAdapter()
    var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.gifsList.adapter = adapter

        viewModel.gifs.onEach { pagedList ->
            adapter.submitData(pagedList)
        }.launchIn(lifecycleScope)
        adapter.addLoadStateListener { loadState ->
            val pg = loadState.source.refresh is LoadState.Loading
            binding.progressBar.isVisible = pg
            binding.gifsList.isVisible = loadState.source.refresh is LoadState.NotLoading
        }

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    query?.let {
                        if (query.toString().isNotEmpty()) {
                            requireActivity().hideKeyboard()
                            viewModel.launchSearch(query.toString())
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(1500L)
                    newText?.let {
                        if (newText.toString().isNotEmpty()) {
                            requireActivity().hideKeyboard()
                            viewModel.launchSearch(newText.toString())
                        }
                    }
                }
                return true
            }
        })


        return binding.root
    }


}