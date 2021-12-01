package com.ratiug.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ratiug.myapplication.checkabletreeview.TaskGroupNode
import com.ratiug.myapplication.checkabletreeview.TaskGroupRow
import com.ratiug.myapplication.checkabletreeview.TreeNode
import com.ratiug.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()

    }
}
