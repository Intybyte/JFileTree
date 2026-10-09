package me.vaan.jfiletree.file

import dev.tamboui.widgets.tree.TreeNode

enum class FileType {
    FILE, DIRECTORY, LINK;

    fun display(t: TreeNode<FileInfo>): String {
        val f = t.data()
        val type = f.type

        val status = f.childScan.state.get()
        if (status.isError) {
            return "❌"
        }

        if (!status.isComplete) {
            return "⏳"
        }


        return when (type) {
            DIRECTORY -> {
                if (t.isExpanded) {
                    "📂"
                } else {
                    "📁"
                }
            }

            FILE -> {
                "📄"
            }

            LINK -> {
                "🔗"
            }
        }
    }
}