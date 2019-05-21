package com.example.userlist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    private var editingMode = false
    private var image : ByteArray? = null
    var id : Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        if(intent.hasExtra("edit")) {
            id = intent.getLongExtra("id", 0)
           var user = RealmObject.readUserById(id)!!


            image = user.image!!

            editingMode = true
            setFields(user)
        }
    }

    private fun setFields(user: User) {
        nameField.setText(user.name)
        companyField.setText(user.company)
        phoneField.setText(user.phone)
        aboutField.setText(user.about)
    }

    private fun saveUserInfo() {
       if(editingMode) {
           val user = User(id, nameField.text.toString(), companyField.text.toString(), phoneField.text.toString(), aboutField.text.toString(), image)

           if(RealmObject.updateUser(user)){
               var notifications = AppNotifications()
               var notifyIntent = Intent(baseContext, LoginActivity::class.java)
               notifyIntent.putExtra("edit", user.id)

               notifications.makeNotifications(
                   this, R.drawable.edit_icon, "User data edited.",
                   "User data edited", notifyIntent,  ++AppNotifications.notificationCounter
               )
           }
       }
        else {
           id = RealmObject.getNewId().toLong()
           val user = User(id, nameField.text.toString(), companyField.text.toString(), phoneField.text.toString(), aboutField.text.toString(), null)

           if(RealmObject.createUser(user)) {
               var notifications = AppNotifications()
               var notifyIntent = Intent(baseContext, LoginActivity::class.java)
               notifyIntent.putExtra("userCreate", user.id)

               notifications.makeNotifications(
                   this, R.drawable.add_icon, "Create user.",
                   "New user created.", notifyIntent,  ++AppNotifications.notificationCounter)
           }
       }

        val intent = Intent(baseContext, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_actionbar, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item != null) {
            when(item.itemId) {
                R.id.edit_button -> saveUserInfo()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
