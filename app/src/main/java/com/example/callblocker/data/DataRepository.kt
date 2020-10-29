package com.example.callblocker.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(private val blockedContactDao: BlockedContactDao){

    suspend fun insertBlockedContactToDb(blockedContact: BlockedContact){
        withContext(Dispatchers.IO){
            blockedContactDao.insertBlockedContact(blockedContact)
        }
    }

    suspend fun deleteBlockedContactFromDb(blockedContact: BlockedContact){
        withContext(Dispatchers.IO){
            blockedContactDao.deleteBlockedContact(blockedContact)
        }
    }

    fun getBlockedContactByNumber(number: String): BlockedContact? {
        return blockedContactDao.getBlockedContact(number)
    }

    fun getAllBlockedContact(): Flow<List<BlockedContact>>{
        return blockedContactDao.getAllBlockedContactList();
    }
}