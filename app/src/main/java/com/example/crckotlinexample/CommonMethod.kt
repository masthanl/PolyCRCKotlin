package com.example.crckotlinexample


object CommonMethod1 {

    fun getCRC16CCITT(inputStr: String, polynomial: Int, crc: Int, isHex: Boolean): String {

        var inputStr = inputStr
        var crc = crc

        var strLen = inputStr.length

        val intArray: IntArray



        if (isHex) {

            if (strLen % 2 != 0) {

                inputStr = (inputStr.substring(0, strLen - 1) + "0"

                        + inputStr.substring(strLen - 1, strLen))

                strLen++

            }



            intArray = IntArray(strLen / 2)

            var ctr = 0

            var n = 0
            while (n < strLen) {

                intArray[ctr] = Integer.valueOf(inputStr.substring(n, n + 2), 16)

                ctr++
                n += 2

            }

        } else {

            intArray = IntArray(inputStr.toByteArray().size)

            var ctr = 0

            for (b in inputStr.toByteArray()) {

                intArray[ctr] = b.toInt()

                ctr++

            }

        }


        // main code for computing the 16-bit CRC-CCITT

        for (b in intArray) {

            for (i in 0..7) {

                val bit = b shr 7 - i and 1 == 1

                val c15 = crc shr 15 and 1 == 1

                crc = crc shl 1

                if (c15 xor bit)

                    crc = crc xor polynomial

            }

        }



        crc = crc and 0xFFFF

        var crcStr = Integer.toHexString(crc).toUpperCase()

        val n = crcStr.length

        for (i in 0 until 4 - n) {

            crcStr = "0$crcStr"

        }

        return crcStr
    }


}

