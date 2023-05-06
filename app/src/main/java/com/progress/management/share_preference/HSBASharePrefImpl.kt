package com.progress.management.share_preference

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.progress.management.entity.response.LoginResponse
import com.progress.management.extension.clearAll
import com.progress.management.extension.setString
import javax.inject.Inject

class HSBASharePrefImpl @Inject constructor(var context: Context) : HSBASharePref {

    companion object {
        const val MSAR_PREF = "HSBA_PREF"
        const val HSBA_SAVED_USER_PREF = "HSBA_SAVED_USER_PREF"
        const val HSBA_SAVED_BIOMETRIC_PREF = "HSBA_SAVED_BIOMETRIC_PREF"
        const val HSBA_PREF_USER = "HSBA_PREF_USER"
    }

    private var mPrefs: SharedPreferences? = null
    private var savedAccountPrefs: SharedPreferences? = null
    private var biometricRegistrationPrefs: SharedPreferences? = null

    init {
        mPrefs = context.getSharedPreferences(MSAR_PREF, Context.MODE_PRIVATE)
        savedAccountPrefs = initSharePref(HSBA_SAVED_USER_PREF)
        biometricRegistrationPrefs = initSharePref(HSBA_SAVED_BIOMETRIC_PREF)
    }

    private fun initSharePref(sharePrefName: String): SharedPreferences {
        val keySpec = KeyGenParameterSpec.Builder(
            "_androidx_security_master_key_",
            PURPOSE_ENCRYPT or PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            sharePrefName,
            MasterKey.Builder(context).setKeyGenParameterSpec(keySpec).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


    override var savedUser: LoginResponse?
        get() = try {
            Gson().fromJson(mPrefs?.getString(HSBA_PREF_USER, null), LoginResponse::class.java)
        } catch (e: Exception) {
            null
        }
        set(value) {
            mPrefs?.setString(HSBA_PREF_USER, Gson().toJson(value))
        }

    override fun logout() {
        mPrefs?.clearAll()
    }

}