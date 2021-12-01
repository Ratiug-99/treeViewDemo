package com.ratiug.myapplication.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ratiug.myapplication.R
import com.ratiug.myapplication.checkabletreeview.TaskGroupNode
import com.ratiug.myapplication.checkabletreeview.TaskGroupRow
import com.ratiug.myapplication.checkabletreeview.TaskRow
import com.ratiug.myapplication.checkabletreeview.TreeNode
import com.ratiug.myapplication.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var binding: MainFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        Log.d("DBG", "onViewCreated: ")
        setupObserver()
        binding?.testbtn?.setOnClickListener {
//            Log.d("DBG", "onViewCreated: ${binding?.treeView?.selectedValues()}")
            val listOfCheckedTasks: ArrayList<TreeNode<TaskGroupNode>> = arrayListOf()
            binding?.treeView?.selectedValuesTasks(listOfCheckedTasks)
//            Log.d("DBG", "selected tasks: ${listOfCheckedTasks.size}\n")
        }
    }

    private fun setupObserver() {
        if (viewModel.test.value == null) {
            setupAdapter()
        } else {
            binding?.treeView?.adapter = viewModel.test.value
        }
    }

    private fun setupAdapter() {

        val workOrderTasks =
            TreeNode(
                TaskGroupNode(
                    TaskGroupRow(
                        0, 0, "Work Order Tasks", "descRu", 0, 0, 0, 0, 0, emptyList()

                    )
                )
            )
        workOrderTasks.isExpanded = true
        val tg1 =
            TreeNode(
                TaskGroupNode(
                    TaskGroupRow(
                        0, 0, "Task Group Name", "descRu", 0, 0, 0, 0, 0, emptyList()

                    )
                ),
                workOrderTasks
            )

        val sa1 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Sub Asset Name", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            tg1
        )

        val p1 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Position Name", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            sa1
        )
        val tasks1: ArrayList<TreeNode<TaskGroupNode>> = arrayListOf()

        for (i in 1..1000) {
            tasks1.add(
                TreeNode(
                    TaskGroupNode(
                        task = TaskRow(
                            1, "$i", "11", "3", "1", "2", "1", "1", "2", "", "Task$i", ""
                        )
                    ),
                    p1
                )
            )
        }

        tasks1.reverse()

        p1.setChildren(tasks1)
        sa1.setChildren(listOf(p1))
        tg1.setChildren(listOf(sa1))

        val tg2 =
            TreeNode(
                TaskGroupNode(
                    TaskGroupRow(
                        0, 0, "Task Group Name2", "descRu", 0, 0, 0, 0, 0, emptyList()

                    )
                ),
                workOrderTasks
            )

        val sa2 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Sub Asset Name2", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            tg2
        )

        val p2 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Position Name2", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            sa2
        )
        val tasks2: ArrayList<TreeNode<TaskGroupNode>> = arrayListOf()

        for (i in 1..1000) {
            tasks2.add(
                TreeNode(
                    TaskGroupNode(
                        task = TaskRow(
                            1, "$i", "11", "3", "1", "2", "1", "1", "2", "", "Task${i + 100}", ""
                        )
                    ),
                    p2
                )
            )
        }

        p2.setChildren(tasks2)
        sa2.setChildren(listOf(p2))
        tg2.setChildren(listOf(sa2))
        val tg3 =
            TreeNode(
                TaskGroupNode(
                    TaskGroupRow(
                        0, 0, "Task Group Name3", "descRu", 0, 0, 0, 0, 0, emptyList()

                    )
                ),
                workOrderTasks
            )

        val sa3 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Sub Asset Name3", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            tg3
        )

        val p3 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Position Name3", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            sa3
        )
        val tasks3: ArrayList<TreeNode<TaskGroupNode>> = arrayListOf()

        for (i in 1..1000) {
            tasks3.add(
                TreeNode(
                    TaskGroupNode(
                        task = TaskRow(
                            1, "$i", "11", "3", "1", "3", "1", "1", "3", "", "Task${i + 100}", ""
                        )
                    ),
                    p3
                )
            )
        }

        p3.setChildren(tasks3)
        sa3.setChildren(listOf(p3))
        tg3.setChildren(listOf(sa3))
        val tg4 =
            TreeNode(
                TaskGroupNode(
                    TaskGroupRow(
                        0, 0, "Task Group Name4", "descRu", 0, 0, 0, 0, 0, emptyList()

                    )
                ),
                workOrderTasks
            )

        val sa4 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Sub Asset Name4", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            tg4
        )

        val p4 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Position Name4", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            sa4
        )
        val tasks4: ArrayList<TreeNode<TaskGroupNode>> = arrayListOf()

        for (i in 1..1000) {
            tasks4.add(
                TreeNode(
                    TaskGroupNode(
                        task = TaskRow(
                            1, "$i", "11", "3", "1", "4", "1", "1", "4", "", "Task${i + 100}", ""
                        )
                    ),
                    p4
                )
            )
        }

        p4.setChildren(tasks4)
        sa4.setChildren(listOf(p4))
        tg4.setChildren(listOf(sa4))
        val tg5 =
            TreeNode(
                TaskGroupNode(
                    TaskGroupRow(
                        0, 0, "Task Group Name5", "descRu", 0, 0, 0, 0, 0, emptyList()

                    )
                ),
                workOrderTasks
            )

        val sa5 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Sub Asset Name5", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            tg5
        )

        val p5 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Position Name5", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            sa5
        )
        val tasks5: ArrayList<TreeNode<TaskGroupNode>> = arrayListOf()

        for (i in 1..1000) {
            tasks5.add(
                TreeNode(
                    TaskGroupNode(
                        task = TaskRow(
                            1, "$i", "11", "3", "1", "5", "1", "1", "5", "", "Task${i + 100}", ""
                        )
                    ),
                    p5
                )
            )
        }

        p5.setChildren(tasks5)
        sa5.setChildren(listOf(p5))
        tg5.setChildren(listOf(sa5))
        val tg6 =
            TreeNode(
                TaskGroupNode(
                    TaskGroupRow(
                        0, 0, "Task Group Name6", "descRu", 0, 0, 0, 0, 0, emptyList()

                    )
                ),
                workOrderTasks
            )

        val sa6 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Sub Asset Name6", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            tg6
        )

        val p6 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Position Name6", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            sa6
        )
        val tasks6: ArrayList<TreeNode<TaskGroupNode>> = arrayListOf()

        for (i in 1..1000) {
            tasks6.add(
                TreeNode(
                    TaskGroupNode(
                        task = TaskRow(
                            1, "$i", "11", "3", "1", "6", "1", "1", "6", "", "Task${i + 100}", ""
                        )
                    ),
                    p6
                )
            )
        }

        p6.setChildren(tasks6)
        sa6.setChildren(listOf(p6))
        tg6.setChildren(listOf(sa6))
        val tg7 =
            TreeNode(
                TaskGroupNode(
                    TaskGroupRow(
                        0, 0, "Task Group Name7", "descRu", 0, 0, 0, 0, 0, emptyList()

                    )
                ),
                workOrderTasks
            )

        val sa7 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Sub Asset Name7", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            tg7
        )

        val p7 = TreeNode(
            TaskGroupNode(
                TaskGroupRow(
                    0, 0, "Position Name7", "descRu", 0, 0, 0, 0, 0, emptyList()

                )
            ),
            sa7
        )
        val tasks7: ArrayList<TreeNode<TaskGroupNode>> = arrayListOf()

        for (i in 1..1000) {
            tasks7.add(
                TreeNode(
                    TaskGroupNode(
                        task = TaskRow(
                            1, "$i", "11", "3", "1", "7", "1", "1", "7", "", "Task${i + 100}", ""
                        )
                    ),
                    p7
                )
            )
        }

        p7.setChildren(tasks7)
        sa7.setChildren(listOf(p7))
        tg7.setChildren(listOf(sa7))
        workOrderTasks.setChildren(listOf(tg1, tg2, tg3, tg4, tg5, tg6, tg7))
        binding?.treeView?.setRoots(listOf(workOrderTasks))
        viewModel.test.value = binding?.treeView?.adapter
    }
}
