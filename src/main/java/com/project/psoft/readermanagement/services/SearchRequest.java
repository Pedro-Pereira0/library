package com.project.psoft.readermanagement.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class SearchRequest<T> {
    @Valid
    @NotNull
    Page page;

    @Valid
    @NotNull
    T query;
}
