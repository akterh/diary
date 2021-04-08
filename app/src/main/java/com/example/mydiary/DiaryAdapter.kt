package com.example.mydiary

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiary.data.DatabaseManager.DiaryEntry.TABLE_NAME
import com.example.mydiary.data.DatabaseManager.DiaryEntry._ID
import com.example.mydiary.data.Diary
import com.example.mydiary.data.DiaryDBHelper
import kotlinx.android.synthetic.main.recycler_diary_item.view.*

class DiaryAdapter(private var diaryList: MutableList<Diary>):RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): DiaryViewHolder {
        val context = viewGroup.context
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false
        val view =inflater.inflate(
            R.layout.recycler_diary_item,
            viewGroup,
            shouldAttachToParentImmediately
        )
        view.delete_btn.setOnClickListener {

            val mDBHelper = DiaryDBHelper(view.context)
            val db = mDBHelper.writableDatabase
            val selection = "$_ID =?"
            val selectionArgs = arrayOf("${diaryList[position].id}")
            db.delete(TABLE_NAME, selection, selectionArgs)
            diaryList.removeAt(position)

            notifyDataSetChanged()
            Toast.makeText(context, "diary deleted", Toast.LENGTH_SHORT).show()

        }

        return DiaryViewHolder(view)


    }


    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val item = diaryList[position]
        holder.bindDiary(item)


    }

    override fun getItemCount(): Int {
        return diaryList.size

    }


     class DiaryViewHolder(v: View):RecyclerView.ViewHolder(v), View.OnClickListener{


         private var view:View = v
         private lateinit var diary: Diary
         private var date:TextView = view.findViewById(R.id.date_txt)
         private var title:TextView = view.findViewById(R.id.title_txt)


         override fun onClick(v: View?){
            val context = itemView.context
            val intent = Intent(context, NewDiaryActivity::class.java)

            intent.putExtra("rowId", diary.id)
            context.startActivity(intent)

         }



         init {

             v.setOnClickListener(this)
         }
         fun bindDiary(diary: Diary){
             this.diary = diary
             date.text = diary.date
             title.text = diary.title
         }


     }


}