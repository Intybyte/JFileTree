package me.vaan.jfiletree

import dev.tamboui.layout.Rect
import dev.tamboui.style.Color
import dev.tamboui.terminal.Frame
import dev.tamboui.toolkit.Toolkit.*
import dev.tamboui.toolkit.element.Element
import dev.tamboui.toolkit.element.RenderContext
import dev.tamboui.toolkit.element.Size
import dev.tamboui.toolkit.elements.TreeElement
import dev.tamboui.toolkit.event.EventResult
import dev.tamboui.tui.event.KeyEvent
import dev.tamboui.widgets.tree.TreeNode
import kotlinx.coroutines.runBlocking
import me.vaan.jfiletree.file.FileInfo
import me.vaan.jfiletree.partition.PartitionVolume

class JFileTree : Element {


    fun treeElement(): TreeElement<FileInfo> {
        return runBlocking {
            val treeCore: List<TreeNode<FileInfo>> =
                PartitionVolume.partitionVolumes.flatMap {
                    it.mountPoints
                }.map {
                    it.toComponent()
                }

            val treeCoreArray = treeCore.toTypedArray()
            return@runBlocking tree(*treeCoreArray)
                .title("JFileTree")
                .rounded()
                .highlightColor(Color.CYAN)
                .scrollbar()
                .focusable()
                .nodeRenderer { node: TreeNode<FileInfo> ->
                    val data = node.data()

                    row(
                        text(data.type.display(node) + " "),
                        text(node.label()).bold(),
                        spacer()
                    )
                }
        }
    }

    var treeElement: TreeElement<FileInfo>? = null

    override fun render(
        frame: Frame?,
        area: Rect?,
        context: RenderContext?
    ) {
        treeElement = treeElement()
        treeElement!!.render(frame, area, context)
    }

    override fun handleKeyEvent(event: KeyEvent?, focused: Boolean): EventResult {
        return treeElement?.handleKeyEvent(event, focused) ?: EventResult.UNHANDLED
    }

    override fun preferredSize(
        availableWidth: Int,
        availableHeight: Int,
        context: RenderContext?
    ): Size = Size.UNKNOWN

    override fun isFocusable(): Boolean = true

    override fun id(): String {
        return "MAINID"
    }
}