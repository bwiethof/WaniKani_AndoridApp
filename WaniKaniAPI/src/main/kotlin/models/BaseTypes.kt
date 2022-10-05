package models

typealias Date = String

const val isoRegex =
    "(\\d{4}-\\d{2}-\\d{2})[A-Z]+(\\d{2}:\\d{2}:\\d{2}).([0-9+-:]+)"

fun Date.isValid(): Boolean {
    return isoRegex.toRegex().matches(this)
}