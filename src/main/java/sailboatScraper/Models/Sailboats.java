package sailboatScraper.Models;

public class Sailboats {

    private final long id;
    private final String content;

    public Sailboats(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}