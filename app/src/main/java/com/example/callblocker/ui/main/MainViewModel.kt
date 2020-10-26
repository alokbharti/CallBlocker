package com.example.callblocker.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.callblocker.data.BlockedContact
import com.example.callblocker.data.DataRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    fun insertBlockedContact(blockedContact: BlockedContact){
        viewModelScope.launch {
            dataRepository.insertBlockedContactToDb(blockedContact)
        }
    }

    fun getBlockedContact(number: String) : LiveData<BlockedContact>{
        return dataRepository.getBlockedContactByNumber(number).asLiveData()
    }

    fun getAllBlockedContacts() : LiveData<List<BlockedContact>> {
        return dataRepository.getAllBlockedContact().asLiveData()
    }

    fun deleteBlockedContact(blockedContact: BlockedContact){
        viewModelScope.launch {
            dataRepository.deleteBlockedContactFromDb(blockedContact)
        }
    }
}