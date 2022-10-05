package queries

import queries.filters.ParameterType

private const val assignmentRoute = "assignments"
private const val levelProgressionRoute = "level_progressions"
private const val resetsRoute = "resets"
private const val reviewRoute = "reviews"
private const val reviewStatisticsRoute = "review_statistics"
private const val spacedRepetitionSystemRoute = "spaced_repetition_systems"
private const val studyMaterialRoute = "study_materials"
private const val subjectRoute = "subjects"
private const val summaryRoute = "summary"
private const val userRoute = "user"
private const val voiceActorsRoute = "voice_actors"


internal class AssignmentsQueryBuilder : QueryBuilderBase(
    listOf(
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
) {
    override var route: String = assignmentRoute
}

internal class LevelProgressionsQueryBuilder :
    QueryBuilderBase(listOf(ParameterType.Ids, ParameterType.UpdatedAfter)) {
    override var route: String = levelProgressionRoute
}

internal class ResetsQueryBuilder : QueryBuilderBase(emptyList()) {
    override var route: String = resetsRoute
}

internal class ReviewQueryBuilder : QueryBuilderBase(
    listOf(
        ParameterType.AssignmentIds,
        ParameterType.Ids,
        ParameterType.SubjectIds,
        ParameterType.UpdatedAfter
    )
) {
    override var route: String = reviewRoute
}

internal class ReviewStatisticsQueryBuilder : QueryBuilderBase(
    listOf(
        ParameterType.Hidden,
        ParameterType.Ids,
        ParameterType.PercentagesGreaterThan,
        ParameterType.PercentagesLessThan,
        ParameterType.SubjectIds,
        ParameterType.SubjectTypes,
        ParameterType.UpdatedAfter
    )
) {
    override var route: String = reviewStatisticsRoute
}


internal class SpacedRepetitionSystemQueryBuilder : QueryBuilderBase(emptyList()) {
    override var route = spacedRepetitionSystemRoute
}

internal class StudyMaterialsQueryBuilder : QueryBuilderBase(
    listOf(
        ParameterType.Hidden,
        ParameterType.Ids,
        ParameterType.SubjectIds,
        ParameterType.SubjectTypes,
        ParameterType.UpdatedAfter
    )
) {
    override var route: String = studyMaterialRoute
}

internal class SubjectsQueryBuilder : QueryBuilderBase(
    listOf(
        ParameterType.Hidden,
        ParameterType.Ids,
        ParameterType.Levels,
        ParameterType.Slugs,
        ParameterType.Types,
        ParameterType.UpdatedAfter
    )
) {
    override var route = subjectRoute
}

internal class SummaryQueryBuilder : QueryBuilderBase(emptyList()) {
    override var route = summaryRoute
}

internal class UserQueryBuilder : QueryBuilderBase(emptyList()) {
    override var route = userRoute
}

internal class VoiceActorsQueryBuilder :
    QueryBuilderBase(listOf(ParameterType.Ids, ParameterType.UpdatedAfter)) {
    override var route = voiceActorsRoute
}