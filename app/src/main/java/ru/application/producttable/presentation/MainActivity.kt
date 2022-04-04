package ru.application.producttable.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import ru.application.producttable.R
import ru.application.producttable.contract.CustomAction
import ru.application.producttable.contract.HasCustomAction
import ru.application.producttable.contract.HasCustomTitle
import ru.application.producttable.contract.Navigator
import ru.application.producttable.databinding.ActivityMainBinding
import ru.application.producttable.presentation.fragments.fragmentallreceipt.ListReceiptsFragment
import ru.application.producttable.presentation.fragments.receiptdatafragment.ReceiptFetchDataFragment
import ru.application.producttable.presentation.fragments.receiptdetailfragment.ReceiptDetailItemFragment
import ru.application.producttable.utils.commitWhenLifecycleResumed

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    lateinit var fragmentContainerView: FragmentContainerView

    private val currentFragment: Fragment
        get() = fragmentContainerView.getFragment()

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?,
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUI()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        fragmentContainerView = binding.fragmentContainer
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.commitWhenLifecycleResumed(lifecycleScope) {
                add(R.id.fragmentContainer, ListReceiptsFragment())
            }
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        updateUI()
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun goToGetReceiptData(qr: String) {
        launchFragment(ReceiptFetchDataFragment.newInstance(qr))
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun goToSettings() {
        Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
    }

    override fun goToReceiptDetail(id: String) {
        launchFragment(ReceiptDetailItemFragment.newInstance(id))
    }

    private fun launchFragment(fragment: Fragment, addBackStack: Boolean = true) {
        supportFragmentManager.commitWhenLifecycleResumed(lifecycleScope) {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            if (addBackStack) addToBackStack(fragment.javaClass.simpleName)
            replace(R.id.fragmentContainer, fragment)
        }
    }

    override fun updateUI() {
        val fragment = currentFragment

        if (fragment is HasCustomTitle) {
            binding.toolbar.title = fragment.getTitleRes()?.let { getString(it) } ?: fragment.getTitleStr()
        } else {
            binding.toolbar.title = getString(R.string.app_name)
        }

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }

        if (fragment is HasCustomAction) {
            createCustomToolbarActions(fragment.getCustomActions())
        } else {
            binding.toolbar.menu.clear()
        }
    }

    private fun createCustomToolbarActions(listAction: List<CustomAction>){
        binding.toolbar.menu.clear()

        listAction.forEach { action ->
            val iconDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(this, action.iconRes)!!)
            iconDrawable.setTint(Color.WHITE)

            binding.toolbar.menu.add(action.titleRes).apply {
                setShowAsAction(action.showAsAction ?: MenuItem.SHOW_AS_ACTION_NEVER)
                icon = iconDrawable
                setOnMenuItemClickListener {
                    action.onCustomAction.run()
                    return@setOnMenuItemClickListener true
                }
            }
        }
    }
}
