package queries.filters

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.function.Executable


import queries.filters.ParameterType.*
import kotlin.test.assertEquals

class ParameterTypeTest {

    private val enumTestData = listOf(
        AssignmentIds to Pair(assignmentIdsParam, IntListFilterParameterImpl::class),
        AvailableAfter to Pair(availableAfterParam, DateFilterParameterImpl::class),
        AvailableBefore to Pair(availableBeforeParam, DateFilterParameterImpl::class),
        Burned to Pair(burnedParam, BooleanFilterParameterImpl::class),
        Hidden to Pair(hiddenParam, BooleanFilterParameterImpl::class),
        Ids to Pair(idsParam, IntListFilterParameterImpl::class),
        ImmediatelyAvailableForLessons to Pair(immediatelyAvailableForLessonsParam, NothingFilterParameterImpl::class),
        ImmediatelyAvailableForReview to Pair(immediatelyAvailableForReviewParam, NothingFilterParameterImpl::class),
        InReview to Pair(inReviewParam, NothingFilterParameterImpl::class),
        Levels to Pair(levelsParam, IntListFilterParameterImpl::class),
        PercentagesGreaterThan to Pair(percentagesGreaterThan, IntFilterParameterImpl::class),
        PercentagesLessThan to Pair(percentagesLessThan, IntFilterParameterImpl::class),
        Slugs to Pair(slugsParam, StringListFilterParameterImpl::class),
        SrsStages to Pair(srsStagesParam, IntListFilterParameterImpl::class),
        Started to Pair(startedParam, BooleanFilterParameterImpl::class),
        SubjectIds to Pair(subjectIdsParam, IntListFilterParameterImpl::class),
        SubjectTypes to Pair(subjectTypesParam, SubjectTypeListFilterParameterImpl::class),
        Types to Pair(typesParam, SubjectTypeListFilterParameterImpl::class),
        Unlocked to Pair(unlockedParam, BooleanFilterParameterImpl::class),
        UpdatedAfter to Pair(updatedAfterParam, DateFilterParameterImpl::class)
    )


    @TestFactory
    fun `All Parameter types return the correct configuration` () = enumTestData.map { (input, expected) ->
        DynamicTest.dynamicTest("$input") {
            val condition = input.getCondition()

            assertAll(
                Executable { assertEquals(condition.parameterType, input) },
                Executable { assertEquals(condition.queryParam, expected.first) },
                Executable { assertEquals(condition::class, expected.second) }
            )
        }
    }
}