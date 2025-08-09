package com.example.flashcard_quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flashcard_quiz.databinding.FragmentCategoryBinding

class Category_frag : Fragment(), CategoryClickHandler {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CategoryViewModel
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dao = flashcardDatabase.getInstance(application).cateDao
        val factory = CategoryViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(CategoryViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = CategoryAdapter(
            clickListener = { clickedItem ->
                val action = Category_fragDirections.actionCategoryToDisplayFrag(clickedItem.categoryid)
                findNavController().navigate(action)
            },
            deleteClickListener = { category ->
                onDeleteClick(category)
            }
        )

        binding.categoryRecyclerView.adapter = adapter

        viewModel.allCategories.observe(viewLifecycleOwner) {
            adapter.Data = it
        }

        binding.addnew.setOnClickListener {
            binding.save.visibility = View.VISIBLE
            binding.catname.visibility = View.VISIBLE
            binding.categoryRecyclerView.visibility = View.GONE
            binding.addnew.visibility = View.GONE
        }

        binding.save.setOnClickListener {
            viewModel.addcategory()
            binding.catname.text.clear()
            binding.catname.visibility = View.GONE
            binding.save.visibility = View.GONE
            binding.categoryRecyclerView.visibility = View.VISIBLE
            binding.addnew.visibility = View.VISIBLE
        }
    }

    override fun onDeleteClick(category: Categorytable) {
        viewModel.deleteCategory(category)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
