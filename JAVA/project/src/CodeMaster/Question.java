package CodeMaster;

public class Question {
    private int srNo;
    private String question;
    private String solution;

    public Question(int srNo, String question, String solution) {
        this.srNo = srNo;
        this.question = question;
        this.solution = solution;
    }

    public int getSrNo() {
        return srNo;
    }

    public String getQuestion() {
        return question;
    }

    public String getSolution() {
        return solution;
    }
}
