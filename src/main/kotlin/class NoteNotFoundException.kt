// Исключение, которое выбрасывается, если не найдена заметка с заданным ID
class NoteNotFoundException(message: String) : Exception(message)