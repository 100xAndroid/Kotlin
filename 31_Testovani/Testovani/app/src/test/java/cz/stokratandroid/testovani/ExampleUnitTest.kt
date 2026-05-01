package cz.stokratandroid.testovani

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testovaciFunkce_isCorrect() {
        val vysledekTestu = MainActivity.testovaciFunkce(2, 3)
        Assert.assertEquals(5, vysledekTestu.toLong())
    }

    @Test
    fun testovaciFunkce_isCorrect2() {
        val vysledekTestu = MainActivity.testovaciFunkce(2, 3)
        Assert.assertEquals(4, vysledekTestu.toLong())
    }
}