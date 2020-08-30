package com.sejarah.apps.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sejarah.apps.adapter.NilaiAdapter
import com.sejarah.apps.data.Nilai
import com.sejarah.apps.R
import com.sejarah.apps.databinding.ActivityEvaluasiBinding


class EvaluasiActivity : AppCompatActivity() {

    private lateinit var adapterNilai: NilaiAdapter
    private lateinit var dbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val dataNilai: ArrayList<Nilai> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityEvaluasiBinding = ActivityEvaluasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = "Data Nilai"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        dbRef = database.getReference("users")

        adapterNilai = NilaiAdapter()
        binding.rvNilai.layoutManager = LinearLayoutManager(this)
        binding.rvNilai.setHasFixedSize(true)
        binding.rvNilai.isNestedScrollingEnabled = false
        binding.rvNilai.adapter = adapterNilai
        adapterNilai.replaceAll(dataNilai)
        fetchNilai()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_guru, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> {
                auth.signOut()
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchNilai() {
        dbRef.orderByChild("type").equalTo("siswa").addChildEventListener(object :
            ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    if (snapshot.child("nilai").value != null) {
                        dataNilai.add(
                            Nilai(
                                snapshot.key.toString(),
                                snapshot.child("nama").value.toString(),
                                snapshot.child("nilai").value.toString()
                            )
                        )
                    }
                }
                adapterNilai.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}