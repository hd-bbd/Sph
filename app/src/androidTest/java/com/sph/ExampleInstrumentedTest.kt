package com.sph

import androidx.test.platform.app.InstrumentationRegistry
import com.sph.base.BasicDataPreferenceUtil
import com.sph.ui.MainModel
import com.sph.ui.PredefinedData

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.runners.JUnit4

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ExampleInstrumentedTest {


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.sph", appContext.packageName)
    }

    @Test
    fun testFormatAddDouble() {
        val double = MainModel().formatAddDouble(31.4323, 2.2323)
        assertEquals(33.6646, double, 0.0001)
    }

    @Test
    fun testSharePreference() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        BasicDataPreferenceUtil.init(appContext)
        val isFirst = BasicDataPreferenceUtil.isFirstRun()
        assertEquals(false, isFirst)
    }

    @Test
    fun testPredefinedDataCache() {
        MainModel().getCachePredefined(PredefinedData.CACHE_JSON)
    }

    @Test
    fun testNetworkRequest() {
        MainModel().load()
    }

    @Test
    fun testReadCache() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        BasicDataPreferenceUtil.init(appContext)
        MainModel().getCachedDataAndLoad()
    }
}
