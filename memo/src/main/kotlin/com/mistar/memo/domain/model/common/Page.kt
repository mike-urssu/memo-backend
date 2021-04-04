package com.mistar.memo.domain.model.common

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class Page(page: Int, size: Int, sortBy: Sort) : PageRequest(page, size, sortBy) {
    constructor(page: Int, size: Int) : this(page, size, Sort.unsorted())
}