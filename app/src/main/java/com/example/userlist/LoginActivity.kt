package com.example.userlist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        RealmObject.initBase(this)
        if(RealmObject.isEmpty()) {
            val admin = AppUser(0, "admin", "admin", "", "", "")

            var defaultUserList = DefaultUserList(this)
            RealmObject.initData(defaultUserList.getUserList(), admin)
        }

        if(RealmObject.isAuthenticate) {
          returnToActivity()
        }

        logButton.setOnClickListener {
            if(authentication(emailField.text.toString(), passwordField.text.toString())){
                RealmObject.isAuthenticate = true
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                emailField.text.clear()
                passwordField.text.clear()
            }
        }

        regButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    fun authentication(email: String, password: String) : Boolean{
      if(email == "") {
          alert("Please enter email!")
          return false
      } else if(password == ""){
          alert("Please enter password!")
          return false
      } else{
          var appUser = RealmObject.readAppUserByEmail(email)!!
          if(appUser.email == ""){
              alert("User email not found!")
              return false
          } else{
              if (appUser.password != password){
                  alert("Incorrect password!")
                  return false
              }
              else { return true }
          }
      }
     return false
    }


    fun returnToActivity() {

        if(intent.hasExtra("userCreate")){
            var id = intent.getLongExtra("userCreate",0)
                val intent = Intent(baseContext, DetailActivity::class.java)
                intent.putExtra("ID", id)
                startActivity(intent)
        }
        else if(intent.hasExtra("edit")) {
            var id = intent.getLongExtra("edit",0)
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("edit", true)
                startActivity(intent)
        }
        else {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        }
    }


    fun alert(message: String) {
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle(message)
            .setIcon(R.drawable.warning)
            .setCancelable(true)
            .setPositiveButton("OK") {_, _ -> }
        var alert = dialog.create()
        alert.show()
    }
}
