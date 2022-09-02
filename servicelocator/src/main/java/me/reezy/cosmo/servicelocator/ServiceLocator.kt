package me.reezy.cosmo.servicelocator

object ServiceLocator {

    private class Service<T>(val newInstance: () -> T, val singleton: Boolean = true, var instance: T? = null)

    private val services: MutableMap<Class<*>, MutableMap<String, Service<*>>> = mutableMapOf()

    fun <T> register(clazz: Class<T>, name: String, singleton: Boolean = true, factory: () -> T) {
        var map = services[clazz]
        if (map == null) {
            map = mutableMapOf()
            services[clazz] = map
        }
        map[name] = Service(factory, singleton)
    }

    @Suppress("UNCHECKED_CAST")
    @Synchronized
    fun <T> resolve(clazz: Class<T>, name: String): T {
        (services[clazz]?.get(name) as? Service<T>)?.let {
            if (!it.singleton) {
                return it.newInstance()
            }
            return it.instance ?: kotlin.run {
                val instance = it.newInstance()
                it.instance = instance
                return instance
            }
        }
        throw Throwable("resolve failed: ${clazz::class.java.canonicalName}, $name")
    }
}