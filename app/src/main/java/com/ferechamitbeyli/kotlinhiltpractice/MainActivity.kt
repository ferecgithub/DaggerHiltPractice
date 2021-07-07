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
constructor(
    private val someDependency: String
) : SomeInterface {
    override fun getAThing(): String {
        return "A Thing, $someDependency"
    }

}

interface SomeInterface{
    fun getAThing(): String
}

@InstallIn(SingletonComponent::class) // These dependencies will live as long as the application lives.
@Module
class MyModule {

    @Singleton
    @Provides
    fun provideSomeString(): String {
        return "some string"
    }

    @Singleton
    @Provides
    fun provideSomeInterface(
        someString: String
    ): SomeInterface {
        return SomeInterfaceImpl(someString)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}