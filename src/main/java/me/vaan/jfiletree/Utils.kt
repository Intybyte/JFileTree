package me.vaan.jfiletree

import dev.tamboui.toolkit.elements.TreeElement
import dev.tamboui.widgets.tree.TreeState
import me.vaan.jfiletree.file.FileType
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.reflect.Field
import java.nio.file.LinkOption
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.isSymbolicLink

fun Path.type(): FileType {
    if (this.isDirectory(LinkOption.NOFOLLOW_LINKS)) return FileType.DIRECTORY
    if (this.isSymbolicLink()) return FileType.LINK;
    return FileType.FILE
}

var TreeElement<*>.state: TreeState
    get() {
        return TreeStateAccessor.getter.invoke(this) as TreeState
    }
    set(value) {
        TreeStateAccessor.setter.invoke(this, value)
    }

private object TreeStateAccessor {
    val getter: MethodHandle
    val setter: MethodHandle

    init {
        val t = TreeElement::class.java
        val lookup = MethodHandles.privateLookupIn(t, MethodHandles.lookup())

        val field: Field = t.getDeclaredField("treeState")
        field.setAccessible(true)
        getter = lookup.unreflectGetter(field)
        setter = lookup.unreflectSetter(field)
    }
}