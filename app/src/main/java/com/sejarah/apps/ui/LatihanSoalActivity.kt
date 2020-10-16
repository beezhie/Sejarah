package com.sejarah.apps.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sejarah.apps.adapter.SoalAdapter
import com.sejarah.apps.data.Soal
import com.sejarah.apps.databinding.ActivityLatihanSoalBinding
import com.sejarah.apps.utils.hideDialog
import com.sejarah.apps.utils.showDialog
import com.sejarah.apps.utils.showScore
import com.sejarah.apps.utils.toast
import kotlin.collections.ArrayList


class LatihanSoalActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLatihanSoalBinding = ActivityLatihanSoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = "Latihan Soal"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        dbRef = database.getReference("users")

        val adapterSoal = SoalAdapter()
        binding.rvLatihan.layoutManager = LinearLayoutManager(this)
        binding.rvLatihan.setHasFixedSize(true)
        binding.rvLatihan.isNestedScrollingEnabled = false
        binding.rvLatihan.adapter = adapterSoal

        checkUserType(auth)

        val score = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

        adapterSoal.itemClick(object : SoalAdapter.OnItemClick {
            override fun onItemClicked(position: Int, nilai: Int) {
                score[position] = nilai
            }
        })

        binding.btnSend.setOnClickListener {
            showDialog()
            dbRef.child(auth.currentUser!!.uid)
                .updateChildren(mapOf("nilai" to (score.sum() * 5).toString()))
                .addOnCompleteListener {
                    hideDialog()
                    if (it.isSuccessful) {
                        showScore(score.sum() * 5)
                    } else {
                        toast("Failure database : ${it.exception}")
                    }
                }
        }
        adapterSoal.replaceAll(fetchSoal())
    }

    private fun checkUserType(auth: FirebaseAuth) {
        dbRef.child(auth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        if (snapshot.child("type").value.toString() == "guru") {
                            startActivity(
                                Intent(
                                    this@LatihanSoalActivity,
                                    EvaluasiActivity::class.java
                                )
                            )
                            finish()
                        } else if (snapshot.child("nilai").value != null) {
                            toast("Akun ini telah melakukan latihan soal")
                            auth.signOut()
                            onBackPressed()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    toast("Database error : ${error.message}")
                }

            })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun fetchSoal(): ArrayList<Soal> {
        val dataSoal: ArrayList<Soal> = ArrayList()
        dataSoal.add(
            Soal(
                1,
                "Pada tanggal 21 Juli 1947 Belanda melancarkan agresi militer I ke daerah kekuasaan RI. Tindakan Belanda itu melanggar suatu kesepakatan dengan Indonesia, yaitu . . . .",
                "Linggarjati",
                listOf(
                    "Linggarjati",
                    "Konferensi Meja Bundar",
                    "Roem Royen",
                    "Renville",
                    "Malino"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                2,
                "Perundingan Linggarjati dilaksanakan pada tanggal . . . .",
                "10 November 1947",
                listOf(
                    "10 November 1947",
                    "10 November 1945",
                    "10 November 1946",
                    "10 November 1948",
                    "10 November 1949"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                3,
                "Dalam perjanjian Linggarjati, pihak Indonesia merasa sangat dirugikan karena . . . .",
                "Wilayah RI hanya mencakup Jawa, Madura dan Sumatera",
                listOf(
                    "Wilayah RI hanya mencakup Jawa, Madura dan Sumatera",
                    "Para tokoh nasional ditawan oleh Belanda",
                    "Belanda mengambil alih seluruh wilayah",
                    "Tentara RI harus ditarik dari daerah pendudukan",
                    "Terjadinya gangguan keamanan di wilayah Indonesia"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                4,
                "Perundingan yang dilaksanakan di atas kapal perang milik Amerika Serikat yang mendarat di pelabuhan Tanjung Priok Jakarta adalah . . . .",
                "Perundingan Renville",
                listOf(
                    "Perundingan Renville",
                    "Perundingan Roem-Royen",
                    "Perundingan Linggarjati",
                    "Perundingan KTN",
                    "Perundingan KMB"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                5,
                "Belanda dengan jelas melaksanakan politik adu domba, hal ini dibuktikan dari wakilnya dalam perjanjian Renville yaitu . . . .",
                "Mohammad Roem",
                listOf(
                    "Mohammad Roem",
                    "Sutan Syahrir",
                    "Amir Syarifuddin",
                    "Sultan Hamid II",
                    "Abdul Kadir Wijoyoatmojo"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                6,
                "Keterlibatan PBB untuk pertama kali dalam ikut menyelesaikan sengketa Indonesia – Belanda dengan cara membentuk . . . .",
                "KTN",
                listOf(
                    "KTN",
                    "Bantuan beras",
                    "Pasukan perdamaian",
                    "UNCI",
                    "UNTEA"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                7,
                "Di Dalam perundingan Renville, atas usulan Amerika Serikat terdapat 3 Negara yang siap menjadi moderator perselisihan Belanda-Indonesia, yaitu . . . .",
                "Australia, Belgia, AS",
                listOf(
                    "Australia, Belgia, AS",
                    "Belanda, Indonesia, AS",
                    "AS, Rusia, Inggris",
                    "AS, Indonesia, Israel",
                    "Belanda, Luxemburg, Vatikan"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                8,
                "Latar belakang agresi militer Belanda II antara lain . . . .",
                "Pertentangan politik internal RI akibat Persetujuan Renville",
                listOf(
                    "Pertentangan politik internal RI akibat Persetujuan Renville",
                    "Rekonstruksi dan rasionalisasi angkatan perang",
                    "Pemberontakan PKI Madiun",
                    "Serangan Umum 1 Maret 1949",
                    "Pemberontakan permesta"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                9,
                "Dalam Serangan Umum 1 Maret Tentara Republik Indonesia berhasil menduduki Kota Yogyakarta selama . . . .",
                "6 jam",
                listOf(
                    "6 jam",
                    "5 jam",
                    "7 jam",
                    "8 jama",
                    "9 jam"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                10,
                "Serangan Umum 1 maret 1949 dipimpin oleh . . . .",
                "Letkol Soeharto",
                listOf(
                    "Letkol Soeharto",
                    "Jenderal Soedirman",
                    "A. H. Nasution",
                    "SAhmad Yani",
                    "Bung Tomo"
                )
            )
        )
        dataSoal.add(
            Soal(
                11,
                "Dampak dari serangan umum 1 maret 1949 yang menunjang perjuangan bangsa Indonesia dalam bidang diplomasi adalah . . . .",
                "Dibawakannya masalah Indonesia dalam forum PBB",
                listOf(
                    "Dibawakannya masalah Indonesia dalam forum PBB",
                    "Dapat dikuasainya kota Jogjakarta selama 6 jam",
                    "Menunjukkan bahwa TNI masih ada dan terorganisir dengan baik.",
                    "Indonesia mendapatkan pengakuan internasional \tsebagai Negara yang berdaulat.",
                    "Terjadinya gejolak di dalam wilayah RI"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                12,
                "Serangan Umum 1 Maret 1949 dilaksanakan pasukan TNI dari Brigade 10/Wehrkreise III yang dipimpin oleh Letnan Kolonel Soeharto, setelah mendapat persetujuan dari . . . .",
                "Sri Sultan Hamengku Buwono IX",
                listOf(
                    "Sri Sultan Hamengku Buwono IX",
                    "Presiden Sukarno",
                    "Jenderal Sudirman",
                    "Mohammad Hatta",
                    "Moh. Yamin"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                13,
                "Usaha yang dilakukan untuk mempertahankan diri dari pendudukan Belanda sebagai akibat diserang dan didudukinya Jogjakarta sebagai ibukota RI dalam agresi Belanda II adalah . . . .",
                "Mendirikan Pemerintahan Darurat RI di Bukit Tinggi",
                listOf(
                    "Mendirikan Pemerintahan Darurat RI di Bukit Tinggi",
                    "Memperkuat pertahanan untuk kembali merebut Jogjakarta",
                    "Melakukan serangan umum 1 Maret 1949 pimpinan Suharto",
                    "Mengungsikan para pemimpin RI ke tempat yang lebih aman",
                    "Melaporkan tindakan Belanda kepada PBB"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                14,
                "Syarifudin Prawiranegara mendirikan Pemerintahan Darurat Republik Indonesia karena . . . .",
                "Melaksanakan mandat dari Soekarno-M. Hatta",
                listOf(
                    "Melaksanakan mandat dari Soekarno-M. Hatta",
                    "Kecewa terhadap pemerintahan pusat",
                    "Tidak berfungsinya pemerintahan di Jogjakarta",
                    "Kurang setuju dengan hasil perundingan Renville",
                    "Belanda ingin menguasai Indonesia kembali"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                15,
                "Alasan utama dari pembentukan PDRI yang berkedudukan di Bukittinggi Sumbar adalah . . . .",
                "Yogyakarta dinilai tidak lagi aman menjadi pusat pemerintahan Indonesia",
                listOf(
                    "Yogyakarta dinilai tidak lagi aman menjadi pusat pemerintahan Indonesia",
                    "Menyelamatkan kabinet yang memerintah saat itu",
                    "Sebagai pemerintahan bayangan karena para pemimpin Indonesia diasingkan Belanda",
                    "Mengelabui Belanda agar tidak menyerang pusat pemerintahan di Yogyakarta",
                    "Mengisyaratkan bahwa basis pertahanan militer adalah Bukittinggi"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                16,
                "KMB yang berlangsung di Den Haag 1949 menghasilkan beberapa keputusan sebagai berikut, kecuali . . . .",
                "Belanda harus membayar hutangnya kepada RIS",
                listOf(
                    "Belanda harus membayar hutangnya kepada RIS",
                    "RIS diakui sebagai negara merdeka dan berdaulat",
                    "Irian Barat akan diselesaikan setahun kemudian",
                    "Akan dibuat UNI Indonesia Belanda",
                    "RIS akan mengembalikan hak milik Belanda"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                17,
                "Perhatikan data-data berikut !\n" +
                        "\tA. Negera Indonesia Timur\n" +
                        "\tB. Kalimantan Barat\n" +
                        "\tC. Negara Pasundan\n" +
                        "\tD. Negara Jawa Timur\n" +
                        "\tE. Kalimantan Timur\n" +
                        "\tF. Kalimantan Tenggara\n" +
                        "Dari data tersebut yang termasuk Satuan-satuan kenegaraan pada RIS adalah . . . .",
                "C) E) F)",
                listOf(
                    "C) E) F)",
                    "A) B) C)",
                    "B) D) F)",
                    "B) C) E)",
                    "F) A) E)"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                18,
                "Berikut ini adalah faktor-faktor yang melatarbelakangi pembubaran RIS, kecuali . . . .",
                "Menyatukan kesatuan dan persatuan",
                listOf(
                    "Menyatukan kesatuan dan persatuan",
                    "Tidak sesuainya negara Republik Indonesia Serikat dalam Konstitusi Republik Indonesia Serikat dengan tujuan awal dan cita-cita proklamasi negara Republik Indonesia pada tahun 1945",
                    "Sebagian besar rakyat Indonesia tidak puas dengan hasil Konferensi Meja Bundar (KMB) yang melahirkan negara Republik Indonesia Serikat (RIS) sehingga menyebabkan banyaknya demonstrasi menuntut bergabung kedalam bagian dari Republik Indonesia",
                    "Bentuk negara federal merupakan bentukan Belanda dibawah pimpinan Van Mook sehingga orang yang menyetujui bentuk negara ini berarti setuju dengan kembalinya kekuasaan Belanda di Indonesia",
                    "Republik Indonesia Serikat (RIS) adalah sistem pemerintahan dari kolonial Belanda yang tidak menginginkan kekuasaan dan pengaruhnya hilang begitu saja dari Indonesia setelah berkuasa selama 350 tahun"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                19,
                "Sejak tanggal berapa UUDS 1945 mulai berlaku dan menandai Pembubaran Republik Indonesia Serikat (RIS) secara resmi dan kembali ke sistem Demokrasi liberal Negara Kesatuan Republik Indonesia . . . .",
                "17 Agustus 1950",
                listOf(
                    "17 Agustus 1950",
                    "17 Agustus 1850",
                    "7 Agustus 1950",
                    "1 Agustus 1950",
                    "17 Agustus 1905"
                ).shuffled()
            )
        )
        dataSoal.add(
            Soal(
                20,
                "Terbentuknya kembali Negara Kesatuan RI (NKRI) 17 Agustus 1950 dalam hubungan dengan UUD 45, berarti . . . .",
                "Kembali pada bentuk negara yang sesuai dengan cita-cita proklamasi",
                listOf(
                    "Kembali pada bentuk negara yang sesuai dengan cita-cita proklamasi",
                    "Berlakunya kembali sistem kabinet ministriil dengan Demokrasi Parlementer",
                    "Kembalinya seluruh pejuang kemerdekaan ke pangkuan RI",
                    "Gagalnya politik devide et impera yang dijalankan Belanda",
                    "Kembali diberlakukannya UUD 45 secara murni dan konsekuen"
                ).shuffled()
            )
        )

        dataSoal.shuffle()
        return dataSoal
    }
}