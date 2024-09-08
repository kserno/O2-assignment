package com.kserno.o2interview.persistence.domain

import kotlinx.coroutines.flow.Flow

interface PersistenceRepository {
    fun putString(key: String, value: String?)
    fun getString(key: String, defaultValue: String?): String?
    fun observeString(key: String): Flow<String?>
}
