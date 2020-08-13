package com.lobo.repogit.core.helpers

import com.google.gson.Gson
import com.orhanobut.hawk.Hawk

class PreferencesImpl : Preferences {

    override fun get(): PreferencesImpl {
        return this
    }

    override fun containsKey(key: String): Boolean {
        return Hawk.contains(key)
    }

    override fun saveKey(key: String, value: Any) {
        Hawk.put(key, value)
    }

    override fun restoreKey(key: String, value: Any): Any {
        return Hawk.get(key)
    }

    companion object {
        private val gson = Gson()
    }
}
