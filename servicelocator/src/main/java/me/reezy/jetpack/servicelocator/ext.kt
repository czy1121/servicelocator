package me.reezy.jetpack.servicelocator


inline fun <reified T> resolve(name: String = T::class.java.name) = ServiceLocator.resolve(T::class.java, name)


inline fun <reified T> singleton(name: String = T::class.java.name, crossinline block: () -> T) {
    ServiceLocator.register(T::class.java, name, true, object : ServiceFactory<T> {
        override fun create(): T = block()
    })
}

inline fun <reified T> factory(name: String = T::class.java.name, crossinline block: () -> T) {
    ServiceLocator.register(T::class.java, name, false, object : ServiceFactory<T> {
        override fun create(): T = block()
    })
}

