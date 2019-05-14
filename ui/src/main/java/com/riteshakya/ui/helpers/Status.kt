package com.riteshakya.ui.helpers;

enum class Status(val value: Int) {
    NONE(0), SUCCESS(1), ERROR(2);

    companion object {
        @JvmStatic
        fun fromId(position: Int): Status {
            for (f in values()) {
                if (f.value == position) return f
            }
            return NONE
        }
    }
}