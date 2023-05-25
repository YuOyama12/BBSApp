package com.yuoyama12.bbsapp.data

data class Thread(
    val threadId: String = "",
    val uId: String = "",
    val title: String = "",
    val postedDate: String = "",
    val modifiedDate: String = "",
    val messages : List<Message> = listOf()
)
