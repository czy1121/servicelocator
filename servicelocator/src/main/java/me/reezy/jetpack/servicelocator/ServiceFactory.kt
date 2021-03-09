package me.reezy.jetpack.servicelocator

interface ServiceFactory<T> {
    fun create(): T
}