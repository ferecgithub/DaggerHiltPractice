package com.ferechamitbeyli.kotlinhiltpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.doAThing())
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
    private val someInterfaceImpl: SomeInterface
   // private val gson: Gson
){
    fun doAThing(): String{
        return "Look I got: ${someInterfaceImpl.getAThing()}"
    }
}

class SomeInterfaceImpl
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing"
    }

}

interface SomeInterface{
    fun getAThing(): String
}

@InstallIn(SingletonComponent::class) // These dependencies will live as long as the application lives.
@Module
abstract class MyModule { // Abstract class for @Binds implementation

    @Singleton
    @Binds
    abstract fun bindSomeDependency(
        someImpl: SomeInterfaceImpl
    ): SomeInterface

    // @Binds doesn't work for Gson
    /*
    @Singleton
    @Binds
    abstract fun bindGson(
        gson: Gson
    ): Gson

     */
}