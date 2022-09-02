# ServiceLocator
 
一个简单的服务定位器模式实现

## Gradle

``` groovy
repositories {
    maven { url "https://gitee.com/ezy/repo/raw/cosmo/"}
}
dependencies {
    implementation "me.reezy.cosmo:servicelocator:0.7.0"
}
```

## API 

```kotlin
// 获取实例
inline fun <reified T> resolve(name: String = T::class.java.name): T?
// 注入实例
inline fun <reified T> inject(name: String = T::class.java.name): Lazy<T>
// 注册为单例
inline fun <reified T> singleton(name: String = T::class.java.name, crossinline block: () -> T)
// 注册为工厂
inline fun <reified T> factory(name: String = T::class.java.name, crossinline block: () -> T)
```

## 使用

单例，每次resolve获得的都是同一实例

```kotlin  
class SomeService {
    fun doSomething() {
    }
}

// 注册 
singleton {
    SomeService()
}

// 获取
val service = resolve<SomeService>()

// 注入
class TheContext {
    val service: SomeService by inject()
}
```

具名单例 

```kotlin
class NamedService(val name: String) {
    fun doSomething() {
    }
}

// 注册 
singleton("a") {
    NamedService("aaa")
}
singleton("b") {
    NamedService("bbb")
}

// 获取 
val serviceA = resolve<NamedService>("a")
val serviceB = resolve<NamedService>("b")
```
  
工厂，每次resolve都会产生新实例

```kotlin  
class SomeService {
    fun doSomething() {
    }
}

// 注册 
factory {
    SomeService()
}

// 获取，每次resolve都会产生新实例
val service1 = resolve<SomeService>() 
val service2 = resolve<SomeService>() 
```
  
具名工厂

```kotlin
class NamedService(val name: String) {
    fun doSomething() {
    }
}

// 注册 
factory("a") {
    NamedService("aaa")
}
factory("b") {
    NamedService("bbb")
}

// 获取
// A1 与 A2 是使用同一工厂产生的不同实例
// A1 与 B1 是使用不同工厂产生的不同实例
val serviceA1 = resolve<NamedService>("a")
val serviceA2 = resolve<NamedService>("a")
val serviceB1 = resolve<NamedService>("b")
val serviceB2 = resolve<NamedService>("b")
```


## LICENSE

The Component is open-sourced software licensed under the [Apache license](LICENSE).
