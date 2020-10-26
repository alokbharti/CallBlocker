package com.example.callblocker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BlockedContact(
    val name: String,
    @PrimaryKey
    val number: String
)