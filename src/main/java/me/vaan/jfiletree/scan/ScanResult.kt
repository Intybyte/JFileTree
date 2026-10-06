package me.vaan.jfiletree.scan

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import me.vaan.jfiletree.file.FileInfo
import me.vaan.jfiletree.file.FileType
import me.vaan.jfiletree.type
import java.io.IOException
import java.nio.file.AccessDeniedException
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicReference
import kotlin.use

class ScanResult(val state: AtomicReference<ScanState>, val children: Deferred<List<FileInfo>>) {

    companion object {
        private val scope = CoroutineScope(Dispatchers.IO)

        fun of(path: Path): ScanResult {
            val state = AtomicReference(ScanState.IN_PROGRESS)
            val type = path.type()


            val children: Deferred<List<FileInfo>> = if (type != FileType.DIRECTORY) CompletableDeferred(emptyList()) else scope.async {

                try {
                    val paths = Files.list(path).use { stream ->
                        stream.toList()
                    }

                    val result = paths.map { FileInfo.of(it) }
                    state.set(ScanState.SUCCESS)
                    result
                } catch (_: AccessDeniedException) {
                    state.set(ScanState.INSUFFICIENT_PERMISSION)
                    listOf()
                } catch (e: IOException) {
                    state.set(ScanState.ERROR)
                    println("CAPTURED ERROR")
                    e.printStackTrace()
                    listOf()
                }
            }

            return ScanResult(state, children)
        }
    }
}