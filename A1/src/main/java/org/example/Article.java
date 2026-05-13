package org.example;

public class Article {
    private String id;
    private String title;
    private String abstractText;

    // Constructor to initialize Article object with ID, title and abstract
    public Article(String id, String title, String abstractText) {
        this.id = id;
        this.title = title;
        this.abstractText = abstractText;
    }

    // Getter methods for accessing private fields
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAbstractText() { return abstractText; }

    // String representation of Article object for debugging
    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", abstract='" + abstractText + '\'' +
                '}';
    }
}
