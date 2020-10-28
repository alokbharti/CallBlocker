package com.example.callblocker.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.callblocker.R
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
    private lateinit var blockedContactList: List<BlockedContact>

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
                blockedContactList = it
            }
        })

        binding.fabAddContact.setOnClickListener{
            val dialog = AddBlockedContactDialog(this)
            dialog.show(requireActivity().supportFragmentManager, dialog.javaClass.simpleName)
        }
    }

    override fun onAddBlockedContact(blockedContact: BlockedContact) {
        //check for already added number
        for (dbBlockedContact in blockedContactList){
            if (blockedContact.number == dbBlockedContact.number) {
                Toast.makeText(context, getString(R.string.already_added_number), Toast.LENGTH_LONG).show()
                return
            }
        }
        viewModel.insertBlockedContact(blockedContact)
    }

    override fun onClick(blockedContact: BlockedContact) {
        viewModel.deleteBlockedContact(blockedContact)
    }
}