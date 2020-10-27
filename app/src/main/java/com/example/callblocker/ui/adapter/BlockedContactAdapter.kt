package com.example.callblocker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.callblocker.data.BlockedContact
import com.example.callblocker.databinding.ItemBlockedContactBinding
import com.example.callblocker.listener.OnRecyclerViewItemClickListener

class BlockedContactAdapter(onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener)
    : ListAdapter<BlockedContact, BlockedContactAdapter.ViewHolder>(
    BlockedContactItemDiffCallback()
)
{
    val listener = onRecyclerViewItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBlockedContactBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blockedContact = getItem(position)
        holder.bind(blockedContact, listener)
    }

    class ViewHolder(binding: ItemBlockedContactBinding) : RecyclerView.ViewHolder(binding.root) {
        private val itemBlockedContactBinding : ItemBlockedContactBinding = binding
        fun bind(blockedContact: BlockedContact, listener: OnRecyclerViewItemClickListener){
            itemBlockedContactBinding.blockedContact = blockedContact
            itemBlockedContactBinding.executePendingBindings()
            itemBlockedContactBinding.btnDeleteNumber.setOnClickListener{
                listener.onClick(blockedContact)
            }
        }
    }

    class BlockedContactItemDiffCallback : DiffUtil.ItemCallback<BlockedContact>(){
        override fun areItemsTheSame(oldItem: BlockedContact, newItem: BlockedContact): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: BlockedContact, newItem: BlockedContact): Boolean {
            return oldItem == newItem
        }

    }

}