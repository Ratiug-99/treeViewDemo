package com.ratiug.myapplication.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var test = MutableLiveData<RecyclerView.Adapter<RecyclerView.ViewHolder>?>(null)
}
