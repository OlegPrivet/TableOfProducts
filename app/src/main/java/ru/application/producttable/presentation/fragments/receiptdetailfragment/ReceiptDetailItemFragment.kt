package ru.application.producttable.presentation.fragments.receiptdetailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import ru.application.producttable.R
import ru.application.producttable.contract.CustomAction
import ru.application.producttable.contract.HasCustomAction
import ru.application.producttable.contract.HasCustomTitle
import ru.application.producttable.contract.navigator
import ru.application.producttable.data.utils.getDate
import ru.application.producttable.databinding.ReceiptDetailFragmentBinding
import ru.application.producttable.presentation.fragments.BaseFragment
import ru.application.producttable.presentation.fragments.receiptdetailfragment.factory.ReceiptDetailItemVMFactory
import ru.application.producttable.utils.application

class ReceiptDetailItemFragment : BaseFragment(), HasCustomTitle, HasCustomAction {

    private lateinit var binding: ReceiptDetailFragmentBinding
    private val TAG = ReceiptDetailItemFragment::class.simpleName
    private val viewModel: ReceiptDetailItemVM by viewModels {
        ReceiptDetailItemVMFactory(
            application().getLocalReceiptUseCase,
            application().deleteReceiptUseCase
        )
    }

    private lateinit var fastAdapter: FastAdapter<ReceiptDetailItem>
    private lateinit var itemAdapter: ItemAdapter<ReceiptDetailItem>

    private var titles = ""
    private var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getString(ID_RECEIPT, "") ?: ""
        registerJob { viewModel.getReceipt(id) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ReceiptDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemAdapter = ItemAdapter()
        fastAdapter = FastAdapter.Companion.with(itemAdapter)
        binding.recyclerDetail.adapter = fastAdapter
        binding.recyclerDetail.layoutManager = LinearLayoutManager(activity)
        binding.recyclerDetail.itemAnimator = DefaultItemAnimator()

        registerJob {
            viewModel.localReceipt.collect {
                binding.tvUser.text = it.user
                binding.tvDate.text = it.timestamp.getDate()
                binding.tvSum.text = it.totalSum
                setTitle(it.target)
                val list = mutableListOf<ReceiptDetailItem>()
                it.items.forEach { localItem ->
                    list.add(ReceiptDetailItem(localItem))
                }
                itemAdapter.clear()
                itemAdapter.add(list)
            }
        }

        fastAdapter.onClickListener = { view, adapter, item, position ->
            // open Receipt Details
            false
        }
    }

    private fun setTitle(title: String) {
        titles = title
        navigator().updateUI()
    }

    companion object {
        private const val ID_RECEIPT = "ID_RECEIPT"

        fun newInstance(id: String): ReceiptDetailItemFragment {
            val args = Bundle()
            args.putString(ID_RECEIPT, id)
            val fragment = ReceiptDetailItemFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getCustomActions(): List<CustomAction> =
        listOf(
            CustomAction(
                iconRes = R.drawable.ic_delete,
                titleRes = R.string.delete,
                onCustomAction = {
                    registerJob {
                        viewModel.deleteReceipt(viewModel.localReceipt.value)
                        navigator().goBack()
                    }
                },
                showAsAction = MenuItem.SHOW_AS_ACTION_ALWAYS
            )
        )

    override fun getTitleRes(): Int? = null

    override fun getTitleStr(): String = titles
}
