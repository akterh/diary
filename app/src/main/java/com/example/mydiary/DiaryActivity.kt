package com.example.mydiary

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiary.data.DatabaseManager.DiaryEntry.COLUMN_DATE
import com.example.mydiary.data.DatabaseManager.DiaryEntry.COLUMN_DIARY
import com.example.mydiary.data.DatabaseManager.DiaryEntry.COLUMN_TITLE
import com.example.mydiary.data.DatabaseManager.DiaryEntry.TABLE_NAME
import com.example.mydiary.data.DatabaseManager.DiaryEntry._ID
import com.example.mydiary.data.Diary
import com.example.mydiary.data.DiaryDBHelper

class DiaryActivity : AppCompatActivity() {

    private var diariList :ArrayList<Diary> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recycle :RecyclerView
    lateinit var adapter: DiaryAdapter
    lateinit var mDBHelper:DiaryDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        recycle = findViewById(R.id.recycler_view)


        mDBHelper = DiaryDBHelper(this)

        displayDataInfo()



    }

    private fun displayDataInfo() {
       val db = mDBHelper.readableDatabase
        val projection = arrayOf(_ID, COLUMN_DATE, COLUMN_TITLE, COLUMN_DIARY)
        val cursor:Cursor = db.query(TABLE_NAME,projection,null,null,null,null,null)
        val idColumnIndex = cursor.getColumnIndexOrThrow(_ID)
        val dateColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE)
        val titleColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE)
        val diaryColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_DIARY)
        while (cursor.moveToNext()){

            val currentId = cursor.getInt(idColumnIndex)
            val currentDate = cursor.getString(dateColumnIndex)
            val currentTitle = cursor.getString(titleColumnIndex)
            val currentDiary= cursor.getString(diaryColumnIndex)
            diariList.add(Diary(currentId,currentDate,currentTitle,currentDiary))
        }
        cursor.close()
        linearLayoutManager = LinearLayoutManager(this)
        recycle.layoutManager = linearLayoutManager
        adapter = DiaryAdapter(diariList)
        recycle.adapter = adapter


    }

    override fun onStart() {
        super.onStart()
        diariList.clear()
        displayDataInfo()
    }

    fun newDiary(view: View){
        val intent = Intent(this,NewDiaryActivity::class.java)
        startActivity(intent)


    }
}