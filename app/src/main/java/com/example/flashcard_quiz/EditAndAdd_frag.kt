package com.example.flashcard_quiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flashcard_quiz.databinding.FragmentEditAndAddFragBinding


class EditAndAdd_frag : Fragment() {

    private var Binding: FragmentEditAndAddFragBinding?=null
    private val binding get()=Binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Binding= FragmentEditAndAddFragBinding.inflate(inflater,container ,false)
        val view=binding.root
        return view }
    override fun onDestroyView() {
        super.onDestroyView()
        Binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dao = flashcardDatabase.getInstance(application).carddao
        val viewModelFactory = CardViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(CardViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val cardId = arguments?.let { EditAndAdd_fragArgs.fromBundle(it).idofcard } ?: -1L
        val categoryId = arguments?.let { EditAndAdd_fragArgs.fromBundle(it).cateId } ?: -1L

        viewModel.id = categoryId

        if (cardId != -1L) {
            viewModel.getCardById(cardId)
            viewModel.currentCard.observe(viewLifecycleOwner) { card ->
                card?.let {
                    binding.Q.setText(it.question)
                    binding.A.setText(it.answer)
                }
            }
        }

        binding.save.setOnClickListener {
            val question = binding.Q.text.toString()
            val answer = binding.A.text.toString()

            if (cardId == -1L) {
                viewModel.addCard(question, answer)
            } else {
                viewModel.updateCurrentCard(question, answer)
            }

            Toast.makeText(context, "Card saved!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }


}