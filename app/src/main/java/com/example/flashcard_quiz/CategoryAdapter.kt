package com.example.flashcard_quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcard_quiz.databinding.CategoryItemBinding

class CategoryAdapter(
    val clickListener: (Categorytable) -> Unit,
    val deleteClickListener: (Categorytable) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var Data = listOf<Categorytable>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Categorytable) {
            binding.categoryNameTextView.text = category.name
            binding.root.setOnClickListener {
                clickListener(category)
            }
            binding.deleteButton.setOnClickListener {
                deleteClickListener(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = Data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Data[position]
        holder.bind(item)
    }
}

