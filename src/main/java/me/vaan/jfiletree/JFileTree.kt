package me.vaan.jfiletree

import dev.tamboui.layout.Rect
import dev.tamboui.style.Color
import dev.tamboui.terminal.Frame
import dev.tamboui.toolkit.Toolkit.row
import dev.tamboui.toolkit.Toolkit.spacer
import dev.tamboui.toolkit.Toolkit.text
import dev.tamboui.toolkit.Toolkit.tree
import dev.tamboui.toolkit.app.ToolkitRunner
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

    private val part = PartitionVolume.getAllPartitionVolumes()[0]

    private val treeElement: TreeElement<String> by lazy {
        tree(TreeNode.of("A", "A").expanded()
            .add(TreeNode.of("AA", "AA").expanded().leaf())
            .add(TreeNode.of("AA", "AA").expanded().leaf())
            .add(TreeNode.of("AA", "AA").expanded().leaf())
            .add(TreeNode.of("BB", "BB").expanded()
                .add(TreeNode.of("BB", "BB").expanded().leaf())
                .add(TreeNode.of("BB", "BB").expanded().leaf())
                .add(TreeNode.of("BB", "BB").expanded().leaf())
            )
        ).title("ABC")
            .scrollbar()
            .rounded()
            .focusable()
        /*
        runBlocking {
            val treeCore: TreeNode<FileInfo> =
                part.mountPoints[0].toComponent()

            tree(treeCore)
                .title("Test")
                .rounded()
                .highlightColor(Color.CYAN)
                .scrollbar()
                .nodeRenderer { node: TreeNode<FileInfo> ->
                    val data = node.data()

                    row(
                        text(data.type.display() + " "),
                        text(node.label()).bold(),
                        spacer()
                    )
                }
        }*/
    }



    override fun render(
        frame: Frame?,
        area: Rect?,
        context: RenderContext?
    ) {
        treeElement.render(frame, area, context)
    }

    override fun handleKeyEvent(event: KeyEvent?, focused: Boolean): EventResult {
        return treeElement.handleKeyEvent(event, focused)
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