package com.ferechamitbeyli.kotlinhiltpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.doAThing1())
        println(someClass.doAThing2())
    }
}

/**
 * YOU CAN'T DO CONSTRUCTOR INJECTION
 * WHEN YOU TRY TO INJECT AN INTERFACE!!
 * YOU ALSO CAN'T INJECT 3RD PARTY LIBRARY
 * (THE CLASSES WE DON'T HAVE REAL IMPLEMENTATION)
 * HILT OFFERS @Binds AND @Provides AS WORKAROUNDS.
 *  - @Binds is used for interface implementations.
 *  - @Provides is used for other 3rd party libraries. (Also works for all scenarios however underlying generated code is worse.)
 */
class SomeClass
@Inject
constructor(
    @Impl1 private val someInterfaceImpl1: SomeInterface,
    @Impl2 private val someInterfaceImpl2: SomeInterface
){
    fun doAThing1(): String{
        return "Look I got: ${someInterfaceImpl1.getAThing()}"
    }

    fun doAThing2(): String{
        return "Look I got: ${someInterfaceImpl2.getAThing()}"
    }
}

class SomeInterfaceImpl1
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing1"
    }
}

class SomeInterfaceImpl2
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing2"
    }
}

interface SomeInterface{
    fun getAThing(): String
}

@InstallIn(SingletonComponent::class) // These dependencies will live as long as the application lives.
@Module
class MyModule {

    @Impl1
    @Singleton
    @Provides
    fun provideSomeInterface1(): SomeInterface {
        return SomeInterfaceImpl1()
    }

    @Impl2
    @Singleton
    @Provides
    fun provideSomeInterface2(): SomeInterface {
        return SomeInterfaceImpl2()
    }

}

/**
 * The annotation classes help us to differentiate instances of the same type
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2
