package com.example.callblocker.ui.main

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.provider.BlockedNumberContract.BlockedNumbers
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.callblocker.data.BlockedContact
import com.example.callblocker.databinding.MainFragmentBinding
import com.example.callblocker.listener.OnRecyclerViewItemClickListener
import com.example.callblocker.ui.adapter.BlockedContactAdapter
import com.example.callblocker.ui.dialog.AddBlockedContactDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(),
    AddBlockedContactDialog.AddBlockedContactListener,
    OnRecyclerViewItemClickListener
{

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: BlockedContactAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        adapter = BlockedContactAdapter(this)
        binding.adapter = adapter

        viewModel.getAllBlockedContacts().observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()){
                binding.tvNoContacts.visibility = VISIBLE
            } else {
                binding.tvNoContacts.visibility = GONE
                adapter.submitList(it)
            }
        })

        binding.fabAddContact.setOnClickListener{
            val dialog = AddBlockedContactDialog(this)
            dialog.show(requireActivity().supportFragmentManager, dialog.javaClass.simpleName)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onAddBlockedContact(blockedContact: BlockedContact) {
        viewModel.insertBlockedContact(blockedContact)
        val values = ContentValues()
        values.put(BlockedNumbers.COLUMN_ORIGINAL_NUMBER, blockedContact.number)
        requireActivity().contentResolver.insert(BlockedNumbers.CONTENT_URI, values)
    }

    override fun onClick(blockedContact: BlockedContact) {
        viewModel.deleteBlockedContact(blockedContact)
    }
}