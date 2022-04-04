package ru.application.producttable.presentation.fragments.receiptdatafragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kotlinx.coroutines.delay
import ru.application.producttable.contract.HasCustomTitle
import ru.application.producttable.contract.navigator
import ru.application.producttable.databinding.ReceiptFetchDataFragmentBinding
import ru.application.producttable.domain.common.Resource
import ru.application.producttable.presentation.fragments.BaseFragment
import ru.application.producttable.presentation.fragments.receiptdatafragment.factory.ReceiptFetchDataVMFactory
import ru.application.producttable.utils.application

class ReceiptFetchDataFragment : BaseFragment(), HasCustomTitle {

    private lateinit var binding: ReceiptFetchDataFragmentBinding
    private val TAG = ReceiptFetchDataFragment::class.simpleName
    private val viewModel: ReceiptFetchDataVM by viewModels {
        ReceiptFetchDataVMFactory(
            application().getReceiptByQRUseCase,
            application().saveReceiptUseCase,
        )
    }
    private var titles = Titles.ENTER_TARGET
    private var qrCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        qrCode = arguments?.getString(QR_STRING, "") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ReceiptFetchDataFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGetReceiptData.setOnClickListener {
            if (binding.targetEdit.text.toString().isNotEmpty()) {
                viewModel.getReceipt(qrCode, binding.targetEdit.text.toString())
                setTitle(Titles.RECEIPT_GET_DATA)
            }
        }
        stateUI()
    }

    private fun stateUI() {
        registerJob {
            viewModel.result.collect {
                when (it) {
                    is Resource.Default -> {}
                    is Resource.GetReceiptSuccess -> {
                        Log.e(TAG, it.data.toString())
                        setTitle(Titles.RECEIPT_SAVE_DATA)
                        viewModel.saveReceipt(it.data)
                        delay(200)
                        navigator().goBack()
                    }
                    is Resource.Loading -> {
                        Log.e(TAG, "Loading")
                        setTitle(Titles.LOADING)
                    }
                    is Resource.Error -> {
                        Log.e(TAG, it.exception)
                        setTitle(Titles.ERROR)
                    }
                }
            }
        }
    }

    private fun setTitle(title: Titles){
        titles = title
        navigator().updateUI()
    }

    companion object {
        private const val QR_STRING = "QR_STRING"

        fun newInstance(qr: String): ReceiptFetchDataFragment {
            val args = Bundle()
            args.putString(QR_STRING, qr)
            val fragment = ReceiptFetchDataFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getTitleRes(): Int = titles.title

    override fun getTitleStr(): String = ""
}
