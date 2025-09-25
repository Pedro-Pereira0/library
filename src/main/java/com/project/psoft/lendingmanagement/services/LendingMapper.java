package com.project.psoft.lendingmanagement.services;

import com.project.psoft.bookmanagement.model.Book;
import com.project.psoft.readermanagement.model.Reader;
import com.project.psoft.lendingmanagement.model.Lending;
import org.springframework.stereotype.Component;

@Component
public class LendingMapper  {

    public Lending create(Reader reader, Book book) {
        if ( reader == null || book == null) {
            return null;
        }
        return new Lending(reader, book);
    }

    public Lending create(Reader reader, Book book, Integer sequenceNumber) {
        if ( reader == null || book == null || sequenceNumber == null) {
            return null;
        }
        return new Lending(reader, book, sequenceNumber);
    }
}
