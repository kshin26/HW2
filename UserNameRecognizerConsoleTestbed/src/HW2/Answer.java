package HW2;

import userNameRecognizerTestbed.UserNameRecognizer;

// answer to a question
public class Answer {
    private String id;
    private String questionId;
    private String author;
    private String content;

    // constructor with validation
    public Answer(String id, String questionId, String author, String content) {
        setId(id);
        setQuestionId(questionId);
        setAuthor(author);
        setContent(content);
    }

    // getters
    public String getId() { return id; }
    public String getQuestionId() { return questionId; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }

    public void setId(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Answer ID cannot be empty.");
        this.id = id;
    }

    // setters with validation
    public void setQuestionId(String questionId) {
        if (questionId == null || questionId.isBlank())
            throw new IllegalArgumentException("Answer must belong to a Question.");
        this.questionId = questionId;
    }

    public void setAuthor(String author) {
        String error = UserNameRecognizer.checkForValidUserName(author);
        if (!error.isEmpty())
            throw new IllegalArgumentException("Invalid Author Username: " + error);
        this.author = author;
    }

    public void setContent(String content) {
        if (content == null || content.length() < 2)
            throw new IllegalArgumentException("Answer must be at least 2 characters.");
        this.content = content;
    }

    @Override
    public String toString() {
        return "A[" + id + "] to Q[" + questionId + "] by " + author + ": " + content;
    }
}
