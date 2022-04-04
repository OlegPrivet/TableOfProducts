package ru.application.producttable.presentation.fragments.fragmentallreceipt

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.fondesa.kpermissions.PermissionStatus
import com.fondesa.kpermissions.coroutines.flow
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import excelkt.Sheet
import excelkt.workbook
import excelkt.write
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanCustomCode
import io.github.g00fy2.quickie.config.BarcodeFormat
import io.github.g00fy2.quickie.config.ScannerConfig
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.IndexedColors
import ru.application.producttable.R
import ru.application.producttable.contract.CustomAction
import ru.application.producttable.contract.HasCustomAction
import ru.application.producttable.contract.HasCustomTitle
import ru.application.producttable.contract.navigator
import ru.application.producttable.data.utils.getDate
import ru.application.producttable.databinding.ListReceiptFramgentBinding
import ru.application.producttable.domain.entities.local.LocalReceipt
import ru.application.producttable.presentation.fragments.BaseFragment
import ru.application.producttable.presentation.fragments.fragmentallreceipt.factory.ListReceiptsVMFactory
import ru.application.producttable.utils.application
import java.io.File


class ListReceiptsFragment : BaseFragment(), HasCustomTitle, HasCustomAction {

    private lateinit var binding: ListReceiptFramgentBinding
    private val TAG = ListReceiptsFragment::class.simpleName
    private val viewModel: ListReceiptsVM by viewModels {
        ListReceiptsVMFactory(
            application().getReceiptsUseCase,
            application().deleteReceiptUseCase
        )
    }

    private lateinit var fastAdapter: FastAdapter<ListReceiptsItem>
    private lateinit var itemAdapter: ItemAdapter<ListReceiptsItem>

    private lateinit var list: List<LocalReceipt>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ListReceiptFramgentBinding.inflate(inflater, container, false)

        binding.fabQR.setOnClickListener {
            scanQrCode.launch(
                ScannerConfig.build {
                    setBarcodeFormats(listOf(selectedBarcodeFormat))
                    setOverlayStringRes(R.string.scan_barcode)
                    setHapticSuccessFeedback(false)
                    setShowTorchToggle(true)
                    setHorizontalFrameRatio(1f)
                    setUseFrontCamera(false)
                }
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemAdapter = ItemAdapter()
        fastAdapter = FastAdapter.Companion.with(itemAdapter)
        binding.recyclerReceipt.adapter = fastAdapter
        binding.recyclerReceipt.layoutManager = LinearLayoutManager(activity)
        binding.recyclerReceipt.itemAnimator = DefaultItemAnimator()

        registerJob {
            viewModel.getAllReceipts().collect {
                list = it
                val list = mutableListOf<ListReceiptsItem>()
                it.forEach { localReceipt ->
                    list.add(ListReceiptsItem(localReceipt))
                }
                itemAdapter.clear()
                itemAdapter.add(list)
            }
        }

        fastAdapter.onClickListener = { view, adapter, item, position ->
            navigator().goToReceiptDetail(item.localReceipt.id)
            false
        }
    }

    private var selectedBarcodeFormat = BarcodeFormat.FORMAT_QR_CODE // FORMAT_ALL_FORMATS
    private val scanQrCode = registerForActivityResult(ScanCustomCode(), ::handleResult)

    private fun handleResult(result: QRResult) {
        when (result) {
            is QRResult.QRSuccess -> {
                Log.e(TAG, result.content.rawValue)
                navigator().goToGetReceiptData(result.content.rawValue)
            }
            QRResult.QRUserCanceled -> Log.e(TAG, "User canceled")
            QRResult.QRMissingPermission -> Log.e(TAG, "Missing permission")
            is QRResult.QRError -> Log.e(TAG,
                "${result.exception.javaClass.simpleName}: ${result.exception.localizedMessage}")
        }

    }

    private fun generateReports() {
        registerJobIO {
            if (list.isNotEmpty()) {
                val path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS)
                val file = File(path, "/${System.currentTimeMillis().getDate()}.xlsx")
                workbook {
                    sheet(list.first().timestamp.getDate()) {
                        customersHeader()
                        list.forEach { localReceipt ->
                            localReceipt.items.forEach { localItem ->
                                row {
                                    cell(localReceipt.user, createCellStyle {
                                        font.bold = false
                                        alignment = CellStyle.ALIGN_LEFT
                                    })
                                    cell(localItem.date)
                                    cell(localItem.name)
                                    cell(String.format("%.3f", localItem.quantity))
                                    cell(localItem.sum, createCellStyle {
                                        alignment = CellStyle.ALIGN_RIGHT
                                        font.bold = false
                                    })
                                    cell(localReceipt.target)
                                }
                            }
                        }
                    }
                }.write(file.absolutePath)
            }
        }
    }

    private fun Sheet.customersHeader() {
        val headings = listOf(
            "Поставщик",
            "Дата",
            "Товарно-материальные ценности",
            "Кол-во",
            "Сумма",
            "Назначение"
        )
        val headingStyle = createCellStyle {
            setFont(createFont {
                font.bold = true
                color = IndexedColors.BLACK.index
            })
        }
        row(headingStyle) {
            headings.forEach { cell(it) }
        }
    }

    override fun getCustomActions(): List<CustomAction> =
        listOf(
            CustomAction(
                iconRes = R.drawable.ic_settings,
                titleRes = R.string.settings,
                onCustomAction = {
                    navigator().goToSettings()
                }
            ),
            CustomAction(
                iconRes = R.drawable.ic_report,
                titleRes = R.string.report,
                onCustomAction = {
                    val result =
                        permissionsBuilder(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).build()
                    registerJobIO {
                        result.flow().collect{
                            it.forEach { status ->
                                when (status){
                                    is PermissionStatus.Granted -> generateReports()
                                    is PermissionStatus.Denied.Permanently -> Log.e(TAG, "Permanently: ${status.permission}")
                                    is PermissionStatus.Denied.ShouldShowRationale -> Log.e(TAG, "ShouldShowRationale: ${status.permission}")
                                    is PermissionStatus.RequestRequired -> Log.e(TAG, "RequestRequired: ${status.permission}")
                                }
                            }
                        }
                    }
                    result.send()
                }
            )
        )

    override fun getTitleRes(): Int = R.string.all_receipt

    override fun getTitleStr(): String = ""
}
