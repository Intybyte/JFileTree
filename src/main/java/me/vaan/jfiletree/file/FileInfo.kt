package me.vaan.jfiletree.file

import dev.tamboui.widgets.tree.TreeNode
import kotlinx.coroutines.*
import me.vaan.jfiletree.scan.ScanResult
import me.vaan.jfiletree.scan.ScanState
import me.vaan.jfiletree.type
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.fileSize
import kotlin.io.path.name

data class FileInfo (
    val name: String,
    val absolutePath: Path,
    val type: FileType,
    val size: Deferred<Long>,
    val childScan: ScanResult
) {

    fun toComponent(): TreeNode<FileInfo> {
        if (type != FileType.DIRECTORY) {
            return TreeNode.of(name, this).expanded().leaf()
        }

        val root = TreeNode.of(name, this).expanded()
        runBlocking {
            childScan.children.await().forEach { child ->
                root.add(child.toComponent())
            }
        }

        return root
    }

    companion object {
        private val scope = CoroutineScope(Dispatchers.IO)

        fun of(path: Path) : FileInfo {
            val name = path.fileName?.name ?: path.toString()
            val absolutePath = path.toAbsolutePath()
            val type = path.type()


            val scanChild = ScanResult.of(path)

            val size = if (type != FileType.DIRECTORY) {
                try {
                    val fileSize = path.fileSize()
                    CompletableDeferred(fileSize)
                } catch (_: Exception) {
                    // TODO: Better use null for this and process accordingly for the sum
                    CompletableDeferred(0L)
                }
            } else scope.async {
                scanChild.children.await().sumOf {
                    it.size.await()
                }
            }

            return FileInfo(name, absolutePath, type, size, scanChild)
        }
    }
}