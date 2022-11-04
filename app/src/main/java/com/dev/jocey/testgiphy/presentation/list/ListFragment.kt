package com.dev.jocey.testgiphy.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dev.jocey.testgiphy.R
import com.dev.jocey.testgiphy.core.ext.hideKeyboard
import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity
import com.dev.jocey.testgiphy.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), GifsPagingAdapter.AdapterClickListener {
    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()
    val adapter = GifsPagingAdapter()
    var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        adapter.adapterClickListener = this
        binding.gifsList.adapter = adapter
        collectFromViewModel()
//        viewModel.gifs.onEach { pagedList ->
//            adapter.submitData(pagedList)
//        }.launchIn(lifecycleScope)
//        adapter.addLoadStateListener { loadState ->
//            val pg = loadState.source.refresh is LoadState.Loading
//            binding.progressBar.isVisible = pg
//            binding.gifsList.isVisible = loadState.source.refresh is LoadState.NotLoading
//        }

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    query?.let {
                        if (query.toString().isNotEmpty()) {
                            adapter.refresh()
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
                            adapter.refresh()
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

    private fun collectFromViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gifs
                    .collectLatest {
                        adapter.submitData(it)
                    }
            }
        }
    }

    override fun onGifClicked(gif: GiphyEntity?) {

    }

    override fun onGifDeleted(gif: GiphyEntity?) {
        gif?.let { viewModel.blockGifById(it.id) }
    }

}