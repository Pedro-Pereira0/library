package com.project.psoft.authormanagement.api;

import com.project.psoft.authormanagement.model.Author;
import com.project.psoft.authormanagement.service.BookResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CoAuthorResponse {
    @Setter
    @Getter
    private Author coAuthor;
    @Setter
    @Getter

    private List<BookResponse> books;

}
