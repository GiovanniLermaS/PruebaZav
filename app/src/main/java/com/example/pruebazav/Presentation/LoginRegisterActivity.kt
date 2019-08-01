package com.example.pruebazav.Presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.pruebazav.Base.BaseActivity
import com.example.pruebazav.R
import com.example.pruebazav.Utils.setWarningsRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.activity_login_register.*

class LoginRegisterActivity : BaseActivity(), View.OnClickListener {

    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun createUser() {
        //Se valida si los campos Edittext son vacios, siendo así retorna sino hace el procedimieto normal de registro
        if (clLoginRegister.setWarningsRequest()) return
        else {
            //Método registro de firebase
            firebaseAuth?.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                ?.addOnCompleteListener(this) {
                    when {
                        //Si hubo registro exitoso
                        it.isSuccessful -> Toast.makeText(
                            this,
                            getString(R.string.userRegisteredSuccessful),
                            Toast.LENGTH_SHORT
                        ).show()
                        //Si ya existe el usuario que se quiere crear
                        it.exception is FirebaseAuthUserCollisionException -> Toast.makeText(
                            this,
                            getString(R.string.userAlreadyRegistered),
                            Toast.LENGTH_SHORT
                        ).show()
                        //Si el usuario no ha podido ser registrado
                        else -> Toast.makeText(this, getString(R.string.userNotRegistered), Toast.LENGTH_SHORT).show()
                    }
                }
            closeProgress()
        }
    }

    private fun loginUser() {
        //Se valida si los campos Edittext son vacios, siendo así retorna sino hace el procedimieto normal de login
        if (clLoginRegister.setWarningsRequest()) return
        else {
            //Método login de firebase
            firebaseAuth?.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                ?.addOnCompleteListener(this) {
                    when {
                        //Si hubo login exitoso
                        it.isSuccessful -> Toast.makeText(
                            this,
                            "${getString(R.string.welcome)} ${etEmail.text}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            closeProgress()
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            btRegister -> {
                showProgress()
                createUser()
            }
            btLogin -> {
                showProgress()
                loginUser()
            }
        }
    }
}
