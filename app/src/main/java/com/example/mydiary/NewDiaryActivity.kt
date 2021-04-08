package com.example.mydiary

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mydiary.data.DatabaseManager.DiaryEntry.COLUMN_DATE
import com.example.mydiary.data.DatabaseManager.DiaryEntry.COLUMN_DIARY
import com.example.mydiary.data.DatabaseManager.DiaryEntry.COLUMN_TITLE
import com.example.mydiary.data.DatabaseManager.DiaryEntry.TABLE_NAME
import com.example.mydiary.data.DatabaseManager.DiaryEntry._ID
import com.example.mydiary.data.DiaryDBHelper
import java.text.SimpleDateFormat
import java.util.*

class NewDiaryActivity : AppCompatActivity() {

    lateinit var date:TextView
    lateinit var title:EditText
    lateinit var diary:EditText

    private var id =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_diary)
        date = findViewById(R.id.edt_date)
        title = findViewById(R.id.title_for_new)
        diary =findViewById(R.id.edit_diary)





        val currentDate = SimpleDateFormat("EEE,d MMM yyy")
        date.text = currentDate.format(Date())



        id = intent.getIntExtra("rowId",0)
        Log.d("NewDiary","the passed Id is $id")

        if(id!=0){
            readDiary(id)
        }


    }

    private fun readDiary(id: Int) {
        val mDBHelper = DiaryDBHelper(this)

        val db = mDBHelper.readableDatabase
        val projection = arrayOf(COLUMN_DATE, COLUMN_TITLE, COLUMN_DIARY)
        val selection = "$_ID= ?"
        val selectionArgs = arrayOf("$id")
        val cursor:Cursor =db.query(
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,null,null,null
        )

        val dateColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE)
        val titleColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE)
        val diaryColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_DIARY)


        while (cursor.moveToNext()){

            val currentDate = cursor.getString(dateColumnIndex)
            val currentTitle = cursor.getString(titleColumnIndex)
            val currentDiary =  cursor.getString(diaryColumnIndex)
            date.text = currentDate
            title.setText(currentTitle)
            diary.setText(currentDiary)

        }
        cursor.close()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater :MenuInflater = menuInflater
        inflater.inflate(R.menu.save_menu,menu)
        return true
    }
    private fun insertDiary() {
        val dateString = date.text.toString()
        val titleString = title.text.toString().trim(){ it <= ' '}
        val diaryString = diary.text.toString().trim(){it <= ' '}
        val mDBHelper = DiaryDBHelper(this)
        val db = mDBHelper.writableDatabase

        if (titleString.isEmpty() || diaryString.isEmpty()){

            Toast.makeText(this,"You must fill the fields", Toast.LENGTH_LONG).show()

        }else{

            val values = ContentValues().apply {
                put(COLUMN_DATE,dateString)
                put(COLUMN_TITLE,titleString)
                put(COLUMN_DIARY,diaryString)

            }


            val rowId = db.insert(TABLE_NAME,null, values)
            if (rowId.equals(-1)) Toast.makeText(this,"diary is not inserted correctly", Toast.LENGTH_SHORT).show() else{
                Toast.makeText(this,"diary insertion successful $rowId", Toast.LENGTH_SHORT).show()
            }

        }



    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            R.id.save_diary->{




               if (id == 0) {
                   insertDiary()


               }else{
                    updateDiary(id)
                }


                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun updateDiary(id:Int) {
        val mDBHelper = DiaryDBHelper(this)
        val db = mDBHelper.writableDatabase
        val values = ContentValues().apply {

            put(COLUMN_TITLE,title.text.toString())
            put(COLUMN_DIARY,diary.text.toString())

        }
        db.update(TABLE_NAME,values,"$_ID= $id",null)


    }


}