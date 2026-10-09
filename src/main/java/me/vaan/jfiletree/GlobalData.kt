package me.vaan.jfiletree

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.concurrent.atomic.AtomicInteger

object GlobalData {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val tasksRunning = AtomicInteger(0)

    fun increaseTasksRunning() {
        scope.async {
            tasksRunning.incrementAndGet()
        }
    }

    fun decreaseTasksRunning() {
        scope.async {
            tasksRunning.decrementAndGet()
        }
    }

    fun getTasksRunning() = tasksRunning.get()
}