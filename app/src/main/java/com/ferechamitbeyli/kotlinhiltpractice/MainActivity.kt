package com.ferechamitbeyli.kotlinhiltpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
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
 */
class SomeClass
@Inject
constructor(
    //private val someInterfaceImpl: SomeInterface,
    private val gson: Gson
){
    fun doAThing(): String{
        //return "Look I got: ${someInterfaceImpl.getAThing()}"
        return "Gson injected, or is it?"
    }
}

class SomeInterfaceImpl
@Inject
constructor() : SomeInterface{
    override fun getAThing(): String {
        return "A Thing"
    }

}

interface SomeInterface{
    fun getAThing(): String
}