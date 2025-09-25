package com.project.psoft.readermanagement.repositories;

import com.project.psoft.readermanagement.model.ReaderPfp;

import java.util.Optional;

public interface ReaderPfpRepository {
    ReaderPfp save(ReaderPfp obj);

    ReaderPfp getByReaderNumber(final String readerNumber);
}
