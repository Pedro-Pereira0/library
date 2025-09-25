package com.project.psoft.authormanagement.api;

import com.project.psoft.authormanagement.model.Author;

public class TopAuthor {
    private final Author author;
    private final Long lendingsCount;

    public TopAuthor(Author author, Long lendingsCount) {
        this.author = author;
        this.lendingsCount = lendingsCount;
    }

    public Author getAuthor() {
        return author;
    }

    public Long getLendingsCount() {
        return lendingsCount;
    }
}
