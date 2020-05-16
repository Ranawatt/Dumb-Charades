package com.example.dumb_charades.directshare

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dumb_charades.R

class SelectContactActivity : AppCompatActivity() {

    private val contactAdapter = object : RecyclerView.Adapter<ContactViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
            val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_contact, parent, false) as TextView
            return ContactViewHolder(textView)
        }

        override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
            val contact = Contact.contacts[position]
            contact.bind(holder.itemView as TextView)
            holder.itemView.setOnClickListener {
                val data = Intent()
                data.putExtra(Contact.id, position)
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }

        override fun getItemCount() = Contact.contacts.size
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_contact)
        if (ACTION_SELECT_CONTACT != intent.action) {
            finish()
            return
        }

        // Set up the list of contacts
        findViewById<RecyclerView>(R.id.recycler_view).apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(this@SelectContactActivity)
        }
    }

    companion object {
        /**
         * The action string for Intents.
         */
        const val ACTION_SELECT_CONTACT =
            "com.example.dumb_charades.directshare.intent.action.SELECT_CONTACT"
    }
}
private class ContactViewHolder(textView: TextView) : RecyclerView.ViewHolder(textView)