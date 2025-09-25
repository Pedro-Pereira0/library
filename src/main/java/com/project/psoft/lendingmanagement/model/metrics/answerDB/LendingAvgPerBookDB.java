package com.project.psoft.lendingmanagement.model.metrics.answerDB;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LendingAvgPerBookDB {
    String bookTitle;
    double averageDuration;
}
