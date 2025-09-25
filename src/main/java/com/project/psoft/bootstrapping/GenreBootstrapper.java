package com.project.psoft.bootstrapping;

import com.project.psoft.genre.model.Genre;
import com.project.psoft.genre.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
@Order(4)
public class GenreBootstrapper implements CommandLineRunner {

    private final GenreRepository genreRepo;

    @Override
    public void run(final String... args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/genres.txt"))) {
            List<String> genres = reader.lines().toList();

            for (String genreName : genres) {
                if (genreRepo.findByGenreName(genreName).isEmpty()) {
                    Genre genre = new Genre(genreName);
                    genreRepo.save(genre);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}