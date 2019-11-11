package TableModel;

import java.time.LocalDate;

public class IssueBookTM {
    private String issueId;
    private String memberId;
    private LocalDate date;
    private LocalDate returnDate;
    private String bookId;

    public IssueBookTM(String issueId, String memberId, LocalDate date, LocalDate returnDate, String bookId) {
        this.issueId = issueId;
        this.memberId = memberId;
        this.date = date;
        this.returnDate = returnDate;
        this.bookId = bookId;
    }

    public IssueBookTM() {
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
