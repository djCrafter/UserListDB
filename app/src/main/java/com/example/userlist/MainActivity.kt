package com.example.userlist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: PartAdapter
    lateinit var defaultUserList: DefaultUserList

    var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        RealmObject.initBase(this)
//        if(RealmObject.isEmpty()) {
//            defaultUserList = DefaultUserList(this)
//            RealmObject.initData(defaultUserList.getUserList())
//        }

        userList = RealmObject.readAllContacts()

        setRecyclerAdapter()
        add_button.setOnClickListener { addEditButtonHandler() }
    }

    fun setRecyclerAdapter() {
        recycler_list.layoutManager = LinearLayoutManager(this)

        adapter = PartAdapter(userList, object : PartAdapter.Callback {
           override fun onItemClicked(item: User) {
             goToDetailActivity(item.id)
           }
       })
        recycler_list.adapter = adapter
        registerForContextMenu(recycler_list)
    }


    private fun goToDetailActivity(user_id: Long) {
        val intent = Intent(baseContext, DetailActivity::class.java)
        intent.putExtra("ID", user_id)
        startActivity(intent)
    }


    private fun addEditButtonHandler() {
        val intent = Intent(baseContext, EditActivity::class.java)
        intent.putExtra("add", false)
        startActivity(intent)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when(item!!.groupId) {
            0 -> openDetail(item)
            1 -> openEdit(item)
            2 -> deleteUser(item)
        }
        return true
    }

    private fun openDetail(item: MenuItem?) {
            if(item != null)
            goToDetailActivity(userList[item.itemId].id)
    }

    private fun openEdit(item: MenuItem?) {
        if(item != null) {
            val intent = Intent(baseContext, EditActivity::class.java)
            intent.putExtra("edit", true)
            intent.putExtra("id", userList[item.itemId].id)
            startActivity(intent)
        }
    }

    private fun deleteUser(item: MenuItem?) {
        if(item != null) {
            var dialog = AlertDialog.Builder(this)
            dialog.setTitle("Delete user")
                .setIcon(R.drawable.warning)
                .setTitle("Delete this user?")
                .setCancelable(false)
                .setPositiveButton("Yes") {_, _ ->


                   if(RealmObject.deleteUser(userList[item.itemId].id)) {
                       var notifications = AppNotifications()
                       var notifyIntent = Intent(this, LoginActivity::class.java)
                       notifications.makeNotifications(
                           this, R.drawable.delete_icon, "User deleted",
                           "The user has been deleted from the database", notifyIntent,  ++AppNotifications.notificationCounter)

                       userList.removeAt(item.itemId)
                       adapter.notifyDataSetChanged()
                   }
                }
                .setNegativeButton("Cancel") {_, _ ->}
            var alert = dialog.create()
            alert.show()
        }
    }
}
