package com.example.mydiary

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DiaryAdapter(private var diaryList: MutableList<Diary>):RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiaryAdapter.DiaryViewHolder {

    }

    override fun onBindViewHolder(holder: DiaryAdapter.DiaryViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {

    }


     class DiaryViewHolder(v:View):RecyclerView.ViewHolder(v), View.onClickListener{


         private var view:View
         lateinit var diary:Diary


         fun onClick(v:View?){
             TODO("jfjdfgsdjgj")

         }



         init {
             view =v
             v.setOnClickListener(this)
         }

     }
}