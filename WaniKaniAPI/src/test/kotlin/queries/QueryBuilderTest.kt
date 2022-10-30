package queries

import queries.filters.ParameterType

import kotlin.test.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.function.Executable
import kotlin.test.assertFails


class QueryBuilderTest {


    private val parameterTypes = ParameterType.values()


    private val enumTestData = listOf(
        Resource.Assignments to Triple(
            "assignments", AssignmentsQueryBuilder::class, listOf(
                ParameterType.AvailableAfter,
                ParameterType.AvailableBefore,
                ParameterType.Burned,
                ParameterType.Hidden,
                ParameterType.Ids,
                ParameterType.ImmediatelyAvailableForLessons,
                ParameterType.ImmediatelyAvailableForReview,
                ParameterType.InReview,
                ParameterType.Levels,
                ParameterType.SrsStages,
                ParameterType.Started,
                ParameterType.SubjectIds,
                ParameterType.SubjectTypes,
                ParameterType.Unlocked,
                ParameterType.UpdatedAfter
            )
        ),
        Resource.LevelProgressions to Triple(
            "level_progressions",
            LevelProgressionsQueryBuilder::class,
            listOf(ParameterType.Ids, ParameterType.UpdatedAfter)
        ),
        Resource.Resets to Triple("resets", ResetsQueryBuilder::class, emptyList()),
        Resource.Reviews to Triple(
            "reviews", ReviewQueryBuilder::class, listOf(
                ParameterType.AssignmentIds,
                ParameterType.Ids,
                ParameterType.SubjectIds,
                ParameterType.UpdatedAfter
            )
        ),
        Resource.ReviewsStatistics to Triple(
            "review_statistics", ReviewStatisticsQueryBuilder::class, listOf(
                ParameterType.Hidden,
                ParameterType.Ids,
                ParameterType.PercentagesGreaterThan,
                ParameterType.PercentagesLessThan,
                ParameterType.SubjectIds,
                ParameterType.SubjectTypes,
                ParameterType.UpdatedAfter
            )
        ),
        Resource.SpacedRepetitionSystem to Triple(
            "spaced_repetition_systems", SpacedRepetitionSystemQueryBuilder::class, emptyList()
        ),
        Resource.StudyMaterials to Triple(
            "study_materials", StudyMaterialsQueryBuilder::class, listOf(
                ParameterType.Hidden,
                ParameterType.Ids,
                ParameterType.SubjectIds,
                ParameterType.SubjectTypes,
                ParameterType.UpdatedAfter
            )
        ),
        Resource.Subjects to Triple(
            "subjects", SubjectsQueryBuilder::class, listOf(
                ParameterType.Hidden,
                ParameterType.Ids,
                ParameterType.Levels,
                ParameterType.Slugs,
                ParameterType.Types,
                ParameterType.UpdatedAfter
            )
        ),
        Resource.Summary to Triple("summary", SummaryQueryBuilder::class, emptyList()),
        Resource.User to Triple("user", UserQueryBuilder::class, emptyList()),
        Resource.VoiceActors to Triple(
            "voice_actors", VoiceActorsQueryBuilder::class, listOf(ParameterType.Ids, ParameterType.UpdatedAfter)
        ),
    )

    @TestFactory
    fun `Resources shall return the right query builder`() = enumTestData.map { (input, expected) ->
        DynamicTest.dynamicTest(input.toString()) {
            val builder = input.getBuilder()
            val (route, kClass, acceptedParams) = expected

            assertAll("Correct query builder",
                Executable { assertEquals(kClass, builder::class) },
                Executable { assertEquals(route, builder.route) }
            )

            val acceptsExecutions =
                parameterTypes.map { Executable { assertEquals(acceptedParams.contains(it), builder.accepts(it)) } }
            assertAll("Accepts correct params", acceptsExecutions)

        }
    }


    class DummyQueryBuilder : QueryBuilderBase(listOf(ParameterType.Hidden, ParameterType.Ids)) {
        override var route = "dummy"
    }

    @Test
    fun `QueryBuilder shall create the right query`() {

        val dummyBuilder = DummyQueryBuilder()

        dummyBuilder.where {
            ParameterType.Hidden eq true
            ParameterType.Ids eq listOf(1, 2, 3)
        }
        dummyBuilder.build()

        assertEquals(
            mapOf("hidden" to "true", "ids" to "1,2,3"),
            dummyBuilder.filterParameterMap,
            "parameter map correctly assigned"
        )
        assertEquals("dummy", dummyBuilder.route, "route parameter not changed")

        dummyBuilder.specificResourceId = 1
        dummyBuilder.build()
        assertEquals(null, dummyBuilder.filterParameterMap, "parameter map deleted")
        assertEquals("dummy/1", dummyBuilder.route, "route parameter adjusted")


        val emptyDummyBuilder = DummyQueryBuilder()
        emptyDummyBuilder.build()

        assertEquals(emptyMap(), emptyDummyBuilder.filterParameterMap)
    }

    @Test
    fun `Create correct query`() {

        val dummyQueryBuilder = query {
            from(Resource.Assignments)
            where { ParameterType.Hidden eq true }
        }.build()!!


        assertEquals(AssignmentsQueryBuilder::class, dummyQueryBuilder::class, "Uses the right builder")
        assertEquals(mapOf("hidden" to "true"), dummyQueryBuilder.filterParameterMap, "Forwards parameter")


        assertAll("Failing without specifying resource",
            Executable { assertFails { query { where { ParameterType.Hidden eq true } } } },
            Executable { assertFails { query { matches(1) } } }
        )
    }
}