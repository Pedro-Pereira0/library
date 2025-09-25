package com.project.psoft.readermanagement.api;

import com.project.psoft.readermanagement.model.Reader;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReaderViewMapper {
    public abstract ReaderView toReaderView(Reader reader);

    public abstract List<ReaderView> toReaderView(List<Reader> readers);
}
