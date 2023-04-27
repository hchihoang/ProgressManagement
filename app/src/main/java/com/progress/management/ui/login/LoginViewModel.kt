package com.progress.management.ui.login

import com.progress.management.base.BaseViewModel
import com.progress.management.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var repo: Repository) : BaseViewModel() {

}
