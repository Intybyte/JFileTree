package me.vaan.jfiletree.scan

enum class ScanState(val isComplete: Boolean = true) {
    IN_PROGRESS(false),
    SUCCESS,
    INSUFFICIENT_PERMISSION,
    ERROR;
}