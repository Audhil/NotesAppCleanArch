package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.repository.FakeNotesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteTest {

    private lateinit var addNote: AddNote
    private lateinit var fakeNotesRepository: FakeNotesRepository

    @Before
    fun setUp() {
        fakeNotesRepository = FakeNotesRepository()
        addNote = AddNote(fakeNotesRepository)
    }

    @Test(expected = InvalidNoteException::class)
    fun `add note by blank title, InvalidNoteException`() = runBlocking {
        val note = Note(title = "", content = "", timestamp = 0, color = 0)
        addNote(note = note)
        val exception = InvalidNoteException("The title of the note can't be empty.")
        assertThat(exception).hasCauseThat().isInstanceOf(InvalidNoteException::class.java)
        assertThat(exception).hasMessageThat().startsWith("The title")
        assertThat(exception).hasMessageThat().endsWith("can't be empty.")
    }

    @Test(expected = InvalidNoteException::class)
    fun `add note by blank content, InvalidNoteException`() = runBlocking {
        val note = Note(title = "", content = "", timestamp = 0, color = 0)
        addNote(note = note)
        val exception = InvalidNoteException("The content of the note can't be empty.")
        assertThat(exception).hasCauseThat().isInstanceOf(InvalidNoteException::class.java)
        assertThat(exception).hasMessageThat().startsWith("The content")
        assertThat(exception).hasMessageThat().endsWith("can't be empty.")
    }

    @Test
    fun `add correct note, check entry`() = runBlocking {
        val note =
            Note(title = "This is title", content = "This is content", timestamp = 0, color = 0)
        addNote(note = note)
        assertThat(fakeNotesRepository.getNotes().first().size).isEqualTo(1)
        assertThat(fakeNotesRepository.getNotes().first()[0].title).contains("This is title")
        assertThat(fakeNotesRepository.getNotes().first()[0].content).contains("This is content")
    }
}