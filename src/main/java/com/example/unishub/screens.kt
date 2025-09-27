package com.example.unishub 
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color.blue
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun InfoCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    backgroundColor: Color = Color(0xFF1A4D8C), // Blue for icon circle
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White), // White card
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp) // subtle shadow
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon inside circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Title & Subtitle
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A4D8C) // Theme Blue
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF666666) // Neutral Gray
                    )
                )
            }
        }
    }
}


@Composable
fun HomeScreen(navController: NavController) {
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


    Box(modifier = Modifier.fillMaxSize()) {
        // Gradient background
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(brush = gradient)
        }

        // Foreground Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        )
        {
//            Text(
//                text = "BUITEMS Dashboard",
//                style = MaterialTheme.typography.headlineMedium.copy(
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White
//                ),
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//
//            Text(
//                text = "Manage your academic journey",
//                style = MaterialTheme.typography.bodyMedium,
//                color = Color.White,
//                modifier = Modifier.padding(bottom = 24.dp)
//            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                item {
                    Text(
                        text = "BUITEMS Dashboard",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                    )
                }

                item {
                    Text(
                        text = "Manage your academic journey",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }

                // Row 1
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        InfoCard(
                            icon = Icons.Default.Book,
                            title = "GPA Calculator",
                            subtitle = "Calculate your semester performance",
                            backgroundColor = Color(0xFF1A4D8C),
                            onClick = { navController.navigate("gpa") },
                            modifier = Modifier.weight(1f)
                        )

                        InfoCard(
                            icon = Icons.Default.ShowChart,
                            title = "CGPA Calculator",
                            subtitle = "Track cumulative academic progress",
                            backgroundColor = Color(0xFF3568A9),
                            onClick = { navController.navigate("cgpa") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Row 2
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        InfoCard(
                            icon = Icons.Default.CalendarMonth,
                            title = "Class Timetable",
                            subtitle = "View your weekly schedule",
                            backgroundColor = Color(0xFF4E7FBF),
                            onClick = { navController.navigate("timetable") },
                            modifier = Modifier.weight(1f)
                        )

                        InfoCard(
                            icon = Icons.Default.Call,
                            title = "Emergency Contacts",
                            subtitle = "Quick access to campus help",
                            backgroundColor = Color(0xFF698FCA),
                            onClick = { navController.navigate("emergency") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Row 3
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        InfoCard(
                            icon = Icons.Default.CalendarMonth,
                            title = "Assignments",
                            subtitle = "View and manage assignments",
                            backgroundColor = Color(0xFF82A5D6),
                            onClick = { navController.navigate("assignments") },
                            modifier = Modifier.weight(1f)
                        )

                        InfoCard(
                            icon = Icons.Default.Person,
                            title = "Teacher Appointment",
                            subtitle = "Book meetings with teachers",
                            backgroundColor = Color(0xFF9BBBE2),
                            onClick = { navController.navigate("appointment") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

//                // Example EventCard section below your InfoCards
//                item {
//                    EventCard(
//                        event = Event(
//                            title = "Orientation Session",
//                            time = "10:00 AM - 12:00 PM",
//                            location = "Main Auditorium",
//                            color = Color(0xFF34A853)
//                        ),
//                        onClick = { navController.navigate("event_details") }
//                    )
//                }
            }
          }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {



    val context = LocalContext.current
    val sharedPrefs = remember {
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }
    var isLoading by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Animation states
    val avatarSize by animateDpAsState(
        targetValue = if (scrollState.value > 150) 56.dp else 120.dp,
        animationSpec = tween(durationMillis = 300),
        label = "avatarSize"
    )


    // Load user data
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    // Academic data - Use proper keys that match your login screen
    var gpa by remember { mutableStateOf(0.0f) }
    var cgpa by remember { mutableStateOf(0.0f) }
    var totalCredits by remember { mutableStateOf(0) }

// Load everything once
    LaunchedEffect(Unit) {
        firstName = sharedPrefs.getString("firstName", "") ?: ""
        lastName = sharedPrefs.getString("lastName", "") ?: ""
        email = sharedPrefs.getString("userEmail", "") ?: ""
        department = sharedPrefs.getString("department", "") ?: ""
        semester = sharedPrefs.getInt("semester", 0).toString()
        studentId = sharedPrefs.getString("userCmsId", "") ?: ""
        phoneNumber = sharedPrefs.getString("phoneNumber", "") ?: ""

        // --- GPA Handling ---
        val loginGpa = sharedPrefs.getFloat("gpa", 0.0f)        // from login
        val savedGpa = sharedPrefs.getFloat("saved_gpa", 0.0f)  // from GPA screen

        gpa = when {
            savedGpa != 0f && savedGpa != loginGpa -> {
                // keep both in sync
                sharedPrefs.edit().putFloat("gpa", savedGpa).apply()
                savedGpa
            }
            savedGpa != 0f -> savedGpa
            else -> loginGpa
        }

        // --- CGPA Handling ---
        val loginCgpa = sharedPrefs.getFloat("cgpa", 0.0f)
        val savedCgpa = sharedPrefs.getFloat("saved_cgpa", 0.0f)

        cgpa = when {
            savedCgpa != 0f && savedCgpa != loginCgpa -> {
                sharedPrefs.edit().putFloat("cgpa", savedCgpa).apply()
                savedCgpa
            }
            savedCgpa != 0f -> savedCgpa
            else -> loginCgpa
        }


        // Other academic info
        totalCredits = sharedPrefs.getInt("total_credits", 0)
    }


    // Department options
    val departments = listOf(
        "Computer Science", "Software Engineering", "Information Technology",
        "Electrical Engineering", "Mechanical Engineering", "Civil Engineering",
        "Business Administration", "Mathematics", "Physics", "Chemistry"
    )

    // Semester options
    val semesters = listOf("1", "2", "3", "4", "5", "6", "7", "8")

    // Colors
    val primaryColor = Color(0xFF1A4D8C)
    val secondaryColor = Color(0xFFFFFFFF)
    val surfaceColor = Color(0xFFF8F9FA)
    val onSurfaceColor = Color(0xFF212529)
    val errorColor = Color(0xFFE63946)

    // Gradient background
    val gradient = Brush.verticalGradient(
        colors = listOf(primaryColor, secondaryColor)
    )

    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(
//                        "Profile",
//                        style = MaterialTheme.typography.headlineSmall.copy(
//                            fontWeight = FontWeight.Bold,
//                            color = Color.White
//                        )
//                    )
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = primaryColor
//                )
//            )
//        }
    ) { innerPadding ->
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(brush = gradient)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .background(Color(0xFFF8F9FA))
                .verticalScroll(scrollState)
                .padding(innerPadding)
        ) {
            Text(
                "Profile",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFF)
                )
            )

            // Profile Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = surfaceColor
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Avatar

                    Box(
                        modifier = Modifier
                            .size(avatarSize)
                            .background(
                                color = primaryColor,
                                shape = CircleShape
                            )
                            .border(
                                width = 3.dp,
                                color = Color.White,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = firstName.firstOrNull()?.uppercase() ?: "S",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                            "$firstName $lastName"
                        } else {
                            "Student Profile"
                        },
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        color = onSurfaceColor
                    )

                    if (studentId.isNotEmpty()) {
                        Text(
                            text = "CMS ID: $studentId",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    if (department.isNotEmpty()) {
                        Text(
                            text = department,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = primaryColor,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    // Academic Stats
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        AcademicMetric(
                            value = "%.2f".format(gpa),
                            label = "GPA",
                            icon = Icons.Default.BarChart
                        )

                        AcademicMetric(
                            value = "%.2f".format(cgpa),
                            label = "CGPA",
                            icon = Icons.Default.TrendingUp
                        )

                        AcademicMetric(
                            value = totalCredits.toString(),
                            label = "Credits",
                            icon = Icons.Default.School
                        )
                    }
                }
            }

            // Personal Information Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = surfaceColor
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        "Personal Information",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = onSurfaceColor,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Form Fields
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name *") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = "First Name")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        isError = firstName.isBlank(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = primaryColor,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                            errorIndicatorColor = errorColor
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = "Last Name")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = primaryColor,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email *") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = "Email")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = email.isBlank(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = primaryColor,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                            errorIndicatorColor = errorColor
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Phone Number") },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = "Phone")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = primaryColor,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            }

            // Academic Information Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = surfaceColor
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        "Academic Information",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = onSurfaceColor,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    var deptExpanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = deptExpanded,
                        onExpandedChange = { deptExpanded = !deptExpanded }
                    ) {
                        OutlinedTextField(
                            value = department,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Department") },
                            leadingIcon = {
                                Icon(Icons.Default.School, contentDescription = "Department")
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = deptExpanded)
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = primaryColor,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = deptExpanded,
                            onDismissRequest = { deptExpanded = false }
                        ) {
                            departments.forEach { dept ->
                                DropdownMenuItem(
                                    text = { Text(dept) },
                                    onClick = {
                                        department = dept
                                        deptExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    var semExpanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = semExpanded,
                        onExpandedChange = { semExpanded = !semExpanded }
                    ) {
                        OutlinedTextField(
                            value = if (semester.isNotEmpty()) "Semester $semester" else "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Semester") },
                            leadingIcon = {
                                Icon(Icons.Default.List, contentDescription = "Semester")
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = semExpanded)
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = primaryColor,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = semExpanded,
                            onDismissRequest = { semExpanded = false }
                        ) {
                            semesters.forEach { sem ->
                                DropdownMenuItem(
                                    text = { Text("Semester $sem") },
                                    onClick = {
                                        semester = sem
                                        semExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Action Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        if (firstName.isBlank() || email.isBlank()) {
                            Toast.makeText(context, "Please fill required fields", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        isLoading = true

                        // Save updated profile data
                        sharedPrefs.edit().apply {
                            putString("firstName", firstName.trim())
                            putString("lastName", lastName.trim())
                            putString("userEmail", email.trim())
                            putString("department", department.trim())
                            putInt("semester", semester.toIntOrNull() ?: 1) // Save as int
                            putString("phoneNumber", phoneNumber.trim())
                            apply()
                        }

                        isLoading = false
                        Toast.makeText(context, "Profile updated successfully ✅", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
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
                        Text("Save Changes", fontWeight = FontWeight.Bold)
                    }
                }

                OutlinedButton(
                    onClick = {
                        // Clear login session and navigate to login
                        sharedPrefs.edit().putBoolean("isLoggedIn", false).apply()
                        navController.navigate("login") {
                            popUpTo("profile") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = errorColor
                    ),
                    border = BorderStroke(1.dp, errorColor)
                ) {
                    Text("Logout", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun AcademicMetric(
    value: String,
    label: String,
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = Color(0xFF1A4D8C).copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFF1A4D8C),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            value,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
@Composable
fun updates(navController: NavController) {
    navController.navigate("news")

}