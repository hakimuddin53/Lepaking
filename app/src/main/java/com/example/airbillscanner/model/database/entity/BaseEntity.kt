package com.example.airbillscanner.model.database.entity

import com.example.airbillscanner.common.utility.DateUtility


/**
 * Created by cytan on 2/13/2018.
 */
abstract class BaseEntity
{
    var isActive: Boolean = true
    var isDeleted: Boolean = false
    var createdDate: String = DateUtility.now()
    var createdBy: String = ""
    var updatedDate: String = DateUtility.now()
    var updatedBy: String = ""
    var remarks: String = ""

}