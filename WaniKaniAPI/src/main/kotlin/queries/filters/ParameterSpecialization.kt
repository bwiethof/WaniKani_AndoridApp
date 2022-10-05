package queries.filters


private const val availableAfterParam = "available_after"
private const val availableBeforeParam = "available_before"
private const val burnedParam = "burned"
private const val hiddenParam = "hidden"
private const val idsParam = "ids"
private const val immediatelyAvailableForLessonsParam = "immediately_available_for_lessons"
private const val immediatelyAvailableForReviewParam = "immediately_available_for_review"
private const val inReviewParam = "in_review"
private const val levelsParam = "levels"
private const val startedParam = "started"
private const val srsStagesParam = "srs_stages"

private const val subjectIdsParam = "subject_ids"
private const val subjectTypesParam = "subject_types"
private const val unlockedParam = "unlocked"
private const val updatedAfterParam = "updated_after"
private const val assignmentIdsParam = "assignment_ids"

private const val percentagesGreaterThan = "percentages_greater_than"
private const val percentagesLessThan = "percentages_less_than"

private const val slugsParam = "slugs"
private const val typesParam = "types" // similar to subject types but specific for subject query


internal class NothingFilterParameter : FilterParameterImpl<Nothing>(ParameterType.Nothing) {

    init {
        throw IllegalArgumentException("Nothing type is not supported")
    }

    override val queryParam: String
        get() = throw Exception("Nothing cannot have a query param")

    override fun getParameterPair(): Pair<String, String>? {
        throw IllegalStateException("NothingFilterParameter cannot build a Parameter set")
    }
}

internal class HiddenParameter(type: ParameterType) : BooleanFilterParameterImpl(type) {
    override var queryParam = hiddenParam
}

internal class IdsParameter(type: ParameterType) : IntListFilterParameterImpl(type) {
    override var queryParam = idsParam
}

internal class AssignmentIdsParameter(type: ParameterType) : IntListFilterParameterImpl(type) {
    override var queryParam = assignmentIdsParam
}

internal class AvailableAfterParameter(type: ParameterType) : DateFilterParameterImpl(type) {
    override var queryParam = availableAfterParam
}

internal class AvailableBeforeParameter(type: ParameterType) : DateFilterParameterImpl(type) {
    override var queryParam = availableBeforeParam
}

internal class BurnedParameter(type: ParameterType) : BooleanFilterParameterImpl(type) {
    override var queryParam = burnedParam
}

internal class ImmediatelyAvailableForLessonsParameter(type: ParameterType) :
    NothingFilterParameterImpl(type) {
    override val queryParam = immediatelyAvailableForLessonsParam
}

internal class ImmediatelyAvailableForReviewParameter(type: ParameterType) :
    NothingFilterParameterImpl(type) {
    override val queryParam = immediatelyAvailableForReviewParam
}

internal class InReviewParameter(type: ParameterType) : NothingFilterParameterImpl(type) {
    override val queryParam = inReviewParam
}

internal class StartedParameter(type: ParameterType) : BooleanFilterParameterImpl(type) {
    override val queryParam = startedParam
}

internal class UnlockedParameter(type: ParameterType) : BooleanFilterParameterImpl(type) {
    override val queryParam = unlockedParam
}

internal class SubjectIdsParameter(type: ParameterType) : IntListFilterParameterImpl(type) {
    override var queryParam = subjectIdsParam
}

internal class LevelsParameter(type: ParameterType) : IntListFilterParameterImpl(type) {
    override var queryParam = levelsParam
}

internal class SlugsParameter(type: ParameterType) :
    StringListFilterParameterImpl(type) {
    override val queryParam = slugsParam
}

internal class SrsStagesParameter(type: ParameterType) : IntListFilterParameterImpl(type) {
    override val queryParam = srsStagesParam
}

internal class SubjectTypesParameter(type: ParameterType) :
    SubjectTypeListFilterParameterImpl(type) {
    override val queryParam = subjectTypesParam
}

internal class TypesParameter(type: ParameterType) :
    SubjectTypeListFilterParameterImpl(type) {
    override val queryParam = typesParam
}

internal class UpdatedAfterParameter(type: ParameterType) : DateFilterParameterImpl(type) {
    override val queryParam = updatedAfterParam
}

internal class PercentagesLessThanParameter(type: ParameterType) : IntFilterParameterImpl(type) {
    override val queryParam = percentagesLessThan
}

internal class PercentagesGreaterThanParameter(type: ParameterType) : IntFilterParameterImpl(type) {
    override val queryParam = percentagesGreaterThan
}