//DEPS dev.tamboui:tamboui-toolkit:LATEST
//DEPS dev.tamboui:tamboui-jline3-backend:LATEST
//DEPS dev.tamboui:tamboui-image:LATEST
//DEPS dev.tamboui:tamboui-panama-backend:LATEST
//SOURCES FileManagerController.java FileManagerView.java FileManagerKeyHandler.java DirectoryBrowserController.java
// Prevents OSX from showing up in the terminal when running the demo
//JAVA_OPTIONS -Dapple.awt.UIElement=true
/*
 * Copyright TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package me.vaan.jfiletree

import dev.tamboui.style.Color
import dev.tamboui.toolkit.Toolkit.*
import dev.tamboui.toolkit.app.ToolkitRunner
import dev.tamboui.tui.TuiConfig
import dev.tamboui.widgets.tree.TreeNode
import kotlinx.coroutines.runBlocking
import me.vaan.jfiletree.file.FileInfo
import me.vaan.jfiletree.partition.PartitionVolume
import java.time.Duration

/**
 *
 * Viewer key bindings:
 *
 *  * Esc - Close viewer
 *  * Up/Down - Scroll text (text files only)
 *  * PgUp/PgDn - Page scroll text (text files only)
 *
 */
object Main {


    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val config = TuiConfig.builder()
                .tickRate(Duration.ofMillis(50))
                .build()

            val tree = JFileTree()

            ToolkitRunner.create(config).use { runner ->
                runner.run {
                    tree
                }
            }

        } catch (e: Exception) {
            println("FAILED")
            e.printStackTrace()
        }
    }
}
