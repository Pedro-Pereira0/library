package com.project.psoft.lendingmanagement.model.metrics.answerDB;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LendingCountPerGenreDB {

    private final String genre;

    private final Long lendingAmount;
}
