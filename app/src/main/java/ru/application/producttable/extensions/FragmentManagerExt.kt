package ru.application.producttable.utils

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleCoroutineScope

fun FragmentManager.commitWhenLifecycleResumed(
    lifecycleScope: LifecycleCoroutineScope,
    allowStateLoss: Boolean = false,
    body: FragmentTransaction.() -> Unit,
) {
    lifecycleScope.launchWhenResumed {
        val transaction = beginTransaction()
        transaction.body()
        if (allowStateLoss) {
            transaction.commitAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }
}
