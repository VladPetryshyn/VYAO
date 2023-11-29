package com.vladpetryshyn.vyao

import android.os.Bundle
import android.text.util.Linkify
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladpetryshyn.vyao.databinding.FragmentTasksCardDescriptionBinding

class TasksCardDescriptionFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTasksCardDescriptionBinding.inflate(inflater, container, false)
        binding.taskCardDescription.apply {
        }
        return binding.root
    }
}