package com.example.crckotlinexample

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var replay: String = ""

    var mAsciiValue: String = ""

    val parts = arrayListOf<String>()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCrc.setOnClickListener {
            val CrcInput = edtCrctext.text.toString()

            if (!CrcInput.isEmpty()) {
                ComposeRead(CrcInput.replace(" ", "").trim { it <= ' ' })
                // hexToASCII(CrcInput)

            } else {
                Toast.makeText(this@MainActivity, "Please Enter crc input", Toast.LENGTH_SHORT).show()
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun ComposeRead(input: String): String {
        val strBuild = StringBuilder()
        val crcString = String.format(input)
        val CRC = CommonMethod1.getCRC16CCITT(crcString, 0x1021, 0x0000, true)
        Log.e("CRC", CRC)
        txtCrcvalue.setText(CRC)
        return replay
    }

    ///Hex to ascii

    fun hexToASCII(hexValue: String): String {
        val sb = StringBuilder()
        val mTrimValues = hexValue.replace(" ", "").replace("00", "30").trim({ it <= ' ' })
        Arrays.toString(splitToNChar(mTrimValues, 2))
        for (component in parts) {
            //int ival = Integer.parseInt(component.replace("0x", ""), 16);
            val ival = Integer.parseInt(component, 16)
            sb.append(ival.toChar())
        }
        mAsciiValue = sb.toString()
        Log.e("hexToASCII", mAsciiValue)
        txtCrcvalue.setText(mAsciiValue)
        asciiToHex(mAsciiValue)
        return mAsciiValue
    }

    ///ascii to Hex

    fun asciiToHex(asciiValue: String): String {
        val chars = asciiValue.toCharArray()
        val hex = StringBuffer()
        for (i in chars.indices) {
            hex.append(Integer.toHexString(chars[i].toInt()))
        }
        Log.e("asciiToHex", (hex).toString())
        toLittleEndian((hex).toString())
        return hex.toString()
    }

    fun splitToNChar(text: String, size: Int): Array<String> {
        //parts = ArrayList<String>()

        val length = text.length
        var i = 0
        while (i < length) {
            parts.add(text.substring(i, Math.min(length, i + size)))
            i += size
        }
        return parts.toTypedArray()
    }


    fun toLittleEndian(hex: String): Int {
        val ret = 0
        var hexLittleEndian = ""
        if (hex.length % 2 != 0) return ret
        var i = hex.length - 2
        while (i >= 0) {
            hexLittleEndian += hex.substring(i, i + 2)
            i -= 2
        }
        //ret = Integer.parseInt(hexLittleEndian, 16);
        Log.e("little-Endian", hexLittleEndian)
        return ret
    }
}
