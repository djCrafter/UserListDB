package com.example.userlist

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class DefaultUserList(val context: Context) {

    fun getUserList() : ArrayList<User> {
       var list = ArrayList<User>()

        val text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut " +
                "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut " +
                "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu " +
                "fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui" +
                " officia deserunt mollit anim id est laborum."

        list.add(User(0, "Valeriy Ivanov", "Metinvest", "+380631070303", text, convertImgToByteArray(R.drawable.face1m)))
        list.add(User(1, "Andrey Petrov", "ISD", "+380661235424", text, convertImgToByteArray(R.drawable.face2m)))
        list.add(User(2, "Sergey Popov", "EPAM", "+380995432121", text, convertImgToByteArray(R.drawable.face3m)))
        list.add(User(3, "Anna Dmitrieva", "EPAM", "+380986321233", text, convertImgToByteArray(R.drawable.face1g)))
        list.add(User(4, "Evgeniy Mironov", "ISD", "+380972312324", text, convertImgToByteArray(R.drawable.face4m)))
        list.add(User(5, "Sergey Shnurov", "SWIT", "+380671321232", text, convertImgToByteArray(R.drawable.face5m)))
        list.add(User(6, "Tatyna Litvinova", "DTEK", "+380984562132", text, convertImgToByteArray(R.drawable.face2g)))
        list.add(User(7, "Olga Demchishna", "DTEK", "+380931234565", text, convertImgToByteArray(R.drawable.face3g)))
        list.add(User(8, "Akexander Tvardovsky", "ISD", "+380931235432", text, convertImgToByteArray(R.drawable.face6m)))

        return list
    }

    fun convertImgToByteArray(imgId: Int) : ByteArray {
        val bitmap = BitmapFactory.decodeResource(context.resources, imgId)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }
}