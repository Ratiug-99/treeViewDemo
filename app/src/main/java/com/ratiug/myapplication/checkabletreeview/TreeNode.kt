package com.ratiug.myapplication.checkabletreeview

import androidx.lifecycle.MutableLiveData

data class StringNode(val str: String) : Checkable(false) {
    override fun toString(): String {
        return str
    }
}

data class TaskGroupNode(val taskGroup: TaskGroupRow? = null, val task: TaskRow? = null) :
    Checkable(false)

data class SubAssetNode(val subAsset: SubAssetRow) : Checkable(false)
data class PositionNode(val positionNode: PositionRow) : Checkable(false)
data class TaskNode(val task: TaskRow) : Checkable(false)

class TreeNode<T : Checkable>(
    private val value: T,
    private val parent: TreeNode<T>?,
    private var children: List<TreeNode<T>>,
    override var isExpanded: Boolean = false
) : HasId, Expandable {
    val listOfCheckedTasksLD = MutableLiveData<ArrayList<TreeNode<TaskGroupNode>>>(arrayListOf())

    override val id: Long by lazy {
        IdGenerator.generate()
    }

    // constructor for root node
    constructor(value: T) : this(value, null, emptyList())

    // constructor for leaf node
    constructor(value: T, parent: TreeNode<T>) : this(value, parent, emptyList())

    // constructor for parent node
    constructor(value: T, children: List<TreeNode<T>>) : this(value, null, children)

    fun isTop(): Boolean {
        return parent == null
    }

    fun isLeaf(): Boolean {
        return children.isEmpty()
    }

    fun getValue(): T {
        return value
    }

    fun getLevel(): Int {
        fun stepUp(node: TreeNode<T>): Int {
            return node.parent?.let { 1 + stepUp(it) } ?: 0
        }
        return stepUp(this)
    }

    fun setChildren(children: List<TreeNode<T>>) {
        this.children = children
    }

    fun getChildren(): List<TreeNode<T>> {
        return children
    }

    fun setChecked(isChecked: Boolean) {
        value.checked = isChecked
        // cascade the action to children
        children.forEach {
            it.setChecked(isChecked)
        }
    }

    fun getCheckedStatus(): NodeCheckedStatus {
        if (isLeaf()) return NodeCheckedStatus(value.checked, value.checked)
        var hasChildChecked = false
        var allChildrenChecked = true
        children.forEach {
            val checkedStatus = it.getCheckedStatus()
            hasChildChecked = hasChildChecked || checkedStatus.hasChildChecked
            allChildrenChecked = allChildrenChecked && checkedStatus.allChildrenChecked
        }
        return NodeCheckedStatus(hasChildChecked, allChildrenChecked)
    }

    fun getAggregatedValues(): List<T> {
        return if (isLeaf()) {
            if (value.checked) listOf(value) else emptyList()
        } else {
            if (getCheckedStatus().allChildrenChecked) {
                listOf(value)
            } else {
                val result = mutableListOf<T>()
                children.forEach {
                    result.addAll(it.getAggregatedValues())
                }
                result
            }
        }
    }

    fun getSelectedTasks(listOfCheckedTasks: ArrayList<TreeNode<TaskGroupNode>>) {
        children.forEach {
            if ((it as TreeNode<TaskGroupNode>).value?.task != null && (it as TreeNode<TaskGroupNode>).value.checked) {
                listOfCheckedTasks.add(it)
            }
            it.getSelectedTasks(listOfCheckedTasks)
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}
