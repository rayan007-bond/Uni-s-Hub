package com.example.unishub.data
import java.security.MessageDigest

class UserRepository(private val dao: UserDao) {

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(password.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
    suspend fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phoneNumber: String,
        department: String,
        semester: Int,
        gpa: Double,
        cgpa: Double,
        cmsId: String,   // ✅ added here
        role: String
    ): Boolean {
        val existing = dao.getUserByEmail(email)
        if (existing != null) return false

        val hashed = hashPassword(password)
        val user = User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            passwordHash = hashed,
            phoneNumber = phoneNumber,
            department = department,
            semester = semester,
            gpa = gpa,
            cgpa = cgpa,
            cmsId = cmsId,   // ✅ save CMS ID
            role = role
        )
        dao.insertUser(user)
        return true
    }


    suspend fun loginUser(email: String, password: String): User? {
        val user = dao.getUserByEmail(email) ?: return null
        val hashed = hashPassword(password)
        return if (user.passwordHash == hashed) user else null
    }
}
