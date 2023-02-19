package com.haman.dearme.utilities

import com.haman.dearme.data.db.entity.CategoryEntity

/**
 * [CategoryEntity] objects for test
 */
val testCategories = (0 until 100).map {
    CategoryEntity(id = null, name = "category${it + 1}", color = (it + 1).toLong())
}