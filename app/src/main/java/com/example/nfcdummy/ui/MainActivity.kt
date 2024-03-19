package com.example.nfcdummy.ui

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.example.nfcdummy.databinding.ActivityMainBinding
import com.example.nfcdummy.util.rep

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this).takeIf { it.isEnabled }
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableReaderMode(
            this,
            {
                runOnUiThread {
                    binding.txtTagId.text = it.id.rep()
                    //TODO validate tag id in list
                    binding.btnLogin.isVisible = true
                }
            },
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null
        )
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableReaderMode(this)
    }

}