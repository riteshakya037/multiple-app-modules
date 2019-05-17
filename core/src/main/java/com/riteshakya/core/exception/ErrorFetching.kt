package com.riteshakya.core.exception

import android.content.res.Resources

class ErrorFetching(item: String) : Resources.NotFoundException("$item not found. ")