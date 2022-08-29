package models

import kotlinx.serialization.Serializable


@Serializable
data class SpacedRepetitionSystem(
    val burning_stage_position: Int,
    val created_at: String,
    val description: String,
    val name: String,
    val passing_stage_position: Int,
    val stages: List<Stage>,
    val starting_stage_position: Int,
    val unlocking_stage_position: Int
) {
    @Serializable
    data class Stage(val interval: Int?, val interval_unit: String?, val position: Int)
}

