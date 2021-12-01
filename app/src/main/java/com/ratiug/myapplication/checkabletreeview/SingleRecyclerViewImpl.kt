package com.ratiug.myapplication.checkabletreeview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ratiug.myapplication.R

private const val TAG = "DBG | SingleRV"

class SingleRecyclerViewImpl<T : Checkable> : RecyclerView, CheckableTreeView<T> {

    private val adapter: TreeAdapter<T> by lazy {
        val indentation = indentation.px
        TreeAdapter(indentation, context)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, style: Int) : super(
        context,
        attributeSet,
        style
    )

    init {
        setAdapter(adapter)
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
    }

    @UiThread
    override fun setRoots(roots: List<TreeNode<T>>) {
        with(adapter) {
            nodes.clear()
            nodes.addAll(roots)

            notifyDataSetChanged()
        }
    }

    fun selectedValuesTasks(listOfCheckedTasks: ArrayList<TreeNode<TaskGroupNode>>) {
        with(adapter) {
            test2(listOfCheckedTasks)
        }
    }
}

class TreeAdapter<T : Checkable>(private val indentation: Int, val context: Context) :
    RecyclerView.Adapter<TreeAdapter<T>.ViewHolder>() {

    internal val nodes: MutableList<TreeNode<T>> = mutableListOf()
    var isGoCollapsing = false
    private val expandCollapseToggleHandler: (TreeNode<T>, ViewHolder) -> Unit =
        { node, viewHolder ->
            if (node.isExpanded) {
                collapse(viewHolder.adapterPosition)
            } else {
                expand(viewHolder.adapterPosition)
            }
            val expandIndicator =
                viewHolder.itemView.findViewById<ExpandToggleButton>(R.id.expandIndicator)
            expandIndicator.startToggleAnimation(node.isExpanded)
        }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return nodes[position].id
    }

    override fun getItemViewType(position: Int): Int {
        val _node = nodes[position] as TreeNode<TaskGroupNode>
        if (_node.getValue().taskGroup != null) return 0
        if (_node.getValue().task != null) return 1
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            1 -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_checkable_task, parent, false),
                    indentation
                )
            }
            else -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_checkable_text, parent, false),
                    indentation
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return nodes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(nodes[position])
    }

    @UiThread
    private fun expand(position: Int) {
        if (position >= 0) {
            // expand
            val node = nodes[position]
            val insertPosition = position + 1
            val insertedSize = node.getChildren().size
            nodes.addAll(insertPosition, node.getChildren())
            node.isExpanded = true
            notifyItemRangeInserted(insertPosition, insertedSize)
        }
    }

    @UiThread
    private fun expandIfExpandedButNotExist(position: Int) {
        if (position >= 0) {
            // expand
            val node = nodes[position]
            val insertPosition = position + 1
            val insertedSize = node.getChildren().size
            if (!nodes.contains(node.getChildren()[0])) {
                nodes.addAll(insertPosition, node.getChildren())
                node.isExpanded = true
                notifyItemRangeInserted(insertPosition, insertedSize)
            }
        }
    }

    @UiThread
    private fun checkIfExpanded(position: Int) {
        if (position >= 0) {
            // expand
            val node = nodes[position]
//            if (node.isExpanded &&)
            val insertPosition = position + 1
            val insertedSize = node.getChildren().size
            nodes.addAll(insertPosition, node.getChildren())
            node.isExpanded = true
            notifyItemRangeInserted(insertPosition, insertedSize)
        }
    }

    @UiThread
    private fun collapse(position: Int) {
        // collapse
        if (position >= 0) {
            val node = nodes[position]
            var removeCount = 0
            fun removeChildrenFrom(cur: TreeNode<T>) {
                nodes.remove(cur)
                removeCount++
                if (cur.isExpanded) {
                    cur.getChildren().forEach {
                        removeChildrenFrom(it)
                    }
                    node.isExpanded = false
                }
            }
            node.getChildren().forEach { removeChildrenFrom(it) }
            node.isExpanded = false
            notifyItemRangeRemoved(position + 1, removeCount)
        }
    }

    fun test2(listOfCheckedTasks: ArrayList<TreeNode<TaskGroupNode>>) {
        return nodes[0].getSelectedTasks(listOfCheckedTasks)
    }

    inner class ViewHolder(view: View, private val _indentation: Int) :
        RecyclerView.ViewHolder(view) {

        internal fun bind(node: TreeNode<T>) {
            if ((node as TreeNode<TaskGroupNode>).getValue().taskGroup != null) {
                val _node = node as TreeNode<TaskGroupNode>
                val indentation = itemView.findViewById<Space>(R.id.indentation)
                val checkText = itemView.findViewById<CheckBoxEx>(R.id.checkText)
                val tvDrawing = itemView.findViewById<TextView>(R.id.tvDrawingNumber)
                val expandIndicator =
                    itemView.findViewById<ExpandToggleButton>(R.id.expandIndicator)
                indentation.minimumWidth = _indentation * node.getLevel()

                val tg = _node.getValue().taskGroup
                checkText.text = tg?.description
                tvDrawing.text = ""
                if (tvDrawing.text.isEmpty()) {
                    tvDrawing.visibility = View.GONE
                } else {
                    tvDrawing.visibility = View.VISIBLE
                }
                checkText.setOnCheckedChangeListener(null)
                val state = _node.getCheckedStatus()
                checkText.isChecked = state.allChildrenChecked
                checkText.setIndeterminate(state.hasChildChecked)
                checkText.setOnCheckedChangeListener { _, isChecked ->
                    _node.setChecked(isChecked)
                    notifyDataSetChanged()
                }
                expandIndicator.startToggleAnimation(node.isExpanded)

                if (_node.isLeaf()) {
                    expandIndicator.visibility = View.GONE
                } else {
                    expandIndicator.visibility = View.VISIBLE

                    if (_node.isExpanded) {
                        itemView.post {
                            expandIfExpandedButNotExist(this.adapterPosition)
                        }
                    }

                    expandIndicator.setOnClickListener {
                        expandCollapseToggleHandler(node, this)
                    }
                }
            } else {
                val _node = node as TreeNode<TaskGroupNode>
                val indentation = itemView.findViewById<Space>(R.id.indentation)
                val checkText = itemView.findViewById<CheckBoxEx>(R.id.checkText)
                val tvDrawing = itemView.findViewById<TextView>(R.id.tvDrawingNumber)
                val expandIndicator =
                    itemView.findViewById<ExpandToggleButton>(R.id.expandIndicator)
                indentation.minimumWidth = _indentation * node.getLevel()

                val task = _node.getValue().task
                checkText.text = task?.woTaskAssetID
                tvDrawing.text = task?.woID
                if (tvDrawing.text.isEmpty()) {
                    tvDrawing.visibility = View.GONE
                } else {
                    tvDrawing.visibility = View.VISIBLE
                }
                checkText.setOnCheckedChangeListener(null)
                val state = _node.getCheckedStatus()
                checkText.isChecked = state.allChildrenChecked
                checkText.setIndeterminate(state.hasChildChecked)
                checkText.setOnCheckedChangeListener { _, isChecked ->
                    _node.setChecked(isChecked)
                    notifyDataSetChanged()
                }

                if (_node.isLeaf()) {
                    expandIndicator.visibility = View.GONE
                } else {
                    expandIndicator.visibility = View.VISIBLE

                    expandIndicator.setOnClickListener { expandCollapseToggleHandler(node, this) }
                }
            }
        }
    }
}
