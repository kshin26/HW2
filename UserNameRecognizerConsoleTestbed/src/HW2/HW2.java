package HW2;

import java.util.*;

import userNameRecognizerTestbed.UserNameRecognizer;

public class HW2 {
    public static Questions questionsDB = new Questions();
    public static Answers answersDB = new Answers();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nHW2 TEST APPLICATION");
            System.out.println("1. Create Question");
            System.out.println("2. Read Question");
            System.out.println("3. Update Question");
            System.out.println("4. Delete Question");
            System.out.println("5. List All Questions");
            System.out.println("6. Search Questions by Author");
            System.out.println("7. Create Answer");
            System.out.println("8. Read Answer");
            System.out.println("9. Update Answer");
            System.out.println("10. Delete Answer");
            System.out.println("11. List All Answers");
            System.out.println("12. Search Answers by Question ID");
            System.out.println("13. Run Automated Test Cases"); // new option
            System.out.println("14. Exit");
            System.out.print("Select option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": createQuestion(); break;
                case "2": readQuestion(); break;
                case "3": updateQuestion(); break;
                case "4": deleteQuestion(); break;
                case "5": listQuestions(); break;
                case "6": searchQuestionsByAuthor(); break;
                case "7": createAnswer(); break;
                case "8": readAnswer(); break;
                case "9": updateAnswer(); break;
                case "10": deleteAnswer(); break;
                case "11": listAnswers(); break;
                case "12": searchAnswersByQuestionId(); break;
                case "13": runAutomatedTests(); break; // runs all test cases
                case "14": System.exit(0); break;
                default: System.out.println("invalid option"); break;
            }
        }
    }

    // question operations
    private static void createQuestion() {
        try {
            System.out.print("question id: ");
            String id = scanner.nextLine();

            // loop until valid username
            String author;
            while (true) {
                System.out.print("author username: ");
                author = scanner.nextLine();
                String error = UserNameRecognizer.checkForValidUserName(author);
                if (error.isEmpty()) break;  // valid username
                System.out.println("error: " + error); // show FSM error
            }

            System.out.print("content: ");
            String content = scanner.nextLine();
            Question q = new Question(id, author, content);
            questionsDB.create(q);
            System.out.println("question created: " + q);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private static void readQuestion() {
        System.out.print("question id: ");
        String id = scanner.nextLine();
        Question q = questionsDB.read(id);
        if (q == null) System.out.println("question not found");
        else System.out.println(q);
    }

    private static void updateQuestion() {
        try {
            System.out.print("question id: ");
            String id = scanner.nextLine();
            System.out.print("new content: ");
            String content = scanner.nextLine();
            questionsDB.update(id, content);
            System.out.println("question updated");
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private static void deleteQuestion() {
        try {
            System.out.print("question id: ");
            String id = scanner.nextLine();
            questionsDB.delete(id);
            System.out.println("question deleted");
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private static void listQuestions() {
        List<Question> list = questionsDB.listAll();
        if (list.isEmpty()) System.out.println("no questions found");
        else list.forEach(System.out::println);
    }

    private static void searchQuestionsByAuthor() {
        System.out.print("author: ");
        String author = scanner.nextLine();
        List<Question> results = questionsDB.searchByAuthor(author);
        if (results.isEmpty()) System.out.println("no questions found for this author");
        else results.forEach(System.out::println);
    }

    // answer operations
    private static void createAnswer() {
        try {
            System.out.print("answer id: ");
            String id = scanner.nextLine();
            System.out.print("question id: ");
            String qid = scanner.nextLine();

            // loop until valid username
            String author;
            while (true) {
                System.out.print("author username: ");
                author = scanner.nextLine();
                String error = UserNameRecognizer.checkForValidUserName(author);
                if (error.isEmpty()) break;
                System.out.println("error: " + error);
            }

            System.out.print("content: ");
            String content = scanner.nextLine();
            Answer a = new Answer(id, qid, author, content);
            answersDB.create(a);
            System.out.println("answer created: " + a);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private static void readAnswer() {
        System.out.print("answer id: ");
        String id = scanner.nextLine();
        Answer a = answersDB.read(id);
        if (a == null) System.out.println("answer not found");
        else System.out.println(a);
    }

    private static void updateAnswer() {
        try {
            System.out.print("answer id: ");
            String id = scanner.nextLine();
            System.out.print("new content: ");
            String content = scanner.nextLine();
            answersDB.update(id, content);
            System.out.println("answer updated");
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private static void deleteAnswer() {
        try {
            System.out.print("answer id: ");
            String id = scanner.nextLine();
            answersDB.delete(id);
            System.out.println("answer deleted");
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private static void listAnswers() {
        List<Answer> list = answersDB.listAll();
        if (list.isEmpty()) System.out.println("no answers found");
        else list.forEach(System.out::println);
    }

    private static void searchAnswersByQuestionId() {
        System.out.print("question id: ");
        String qid = scanner.nextLine();
        List<Answer> results = answersDB.searchByQuestionId(qid);
        if (results.isEmpty()) System.out.println("no answers found for this question");
        else results.forEach(System.out::println);
    }

    // automated tests
    private static void runAutomatedTests() {
        System.out.println("\nRUNNING AUTOMATED TEST CASES");
        int passed = 0, failed = 0;

        // tc1 - create valid question
        try {
            Question q1 = new Question("Q1","student1","What is OOP?");
            questionsDB.create(q1);
            passed++;
            System.out.println("tc1 passed");
        } catch(Exception e){ failed++; System.out.println("tc1 failed: " + e.getMessage()); }

        // tc2 - empty ID
        try { new Question("","student1","content"); failed++; System.out.println("tc2 failed"); } 
        catch(Exception e){ passed++; System.out.println("tc2 passed"); }

        // tc3 - invalid author
        try { new Question("QX","invalid!","content"); failed++; System.out.println("tc3 failed"); }
        catch(Exception e){ passed++; System.out.println("tc3 passed"); }

        // tc4 - content <5 chars
        try { new Question("Q2","student1","abc"); failed++; System.out.println("tc4 failed"); }
        catch(Exception e){ passed++; System.out.println("tc4 passed"); }

        // tc5 - read existing
        if (questionsDB.read("Q1") != null) { passed++; System.out.println("tc5 passed"); } else { failed++; System.out.println("tc5 failed"); }

        // tc6 - read non-existent
        if (questionsDB.read("Q999") == null) { passed++; System.out.println("tc6 passed"); } else { failed++; System.out.println("tc6 failed"); }

        // tc7 - update valid
        try { questionsDB.update("Q1","Updated content"); passed++; System.out.println("tc7 passed"); } 
        catch(Exception e){ failed++; System.out.println("tc7 failed"); }

        // tc8 - update non-existent
        try { questionsDB.update("Q999","content"); failed++; System.out.println("tc8 failed"); } 
        catch(Exception e){ passed++; System.out.println("tc8 passed"); }

        // tc9 - delete existing
        try { questionsDB.delete("Q1"); passed++; System.out.println("tc9 passed"); } 
        catch(Exception e){ failed++; System.out.println("tc9 failed"); }

        // tc10 - delete non-existent
        try { questionsDB.delete("Q999"); failed++; System.out.println("tc10 failed"); } 
        catch(Exception e){ passed++; System.out.println("tc10 passed"); }

        // tc11 - list all when has questions
        questionsDB.create(new Question("Q3","student2","Valid content"));
        if (questionsDB.listAll().size() > 0) { passed++; System.out.println("tc11 passed"); } else { failed++; System.out.println("tc11 failed"); }

        // tc12 - list all empty
        questionsDB = new Questions(); // reset db to empty
        if (questionsDB.listAll().isEmpty()) { passed++; System.out.println("tc12 passed"); } 
        else { failed++; System.out.println("tc12 failed"); }

        // tc13 - search by author exists
        questionsDB.create(new Question("Q4","studentX","content")); 
        if (!questionsDB.searchByAuthor("studentX").isEmpty()) { passed++; System.out.println("tc13 passed"); } else { failed++; System.out.println("tc13 failed"); }

        // tc14 - search by author not found
        if (questionsDB.searchByAuthor("unknown").isEmpty()) { passed++; System.out.println("tc14 passed"); } else { failed++; System.out.println("tc14 failed"); }

        // similar for answers
        // tc15 - create valid answer
        try { Answer a1 = new Answer("A1","Q4","studentY","answer content"); answersDB.create(a1); passed++; System.out.println("tc15 passed"); } 
        catch(Exception e){ failed++; System.out.println("tc15 failed: " + e.getMessage()); }

        // tc16 - create answer empty id
        try { new Answer("","Q4","studentY","content"); failed++; System.out.println("tc16 failed"); } 
        catch(Exception e){ passed++; System.out.println("tc16 passed"); }

        // tc17 - content <2 chars
        try { new Answer("A2","Q4","studentY","a"); failed++; System.out.println("tc17 failed"); } 
        catch(Exception e){ passed++; System.out.println("tc17 passed"); }

        // tc18 - empty question ID
        try { new Answer("A3","","studentY","content"); failed++; System.out.println("tc18 failed"); } 
        catch(Exception e){ passed++; System.out.println("tc18 passed"); }

        // tc19 - read existing
        if (answersDB.read("A1") != null) { passed++; System.out.println("tc19 passed"); } else { failed++; System.out.println("tc19 failed"); }

        // tc20 - read non-existent
        if (answersDB.read("AX") == null) { passed++; System.out.println("tc20 passed"); } else { failed++; System.out.println("tc20 failed"); }

        // tc21 - update valid
        try { answersDB.update("A1","Updated answer"); passed++; System.out.println("tc21 passed"); } 
        catch(Exception e){ failed++; System.out.println("tc21 failed"); }

        // tc22 - update non-existent
        try { answersDB.update("AX","content"); failed++; System.out.println("tc22 failed"); } 
        catch(Exception e){ passed++; System.out.println("tc22 passed"); }

        // tc23 - delete existing
        try { answersDB.delete("A1"); passed++; System.out.println("tc23 passed"); } 
        catch(Exception e){ failed++; System.out.println("tc23 failed"); }

        // tc24 - delete non-existent
        try { answersDB.delete("AX"); failed++; System.out.println("tc24 failed"); } 
        catch(Exception e){ passed++; System.out.println("tc24 passed"); }

        // tc25 - list all when has answers
        answersDB.create(new Answer("A5","Q4","studentY","content")); 
        if (!answersDB.listAll().isEmpty()) { passed++; System.out.println("tc25 passed"); } else { failed++; System.out.println("tc25 failed"); }

        // tc26 - list all empty
        answersDB = new Answers(); // reset db to empty
        if (answersDB.listAll().isEmpty()) { passed++; System.out.println("tc26 passed"); } 
        else { failed++; System.out.println("tc26 failed"); }

        // tc27 - search by question ID exists
        answersDB.create(new Answer("A6","Q4","studentY","content"));
        if (!answersDB.searchByQuestionId("Q4").isEmpty()) { passed++; System.out.println("tc27 passed"); } else { failed++; System.out.println("tc27 failed"); }

        // tc28 - search by question ID no answers
        if (answersDB.searchByQuestionId("QX").isEmpty()) { passed++; System.out.println("tc28 passed"); } else { failed++; System.out.println("tc28 failed"); }

        System.out.println("\nTEST CASES COMPLETED");
        System.out.println("Passed: " + passed + ", Failed: " + failed);
    }
}