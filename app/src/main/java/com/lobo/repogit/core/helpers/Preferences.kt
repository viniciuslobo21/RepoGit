package com.lobo.repogit.core.helpers

interface Preferences {

    fun get(): Preferences

    fun containsKey(key: String): Boolean

    fun saveKey(key: String, value: Any)

    fun restoreKey(key: String, value: Any): Any
}