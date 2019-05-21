package com.example.userlist

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User (@PrimaryKey var id: Long = -1,
                             var name: String? = null,
                             var company: String? = null,
                             var phone: String? = null,
                             var about: String? = null,
                             var image: ByteArray? = null) : RealmObject()

