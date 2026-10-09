package me.vaan.jfiletree.scan

enum class ScanState(val isComplete: Boolean = true, val isError: Boolean = false) {
    IN_PROGRESS(false),
    SUCCESS,
    INSUFFICIENT_PERMISSION(true, true),
    ERROR(true, true);
}