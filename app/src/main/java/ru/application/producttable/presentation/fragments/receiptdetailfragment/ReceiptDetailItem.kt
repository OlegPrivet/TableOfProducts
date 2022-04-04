package ru.application.producttable.presentation.fragments.receiptdetailfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import ru.application.producttable.R
import ru.application.producttable.databinding.ReceiptDetailItemBinding
import ru.application.producttable.domain.entities.local.LocalItem

class ReceiptDetailItem(
    var localItem: LocalItem,
) : AbstractBindingItem<ReceiptDetailItemBinding>() {

    override val type: Int
        get() = R.id.receipt_detail_item

    override fun bindView(binding: ReceiptDetailItemBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.tvName.text = localItem.name
        binding.tvCount.text = localItem.quantity.toString()
        binding.tvSum.text = localItem.sum
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?,
    ): ReceiptDetailItemBinding {
        return ReceiptDetailItemBinding.inflate(inflater, parent, false)
    }
}
