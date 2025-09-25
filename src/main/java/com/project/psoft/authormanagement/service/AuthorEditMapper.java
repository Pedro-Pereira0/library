package com.project.psoft.authormanagement.service;

import com.project.psoft.authormanagement.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AuthorEditMapper {
    public abstract Author create(CreateAuthorRequest request);
}
