package com.project.psoft.authormanagement.service;

import com.project.psoft.authormanagement.api.CoAuthorResponse;
import com.project.psoft.authormanagement.api.TopReaderPerGenreDTO;
import com.project.psoft.authormanagement.model.Author;
import com.project.psoft.authormanagement.api.TopAuthor;
import com.project.psoft.bookmanagement.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<TopReaderPerGenreDTO> getTopReadersPerGenre(String genre, LocalDate startDate, LocalDate endDate);

    Author createAuthor(CreateAuthorRequest request, MultipartFile photo);

    Author updateAuthor(Long authorId, UpdateAuthorRequest request, MultipartFile photo);

    ResponseEntity<Author> getAuthorById(Long authorId);

    ResponseEntity<List<Author>> searchAuthorsByName(String name);

    List<Author> getAllAuthors();

    List<TopAuthor> getTopAuthors();

    byte[] getAuthorPic(Long authorId);

    List<Book> getBooksByAuthorName(String authorName, int page, int size);

    List<CoAuthorResponse> getCoAuthorsAndBooks(String authorName, int page, int size);

    Optional<Author> findByName(String name);

    Author mapStringToAuthor(String authorName);

}
