package TableModel;

import java.time.LocalDate;

public class ReturnBookTM {

    private String issueId;
    private String bookId;
    private LocalDate issueDate;
    private LocalDate mustReturnDate;
    private LocalDate returnDate;
    private double fine;

    public ReturnBookTM(String issueId, String bookId, LocalDate issueDate, LocalDate mustReturnDate, LocalDate returnDate, double fine) {
        this.issueId = issueId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.mustReturnDate = mustReturnDate;
        this.returnDate = returnDate;
        this.fine = fine;
    }

    public ReturnBookTM() {
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getMustReturnDate() {
        return mustReturnDate;
    }

    public void setMustReturnDate(LocalDate mustReturnDate) {
        this.mustReturnDate = mustReturnDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
}
