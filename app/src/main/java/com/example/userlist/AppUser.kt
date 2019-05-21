package com.example.userlist

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class AppUser (@PrimaryKey var id: Long = -1,
                    var email: String = "",
                    var password: String = "",
                    var firstname: String = "",
                    var lastname: String = "",
                    var Phone: String = "") : RealmObject()

