import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

// Класс для тестирования сервиса заметок
class NoteServiceTest {
    // Экземпляр сервиса заметок для тестирования
    private lateinit var noteService: NoteService

    // Инициализирует сервис заметок перед каждым тестом
    @Before
    fun setUp() {
        noteService = NoteService()
    }

    // Тест: Создание заметки и получение ее по ID
    @Test
    fun `should create note and get it by id`() {
        // Создаем тестовую заметку
        val note = noteService.createNote("Test Note")
        // Получаем заметку по ID
        val retrievedNote = noteService.getNoteById(note.id)

        // Проверяем, что полученная заметка не null
        assertNotNull(retrievedNote)
        // Проверяем, что полученная заметка совпадает с созданной
        assertEquals(note, retrievedNote)
    }

    // Тест: Получение всех заметок
    @Test
    fun `should get all notes`() {
        // Создаем две тестовые заметки
        val note1 = noteService.createNote("Note 1")
        val note2 = noteService.createNote("Note 2")
        // Получаем список всех заметок
        val notes = noteService.getAllNotes()

        // Проверяем, что в списке 2 заметки
        assertEquals(2, notes.size)
        // Проверяем, что в списке есть первая заметка
        assertTrue(notes.contains(note1))
        // Проверяем, что в списке есть вторая заметка
        assertTrue(notes.contains(note2))
    }

    // Тест: Обновление заметки
    @Test
    fun `should update note`() {
        // Создаем тестовую заметку
        val note = noteService.createNote("Test Note")
        // Текст для обновления заметки
        val updatedText = "Updated Note"
        // Обновляем заметку
        assertTrue(noteService.updateNote(note.id, updatedText))
        // Получаем обновленную заметку
        val retrievedNote = noteService.getNoteById(note.id)

        // Проверяем, что заметка не null
        assertNotNull(retrievedNote)
        // Проверяем, что текст заметки был обновлен
        assertEquals(updatedText, retrievedNote?.text)
    }

    // Тест: Удаление заметки
    @Test
    fun `should delete note`() {
        // Создаем тестовую заметку
        val note = noteService.createNote("Test Note")
        // Удаляем заметку
        assertTrue(noteService.deleteNote(note.id))
        // Пытаемся получить удаленную заметку
        val retrievedNote = noteService.getNoteById(note.id)

        // Проверяем, что заметка не найдена
        assertNull(retrievedNote)
    }

    // Тест: Восстановление заметки
    @Test
    fun `should restore note`() {
        // Создаем тестовую заметку
        val note = noteService.createNote("Test Note")
        // Удаляем заметку
        noteService.deleteNote(note.id)
        // Восстанавливаем заметку
        assertTrue(noteService.restoreNote(note.id))
        // Получаем восстановленную заметку
        val retrievedNote = noteService.getNoteById(note.id)

        // Проверяем, что заметка не null
        assertNotNull(retrievedNote)
        // Проверяем, что текст заметки восстановлен
        assertEquals("Test Note", retrievedNote?.text)
    }

    // Тест: Создание комментария к заметке
    @Test
    fun `should create comment for note`() {
        // Создаем тестовую заметку
        val note = noteService.createNote("Test Note")
        // Создаем комментарий к заметке
        val comment = noteService.createComment(note.id, "Test Comment")
        // Получаем список комментариев к заметке
        val comments = noteService.getCommentsByNoteId(note.id)

        // Проверяем, что в списке 1 комментарий
        assertEquals(1, comments.size)
        // Проверяем, что в списке есть созданный комментарий
        assertTrue(comments.contains(comment))
    }

    // Тест: Попытка создать комментарий к несуществующей заметке (должно возникнуть исключение)
    @Test(expected = NoteNotFoundException::class)
    fun `should throw exception when creating comment for non-existent note`() {
        // Пытаемся создать комментарий к несуществующей заметке
        noteService.createComment(100, "Test Comment")
    }

    // Тест: Получение комментариев по ID заметки
    @Test
    fun `should get comments by note id`() {
        // Создаем тестовую заметку
        val note = noteService.createNote("Test Note")
        // Создаем два комментария к заметке
        val comment1 = noteService.createComment(note.id, "Comment 1")
        val comment2 = noteService.createComment(note.id, "Comment 2")
        // Получаем список комментариев к заметке
        val comments = noteService.getCommentsByNoteId(note.id)

        // Проверяем, что в списке 2 комментария
        assertEquals(2, comments.size)
        // Проверяем, что в списке есть первый комментарий
        assertTrue(comments.contains(comment1))
        // Проверяем, что в списке есть второй комментарий
        assertTrue(comments.contains(comment2))
    }

    // Тест: Обновление комментария
    @Test
    fun `should update comment`() {
        // Создаем тестовую заметку
        val note = noteService.createNote("Test Note")
        // Создаем комментарий к заметке
        val comment = noteService.createComment(note.id, "Test Comment")
        // Текст для обновления комментария
        val updatedText = "Updated Comment"
        // Обновляем комментарий
        assertTrue(noteService.updateComment(comment.id, updatedText))
        // Получаем список комментариев к заметке
        val comments = noteService.getCommentsByNoteId(note.id)

        // Проверяем, что в списке 1 комментарий
        assertEquals(1, comments.size)
        // Проверяем, что текст комментария был обновлен
        assertEquals(updatedText, comments[0].text)
    }

    // Тест: Удаление комментария
    @Test
    fun `should delete comment`() {
        // Создаем тестовую заметку
        val note = noteService.createNote("Test Note")
        // Создаем комментарий к заметке
        val comment = noteService.createComment(note.id, "Test Comment")
        // Удаляем комментарий
        assertTrue(noteService.deleteComment(comment.id))
        // Получаем список комментариев к заметке
        val comments = noteService.getCommentsByNoteId(note.id)

        // Проверяем, что список комментариев пуст
        assertTrue(comments.isEmpty())
    }

    // Тест: Восстановление комментария
    @Test
    fun `should restore comment`() {
        // Создаем тестовую заметку
        val note = noteService.createNote("Test Note")
        // Создаем комментарий к заметке
        val comment = noteService.createComment(note.id, "Test Comment")
        // Удаляем комментарий
        noteService.deleteComment(comment.id)
        // Восстанавливаем комментарий
        assertTrue(noteService.restoreComment(comment.id))
        // Получаем список комментариев к заметке
        val comments = noteService.getCommentsByNoteId(note.id)

        // Проверяем, что в списке 1 комментарий
        assertEquals(1, comments.size)
        // Проверяем, что текст комментария восстановлен
        assertEquals("Test Comment", comments[0].text)
    }
}