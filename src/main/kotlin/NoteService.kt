class NoteService {
    // Список заметок
    private var notes = mutableListOf<Note>()

    // Список комментариев
    private var comments = mutableListOf<Comment>()

    // Счетчик для генерации ID заметок
    private var noteIdCounter = 1

    // Счетчик для генерации ID комментариев
    private var commentIdCounter = 1

    // Создает новую заметку
    fun createNote(text: String): Note {
        // Создаем новую заметку с уникальным ID и заданным текстом
        val note = Note(noteIdCounter++, text)
        // Добавляем заметку в список заметок
        notes.add(note)
        // Возвращаем созданную заметку
        return note
    }

    // Возвращает список всех заметок
    fun getAllNotes(): List<Note> {
        // Фильтруем список заметок, оставляя только те, которые не удалены (isDeleted = false)
        return notes.filter { !it.isDeleted }
    }

    // Возвращает заметку по ID, если она существует и не удалена
    fun getNoteById(id: Int): Note? {
        // Находим заметку по ID
        return notes.find { it.id == id && !it.isDeleted }
    }

    // Обновляет текст заметки по ID
    fun updateNote(id: Int, text: String): Boolean {
        // Находим заметку по ID
        val note = notes.find { it.id == id && !it.isDeleted }
        // Если заметка найдена, обновляем ее текст и возвращаем true
        if (note != null) {
            note.text = text
            return true
        }
        // Если заметка не найдена, возвращаем false
        return false
    }

    // Логически удаляет заметку по ID
    fun deleteNote(id: Int): Boolean {
        // Находим заметку по ID
        val note = notes.find { it.id == id && !it.isDeleted }
        // Если заметка найдена, помечаем ее как удаленную (isDeleted = true) и возвращаем true
        if (note != null) {
            note.isDeleted = true
            return true
        }
        // Если заметка не найдена, возвращаем false
        return false
    }

    // Восстанавливает логически удаленную заметку по ID
    fun restoreNote(id: Int): Boolean {
        // Находим заметку по ID
        val note = notes.find { it.id == id && it.isDeleted }
        // Если заметка найдена, снимаем метку удаления (isDeleted = false) и возвращаем true
        if (note != null) {
            note.isDeleted = false
            return true
        }
        // Если заметка не найдена, возвращаем false
        return false
    }

    // Создает новый комментарий к заметке
    fun createComment(noteId: Int, text: String): Comment {
        // Находим заметку по ID
        val note = getNoteById(noteId)
        // Если заметка найдена, создаем новый комментарий с уникальным ID, заданным текстом и ID заметки
        if (note != null) {
            val comment = Comment(commentIdCounter++, text, noteId)
            // Добавляем комментарий в список комментариев
            comments.add(comment)
            // Возвращаем созданный комментарий
            return comment
        } else {
            // Если заметка не найдена, выбрасываем исключение NoteNotFoundException
            throw NoteNotFoundException("Note with id $noteId not found.")
        }
    }

    // Возвращает список комментариев к заметке по ID
    fun getCommentsByNoteId(noteId: Int): List<Comment> {
        // Фильтруем список комментариев, оставляя только те, которые относятся к заданной заметке и не удалены
        return comments.filter { it.noteId == noteId && !it.isDeleted }
    }

    // Обновляет текст комментария по ID
    fun updateComment(id: Int, text: String): Boolean {
        // Находим комментарий по ID
        val comment = comments.find { it.id == id && !it.isDeleted }
        // Если комментарий найден, обновляем его текст и возвращаем true
        if (comment != null) {
            comment.text = text
            return true
        }
        // Если комментарий не найден, возвращаем false
        return false
    }

    // Логически удаляет комментарий по ID
    fun deleteComment(id: Int): Boolean {
        // Находим комментарий по ID
        val comment = comments.find { it.id == id && !it.isDeleted }
        // Если комментарий найден, помечаем его как удаленный (isDeleted = true) и возвращаем true
        if (comment != null) {
            comment.isDeleted = true
            return true
        }
        // Если комментарий не найден, возвращаем false
        return false
    }

    // Восстанавливает логически удаленный комментарий по ID
    fun restoreComment(id: Int): Boolean {
        // Находим комментарий по ID
        val comment = comments.find { it.id == id && it.isDeleted }
        // Если комментарий найден, снимаем метку удаления (isDeleted = false) и возвращаем true
        if (comment != null) {
            comment.isDeleted = false
            return true
        }
        // Если комментарий не найден, возвращаем false
        return false
    }
}


// Класс данных, представляющий заметку
data class Note(
    val id: Int,
    var text: String,
    var isDeleted: Boolean = false
)

// Класс данных, представляющий комментарий к заметке
data class Comment(
    val id: Int,
    var text: String,
    val noteId: Int,
    var isDeleted: Boolean = false
)