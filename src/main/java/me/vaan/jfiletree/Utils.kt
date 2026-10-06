package me.vaan.jfiletree

import me.vaan.jfiletree.file.FileType
import java.nio.file.LinkOption
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.isSymbolicLink

fun Path.type(): FileType {
    if (this.isDirectory(LinkOption.NOFOLLOW_LINKS)) return FileType.DIRECTORY
    if (this.isSymbolicLink()) return FileType.LINK;
    return FileType.FILE
}