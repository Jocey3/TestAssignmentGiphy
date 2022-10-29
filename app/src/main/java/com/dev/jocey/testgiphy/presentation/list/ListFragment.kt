package com.dev.jocey.testgiphy.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.dev.jocey.testgiphy.R
import com.dev.jocey.testgiphy.databinding.FragmentListBinding

class ListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.tvNext.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_listFragment_to_detailFragment)
        }

        return binding.root

    }


}