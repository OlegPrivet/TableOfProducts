package ru.application.producttable.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.application.producttable.presentation.fragments.fragmentallreceipt.ListReceiptsFragment

class MainViewModel : ViewModel() {

    private val _currentFragment: MutableStateFlow<Fragment> = MutableStateFlow(ListReceiptsFragment())
    val currentFragment: StateFlow<Fragment> = _currentFragment.asStateFlow()

    fun setFragment(fragment: Fragment){
        _currentFragment.value = fragment
    }

}
