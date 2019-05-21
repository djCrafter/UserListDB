package com.example.userlist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var intentThatStartedThisActivity = getIntent()
        var userId = intentThatStartedThisActivity.getLongExtra("ID", 0)

        user = RealmObject.readUserById(userId)!!
        setData()
    }

    private fun setData() {
        name_label.text = user.name
        company_label.text = user.company
        phone_label.text = user.phone
        characteristic_label.text = user.about

        if(user.image != null) {
            detail_image.setImageBitmap(convertToBitmap(user.image!!))
        }
        else {
            detail_image.setImageResource(R.drawable.unknown)
        }
    }

    private fun convertToBitmap(byteArray: ByteArray) : Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}
