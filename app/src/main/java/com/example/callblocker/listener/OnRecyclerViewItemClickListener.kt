package com.example.callblocker.listener

import com.example.callblocker.data.BlockedContact

interface OnRecyclerViewItemClickListener {
    fun onClick(blockedContact: BlockedContact)
}