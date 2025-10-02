package HW2;

import userNameRecognizerTestbed.UserNameRecognizer;

// represents a student question
public class Question {
    private String id; // id for question
    private String author; // author username
    private String content;	// question text

    // constructor with validation
    public Question(String id, String author, String content) {
        setId(id);
        setAuthor(author);
        setContent(content);
    }

    // getters
    public String getId() { return id; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }

    // setters with validation
    public void setId(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Question ID cannot be empty.");
        this.id = id;
    }

    public void setAuthor(String author) {
        String error = UserNameRecognizer.checkForValidUserName(author);
        if (!error.isEmpty())
            throw new IllegalArgumentException("Invalid Author Username: " + error);
        this.author = author;
    }

    public void setContent(String content) {
        if (content == null || content.length() < 5)
            throw new IllegalArgumentException("Question must be at least 5 characters.");
        this.content = content;
    }

    @Override
    public String toString() {
        return "Q[" + id + "] by " + author + ": " + content;
    }
}
