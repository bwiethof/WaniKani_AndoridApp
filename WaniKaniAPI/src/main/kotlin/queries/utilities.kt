package queries

import models.Subject

fun MutableList<Subject.Type>.addIfNotExist(other: List<Subject.Type>): MutableList<Subject.Type> {
    other.forEach { it ->
        if (this.contains(it))
            this.add(it)
    }
    return this
}

fun <T> addToList(base: List<T>?, other: List<T>): List<T> {
    return if(base.isNullOrEmpty()) other else base.plus(other)
}
