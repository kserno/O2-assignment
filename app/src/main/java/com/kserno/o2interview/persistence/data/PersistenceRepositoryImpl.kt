package com.kserno.o2interview.persistence.data

import android.content.SharedPreferences
import com.kserno.o2interview.persistence.domain.PersistenceRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class PersistenceRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : PersistenceRepository {
    override fun putString(key: String, value: String?) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    override fun observeString(key: String): Flow<String?> {
        return callbackFlow {
            val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, prefKey ->
                if (prefKey == key) {
                    trySend(getString(key, null))
                }
            }

            sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

            awaitClose {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
            }
        }
    }
}
