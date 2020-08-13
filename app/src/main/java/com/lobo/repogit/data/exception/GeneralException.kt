package com.lobo.repogit.data.exception

open class GeneralException : Exception {
    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(message: String?) : super(message)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return this.message.equals((other as Exception).message)
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
