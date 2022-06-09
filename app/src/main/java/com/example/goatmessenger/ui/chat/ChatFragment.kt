package com.example.goatmessenger.ui.chat

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goatmessenger.R
import com.example.goatmessenger.adapter.ChatMessageAdapter
import com.example.goatmessenger.data.Message
import com.example.goatmessenger.databinding.ChatFragmentBinding
import com.example.goatmessenger.getNavigationController


/**
 * The chat screen.
 */
class ChatFragment : Fragment() {

    var messageId:Long = 3

    companion object {
        private const val ARG_ID = "id"
        fun newInstance(id: Long) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, id)
                }
            }
    }

    private val viewModel: ChatViewModel by viewModels()

    //use this for setup view
    private var binding: ChatFragmentBinding? = null
    private lateinit var adapter:ChatMessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        enterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.slide_bottom)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChatFragmentBinding.inflate(inflater,container,false)

        binding!!.messages.layoutManager= LinearLayoutManager(requireActivity(),
            LinearLayoutManager.VERTICAL, false)

        Log.i("mag2851",viewModel.getMessages().value.toString())

       adapter=ChatMessageAdapter(viewModel.getMessages().value,requireActivity(),object : ChatMessageAdapter.OnClickListener {
           override fun onLongClick(id: Long) {
                showDialog(id)

           }
       })
        binding!!.messages.adapter=adapter




        binding!!.send.setOnClickListener {
            if (binding!!.input.text?.length!=0){
                messageId++
//                val contact=Contact(1L, "Amir", R.drawable.ic_avatar)
//                contact.createSimpleMessage()
                viewModel.addMessages(Message(messageId,1L, binding!!.input.text.toString(),
                    System.currentTimeMillis()))

                viewModel.getMessages().observe(requireActivity(), Observer {
                    adapter.notifyDataSetChanged()
                })


                binding!!.input.setText("")

                messageId++
                viewModel.addMessages(Message(messageId,0L,"Bale",System.currentTimeMillis()))


                viewModel.getMessages().observe(requireActivity(), Observer {
                    adapter.notifyDataSetChanged()
                })



            }


        }



        return binding?.root
    }

    private fun showDialog(id: Long)
    {
        val builder = AlertDialog.Builder(requireActivity())
        val v: View = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_, null)
        val tvDelete: Button
        val tvEdit: Button
        tvDelete = v.findViewById(R.id.delete)
        tvEdit = v.findViewById(R.id.edit)
        builder.setView(v)
        val dialog = builder.create()
        dialog.show()
        tvDelete.setOnClickListener {



            dialog.dismiss()
        }
        tvEdit.setOnClickListener {

        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        donNotChangeThisMethod()

        // write your code after this line here
    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        binding = null
        super.onDestroyView()
    }

    private fun donNotChangeThisMethod() {
        val id = arguments?.getLong(ARG_ID)
        if (id == null) {
            parentFragmentManager.popBackStack()
            return
        }
        viewModel.setContactId(id)
        viewModel.contact.observe(viewLifecycleOwner) { contact ->
            if (contact == null) {
                Toast.makeText(context, "Contact not found", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                getNavigationController().updateAppBar { name, icon ->
                    name.text = contact.name
                    icon.setImageDrawable(ContextCompat.getDrawable(requireContext(), contact.icon))
                    startPostponedEnterTransition()
                }
            }
        }
    }




}
