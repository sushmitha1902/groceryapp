package ghanam.com.abdo

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import ghanam.com.abdo.adapters.ChatMessageAdapter
import ghanam.com.abdo.databinding.FragmentChatBinding
import ghanam.com.abdo.dataclasses.ChatMessage

class ChatFragment : Fragment() {


    lateinit var binding: FragmentChatBinding
    private lateinit var msgAdapter: ChatMessageAdapter
    private val messages = mutableListOf(
        ChatMessage("Hello I am robot how can I help you ?",0)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentChatBinding.inflate(inflater,container,  false)
        initializeChat()
        binding.exitButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_chatFragment_to_homeFragment)
        }
        return binding.root
    }

    private fun initializeChat() {
        msgAdapter = ChatMessageAdapter(messages)

        binding.apply {
            messagesRecycler.adapter=msgAdapter
            binding.messagesRecycler.layoutManager = LinearLayoutManager(context)
            sendButton.setOnClickListener {
                sendMessage(messageInputText.text)
            }
        }
    }

    private fun sendMessage(text: Editable?) {
        if (text.isNullOrEmpty()){
            Toast.makeText(context,"please enter a message first", Toast.LENGTH_SHORT).show()
            return
        }else{
            msgAdapter.addMessage(ChatMessage(text.toString(),1))
            msgAdapter.addMessage(ChatMessage(text.toString(),0))
            binding.messageInputText.setText("")
            Log.d(TAG, msgAdapter.itemCount.toString())
            return
        }
    }

}