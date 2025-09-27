package com.example.unishub.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val passwordHash: String,

    val phoneNumber: String,
    val department: String,
    val semester: Int,
    val gpa: Double,
    val cgpa: Double,
    val cmsId: String,   // ✅ NEW FIELD
    val role: String     // "master", "admin", "student"
)
