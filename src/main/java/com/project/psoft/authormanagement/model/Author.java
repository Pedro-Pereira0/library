package com.project.psoft.authormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.psoft.bookmanagement.model.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class Author {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Size(max = 150)
    @NotBlank(message = "Name cannot be blank")
    @NotNull
    private String name;

    @Getter
    @Setter
    @Size(max = 4096)
    @NotBlank(message = "Short bio cannot be blank")
    @NotNull
    private String shortBio;

    @JsonIgnore
    @Getter
    @Setter
    private String photoPath;

    @Getter
    @Setter
    private String photoURL;


}
