package com.example.goatmessenger.adapter

import android.R.attr.button
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.goatmessenger.R
import com.example.goatmessenger.data.Message
import com.example.goatmessenger.databinding.MessageItemBinding


class ChatMessageAdapter () : RecyclerView.Adapter<ChatMessageAdapter.MyViewHolder>()
{
    lateinit var context: Context;
    private var items:List<Message> ?=null
    var onclicklistener:OnClickListener?=null
    //lateinit var binding:PatternCoinSummeryBinding;

    constructor(items: List<Message>?, context: Context,onClickListener: OnClickListener) : this()
    {
        this.context=context;
        this.onclicklistener=onclicklistener
        this.items=items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding=MessageItemBinding.inflate(LayoutInflater.from(context),parent,false)

        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        when(holder){
            is MyViewHolder ->{
                val model=items?.get(position)
                holder.binding.message.text=model?.text

                if (model?.sender==0L){
                    holder.binding.message.apply {
                        background=ContextCompat.getDrawable(context, R.drawable.message_outgoing)

                        val params = getLayoutParams() as RelativeLayout.LayoutParams
                        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                        right
                        setLayoutParams(params);
                    }

                }


                holder.itemView.setOnClickListener {
                    onclicklistener?.onLongClick(model!!.id)

                }

            }
        }


    }

    override fun getItemCount(): Int {
        return items!!.size


    }
    class MyViewHolder(binding: MessageItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: MessageItemBinding
        init {
            this.binding = binding
        }
    }
     interface OnClickListener{
        fun onLongClick(id:Long)
    }

}