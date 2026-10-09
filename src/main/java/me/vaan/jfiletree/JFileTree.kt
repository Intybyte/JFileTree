package me.vaan.jfiletree

import dev.tamboui.layout.Constraint
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
import dev.tamboui.widgets.tree.TreeState
import kotlinx.coroutines.runBlocking
import me.vaan.jfiletree.file.FileInfo
import me.vaan.jfiletree.partition.PartitionVolume
import kotlin.Boolean
import kotlin.Int


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
    var treeState: TreeState? = null

    override fun render(
        frame: Frame?,
        area: Rect?,
        context: RenderContext?
    ) {
        treeElement = treeElement()
        if (treeState == null) {
            treeState = treeElement!!.state
        } else {
            treeElement!!.state = treeState!!
        }

        val ui = dock()
            .center(treeElement)
            .bottom(messageBar(), Constraint.length(1))

        ui.render(frame, area, context)
    }

    override fun handleKeyEvent(event: KeyEvent?, focused: Boolean): EventResult {
        return treeElement?.handleKeyEvent(event, focused) ?: EventResult.UNHANDLED
    }

    override fun preferredSize(
        availableWidth: Int,
        availableHeight: Int,
        context: RenderContext?
    ): Size = Size.UNKNOWN

    private fun messageBar(): Element {
        val tasks = GlobalData.getTasksRunning()
        val out = if (tasks == 0) "No tasks running" else "There are $tasks running tasks"

        return row(
            text(out).fill(),
            //text("[Enter] Open  [v] View  [Backspace] Up  [+] Mark All  [-] Unmark").dim()
        ).length(1)
    }
}