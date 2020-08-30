package com.sejarah.apps.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sejarah.apps.R
import com.sejarah.apps.databinding.ActivityLoginBinding
import com.sejarah.apps.databinding.DialogRegisterBinding
import com.sejarah.apps.utils.*


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        dbRef = database.getReference("users")

        binding.btnLogin.setOnClickListener {
            showDialog()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val validation = arrayOfNulls<Boolean>(2)

            validation[0] = binding.email.inputError(
                email,
                "${resources.getString(R.string.email)} harap diisi"
            )
            validation[1] = binding.password.inputError(
                password,
                "${resources.getString(R.string.password)} harap diisi"
            )

            if (!validation.contains(false)) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            dbRef.child(task.result?.user!!.uid).child("type")
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        hideDialog()
                                        if(snapshot.exists()){
                                            if(snapshot.toString() == "guru"){
                                                startActivity(Intent(this@LoginActivity,EvaluasiActivity::class.java))
                                            }else{
                                                startActivity(Intent(this@LoginActivity,LatihanSoalActivity::class.java))
                                            }
                                            finish()
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        hideDialog()
                                        toast("Database error : ${error.message}")
                                    }

                                })
                        } else {
                            hideDialog()
                            toast("Authentication failed :  ${task.exception?.message}")
                        }
                    }
            } else {
                hideDialog()
            }
        }

        binding.btnDaftar.setOnClickListener {
            dialogForm()
        }
    }

    private fun dialogForm() {
        val alertDialog = AlertDialog.Builder(this)
        val registerBinding: DialogRegisterBinding =
            DialogRegisterBinding.inflate(this.layoutInflater)
        alertDialog.setView(registerBinding.root)
        alertDialog.setTitle("Form Registrasi")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton(
            "DAFTAR", null
        )
        alertDialog.setNegativeButton(
            "CANCEL", null
        )
        val alert = alertDialog.create()
        alert.setOnShowListener {
            val daftar = alert.getButton(AlertDialog.BUTTON_POSITIVE)
            daftar.setOnClickListener {
                val nama = registerBinding.etNama.text.toString().trim()
                val email = registerBinding.etEmail.text.toString().trim()
                val password = registerBinding.etPassword.text.toString().trim()
                val validation = arrayOfNulls<Boolean>(3)

                validation[0] = registerBinding.nama.inputError(
                    nama,
                    "${resources.getString(R.string.nama)} harap diisi"
                )

                validation[1] = if (registerBinding.email.inputError(
                        email,
                        "${resources.getString(R.string.email)} harap diisi"
                    )
                ) {
                    if (!email.isEmailValid()) {
                        registerBinding.email.error =
                            "${resources.getString(R.string.email)} format salah"
                        false
                    } else {
                        true
                    }
                } else {
                    false
                }
                validation[2] = registerBinding.password.inputError(
                    password,
                    "${resources.getString(R.string.password)} harap diisi"
                )

                if (!validation.contains(false)) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                dbRef.child(task.result?.user!!.uid)
                                    .updateChildren(mapOf("nama" to nama, "type" to "siswa"))
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            toast("Registrasi Berhasil")
                                            alert.dismiss()
                                        } else {
                                            toast("Failure Create :  ${task.exception?.message}")
                                        }
                                    }
                            } else {
                                toast("Authentication failed :  ${task.exception?.message}")
                            }
                        }
                }
            }
        }

        alert.show()
    }
}