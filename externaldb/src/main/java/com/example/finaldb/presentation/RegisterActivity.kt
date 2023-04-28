package com.example.finaldb.presentation

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.finaldb.R
import com.example.finaldb.data.User

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val firstName : EditText = findViewById(R.id.firstName_et)
        val lastName : EditText = findViewById(R.id.lastName_et)
        val middleName : EditText = findViewById(R.id.middleName_et)

        val contents = listOf(firstName,lastName,middleName)

        val btn = findViewById<Button>(R.id.btn_add)
        btn.setOnClickListener {
            if (contents.any { editText -> TextUtils.isEmpty(editText.text) }){
                setResult(RESULT_CANCELED, intent)
            } else{
                intent.putExtra(
                    EXTRA_REPLY, User(
                    name = firstName.text.toString(),
                    lastName = lastName.text.toString(),
                    middleName = middleName.text.toString()
                ))
                setResult(RESULT_OK, intent)
            }
            finish()
        }
    }
    companion object{
        const val EXTRA_REPLY = "com.example.android.userlistsql.REPLY"
    }


}