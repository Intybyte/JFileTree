package me.vaan.jfiletree.file

enum class FileType {
    FILE, DIRECTORY, LINK;

    fun display(): String {
        return this.name[0].toString()
    }
}