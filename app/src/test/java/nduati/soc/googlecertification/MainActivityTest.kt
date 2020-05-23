package nduati.soc.googlecertification

import org.junit.Test

import org.junit.Assert.*

class MainActivityTest {

    @Test
    fun addNums() {
        val total : Int = MainActivity().addNums(5, -1)
        assertEquals(4, total)
    }
}