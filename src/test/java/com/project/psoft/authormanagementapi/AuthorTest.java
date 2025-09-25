/*package com.project.psoft.authormanagementapi;

import com.project.psoft.authormanagement.model.Author;
import com.project.psoft.authormanagement.repositories.AuthorRepository;
import com.project.psoft.authormanagement.service.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorTest {

	@InjectMocks
	private AuthorServiceImpl authorService;

	@Mock
	private AuthorRepository authorRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateAuthor() {
		Author author = new Author();
		author.setName("John Doe");
		author.setShortBio("Short bio");

		when(authorRepository.save(any(Author.class))).thenReturn(author);

		Author createdAuthor = authorService.createAuthor(author);

		assertEquals("John Doe", createdAuthor.getName());
		assertEquals("Short bio", createdAuthor.getShortBio());
	}

	@Test
	public void testUpdateAuthor() {
		Author author = new Author();
		author.setId(1L);
		author.setName("John Doe");
		author.setShortBio("Short bio");

		when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
		when(authorRepository.save(any(Author.class))).thenReturn(author);

		Author updatedAuthor = authorService.updateAuthor(1L, "Jane Doe", "Updated bio");

		assertEquals("Jane Doe", updatedAuthor.getName());
		assertEquals("Updated bio", updatedAuthor.getShortBio());
	}

	@Test
	public void testGetAuthorById() {
		Author author = new Author();
		author.setId(1L);
		author.setName("John Doe");
		author.setShortBio("Short bio");

		when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

		ResponseEntity<Author> response = authorService.getAuthorById(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("John Doe", response.getBody().getName());
		assertEquals("Short bio", response.getBody().getShortBio());
	}

	@Test
	public void testSearchAuthorsByName() {
		Author author1 = new Author();
		author1.setName("John Doe");
		author1.setShortBio("Short bio");

		Author author2 = new Author();
		author2.setName("Jane Doe");
		author2.setShortBio("Short bio");

		List<Author> authors = Arrays.asList(author1, author2);

		when(authorRepository.findByNameStartingWith("Doe")).thenReturn(authors);

		ResponseEntity<List<Author>> response = authorService.searchAuthorsByName("Doe");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}

	@Test
	public void testGetAllAuthors() {
		Author author1 = new Author();
		author1.setName("John Doe");
		author1.setShortBio("Short bio");

		Author author2 = new Author();
		author2.setName("Jane Doe");
		author2.setShortBio("Short bio");

		List<Author> authors = Arrays.asList(author1, author2);

		when(authorRepository.findAll()).thenReturn(authors);

		List<Author> result = authorService.getAllAuthors();

		assertEquals(2, result.size());
	}
}*/
