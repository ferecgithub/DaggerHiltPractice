package com.ferechamitbeyli.kotlinhiltpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

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
 * A class with SingletonScope is injected
 * to MyFragment class via Field Injection.
 * It will work unless we use FragmentScope
 * on SomeClass.
 * !! Tier system works downwards. For example,
 * A FragmentScore can't be used in a SingletonScope.
 */
@AndroidEntryPoint
class MyFragment : Fragment() {

    // Field injection in Fragment class
    @Inject
    lateinit var someClass: SomeClass
}

/**
 * EXAMPLE CLASS FOR FIELD INJECTION
 * Hilt basically creates and holds SomeClass in memory
 * and when it's necessary, injects it.
 */
//@Singleton
@ActivityScoped
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

/**
 * Tier system for Scope components, from long living to the bottom:
 * 1- SingletonScope : Lives as long as application lives
 * 2- ActivityRetainedScope : Basically scope of the ViewModel. Lives longer than Activity but less than the application itself.
 * 3- ActivityScope : Only lives as long as activity
 * 4- FragmentScope : Only lives as long as fragment
 * Docs: https://developer.android.com/training/dependency-injection/hilt-android#component-scopes
 */


