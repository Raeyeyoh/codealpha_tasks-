package com.example.flashcard_quiz

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flashcard_quiz.databinding.FragmentDisplayFragBinding


class Display_frag : Fragment() {
    private var Binding:FragmentDisplayFragBinding? = null
    private val binding get()=Binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Binding= FragmentDisplayFragBinding.inflate(inflater,container ,false)
        val view=binding.root
        return view }
    override fun onDestroyView() {
        super.onDestroyView()
        Binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application= requireNotNull(this.activity).application
        val dao=flashcardDatabase.getInstance(application).carddao
        val viewModelFactory=CardViewModelFactory(dao)
        val viewModel= ViewModelProvider(this,viewModelFactory).get(CardViewModel::class.java)
        binding.viewModel=viewModel
        binding.lifecycleOwner=viewLifecycleOwner


            val categoryId = arguments?.let {
                Display_fragArgs.fromBundle(it).idofcategory
            } ?: return

        viewModel.loadCardsForCategory(categoryId)



        val cardView = binding.cardView
        val flipTextView = binding.flipTextView
        var showingQuestion = true

        binding.showAnswerButton.setOnClickListener {
            val scale = requireContext().resources.displayMetrics.density
            cardView.cameraDistance = 8000 * scale

            val flipOut = ObjectAnimator.ofFloat(cardView, "rotationY", 0f, 90f)
            val flipIn = ObjectAnimator.ofFloat(cardView, "rotationY", -90f, 0f)

            flipOut.duration = 200
            flipIn.duration = 200

            flipOut.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {

                    val card = viewModel.currentCard.value
                    if (card != null) {
                        flipTextView.text = if (showingQuestion) card.answer else card.question
                    }
                    flipIn.start()
                }
            })

            flipOut.start()
            showingQuestion = !showingQuestion
        }

        viewModel.currentCard.observe(viewLifecycleOwner) { card ->
            if (card != null) {
                binding.flipTextView.text = card.question
                showingQuestion = true
            } else {
                binding.flipTextView.text = "No cards"
            }
        }
        binding.addbutton.setOnClickListener(){
       val action = Display_fragDirections.disToEditOrAdd(-1L,categoryId)
       findNavController().navigate(action)
   }
        binding.editbutton.setOnClickListener(){
            val currentCard = viewModel.currentCard.value
            if (currentCard != null) {
                val cardId = currentCard.cardid
                val action = Display_fragDirections.disToEditOrAdd(cardId, categoryId)
                findNavController().navigate(action)
            }
        }
    }

}