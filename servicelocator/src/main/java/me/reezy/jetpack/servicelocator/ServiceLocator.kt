package me.reezy.jetpack.servicelocator

object ServiceLocator {

    private class Service<T>(val factory: ServiceFactory<T>, val singleton: Boolean = true, var instance: T? = null)

    private val services: MutableMap<Class<*>, MutableMap<String, Service<*>>> = mutableMapOf()


    fun <T> register(clazz: Class<T>, name: String, singleton: Boolean = true, factory: ServiceFactory<T>) {
        var map = services[clazz]
        if (map == null) {
            map = mutableMapOf()
            services[clazz] = map
        }
        map[name] = Service(factory, singleton)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> resolve(clazz: Class<T>, name: String): T? {
        (services[clazz]?.get(name) as? Service<T>)?.let {
            if (it.singleton) {
                if (it.instance == null) {
                    it.instance = it.factory.create()
                }
                return it.instance
            }
            return it.factory.create()
        }
        return null
    }
}