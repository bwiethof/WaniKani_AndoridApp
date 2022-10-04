package queries

import queries.filters.ParameterType

private const val studyMaterialRoute = "study_materials"
private const val resetsRoute = "resets"
private const val spacedRepetitionSystemRoute = "spaced_repetition_systems"


internal class StudyMaterialQueryBuilder :
    QueryBuilderBase(listOf(ParameterType.Hidden, ParameterType.Ids)) {
    override var route: String = studyMaterialRoute
}


internal class ResetsQueryBuilder : QueryBuilderBase(emptyList()) {
    override var route: String = resetsRoute
}


internal class SpacedRepetitionSystemQueryBuilder : QueryBuilderBase(emptyList()) {
    override var route = spacedRepetitionSystemRoute
}