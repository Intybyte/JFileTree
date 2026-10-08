package me.vaan.jfiletree.partition

import me.vaan.jfiletree.file.FileInfo
import java.nio.file.FileStore
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path


class PartitionVolume {
    val name: String
    val filesystem: String
    val totalSpace: Long
    val usableSpace: Long
    val mountPoints: List<FileInfo>

    constructor(name: String, filesystem: String, totalSpace: Long, usableSpace: Long, mountPoitns: List<FileInfo>) {
        this.name = name
        this.filesystem = filesystem
        this.totalSpace = totalSpace
        this.usableSpace = usableSpace
        this.mountPoints = mountPoitns
    }

    companion object {
        val partitionVolumes : List<PartitionVolume>  by lazy {
            val out = mutableListOf<PartitionVolume>()
            val mounts: MutableMap<FileStore, MutableList<Path>> = HashMap()

            for (root in FileSystems.getDefault().rootDirectories) {
                val store = Files.getFileStore(root)

                mounts.computeIfAbsent(store) { `_`: FileStore? -> ArrayList() }
                    .add(root)
            }

            for (volume in FileSystems.getDefault().fileStores) {
                val toAdd = PartitionVolume(
                    volume.name(),
                    volume.type(),
                    volume.totalSpace,
                    volume.usableSpace,
                    mounts[volume]?.map { FileInfo.of(it) }?.toList() ?: emptyList()
                )
                out.add(toAdd)
            }

            out
        }
    }
}