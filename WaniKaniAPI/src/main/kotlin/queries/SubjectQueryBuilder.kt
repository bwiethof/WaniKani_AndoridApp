package queries

import models.Subject


private const val subjectRoute = "subjects"
private const val idsParam = "ids"
private const val typesParam = "types"
private const val slugsParam = "slugs"
private const val levelsParam = "levels"
private const val hiddenParam = "hidden"
private const val updatedAfterParam = "updated_after"


class SubjectQueryBuilder : CollectionQueryBuilder() {
    override var route = subjectRoute

    private var ids: List<Int>? = null
    private var types: MutableList<Subject.Type>? = null
    private var slugs: List<String>? = null
    private var levels: List<Int>? = null
    private var hidden: Boolean? = null
    private var updatedAfter: String? = null



    fun withTypes(types: List<Subject.Type>): SubjectQueryBuilder {
        this.types = if (this.types.isNullOrEmpty()) types.toMutableList() else this.types!!.addIfNotExist(types)
        return this
    }

    fun withType(type: Subject.Type): SubjectQueryBuilder = withTypes(listOf(type))


    fun withSlugs(slugs: List<String>): SubjectQueryBuilder {
        this.slugs = if (this.slugs.isNullOrEmpty()) slugs else this.slugs!!.plus(slugs)
        return this
    }

    fun withSlug(slug: String) = withSlugs(listOf(slug))

    fun withLevels(levels: List<Int>): SubjectQueryBuilder {
        this.levels = if (this.levels.isNullOrEmpty()) levels else this.levels!!.plus(levels)
        return this
    }

    fun withLevel(level: Int) = withLevels(listOf(level))

    fun withHidden(hidden: Boolean): SubjectQueryBuilder {
        this.hidden = hidden
        return this
    }

    override fun buildInternal(): SubjectQueryBuilder {
        super.buildInternal()

        if (!types.isNullOrEmpty()) this.parameters[typesParam] =
            types!!.joinToString(transform = { type: Subject.Type -> type.toString() })

        if (!slugs.isNullOrEmpty()) this.parameters[slugsParam] = slugs!!.joinToString(",")

        if (!levels.isNullOrEmpty()) this.parameters[levelsParam] = levels!!.joinToString(",")

        if (hidden != null) this.parameters[hiddenParam] = hidden!!.toString()

        return this
    }


}