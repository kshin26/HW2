package HW2;

import java.util.*;

// manages a collection of answers (crud + search)
public class Answers {
    private Map<String, Answer> answers = new HashMap<>();

    // create a new answer
    public void create(Answer a) {
        if (answers.containsKey(a.getId()))
            throw new IllegalArgumentException("Answer with this ID already exists.");
        answers.put(a.getId(), a);
    }

    // read an answer
    public Answer read(String id) {
        return answers.get(id);
    }

    // update answer content
    public void update(String id, String newContent) {
        Answer a = answers.get(id);
        if (a == null) throw new IllegalArgumentException("Answer not found.");
        a.setContent(newContent);
    }

    // delete an answer
    public void delete(String id) {
        if (!answers.containsKey(id))
            throw new IllegalArgumentException("Answer not found.");
        answers.remove(id);
    }

    // list all answers
    public List<Answer> listAll() {
        return new ArrayList<>(answers.values());
    }

    // search answers by question id
    public List<Answer> searchByQuestionId(String qid) {
        List<Answer> results = new ArrayList<>();
        for (Answer a : answers.values()) {
            if (a.getQuestionId().equals(qid)) results.add(a);
        }
        return results;
    }
}
