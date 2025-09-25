package com.project.psoft.readermanagement.repositories;

import com.project.psoft.exceptions.NotFoundException;
import com.project.psoft.readermanagement.model.Reader;
import com.project.psoft.readermanagement.services.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface ReaderRepository {
    <S extends Reader> List<S> saveAll(Iterable<S> entities);

    List<Reader> findAll(Pageable page);
    List<Reader> findAll();


    <S extends Reader> S save(S entity);

    Optional<Reader> findById(Long objectId);

    Optional<Reader> findByUsername(String username);

    Optional<Reader> findByReaderNumber(String readerNumber);

    default Reader getByReaderNumber(final String readerNumber){
        final Optional<Reader> maybeReader = findByReaderNumber(readerNumber);
        // throws 404 Not Found if the user does not exist or is not enabled
        return maybeReader.filter(Reader::isEnabled).orElseThrow(() -> new NotFoundException(Reader.class, readerNumber));
    }

    default Reader getByUsername(final String username) {
        final Optional<Reader> maybeReader = findByUsername(username);
        // throws 404 Not Found if the user does not exist or is not enabled
        return maybeReader.filter(Reader::isEnabled).orElseThrow(() -> new NotFoundException(Reader.class, username));
    }

    String findLastReaderNumber(final String year);

    default String getLastReaderNumber(final String year){
        final String lastReaderNumber = findLastReaderNumber(year);

        if(lastReaderNumber == null)
        {
            return year+"/0";
        }else
        {
            return lastReaderNumber;
        }

    }

    List<Reader> getReaderByName(Page page, String name);

    List<Reader> getByPhoneNumber(final String phoneNumber, Pageable page);

    List<Reader> getByUsername(String username, Pageable page);

}
