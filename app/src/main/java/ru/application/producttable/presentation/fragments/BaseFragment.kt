package ru.application.producttable.presentation.fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseFragment : Fragment() {

    private var bindingList: MutableList<Job> = mutableListOf()

    override fun onDestroyView() {
        super.onDestroyView()
        bindingList.map {
            it.cancel()
        }.let {
            bindingList.clear()
        }
    }

    fun registerJob(block: suspend () -> Unit) {
        bindingList.add(lifecycleScope.launch { block() })
    }

    fun registerJobIO(block: suspend () -> Unit) {
        bindingList.add(lifecycleScope.launch(Dispatchers.IO) { block() })
    }
}

