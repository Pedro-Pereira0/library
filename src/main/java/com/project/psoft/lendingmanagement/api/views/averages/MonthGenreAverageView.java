package com.project.psoft.lendingmanagement.api.views.averages;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class MonthGenreAverageView {

    String month;
    List<GenreAverageView> genreAverages;
}
