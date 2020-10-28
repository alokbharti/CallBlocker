package com.example.callblocker.ui.dialog

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
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
    private val PICK_CONTACT_REQUEST_CODE = 103

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

        binding.ivContactChooser.setOnClickListener{
            val pickContactIntent = Intent(
                Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI
            )

            pickContactIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST_CODE)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_CONTACT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            if (uri != null) {
                var c: Cursor? = null
                try {
                    c = requireActivity().contentResolver.query(
                        uri, arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
                        null, null, null
                    )
                    if (c != null && c.moveToFirst()) {
                        val number: String = c.getString(0)
                        binding.etPersonNumber.setText(number)
                    }
                } finally {
                    c?.close()
                }
            }
        }
    }
}