package com.example.finaldb.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finaldb.R
import com.example.finaldb.UserApplication
import com.example.finaldb.data.User
import com.example.finaldb.data.toUserEntity
import com.example.finaldb.domain.UserViewModel
import com.example.finaldb.domain.UserViewModelFactory
import com.example.finaldb.entities.UserInfo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val userRegisterActivityRequestCode = 1
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as UserApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            userViewModel.refreshStates()
            userViewModel.userInfo.collectLatest { users ->
                users.let { adapter.submitList(it) }
            }
            userViewModel.mainState.collectLatest {state ->
                Log.e("current state", "$state")
            }
        }

        val newbtn = findViewById<Button>(R.id.newbtn)
        newbtn.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivityForResult(intent, userRegisterActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentdata: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentdata)

        if (requestCode == userRegisterActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentdata?.getParcelableExtra<User>(RegisterActivity.EXTRA_REPLY)?.let {
                userViewModel.insert(it)

                userViewModel.insertDummies()

                userViewModel.refreshStates()
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
