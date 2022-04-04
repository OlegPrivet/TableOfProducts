package ru.application.producttable.presentation.fragments.fragmentallreceipt

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import ru.application.producttable.R
import ru.application.producttable.data.utils.getDate
import ru.application.producttable.databinding.ReceiptItemBinding
import ru.application.producttable.domain.entities.local.LocalReceipt

class ListReceiptsItem(
    var localReceipt: LocalReceipt
) : AbstractBindingItem<ReceiptItemBinding>() {

    override val type: Int
        get() = R.id.receipt_item

    override fun bindView(binding: ReceiptItemBinding, payloads: List<Any>) {
        binding.tvUser.text = localReceipt.user
        binding.tvDate.text = localReceipt.timestamp.getDate()
        binding.tvSum.text = localReceipt.totalSum
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ReceiptItemBinding {
        return ReceiptItemBinding.inflate(inflater, parent, false)
    }
}
