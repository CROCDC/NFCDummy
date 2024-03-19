package com.example.nfcdummy.util

fun ByteArray.rep(): String {
    return this.map {byte-> "%02X".format(byte)}.joinToString("")
}