package com.yuoyama12.bbsapp.data

data class Thread(
    val threadId: String = "",
    val userId: String = "",
    val title: String = "",
    val createdDate: String = "",
    val modifiedDate: String = "",
    val latestMessageBody: String = "",
)
