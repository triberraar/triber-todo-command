package be.tribersoft.util

import java.time.LocalDateTime

object DateFixator {

    private var now: LocalDateTime? = null

    fun fixate(fixation: LocalDateTime) {
        now = fixation
    }

    fun clear() {
        now = null
    }

    fun getNow(): LocalDateTime {
        return now ?: LocalDateTime.now()
    }
}
