package com.project.psoft.bookmanagement.services;

import com.project.psoft.authormanagement.model.Author;
import com.project.psoft.authormanagement.repositories.AuthorRepository;
import com.project.psoft.bookmanagement.model.Book;
import com.project.psoft.bookmanagement.model.dto.SimplifiedBookDTO;
import com.project.psoft.bookmanagement.model.dto.TopBookLentDTO;
import com.project.psoft.bookmanagement.repositories.BookRepository;
import com.project.psoft.bookmanagement.utils.CoverUrlUtil;
import com.project.psoft.genre.model.Genre;
import com.project.psoft.genre.model.TopGenreDTO;
import com.project.psoft.exceptions.NotFoundException;
import com.project.psoft.genre.repository.GenreRepository;
import com.project.psoft.lendingmanagement.repositories.LendingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final EditBookMapper bookEditMapper;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final FileStorageService fileStorageService;
    private final LendingRepository lendingRepo;

    @Override
    public List<Book> findAll(int page, int limit) {
        int offset = (page - 1) * limit;
        return bookRepository.findAll(offset, limit);
    }

    @Override
    public long countBooks() {
        return bookRepository.count();
    }

    @Override
    public Optional<Book> findOne(final String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Book create(final String isbn, final EditBookRequest resource) {
        Genre genre = genreRepository.findByGenreName(resource.getGenre())
                .orElseThrow(() -> new NotFoundException("Genre not found: " + resource.getGenre()));

        if (resource.getAuthors() == null || resource.getAuthors().isEmpty()) {
            throw new IllegalArgumentException("At least one author is required");
        }

        List<Author> authors = resource.getAuthors().stream()
                .map(authorName -> authorRepository.findByName(authorName.trim())
                        .orElseThrow(() -> new NotFoundException("Author not found: " + authorName)))
                .collect(Collectors.toList());

        final Book book = bookEditMapper.create(isbn, resource);
        book.setGenre(genre);
        book.setAuthors(authors);

        if (resource.getCoverFile() != null && !resource.getCoverFile().isEmpty()) {
            final String filename = fileStorageService.storeFile(isbn, resource.getCoverFile());
            final String coverUrl = CoverUrlUtil.simplifiedUrl() + isbn + "/" + filename;
            book.setCoverUrl(coverUrl);
        }

        return bookRepository.save(book);
    }

    @Override
    public Book update(final String isbn, final EditBookRequest resource, final long desiredVersion) {
        final var book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Cannot update an object that does not yet exist"));

        if (resource.getTitle() == null || resource.getTitle().isEmpty() ||
                resource.getGenre() == null || resource.getGenre().isEmpty() ||
                resource.getAuthors() == null || resource.getAuthors().isEmpty()) {
            throw new IllegalArgumentException("Replace: title, genre and authors are mandatory");
        }

        Genre genre = genreRepository.findByGenreName(resource.getGenre())
                .orElseThrow(() -> new NotFoundException("Genre not found: " + resource.getGenre()));

        List<Author> authors = resource.getAuthors().stream()
                .map(authorName -> authorRepository.findByName(authorName.trim())
                        .orElseThrow(() -> new NotFoundException("Author not found: " + authorName)))
                .collect(Collectors.toList());

        if (resource.getCoverFile() != null && !resource.getCoverFile().isEmpty()) {
            final String filename = fileStorageService.storeFile(isbn, resource.getCoverFile());
            final String coverUrl = CoverUrlUtil.simplifiedUrl() + isbn + "/" + filename;
            book.setCoverUrl(coverUrl);
        } else if (resource.getCoverFile() == null) {
            book.setCoverUrl(null);
        }

        book.updateData(desiredVersion, resource.getTitle(), genre, resource.getDescription(), authors);
        return bookRepository.save(book);
    }

    @Override
    public Book partialUpdate(final String isbn, final EditBookRequest resource, final long desiredVersion) {
        final var book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Cannot update an object that does not yet exist"));

        Genre genre = null;
        if (resource.getGenre() != null) {
            genre = genreRepository.findByGenreName(resource.getGenre())
                    .orElseThrow(() -> new NotFoundException("Genre not found: " + resource.getGenre()));
        }
        if (resource.getGenre() != null) {
            genreRepository.findByGenreName(resource.getGenre())
                    .orElseThrow(() -> new NotFoundException("Genre not found: " + resource.getGenre()));
        }

        List<Author> authors = null;
        if (resource.getAuthors() != null) {
            authors = resource.getAuthors().stream()
                    .map(authorName -> authorRepository.findByName(authorName.trim())
                            .orElseThrow(() -> new NotFoundException("Author not found: " + authorName)))
                    .collect(Collectors.toList());
        }

        if (resource.getCoverFile() != null && !resource.getCoverFile().isEmpty()) {
            final String filename = fileStorageService.storeFile(isbn, resource.getCoverFile());
            final String coverUrl = CoverUrlUtil.simplifiedUrl() + isbn + "/" + filename;
            book.setCoverUrl(coverUrl);
        }

        book.applyPatch(desiredVersion, resource.getTitle(), genre, resource.getDescription(), authors);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> searchTitle(String title, int page, int limit) {
        long totalBooks = bookRepository.countByTitle(title);
        if (totalBooks == 0) {
            throw new NotFoundException("Book with title <" + title + "> not found.");
        }
        int totalPages = (int) Math.ceil((double) totalBooks / limit);
        if (page > totalPages) {
            throw new NotFoundException("Page " + page + " does not exist.");
        }
        int offset = (page - 1) * limit;
        return bookRepository.searchTitle(title, offset, limit);
    }

    @Override
    public List<Book> searchGenre(String genre, int page, int limit) {
        long totalBooks = bookRepository.countByGenre(genre);
        if (totalBooks == 0) {
            throw new NotFoundException("Book with genre <" + genre + "> not found.");
        }
        int totalPages = (int) Math.ceil((double) totalBooks / limit);
        if (page > totalPages) {
            throw new NotFoundException("Page " + page + " not found.");
        }
        int offset = (page - 1) * limit;
        return bookRepository.searchGenre(genre, offset, limit);
    }

    @Override
    public List<Book> searchAuthor(String author, int page, int limit) {
        long totalBooks = bookRepository.countByAuthor(author);
        if (totalBooks == 0) {
            throw new NotFoundException("Book with author <" + author + "> not found.");
        }
        int totalPages = (int) Math.ceil((double) totalBooks / limit);
        if (page > totalPages) {
            throw new NotFoundException("Page " + page + " not found.");
        }

        Pageable pageable = PageRequest.of(page - 1, limit);
        return bookRepository.searchAuthor(author, pageable);
    }

    @Override
    public List<Book> searchByTitleAndGenre(String title, String genre, int page, int limit) {
        long totalBooks = bookRepository.countByTitleAndGenre(title, genre);
        if (totalBooks == 0) {
            throw new NotFoundException("Book with title <" + title + "> and genre <" + genre + "> not found.");
        }
        int totalPages = (int) Math.ceil((double) totalBooks / limit);
        if (page > totalPages) {
            throw new NotFoundException("Page " + page + " not found.");
        }
        int offset = (page - 1) * limit;
        return bookRepository.searchTitleAndGenre(title, genre, offset, limit);
    }

    @Override
    public long countBooksByTitle(String title) {
        return bookRepository.countByTitle(title);
    }

    @Override
    public long countBooksByGenre(String genre) {
        return bookRepository.countByGenre(genre);
    }

    @Override
    public long countBooksByAuthor(String author) {
        return bookRepository.countByAuthor(author);
    }

    @Override
    public long countBooksByTitleAndGenre(String title, String genre) {
        return bookRepository.countByTitleAndGenre(title, genre);
    }

    @Override
    public Map<String, TopGenreDTO> getTopGenres() {
        List<Object[]> results = bookRepository.findTopGenres(PageRequest.of(0, 5));
        Map<String, TopGenreDTO> topGenres = new LinkedHashMap<>();
        String[] positions = {"top1", "top2", "top3", "top4", "top5"};

        for (int i = 0; i < results.size(); i++) {
            Object[] result = results.get(i);
            String genre = (String) result[0];
            int count = ((Long) result[1]).intValue();
            String position = positions[i];
            topGenres.put(position, new TopGenreDTO(genre, count));
        }
        return topGenres;
    }

    @Override
    public List<TopBookLentDTO> getTopBooksLent() {
        List<Object[]> results = lendingRepo.getTopBooksLent(PageRequest.of(0, 5));

        return results.stream()
                .map(result -> {
                    String isbn = (String) result[0];
                    Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new NotFoundException("Book not found"));
                    int count = ((Number) result[1]).intValue();

                    String coverUrl = null;
                    if (book.getCoverUrl() != null && !book.getCoverUrl().isEmpty()) {
                        coverUrl = CoverUrlUtil.generateSimplifiedCoverUrl(book.getIsbn());
                    }

                    SimplifiedBookDTO simplifiedBookDTO = new SimplifiedBookDTO(book.getIsbn(), book.getTitle(), coverUrl);
                    return new TopBookLentDTO(simplifiedBookDTO, count);
                })
                .collect(Collectors.toList());
    }
}