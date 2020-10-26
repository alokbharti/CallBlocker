package com.example.callblocker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BlockedContactDao {

    @Insert
    fun insertBlockedContact(blockedContact: BlockedContact)

    @Delete
    fun deleteBlockedContact(blockedContact: BlockedContact)

    @Query("SELECT * FROM BlockedContact")
    fun getAllBlockedContactList(): Flow<List<BlockedContact>>

    @Query("SELECT * FROM BlockedContact WHERE number = :numberToBeSearched")
    fun getBlockedContact(numberToBeSearched : String): Flow<BlockedContact>
}