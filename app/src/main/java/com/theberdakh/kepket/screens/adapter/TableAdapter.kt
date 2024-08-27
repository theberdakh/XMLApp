package com.theberdakh.kepket.screens.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.kepket.R
import com.theberdakh.kepket.databinding.ItemRecyclerTableBinding

data class TableNumber(
    val number: Int
)

class CourseNumberDiffCallback : DiffUtil.ItemCallback<TableNumber>() {
    override fun areItemsTheSame(oldItem: TableNumber, newItem: TableNumber): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: TableNumber, newItem: TableNumber): Boolean {
        return oldItem.number == newItem.number
    }
}

class TableAdapter : ListAdapter<TableNumber, TableAdapter.ViewHolder>(CourseNumberDiffCallback()) {

    private var selectedPosition = RecyclerView.NO_POSITION

    @SuppressLint("NotifyDataSetChanged")
    fun unselectAll() {
        selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }

    private var onClickListener: ((Int) -> Unit)? = null
    fun setOnClickListener(block: ((Int) -> Unit)) {
        onClickListener = block
    }

    private var onClickListenerExtended: ((Int, View) -> Unit)? = null
    fun setOnClickListenerExtended(block: ((Int, View) -> Unit)) {
        onClickListenerExtended = block
    }

    inner class ViewHolder(private val binding: ItemRecyclerTableBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.apply {
                if (position == selectedPosition){
                    root.setBackgroundResource(R.drawable.shape_bg_textfield_stroke_green)}
                else {
                    root.setBackgroundResource(R.drawable.shape_bg_textfield_stroke)
                }

                getItem(adapterPosition)?.let { courseNumber ->
                    root.text = courseNumber.number.toString()
                    root.setOnClickListener {
                        onClickListenerExtended?.invoke(courseNumber.number, root)
                        onClickListener?.invoke(courseNumber.number)

                        notifyItemChanged(selectedPosition) // Clear the old selection
                        selectedPosition = adapterPosition
                        notifyItemChanged(selectedPosition)
                    }
                }


            }
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecyclerTableBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


}
