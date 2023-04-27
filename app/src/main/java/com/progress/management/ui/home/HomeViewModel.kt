package com.progress.management.ui.home

import com.progress.management.base.BaseViewModel
import com.progress.management.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var repo: Repository) : BaseViewModel() {

}