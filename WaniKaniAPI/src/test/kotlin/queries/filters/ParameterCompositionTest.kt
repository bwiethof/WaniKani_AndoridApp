package queries.filters

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ParameterCompositionTest {

    @Test
    fun `ParamComposition shall be cool`() {

        val paramComposition = ParameterComposition(listOf(ParameterType.Hidden, ParameterType.Ids))

        assertFails { paramComposition.apply { ParameterType.Burned eq true } }

        paramComposition.apply {
            ParameterType.Hidden eq true
            ParameterType.Ids eq listOf(1, 2, 3)
        }

        assertEquals(mapOf("hidden" to "true", "ids" to "1,2,3"), paramComposition.build())
    }
}