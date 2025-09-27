package com.example.unishub

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Support
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.LongState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.example.unishub.data.AppDatabase
import com.example.unishub.data.UserRepository
import com.google.type.Date
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


//private val Notification.id: Any
//private val Icons.Filled.Notifications: ImageVector
//private val Event.id: Any


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GpaScreen() {
    var subjects by remember { mutableStateOf(listOf<Subject>()) }
    var subjectName by remember { mutableStateOf("") }
    var creditHours by remember { mutableStateOf("") }
    var selectedGrade by remember { mutableStateOf("") }
    val grades = listOf("A", "B", "C", "D", "F")
    var gradeMenuExpanded by remember { mutableStateOf(false) }
    var gpa by remember { mutableStateOf(0.0) }
    var errorMessage by remember { mutableStateOf("") }

    var editingSubject by remember { mutableStateOf<Subject?>(null) }
    var editCreditHours by remember { mutableStateOf("") }
    var editGrade by remember { mutableStateOf("") }
    var editExpanded by remember { mutableStateOf(false) }
    // Define colors
    val primaryColor = Color(0xFF1A4D8C)
    val secondaryColor = Color(0xFFFFFFFF)

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1A4D8C), // dark top
            Color(0xFF3568A9), // mid blue
            Color(0xFF82A5D6), // light blue
            Color.White        // bottom
        )
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A4D8C))
                .padding(10.dp)
        ) {
            Column {
                Text(
                    "GPA Calculator",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    "Add subjects to calculate your GPA",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Subject name
            item {
                OutlinedTextField(
                    value = subjectName,
                    onValueChange = { subjectName = it },
                    label = { Text("Subject Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Credit hours
            item {
                OutlinedTextField(
                    value = creditHours,
                    onValueChange = { creditHours = it },
                    label = { Text("Credit Hours") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Grade selector
            item {
                ExposedDropdownMenuBox(
                    expanded = gradeMenuExpanded,
                    onExpandedChange = { gradeMenuExpanded = !gradeMenuExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedGrade,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Grade") },
                        placeholder = { Text("Select Grade") },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = gradeMenuExpanded)
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = gradeMenuExpanded,
                        onDismissRequest = { gradeMenuExpanded = false }
                    ) {
                        grades.forEach { grade ->
                            DropdownMenuItem(
                                text = { Text(grade) },
                                onClick = {
                                    selectedGrade = grade
                                    gradeMenuExpanded = false
                                    errorMessage = ""
                                }
                            )
                        }
                    }
                }
            }

            if (errorMessage.isNotEmpty()) {
                item {
                    Text(
                        errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            // Add button
            item {
                Button(
                    onClick = {
                        val ch = creditHours.toIntOrNull()
                        val gp = gradeToPoint(selectedGrade)

                        when {
                            subjectName.isBlank() -> errorMessage = "Please enter subject name"
                            ch == null || ch <= 0 -> errorMessage = "Please enter valid credit hours"
                            gp == null -> errorMessage = "Please select a grade"
                            else -> {
                                subjects = subjects + Subject(subjectName.trim(), ch, gp)
                                subjectName = ""
                                creditHours = ""
                                selectedGrade = ""
                                gpa = calculateGpa(subjects)
                                errorMessage = ""
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1A4D8C)
                    )
                ) {
                    Text("Add Subject", fontWeight = FontWeight.Bold)
                }
            }

            if (subjects.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Your Subjects:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A4D8C)
                    )
                }

                items(subjects) { subject ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F4FD)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = subject.name,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${subject.creditHours} credit hours • Grade: ${pointToGrade(subject.gradePoint)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }

                            Row {
                                IconButton(
                                    onClick = {
                                        editingSubject = subject
                                        editCreditHours = subject.creditHours.toString()
                                        editGrade = pointToGrade(subject.gradePoint)
                                        editExpanded = false
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        tint = Color(0xFF1A4D8C)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        subjects = subjects.filterNot { it === subject }
                                        gpa = calculateGpa(subjects)
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A4D8C)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Your GPA",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            "%.2f".format(gpa),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            item {
                val context = LocalContext.current

                Button(
                    onClick = {
                        // Save GPA into SharedPreferences
                        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        prefs.edit()
                            .putFloat("saved_gpa", gpa.toFloat())
                            .apply()

                        Toast.makeText(context, "GPA saved successfully!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1A4D8C), // Background color
                        contentColor = Color.White          // Text color
                    ),
                    shape = RoundedCornerShape(12.dp) // Rounded corners
                ) {
                    Text(
                        text = "Save GPA",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }

    // Edit dialog
    if (editingSubject != null) {
        AlertDialog(
            onDismissRequest = { editingSubject = null },
            title = {
                Text(
                    "Edit ${editingSubject!!.name}",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = editCreditHours,
                        onValueChange = { editCreditHours = it },
                        label = { Text("Credit Hours") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ExposedDropdownMenuBox(
                        expanded = editExpanded,
                        onExpandedChange = { editExpanded = !editExpanded }
                    ) {
                        OutlinedTextField(
                            value = editGrade,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Grade") },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = editExpanded)
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = editExpanded,
                            onDismissRequest = { editExpanded = false }
                        ) {
                            grades.forEach { grade ->
                                DropdownMenuItem(
                                    text = { Text(grade) },
                                    onClick = {
                                        editGrade = grade
                                        editExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val ch = editCreditHours.toIntOrNull()
                        val gp = gradeToPoint(editGrade)
                        if (ch != null && gp != null && editingSubject != null) {
                            subjects = subjects.map {
                                if (it === editingSubject) it.copy(creditHours = ch, gradePoint = gp) else it
                            }
                            gpa = calculateGpa(subjects)
                            editingSubject = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1A4D8C)
                    )
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { editingSubject = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// Data class and helper functions
data class Subject(val name: String, val creditHours: Int, val gradePoint: Int)

fun gradeToPoint(grade: String): Int? = when (grade.uppercase()) {
    "A" -> 4
    "B" -> 3
    "C" -> 2
    "D" -> 1
    "F" -> 0
    else -> null
}

fun pointToGrade(point: Int): String = when (point) {
    4 -> "A"
    3 -> "B"
    2 -> "C"
    1 -> "D"
    0 -> "F"
    else -> "-"
}

fun calculateGpa(subjects: List<Subject>): Double {
    val totalPoints = subjects.sumOf { it.creditHours * it.gradePoint }
    val totalCredits = subjects.sumOf { it.creditHours }
    return if (totalCredits > 0) totalPoints.toDouble() / totalCredits else 0.0
}
@Composable
fun CgpaScreen() {
    val context = LocalContext.current
    val sharedPrefs = remember {
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    var semesters by remember { mutableStateOf(listOf<SemesterSimple>()) }
    var semesterName by remember { mutableStateOf("") }
    var gpaInput by remember { mutableStateOf("") }
    var cgpa by remember { mutableStateOf(0.0) }
    var errorMessage by remember { mutableStateOf("") }

    // Load saved CGPA when screen starts
    LaunchedEffect(Unit) {
        cgpa = sharedPrefs.getFloat("cgpa", 0f).toDouble()
    }

    Scaffold(
        // TopAppBar optional
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A4D8C))
                    .padding(10.dp)
            ) {
                Column {
                    Text(
                        "CGPA Calculator",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        "Add your semester GPAs to calculate CGPA",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = semesterName,
                        onValueChange = { semesterName = it },
                        label = { Text("Semester Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                item {
                    OutlinedTextField(
                        value = gpaInput,
                        onValueChange = { gpaInput = it },
                        label = { Text("GPA (0.0 - 4.0)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                if (errorMessage.isNotEmpty()) {
                    item {
                        Text(
                            errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                item {
                    Button(
                        onClick = {
                            val gpa = gpaInput.toDoubleOrNull()

                            when {
                                semesterName.isBlank() -> {
                                    errorMessage = "Please enter semester name"
                                }
                                gpa == null || gpa < 0 || gpa > 4 -> {
                                    errorMessage = "Please enter valid GPA (0.0 - 4.0)"
                                }
                                else -> {
                                    semesters = semesters + SemesterSimple(semesterName.trim(), gpa)
                                    semesterName = ""
                                    gpaInput = ""
                                    cgpa = semesters.map { it.gpa }.average()
                                    errorMessage = ""

                                    // ✅ Save CGPA to SharedPreferences
                                    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                                    prefs.edit()
                                        .putFloat("saved_cgpa", cgpa.toFloat()) // store temporary
                                        .putFloat("cgpa", cgpa.toFloat())       // always sync with main user cgpa
                                        .apply()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1A4D8C)
                        )
                    ) {
                        Text("Add Semester", fontWeight = FontWeight.Bold)
                    }

                }

                if (semesters.isNotEmpty()) {
                    item {
                        Text(
                            "Your Semesters:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A4D8C)
                        )
                    }

                    items(semesters) { semester ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F4FD)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "${semester.name}: GPA ${"%.2f".format(semester.gpa)}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                IconButton(
                                    onClick = {
                                        semesters = semesters.filterNot { it == semester }
                                        cgpa = if (semesters.isNotEmpty()) {
                                            semesters.map { it.gpa }.average()
                                        } else 0.0

                                        // ✅ Save updated CGPA after deletion
                                        sharedPrefs.edit()
                                            .putFloat("cgpa", cgpa.toFloat())
                                            .apply()
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A4D8C)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Your CGPA",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                            Text(
                                "%.2f".format(cgpa),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
// Data class remains the same
data class SemesterSimple(val name: String, val gpa: Double)
// Remaining functions (TimetableScreen, EmergencyScreen, Loginform, SignUpScreen, Newsection)
// would be improved similarly with consistent styling and better UI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableScreen() {
    val context = LocalContext.current

    // Faculties with their PDF links - using BUITEMS-specific faculties
    val faculties = listOf(
        "Faculty of Information Technology" to "https://buitms.edu.pk/it_timetable.pdf",
        "Faculty of Engineering" to "https://buitms.edu.pk/engineering_timetable.pdf",
        "Faculty of Management Sciences" to "https://buitms.edu.pk/management_timetable.pdf",
        "Faculty of Arts & Social Sciences" to "https://buitms.edu.pk/arts_timetable.pdf",
        "Faculty of Life Sciences" to "https://buitms.edu.pk/life_sciences_timetable.pdf",
        "Faculty of Physical Sciences" to "https://buitms.edu.pk/physical_sciences_timetable.pdf"
    )

    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(
//                        "📅 Class Timetables",
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold
//                    )
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = Color(0xFF1A4D8C)
//                )
//            )
//        }
    )
    { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                //.padding(padding)
                .background(Color(0xFFF8F9FA))
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A4D8C))
                    .padding(10.dp)
            ) {
                Column {
                    Text(
                        "BUITEMS Class Schedules",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        "Select your faculty to view timetable",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(faculties) { (name, url) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Cannot open PDF. Please check the link.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF1A4D8C)
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "View timetable PDF",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.PictureAsPdf,
                                contentDescription = "Open PDF",
                                tint = Color(0xFF1A4D8C),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }

                // Additional helpful information
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F4FD)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                "ℹ️ Information",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A4D8C)
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "• Timetables are updated every semester\n" +
                                        "• Contact your department for changes\n" +
                                        "• PDF viewer required to open schedules",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyScreen() {
    val context = LocalContext.current

    // BUITEMS emergency contacts
    val emergencyContacts = listOf(
        EmergencyContact("Health Center", "081-111222", Icons.Default.Call),
        EmergencyContact("Security Office", "081-111333", Icons.Default.Security),
        EmergencyContact("Admin Office", "081-111444", Icons.Default.Business),
        EmergencyContact("IT Support", "081-111555", Icons.Default.Support)
    )

    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(
//                        "🆘 Emergency Contacts",
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold
//                    )
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = Color(0xFFD32F2F) // Emergency red color
//                )
//            )
//        }
    )
    { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                //.padding(padding)
                .background(Color(0xFFF8F9FA))
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A4D8C))
                    .padding(10.dp)
            ) {
                Column {
                    Text(
                        "BUITEMS Emergency Services",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        "Immediate assistance available 24/7",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(emergencyContacts) { contact ->
                    EmergencyContactCard(contact = contact, context = context)
                }

                // Additional emergency information
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                "🚨 Emergency Procedures",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD32F2F)
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "• Stay calm and call the appropriate department\n" +
                                        "• Provide clear location and nature of emergency\n" +
                                        "• Follow instructions from security personnel\n" +
                                        "• Medical emergencies: contact Health Center first",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmergencyContactCard(contact: EmergencyContact, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${contact.number}")
                }
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = contact.icon,
                    contentDescription = contact.name,
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(28.dp)
                )
                Column {
                    Text(
                        text = contact.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1A4D8C)
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = contact.number,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F)
                        )
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Call",
                tint = Color(0xFF1A4D8C),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

data class EmergencyContact(
    val name: String,
    val number: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Loginform(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Header
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "BUITEMS",
                        tint = Color(0xFF1A4D8C),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Welcome to BUITEMS Hub",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A4D8C)
                        )
                    )
                    Text(
                        text = "Sign in to your account",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                // Error message
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Email
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = ""
                    },
                    label = { Text("BUITEMS Email") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = "Email",
                            tint = Color(0xFF1A4D8C)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                // Password
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                    },
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = "Password",
                            tint = Color(0xFF1A4D8C)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Password",
                                tint = Color(0xFF1A4D8C)
                            )
                        }
                    }
                )

                val context = LocalContext.current
                val scope = rememberCoroutineScope()
                val db = AppDatabase.getDatabase(context)
                val repo = UserRepository(db.userDao())

                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            errorMessage = "Please enter both email and password"
                            return@Button
                        }

                        isLoading = true
                        errorMessage = ""

                        scope.launch {
                            try {
                                val user = repo.loginUser(email.trim(), password)
                                if (user != null) {
                                    // Save login session with more user data
                                    val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                                    sharedPrefs.edit()
                                        .putBoolean("isLoggedIn", true)
                                        .putString("userEmail", user.email)
                                        .putString("userCmsId", user.cmsId) // ✅ Save CMS ID
                                        .putString("firstName", user.firstName)
                                        .putString("lastName", user.lastName)
                                        .putString("phoneNumber", user.phoneNumber)
                                        .putString("department", user.department)
                                        .putInt("semester", user.semester)
                                        .putFloat("gpa", user.gpa.toFloat())
                                        .putFloat("cgpa", user.cgpa.toFloat())
                                        .putString("role", user.role)
                                        .apply()

                                    Toast.makeText(context, "Welcome ${user.firstName}!", Toast.LENGTH_SHORT).show()

                                    // Navigate to Profile after login
                                    navController.navigate("profile") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                } else {
                                    errorMessage = "Invalid email or password"
                                }
                            } catch (e: Exception) {
                                errorMessage = "Login failed. Please try again."
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1A4D8C),
                        contentColor = Color.White
                    ),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Sign In", fontWeight = FontWeight.Bold)
                    }
                }

                // Sign up link
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "New to BUITEMS Hub?",
                        color = Color.Gray
                    )
                    TextButton(
                        onClick = { navController.navigate("signup") }
                    ) {
                        Text(
                            text = "Create Account",
                            color = Color(0xFF1A4D8C),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cmsId by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("") }
    var gpa by remember { mutableStateOf("") }
    var cgpa by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var repeatPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Create BUITEMS Account",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1A4D8C)
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF8F9FA))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = "Sign Up",
                    tint = Color(0xFF1A4D8C),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Join BUITEMS Community",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A4D8C)
                    )
                )
                Text(
                    "Create your student account",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Error message
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }

            val textFieldModifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)

            // First Name
            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                    errorMessage = ""
                },
                label = { Text("First Name") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "First Name",
                        tint = Color(0xFF1A4D8C)
                    )
                },
                singleLine = true,
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp)
            )

            // Last Name
            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                    errorMessage = ""
                },
                label = { Text("Last Name") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Last Name",
                        tint = Color(0xFF1A4D8C)
                    )
                },
                singleLine = true,
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp)
            )

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = ""
                },
                label = { Text("BUITEMS Email") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Email",
                        tint = Color(0xFF1A4D8C)
                    )
                },
                singleLine = true,
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // CMS ID
            OutlinedTextField(
                value = cmsId,
                onValueChange = {
                    cmsId = it
                    errorMessage = ""
                },
                label = { Text("CMS ID") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Badge,
                        contentDescription = "CMS ID",
                        tint = Color(0xFF1A4D8C)
                    )
                },
                singleLine = true,
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp)
            )

            // Phone Number
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    errorMessage = ""
                },
                label = { Text("Phone Number") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "Phone Number",
                        tint = Color(0xFF1A4D8C)
                    )
                },
                singleLine = true,
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            // Department
            OutlinedTextField(
                value = department,
                onValueChange = {
                    department = it
                    errorMessage = ""
                },
                label = { Text("Department") },
                leadingIcon = {
                    Icon(
                        Icons.Default.School,
                        contentDescription = "Department",
                        tint = Color(0xFF1A4D8C)
                    )
                },
                singleLine = true,
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp)
            )

            // Semester
            OutlinedTextField(
                value = semester,
                onValueChange = {
                    semester = it
                    errorMessage = ""
                },
                label = { Text("Semester") },
                leadingIcon = {
                    Icon(
                        Icons.Default.List,
                        contentDescription = "Semester",
                        tint = Color(0xFF1A4D8C)
                    )
                },
                singleLine = true,
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // GPA
            OutlinedTextField(
                value = gpa,
                onValueChange = {
                    gpa = it
                    errorMessage = ""
                },
                label = { Text("GPA") },
                leadingIcon = {
                    Icon(
                        Icons.Default.TrendingUp,
                        contentDescription = "GPA",
                        tint = Color(0xFF1A4D8C)
                    )
                },
                singleLine = true,
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // CGPA
            OutlinedTextField(
                value = cgpa,
                onValueChange = {
                    cgpa = it
                    errorMessage = ""
                },
                label = { Text("CGPA") },
                leadingIcon = {
                    Icon(
                        Icons.Default.TrendingUp,
                        contentDescription = "CGPA",
                        tint = Color(0xFF1A4D8C)
                    )
                },
                singleLine = true,
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = ""
                },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Password",
                        tint = Color(0xFF1A4D8C)
                    )
                },
                singleLine = true,
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle Password",
                            tint = Color(0xFF1A4D8C)
                        )
                    }
                }
            )

            // Repeat Password
            OutlinedTextField(
                value = repeatPassword,
                onValueChange = {
                    repeatPassword = it
                    errorMessage = ""
                },
                label = { Text("Confirm Password") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Confirm Password",
                        tint = Color(0xFF1A4D8C)
                    )
                },
                singleLine = true,
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (repeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { repeatPasswordVisible = !repeatPasswordVisible },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (repeatPasswordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle Confirm Password",
                            tint = Color(0xFF1A4D8C)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            val context = LocalContext.current
            val scope = rememberCoroutineScope()
            val db = AppDatabase.getDatabase(context)
            val repo = UserRepository(db.userDao())

            Button(
                onClick = {
                    // Validation
                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank() ||
                        cmsId.isBlank() || phoneNumber.isBlank() || department.isBlank() ||
                        semester.isBlank() || gpa.isBlank() || cgpa.isBlank() || password.isBlank()) {
                        errorMessage = "Please fill all required fields"
                        return@Button
                    }

                    if (password != repeatPassword) {
                        errorMessage = "Passwords do not match"
                        return@Button
                    }

                    if (password.length < 6) {
                        errorMessage = "Password must be at least 6 characters"
                        return@Button
                    }

                    // Validate numeric fields
                    val semesterInt = semester.toIntOrNull()
                    val gpaDouble = gpa.toDoubleOrNull()
                    val cgpaDouble = cgpa.toDoubleOrNull()

                    if (semesterInt == null || gpaDouble == null || cgpaDouble == null) {
                        errorMessage = "Please enter valid numbers for semester, GPA, and CGPA"
                        return@Button
                    }

                    isLoading = true
                    errorMessage = ""

                    scope.launch {
                        try {
                            val success = repo.registerUser(
                                firstName = firstName,
                                lastName = lastName,
                                email = email.trim(),
                                password = password,
                                phoneNumber = phoneNumber,
                                department = department,
                                semester = semesterInt,
                                gpa = gpaDouble,
                                cgpa = cgpaDouble,
                                cmsId = cmsId,
                                role = "student" // Default role for signup
                            )

                            if (success) {
                                Toast.makeText(context, "Account created successfully!", Toast.LENGTH_SHORT).show()
                                navController.navigate("login") {
                                    popUpTo("signup") { inclusive = true }
                                }
                            } else {
                                errorMessage = "Email already registered"
                            }
                        } catch (e: Exception) {
                            errorMessage = "Registration failed. Please try again."
                            Log.e("SignUpScreen", "Registration error", e)
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1A4D8C),
                    contentColor = Color.White
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Create Account", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login link
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account?",
                    color = Color.Gray
                )
                TextButton(
                    onClick = { navController.navigate("login") }
                ) {
                    Text(
                        text = "Sign In",
                        color = Color(0xFF1A4D8C),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
@Composable
fun NewSection() {
    val context = LocalContext.current

    // Define primary and secondary colors
    val primaryColor = Color(0xFF1A4D8C)
    val secondaryColor = Color(0xFFFFFFFF)

    // Gradient background
    val gradient = Brush.verticalGradient(
        colors = listOf(primaryColor, secondaryColor)
    )

    // Sample events data
    val events = listOf(
        Event("Tech Workshop", "Today, 2:00 PM", "Computer Lab", Color(0xFF4285F4)),
        Event("Career Fair", "Tomorrow, 10:00 AM", "Main Auditorium", Color(0xFF34A853)),
        Event("Sports Day", "Friday, 9:00 AM", "Sports Ground", Color(0xFFFBBC05)),
        Event("Alumni Meet", "Next Week", "Conference Hall", Color(0xFFEA4335))
    )

    // Sample notifications with timestamps
    val notifications = listOf(
        Notification("Assignment Due", "Software Engineering project submission deadline", "1 day ago", Icons.Default.Assignment),
        Notification("Library Alert", "Your borrowed books are due tomorrow", "1 day ago", Icons.Default.Book),
        Notification("Exam Schedule", "Final exam timetable has been released", "2 days ago", Icons.Default.School),
        Notification("System Maintenance", "Campus portal will be down for maintenance", "3 days ago", Icons.Default.Settings)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Gradient background
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(brush = gradient)
        }

        // Foreground content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Events Section
            item {
                Text(
                    text = "Upcoming Events",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )
            }

            items(events) { event ->
                EventCard(event = event, onClick = {
                    Toast.makeText(context, "Event: ${event.title}", Toast.LENGTH_SHORT).show()
                })
            }

            // Notifications Section
            item {
                Text(
                    text = "Recent Notifications",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }

            items(notifications) { notification ->
                NotificationCard(notification = notification)
            }

            // Bottom Spacer
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
@Composable
fun EventCard(event: Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color indicator
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(event.color, CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1A4D8C)
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = event.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = event.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "Event",
                tint = Color(0xFF1A4D8C),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun NotificationCard(notification: Notification) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = notification.icon,
                contentDescription = notification.title,
                tint = Color(0xFF1A4D8C),
                modifier = Modifier
                    .size(20.dp)
                    .padding(top = 1.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1A4D8C)
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = notification.timestamp,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            if (notification.title.contains("Due") || notification.title.contains("Alert")) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Important",
                    tint = Color(0xFFEA4335),
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

data class Event(
    val title: String,
    val time: String,
    val location: String,
    val color: Color
)

data class Notification(
    val title: String,
    val message: String,
    val timestamp: String,
    val icon: ImageVector
)

// Create a new composable function to handle the login check
@Composable
fun ProfileOrLoginScreen(navController: NavController) {
    val context = LocalContext.current

    // Simple state management without listeners
    var isLoggedIn by remember {
        mutableStateOf(
            context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                .getBoolean("isLoggedIn", false)
        )
    }

    // Check login status when composable is first created
    LaunchedEffect(Unit) {
        isLoggedIn = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getBoolean("isLoggedIn", false)
    }

    if (isLoggedIn) {
        ProfileScreen(navController = navController)
    } else {
        Loginform(navController = navController)
    }
}



