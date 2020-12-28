@file:Suppress("DEPRECATION")

package ru.nobird.template.cache.base

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.security.Key
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.util.Calendar
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import javax.security.auth.x500.X500Principal

/**
 * Created by Konstantin Isaev (isaevkonstantin1@gmail.com) on 30.09.20.
 */
class Cryptographer(
    private val context: Context
) {
    companion object {
        private const val KEY_STORE = "AndroidKeyStore"
        private const val KEY_ALIAS = "app_key"
        private const val AES_MODE = "AES/GCM/NoPadding"
        private const val RSA_MODE = "RSA/ECB/PKCS1Padding"
        private const val FIXED_IV = "qwertyuiopas"
    }

    private lateinit var keyStore: KeyStore

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            generateKeyApi23()
        } else {
            generateKey()
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun generateKeyApi23() {
        keyStore = KeyStore.getInstance(KEY_STORE)
        keyStore.load(null)

        if (!keyStore.containsAlias(KEY_ALIAS)) {
            val keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                KEY_STORE
            )
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setRandomizedEncryptionRequired(false)
                    .build()
            )
            keyGenerator.generateKey()
        }
    }

    @SuppressLint("InlinedApi")
    private fun generateKey() {
        keyStore = KeyStore.getInstance(KEY_STORE)
        keyStore.load(null)
        // Generate the RSA key pairs
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            // Generate a key pair for encryption
            val start = Calendar.getInstance()
            val end = Calendar.getInstance()
            end.add(Calendar.YEAR, 30)

            val spec = KeyPairGeneratorSpec.Builder(context)
                .setAlias(KEY_ALIAS)
                .setSubject(X500Principal("CN=$KEY_ALIAS"))
                .setSerialNumber(BigInteger.TEN)
                .setStartDate(start.time)
                .setEndDate(end.time)
                .build()
            val kpg = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA,
                KEY_STORE
            )
            kpg.initialize(spec)
            kpg.generateKeyPair()
        }
    }

    private fun encrypt(secret: ByteArray): ByteArray =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            encryptAes(secret)
        else
            encryptRsa(secret)

    private fun decrypt(encrypted: ByteArray): ByteArray =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            decryptAes(encrypted)
        else
            decryptRsa(encrypted)

    private fun encryptAes(secret: ByteArray): ByteArray {
        val c = Cipher.getInstance(AES_MODE)
        c.init(Cipher.ENCRYPT_MODE, getSecretKey(KEY_ALIAS), GCMParameterSpec(128, FIXED_IV.toByteArray()))
        return c.doFinal(secret)
    }

    private fun decryptAes(encrypted: ByteArray): ByteArray {
        val c = Cipher.getInstance(AES_MODE)
        c.init(Cipher.DECRYPT_MODE, getSecretKey(KEY_ALIAS), GCMParameterSpec(128, FIXED_IV.toByteArray()))
        return c.doFinal(encrypted)
    }

    private fun encryptRsa(secret: ByteArray): ByteArray {
        val privateKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.PrivateKeyEntry
        val inputCipher = Cipher.getInstance(RSA_MODE, "AndroidOpenSSL")
        inputCipher.init(Cipher.ENCRYPT_MODE, privateKeyEntry.certificate.publicKey)
        val outputStream = ByteArrayOutputStream()
        val cipherOutputStream = CipherOutputStream(outputStream, inputCipher)
        cipherOutputStream.write(secret)
        cipherOutputStream.close()
        return outputStream.toByteArray()
    }

    private fun decryptRsa(encrypted: ByteArray): ByteArray {
        val privateKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.PrivateKeyEntry
        val output = Cipher.getInstance(RSA_MODE, "AndroidOpenSSL")
        output.init(Cipher.DECRYPT_MODE, privateKeyEntry.privateKey)
        val cipherInputStream = CipherInputStream(
            ByteArrayInputStream(encrypted), output
        )
        val values = arrayListOf<Byte>()
        var nextByte: Int
        while (true) {
            nextByte = cipherInputStream.read()
            if (nextByte == -1) break
            values.add(nextByte.toByte())
        }
        val bytes = ByteArray(values.size)
        for (i in bytes.indices) {
            bytes[i] = values[i]
        }
        return bytes
    }

    private fun getSecretKey(@Suppress("SameParameterValue") alias: String): Key =
        keyStore.getKey(alias, null)

    fun encryptBase64(secret: String): String {
        val bytes = secret.toByteArray()
        val encrypted = encrypt(bytes)
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    fun decryptBase64(encryptedBase64: String): String {
        val bytes = Base64.decode(encryptedBase64, Base64.DEFAULT)
        val decrypted = decrypt(bytes)
        return decrypted.toString(Charsets.UTF_8)
    }
}
