package queries.filters

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.function.Executable
import kotlin.test.assertEquals
import kotlin.test.assertFails

import models.Subject
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.reflect.full.primaryConstructor

class ParameterTest {

    private data class TestData<T, K>(
        val initialValue: T,
        val wrongValue: K,
        val expectedParameter: String
    )

    private val inputParamType = ParameterType.Hidden
    private val inputQueryParam = "aQueryParam"

    private val filterImplTestData =
        listOf(
            IntFilterParameterImpl::class to TestData(1, "wrong", 1.toString()),
            IntListFilterParameterImpl::class to TestData(listOf(1, 2, 3), "wrong", listOf(1, 2, 3).joinToString(",")),
            BooleanFilterParameterImpl::class to TestData(true, "", true.toString()),
            StringFilterParameterImpl::class to TestData("initial", true, "initial"),
            StringListFilterParameterImpl::class to TestData(
                listOf("initial", "list", "of", "values"),
                "",
                listOf("initial", "list", "of", "values").joinToString(",")
            ),
            DateFilterParameterImpl::class to TestData("2017-11-11T07:42:00Z", 1, "2017-11-11T07:42:00Z"),
            DateFilterParameterImpl::class to TestData("2017-11-11T07:42:00Z", "02-27-2018", "2017-11-11T07:42:00Z"),
            SubjectTypeListFilterParameterImpl::class to TestData(
                listOf(Subject.Type.LESSON, Subject.Type.RADICAL),
                "wrong",
                listOf(Subject.Type.LESSON, Subject.Type.RADICAL).joinToString { it.toString() }),
        )

    @TestFactory
    fun `filter shall provide the right right access and Type`() = filterImplTestData.map { (input, testData) ->


        val paramImpl = input.primaryConstructor!!.call(inputQueryParam, inputParamType)
        paramImpl.setValue(testData.initialValue)


        DynamicTest.dynamicTest("$input") {

            assertAll(
                Executable {
                    assertEquals(
                        inputQueryParam to testData.expectedParameter,
                        paramImpl.getParameterPair()
                    )
                },
                Executable {
                    assertFails {
                        paramImpl.setValue(testData.wrongValue)
                        println(paramImpl.getParameterPair())
                    }
                }
            )
        }


    }
}