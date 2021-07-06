package com.ferechamitbeyli.kotlinhiltpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Field injection
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.doAThing()) // Result for field injection
        println(someClass.doSomeOtherThing()) // Result for constructor injection
    }
}

/**
 * EXAMPLE CLASS FOR FIELD INJECTION
 * Hilt basically creates and holds SomeClass in memory
 * and when it's necessary, injects it.
 */
class SomeClass
@Inject
constructor(
    private val someOtherClass: SomeOtherClass // Constructor injection
) {
    fun doAThing(): String {
        return "Look I did a thing!"
    }

    fun doSomeOtherThing(): String {
        return someOtherClass.doSomeOtherThing()
    }
}

/**
 * EXAMPLE CLASS FOR CONSTRUCTOR INJECTION
 */
class SomeOtherClass
@Inject
constructor() {
    fun doSomeOtherThing(): String {
        return "Look I did some other thing!"
    }
}


