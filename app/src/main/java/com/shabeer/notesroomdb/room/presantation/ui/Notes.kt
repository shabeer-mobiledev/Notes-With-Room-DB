package com.shabeer.notesroomdb.room.presantation.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shabeer.notesroomdb.room.data.models.NotesEntity
import com.shabeer.notesroomdb.room.presantation.viewmodel.NotesViewModel
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(
    notesViewModel: NotesViewModel = hiltViewModel(),
    onClick: (title: String, description: String) -> Unit,
    searchQuery: String
) {
    val allNote = notesViewModel._getAllNotes.observeAsState(emptyList())
    var isDialogueShow by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<NotesEntity?>(null) }
    var isDialogueShowNote by remember { mutableStateOf(false) }

    var titleValue by remember { mutableStateOf(TextFieldValue("")) }
    var dicsValue by remember { mutableStateOf(TextFieldValue("")) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    val filteredNotes = allNote.value.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 72.dp)
        ) {
            items(filteredNotes) { note ->
                NotesItem(
                    notesEntity = note,
                    onClick = { title, description -> onClick(title, description) },
                    onDelete = {
                        isDialogueShow = true
                        noteToDelete = note
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            AddNoteButton { isDialogueShowNote = true }
        }
    }


    if (isDialogueShowNote) {
        AlertDialog(
            onDismissRequest = {
                isDialogueShowNote = false
            },
            title = {
                Text(text = "Add New Note", color = MaterialTheme.colorScheme.primary)
            },
            text = {
                Column {
                    TextField(
                        value = titleValue,
                        onValueChange = { titleValue = it },
                        label = { Text(text = "Enter Note Title", color = MaterialTheme.colorScheme.onSurface) },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    TextField(
                        value = dicsValue,
                        onValueChange = { dicsValue = it },
                        label = { Text(text = "Enter Note Description", color = MaterialTheme.colorScheme.onSurface) },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (titleValue.text.isBlank() || dicsValue.text.isBlank()) {
                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                        } else {
                            scope.launch {
                                notesViewModel.insertNotes(
                                    NotesEntity(
                                        title = titleValue.text,
                                        discription = dicsValue.text
                                    )
                                )
                                Log.d("values", "${titleValue},${dicsValue}")
                            }

                            isDialogueShowNote = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Submit", color = Color.White)
                }

                titleValue = TextFieldValue("")
                dicsValue = TextFieldValue("")
            },
            dismissButton = {
                Button(
                    onClick = {
                        isDialogueShowNote = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Cancel", color = Color.White)
                }
            }
        )
    }


    if (isDialogueShow) {
        AlertDialog(
            onDismissRequest = { isDialogueShow = false },
            title = {
                Text(text = "Are you sure you want to delete this note?", color = MaterialTheme.colorScheme.primary)
            },
            confirmButton = {
                Button(
                    onClick = {
                        noteToDelete?.let { notesViewModel.deleteNote(it) }
                        isDialogueShow = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Delete", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = { isDialogueShow = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Cancel", color = Color.White)
                }
            }
        )
    }
}

@Composable
fun NotesItem(
    notesEntity: NotesEntity,
    onClick: (title: String, description: String) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(notesEntity.title, notesEntity.discription) },
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = notesEntity.title,
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "View Description --->",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { onDelete() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Note",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun AddNoteButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(Icons.Filled.Add, "Floating action button", tint = Color.White)
    }
}
