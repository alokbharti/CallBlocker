package com.example.callblocker.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.callblocker.R
import com.example.callblocker.data.BlockedContact
import com.example.callblocker.databinding.DialogAddBlockedContactBinding

class AddBlockedContactDialog(addBlockedContactListener: AddBlockedContactListener) : DialogFragment(){
    private lateinit var binding: DialogAddBlockedContactBinding
    private val listener = addBlockedContactListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddBlockedContactBinding.inflate(inflater, container, false)
        binding.dialog = this
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddNumber.setOnClickListener{
            var name = binding.etPersonName.text.toString()
            val number = binding.etPersonNumber.text.toString()
            if (name.isEmpty()){
                name = "Unknown" //default value
            }
            if (number.isEmpty()){
                Toast.makeText(context, getString(R.string.empty_phone_message), Toast.LENGTH_SHORT)
                    .show()
            } else {
                dismiss()
                val blockedContact = BlockedContact(name = name, number = number)
                listener.onAddBlockedContact(blockedContact)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (dialog?.window != null) {
            dialog!!.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog!!.setCancelable(false)
            dialog!!.setCanceledOnTouchOutside(false)
        }
    }

    interface AddBlockedContactListener{
        fun onAddBlockedContact(blockedContact: BlockedContact)
    }
}