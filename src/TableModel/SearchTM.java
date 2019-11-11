package TableModel;

public class SearchTM {

    private String bookId;
    private String title;
    private String author;
    private String availability;

    public SearchTM() {
    }

    public SearchTM(String bookId, String title, String author, String availability) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.availability = availability;
    }



    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
