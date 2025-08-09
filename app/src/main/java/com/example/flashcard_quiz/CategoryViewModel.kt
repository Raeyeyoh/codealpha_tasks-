package com.example.flashcard_quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(private val dao: catagoreyDao) : ViewModel() {
     var catagoreyname= MutableLiveData<String>()
     val allCategories: LiveData<List<Categorytable>> = dao.getallcategory()
    fun addcategory (){
        val name = catagoreyname.value ?: return
        val category = Categorytable(name = name)
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertcategory(category)
        }
    }

    fun deleteCategory(category: Categorytable) {
        dao.deletecategory(category)
    }


}
