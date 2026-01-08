package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hackathon.domain.repository.CombinationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val repository: CombinationRepository,
    ) : ViewModel()
