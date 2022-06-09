package com.example.goatmessenger.data

import android.content.Context
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executor
import java.util.concurrent.Executors

interface ChatRepository {
    fun getContacts(): LiveData<List<Contact>>
    fun findContact(id: Long): LiveData<Contact?>
    fun getMessages(): MutableLiveData<List<Message>>
    fun addMessages(msg:Message)
    fun deleteMessage(msg: Message)
    // Add your methods definition here
}

class DefaultChatRepository internal constructor() : ChatRepository
{

    companion object {
        private var instance: DefaultChatRepository? = null

        fun getInstance(context: Context): DefaultChatRepository {
            return instance ?: synchronized(this) {
                instance ?: DefaultChatRepository().also {
                    instance = it
                }
            }
        }
    }

    private var currentContact: Contact = Contact.CONTACTS[0]

    private val messages = mutableListOf(
        Message(1L, currentContact.id, "Hey...!", System.currentTimeMillis()),
        Message(2L, currentContact.id, "What's Up...", System.currentTimeMillis()),
        Message(3L, currentContact.id, "Send me a message", System.currentTimeMillis()),
    )




    @MainThread
    override fun getContacts(): LiveData<List<Contact>> {
        return MutableLiveData<List<Contact>>().apply {
            postValue(Contact.CONTACTS)
        }
    }

    @MainThread
    override fun findContact(id: Long): LiveData<Contact?> {
        return MutableLiveData<Contact>().apply {
            postValue(Contact.CONTACTS.find { it.id == id })
        }
    }

    override fun getMessages(): MutableLiveData<List<Message>> {
//        return MutableLiveData<List<Message>>().apply {
//            postValue(messages)
//        }
        var livedata=MutableLiveData<List<Message>>()
        livedata.apply {
            value=messages
        }

        Log.i("mag2851-live",livedata.value.toString())
        return livedata


    }

    override fun addMessages(msg: Message) {
        messages.add(msg)
    }

    override fun deleteMessage(msg: Message) {
        messages.remove(msg)
    }


    // Add your methods implantation here

}
