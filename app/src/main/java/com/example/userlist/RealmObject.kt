package com.example.userlist

import android.content.Context
import io.realm.Realm
import java.lang.Exception

object RealmObject {

    lateinit var realm : Realm
    var isAuthenticate = false

    fun initBase(context: Context) {
        Realm.init(context)
        realm = Realm.getDefaultInstance()
    }

    fun createUser(user: User) : Boolean {
      try {
          realm.executeTransaction {
              it.copyToRealm(user)
          }
          return true
      }
      catch (ex: Exception){
          println(ex)
          return false
      }
    }

    fun createAppUser(appUser: AppUser) : Boolean {
        try {
            realm.executeTransaction {
                it.copyToRealm(appUser)
            }
            return true
        }
        catch (ex: Exception){
            println(ex)
            return false
        }
    }


    fun readUserById(id: Long) : User? {
          var user = User()
          try {
              realm.executeTransaction{
                  user = it.where(User :: class.java).equalTo("id", id).findFirst()!!
              }
          }
          catch (ex: Exception){
              println(ex)
          }
        return user
    }

    fun readAppUserByEmail(email: String) : AppUser? {
        var appUser = AppUser()
        try {
            realm.executeTransaction {
                appUser = it.where(AppUser::class.java).equalTo("email", email).findFirst()!!
            }
        } catch (ex: Exception) {
            println(ex)
        }
        return appUser
    }


    fun readAllContacts() : ArrayList<User> {
        var userList = ArrayList<User>()
        try{
            realm.executeTransaction{
               val result = it.where(User::class.java).findAll()
                result.forEach{ user ->
                    userList.add(user)
                }
            }
        }
        catch (ex:Exception) {
            println(ex)
        }
        return userList
    }


    fun updateUser(user: User) : Boolean {
        try{
            realm.executeTransaction {
                it.copyToRealmOrUpdate(user)
            }
            return true
        }
        catch (e: Exception) {
            println(e)
            return false
        }
    }

    fun deleteUser(id: Long) : Boolean {
         try{
            realm.executeTransaction{
                it.where(User :: class.java).equalTo("id", id).findFirst()!!.deleteFromRealm()
            }
             return true
         }
         catch (ex: Exception){
             println(ex)
             return false
         }
    }

    fun getNewId() : Int {
        var id = 0
        try {
            realm.executeTransaction {
                id = it.where(User::class.java).max("id")!!.toInt() + 1
            }
        }
        catch (ex: Exception) {
            println(ex)
        }
        return id
    }

    fun initData(defaulUserList: ArrayList<User>, defaultAppUser: AppUser): Boolean {
        try {
            createAppUser(defaultAppUser)

            for(user in defaulUserList) {
                   createUser(user)
               }
        } catch (ex: Exception) {
            print(ex)
            return false
        }
        return true
    }

    fun isEmpty() : Boolean {
       return realm.isEmpty
    }

    fun getAppUser(email: String): AppUser? {
        var user = AppUser()
        try {
            realm.executeTransaction {
                user = it.where(AppUser :: class.java).equalTo("email", email).findFirst()!!
            }
        } catch (ex: Exception) {
            print(ex)
        }
        return user
    }

    fun getUniqueAppUserId(): Long {
        var key : Long = 0
        try {
            realm.executeTransaction {

                key = it.where(AppUser::class.java).max("id")!!.toLong() + 1
            }
        } catch(ex: Exception) {
            print(ex)
        }
        return key
    }

    fun addAppUser(user: AppUser): Boolean {
        try {
            user.id = getUniqueAppUserId()
            realm.executeTransaction {
                it.copyToRealm(user)}
            return true
        } catch (e: Exception) {
            print(e)
            return false
        }
    }
}