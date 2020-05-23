package nduati.soc.googlecertification

import org.junit.Test

import org.junit.Assert.*

class CalculatorTest {

    @Test
    fun addition() {
        val sumTotal : Int = Calculator().addition(3,1)
        assertEquals(4, sumTotal)
    }
}