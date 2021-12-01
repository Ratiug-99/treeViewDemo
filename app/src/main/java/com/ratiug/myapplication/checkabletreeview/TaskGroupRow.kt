package com.ratiug.myapplication.checkabletreeview

data class TaskGroupRow(
    val viewType: Int = 0,
    val taskGroupID: Int,
    val description: String,
    val descriptionRU: String,
    val inprgCount: Int,
    val complCount: Int,
    val planCount: Int,
    val monCount: Int,
    val otherCount: Int,
    val children: List<SubAssetRow>
)

data class SubAssetRow(
    val description: String,
    val descriptionRU: String,
    val inprgCount: Int,
    val complCount: Int,
    val planCount: Int,
    val monCount: Int,
    val otherCount: Int,
    val children: List<PositionRow>
)

data class PositionRow(
    val description: String,
    val descriptionRU: String,
    val inprgCount: Int,
    val complCount: Int,
    val planCount: Int,
    val monCount: Int,
    val otherCount: Int,
    val children: List<TaskRow>
)

data class TaskRow(
    val viewType: Int = 2,

    var woID: String = "",
    var woTaskUID: String = "",
    var woTaskRowNumber: String = "",
    var woTaskIsActive: String = "",
    var woTaskStatusID: String = "",
    var woTaskCompletedTime: String = "",
    var woTaskAssetSubID: String = "",
    var woTaskAssetCountID: String = "",
    var woTaskMajorEQID: String = "",
    var woTaskAssetID: String = "",
    var woTaskPosition: String = "",
)
