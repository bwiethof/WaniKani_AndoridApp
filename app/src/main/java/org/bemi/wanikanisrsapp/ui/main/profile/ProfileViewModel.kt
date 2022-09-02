package org.bemi.wanikanisrsapp.ui.main.profile

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.bemi.wanikanisrsapp.data.TokenStore

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = loadText()
    }

    private val _buttonVisibility = MutableLiveData<Int>().apply {
        value = setVisibility()
    }

    val text: LiveData<String> = _text
    val buttonVisibility: LiveData<Int> = _buttonVisibility

    private fun loadText(): String {
        return if (TokenStore.token != null) {
            "Your user token is: ${TokenStore.token}"
        } else {
            "No user token available."
        }
    }

    private fun setVisibility(): Int {
        return if (TokenStore.token.isNullOrBlank()) View.VISIBLE else View.GONE
    }

}