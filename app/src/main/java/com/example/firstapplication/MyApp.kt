package com.example.firstapplication

import android.app.Activity
import android.app.Application
import android.os.Bundle
import timber.log.Timber

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Ініціалізація Timber для Debug-збірки
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Реєстрація глобального відстеження станів усіх Activity
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                logState("onActivityCreated", activity)
            }

            override fun onActivityStarted(activity: Activity) {
                logState("onActivityStarted", activity)
            }

            override fun onActivityResumed(activity: Activity) {
                logState("onActivityResumed", activity)
            }

            override fun onActivityPaused(activity: Activity) {
                logState("onActivityPaused", activity)
            }

            override fun onActivityStopped(activity: Activity) {
                logState("onActivityStopped", activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                logState("onActivitySaveInstanceState", activity)
            }

            override fun onActivityDestroyed(activity: Activity) {
                logState("onActivityDestroyed", activity)
            }

            private fun logState(methodName: String, activity: Activity) {
                if (activity is androidx.lifecycle.LifecycleOwner) {
                    val screenName = activity.javaClass.simpleName
                    val state = activity.lifecycle.currentState
                    Timber.d("GlobalTracker: Method=$methodName | Screen=$screenName | State=$state")
                }
            }
        })
    }
}