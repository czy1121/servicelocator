package me.reezy.cosmo.servicelocator

inline fun <reified T> inject(name: String = T::class.java.name): Lazy<T> = lazy {
    ServiceLocator.resolve(T::class.java, name)
}

inline fun <reified T> resolve(name: String = T::class.java.name) =
    ServiceLocator.resolve(T::class.java, name)


inline fun <reified T> singleton(name: String = T::class.java.name, noinline block: () -> T) {
    ServiceLocator.register(T::class.java, name, true, block)
}

inline fun <reified T> factory(name: String = T::class.java.name, noinline block: () -> T) {
    ServiceLocator.register(T::class.java, name, false, block)
}

