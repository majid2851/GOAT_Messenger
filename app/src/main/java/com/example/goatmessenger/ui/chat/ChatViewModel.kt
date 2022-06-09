package com.example.goatmessenger.ui.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.goatmessenger.data.ChatRepository
import com.example.goatmessenger.data.DefaultChatRepository
import com.example.goatmessenger.data.Message

class ChatViewModel @JvmOverloads constructor(
    application: Application,
    private val repository: ChatRepository = DefaultChatRepository.getInstance(application)
) : AndroidViewModel(application) {

    private val contactId = MutableLiveData<Long>()

    /**
     * The contact of this chat.
     */
    val contact = contactId.switchMap { id -> repository.findContact(id) }

    fun setContactId(id: Long) {
        contactId.value = id
    }
    fun getMessages(): MutableLiveData<List<Message>> {
        return repository.getMessages()
    }

    fun addMessages(msg: Message) {
        repository.addMessages(msg)
    }

    fun deleteMessage(msg: Message) {
        repository.deleteMessage(msg)
    }

}
