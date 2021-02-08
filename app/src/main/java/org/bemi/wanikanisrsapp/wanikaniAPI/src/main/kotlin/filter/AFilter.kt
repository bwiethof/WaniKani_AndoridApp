package wanikaniAPI.filter

import kotlinx.coroutines.CoroutineScope
import wanikaniAPI.API.WaniKaniService

abstract class AFilter(val service: WaniKaniService) {
    protected abstract var scope: CoroutineScope
    abstract suspend fun updater()



}