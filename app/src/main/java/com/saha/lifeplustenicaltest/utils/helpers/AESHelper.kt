package com.saha.lifeplustenicaltest.utils.helpers

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.VisibleForTesting
import java.security.Key
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec

class AESHelper {

    private val AES_MODE_M = "AES/GCM/NoPadding"
    private val KEY_ALIAS = "KeyAlias"
    private val ANDROID_KEY_STORE = "AndroidKeyStore"


    private var keyStore: KeyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
    private var aesKey: Key

    @VisibleForTesting
    constructor(testKey: Key) {
        aesKey = testKey
    }

    constructor() {
        keyStore.load(null)

        generateEncryptKey()

        aesKey = keyStore.getKey(KEY_ALIAS, null) as SecretKey
    }

    private fun generateEncryptKey() {
        try {
            if (!keyStore.containsAlias(KEY_ALIAS)) {

                val keyGenerator =
                    KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
                val keyGenParameterSpecBuilder = KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )

                val keyGenParameter = keyGenParameterSpecBuilder
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setRandomizedEncryptionRequired(false).build()

                keyGenerator.init(keyGenParameter)
                keyGenerator.generateKey()
            }
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        }

    }

    fun encrypt(input: String): Pair<String, String> {
        return try {
            val aesIv =  generateRandomIV()
            val cipher = getCipherFromIv(aesIv, Cipher.ENCRYPT_MODE)
            val encodedBytes: ByteArray = cipher.doFinal(input.toByteArray(Charsets.UTF_8))

            Pair(aesIv, Base64.encodeToString(encodedBytes, Base64.DEFAULT))
        } catch (e: Exception) {
            e.printStackTrace()
            Pair("", "")
        }

    }

    private fun generateRandomIV(): String {
        val random = SecureRandom()
        val generated: ByteArray = random.generateSeed(12)
        return Base64.encodeToString(generated, Base64.DEFAULT)
    }

    fun decrypt(publicIv: String, encrypted: String): String {
        return try {
            val decodedValue = Base64.decode(encrypted.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
            val cipher = getCipherFromIv(publicIv, Cipher.DECRYPT_MODE)
            val decryptedVal: ByteArray = cipher.doFinal(decodedValue)

            String(decryptedVal)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }

    }

    private fun getCipherFromIv(iv: String, cipherMode: Int): Cipher {
        val cipher: Cipher = Cipher.getInstance(AES_MODE_M)
        try {

            val parameterSpec = GCMParameterSpec(128, Base64.decode(iv, Base64.DEFAULT))
            cipher.init(cipherMode, aesKey, parameterSpec)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return cipher
    }

}