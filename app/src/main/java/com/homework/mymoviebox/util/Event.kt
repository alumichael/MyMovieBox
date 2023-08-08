package com.homework.mymoviebox.util

class Event<out T>(private val content: T) {
    var hasBeenHandled: Boolean = false
        // Allow read, but prevent change from the outside
        private set

    /**
     * Public getter for content
     * if content has been handled return null
     * otherwise it will return the content
     */
    fun getContent(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
}