package com.example.userlist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.register_actionbar, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item != null) {
            when(item.itemId) {
                R.id.register_button -> register()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun register() {
        if(checkIfDataIsValid()){
          addAppUser()
        }
    }

    private fun addAppUser() {
        if(RealmObject.getAppUser(email_field.text.toString()) != null) {
            var user = AppUser(0, email_field.text.toString(),
                password_field.text.toString(), firstname_field.text.toString(),
                lastname_field.text.toString(), phone_field.text.toString())
            var result = RealmObject.addAppUser(user)

            if(result){
                val intent = Intent(baseContext, LoginActivity::class.java)
                startActivity(intent)
            } else {
                alert("Can't add this user! Try again!")
            }
        } else {
            alert("This user is already exist! Try again!")
        }
    }


    private fun checkIfDataIsValid():Boolean {
        var firstNameValidationMessage = validateName(firstname_field.text.toString())
        var lastNameValidationMessage = validateName(lastname_field.text.toString())
        var passwordValidationMessage = validatePassword(password_field.text.toString())
        var emailValidationMessage = validateEmail(email_field.text.toString())
        var phoneValidationMessage = validatePhone(phone_field.text.toString())



            when {
                (emailValidationMessage != "OK") -> {
                    alert("Email $emailValidationMessage")
                    return false
                }
                (password_field.text.toString() != repeatpassword_field.text.toString()) -> {
                    alert("Passwords don't match!")
                    return false
                }
                (passwordValidationMessage != "OK") -> {
                    alert("Password $passwordValidationMessage")
                    return false
                }
                (firstNameValidationMessage != "OK") -> {
                    alert("FirstName $firstNameValidationMessage")
                    return false
                }
                (lastNameValidationMessage != "OK") -> {
                    alert("LastName $lastNameValidationMessage")
                    return false
                }
                (phoneValidationMessage != "OK") -> {
                    alert("Phone $phoneValidationMessage")
                }
                else -> return true
            }


        return false
    }


    private fun validateName(name: String): String {
        when {
            (name.length == 0 || name == " ") -> return " could't be blank !"
            (name.length < 2 || name.length > 200) -> return " should be between 2 and 200 characters!"
            (Regex(pattern = """[^\w\s'-]""").containsMatchIn(input = name)) -> return  " can only contains letters!"
            (Regex(pattern = """^[\s-']|[\s-']$""").containsMatchIn(input = name)) -> return " can't start or end with not a letter!"
            else -> return "OK"
        }
    }
    private fun validatePassword(pwd: String): String {
        when {
            (pwd.length == 0 || pwd == " ") -> return " could't be blank !"
            (pwd.length < 5 || pwd.length > 32)  -> return " should be between 6 and 32 characters!"
            (Regex(pattern = """[^\w\d]""").containsMatchIn(input = pwd)) -> return " couldn't consists of other characters except numbers and letters!"
            else -> return "OK"
        }
    }
    private fun validateEmail(email: String): String{
        when {
            (email.length == 0 || email == " ") -> return " could't be blank !"
            (email.length < 5 || email.length > 100)  -> return " should be between 5 and 100 characters!"
            (!email.contains("@"))  -> return " should contain @ character!"
            (!Regex(pattern = """.{1,}@[\w\d.-_]+[.].+[\w\d.-_]""").containsMatchIn(input = email)) -> return " is incorrect!"
            else -> return "OK"
        }
    }
    private fun validatePhone(phone: String): String{
        when {
            (phone.length == 0 || phone == " ") -> return " could't be blank!"
            (phone.length < 8 || phone.length > 32)  -> return " should be between 8 and 32 characters!"
            (Regex(pattern = """[^\d+]+""").containsMatchIn(input = phone)) -> return " couldn't consist of non-numbers!"
            else -> return "OK"
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
