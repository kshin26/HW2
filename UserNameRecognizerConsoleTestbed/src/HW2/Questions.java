package HW2;

import java.util.*;

// manages a collection of questions (crud + search)
public class Questions {
    private Map<String, Question> questions = new HashMap<>();
    
    // creates a new question
    public void create(Question q) {
        if (questions.containsKey(q.getId()))
            throw new IllegalArgumentException("Question with this ID already exists.");
        questions.put(q.getId(), q);
    }
    
    // reads an existing question
    public Question read(String id) {
        return questions.get(id);
    }

    // update question content
    public void update(String id, String newContent) {
        Question q = questions.get(id);
        if (q == null) throw new IllegalArgumentException("Question not found.");
        q.setContent(newContent);
    }
    
    // delete a question
    public void delete(String id) {
        if (!questions.containsKey(id))
            throw new IllegalArgumentException("Question not found.");
        questions.remove(id);
    }
    
    // list all questions
    public List<Question> listAll() {
        return new ArrayList<>(questions.values());
    }

    // search questions by author
    public List<Question> searchByAuthor(String author) {
        List<Question> results = new ArrayList<>();
        for (Question q : questions.values()) {
            if (q.getAuthor().equalsIgnoreCase(author)) results.add(q);
        }
        return results;
    }
}
