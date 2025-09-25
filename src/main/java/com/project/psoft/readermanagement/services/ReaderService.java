package com.project.psoft.readermanagement.services;

import com.project.psoft.bookmanagement.model.Book;
import com.project.psoft.bookmanagement.repositories.BookRepository;
import com.project.psoft.bookmanagement.utils.CoverUrlUtil;
import com.project.psoft.exceptions.ConflictException;
import com.project.psoft.exceptions.NotFoundException;
import com.project.psoft.forbiddenWords.repository.ForbiddenwordRepository;
import com.project.psoft.genre.repository.GenreRepository;
import com.project.psoft.lendingmanagement.repositories.LendingRepository;
import com.project.psoft.readermanagement.model.*;
import com.project.psoft.readermanagement.repositories.ReaderPfpRepository;
import com.project.psoft.readermanagement.repositories.ReaderRepository;
import com.project.psoft.usermanagement.model.Role;
import com.project.psoft.usermanagement.model.User;
import com.project.psoft.usermanagement.repositories.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepo;
    private final UserRepository userRepo;
    private final ForbiddenwordRepository fwRepo;
    private final BookRepository bookRepo;
    private final GenreRepository genreRepo;
    private final LendingRepository lendingRepo;
    private final ReaderPfpRepository readerPfpRepo;
    private final ReaderEditMapper readerEditMapper;
    private final Password passVal = new Password();
    private final PhoneNumber phoneNumberVal = new PhoneNumber();
    private final NameV nameVal = new NameV();
    private final ReaderNumber readerNumGen = new ReaderNumber();
    private final Date dateVal = new Date();
    private final InterestList interestList = new InterestList();

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Reader create(final CreateReaderRequest request){
        if (readerRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new ConflictException("Username already exists!");
        }
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ValidationException("Passwords don't match!");
        }
        if(!passVal.validate(request.getPassword())){
            throw new ValidationException("Password is not valid! It must contain at least one uppercase char, one special character or number and cannot exceed the limit of 8 characters!");
        }

        final Reader reader = readerEditMapper.create(request);

        Validate(reader);

        reader.setReaderNumber(readerNumGen.generate(readerNumGen.getNextReaderNumber(readerRepo)));

        User user = User.newUser(reader.getUsername(),passwordEncoder.encode(request.getPassword()),reader.getName(), Role.READER);
        userRepo.save(user);
        reader.setUser(user);

        return readerRepo.save(reader);
    }
    @Transactional
    public Reader update(final String username, final UpdateReaderRequest request) {

        if(!passVal.validate(request.getPassword())){
            throw new ValidationException("Password is not valid! It must contain at least one uppercase char, one special character or number and cannot exceed the limit of 8 characters!");
        }

        final Reader reader = readerRepo.getByUsername(username);
        final User user = userRepo.getById(reader.getUser().getId());


        readerEditMapper.update(request, reader);

        Validate(reader);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(reader.getName());

        userRepo.save(user);


        return readerRepo.save(reader);
    }


    public Reader getReaderByReaderNumber(final String readerNumber){
        final Reader reader = readerRepo.getByReaderNumber(readerNumber);
        if (reader==null) {
            throw new NotFoundException("Reader with number " + readerNumber + " not found");
        }
        return reader;
    }


    public List<Reader> getReaderByName(Page page, final String name){
        if (page == null) {
            page = new Page(1, 5);
        }

        if(!nameVal.validate(name))
        {
            throw new ValidationException("Name is not valid! It must contain no special characters and there is a limit of 150 characters.");
        }

        final List<Reader> readers = readerRepo.getReaderByName(page, name);
        if (readers.isEmpty()) {
            throw new NotFoundException("Reader with name " + name + " not found");
        }
        return readers;
    }

    public List<Book> getBookSuggestions(Page page, final String username){
        if (page == null) {
            page = new Page(1, 5);
        }

        Reader reader = readerRepo.getByUsername(username);

        Set<String> interestList = reader.getInterestList();

        if(reader.getInterestList().isEmpty()){

            interestList = lendingRepo.getTopGenres();

        }
        final List<Book> books = bookRepo.getBookSuggestions(page, interestList);
        if (books.isEmpty()) {
            throw new NotFoundException("No books found.");
        }

        books.forEach(book -> {
            if (book.getCoverUrl() != null && !book.getCoverUrl().isEmpty()) {
                book.setCoverUrl(CoverUrlUtil.generateSimplifiedCoverUrl(book.getIsbn()));
            }
        });

        return books;
    }

    public List<Reader> getTopReaders(){
        return lendingRepo.getTopReaders();
    }

    @Transactional
    public ReaderPfp createProfilePic(final Reader reader, final MultipartFile pfpFile){
        try {
            String fileUriDownload = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("api/reader/"+reader.getReaderNumber()+"/profilePic")
                    .toUriString();

            ReaderPfp readerPfp = new ReaderPfp(reader,pfpFile.getBytes(),pfpFile.getContentType(),pfpFile.getName(),fileUriDownload);

            reader.setFileDownload(readerPfp.getDownloadUri());
            readerRepo.save(reader);
            return readerPfpRepo.save(readerPfp);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public ReaderPfp updateProfilePic(final Reader reader, final MultipartFile pfpFile){
        ReaderPfp readerPfp = readerPfpRepo.getByReaderNumber(reader.getReaderNumber());
        if(readerPfp == null){
            return createProfilePic(reader, pfpFile);
        }else{
            try {
                readerPfp.setImage(pfpFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            reader.setFileDownload(readerPfp.getDownloadUri());            readerRepo.save(reader);
            return readerPfpRepo.save(readerPfp);
        }
    }


    public ReaderPfp getProfilePic(final String readerNumber){
        final ReaderPfp pfp = readerPfpRepo.getByReaderNumber(readerNumber);
        if(pfp == null){
            throw new NotFoundException("Reader doesn't have a profile picture.");
        }
        return pfp;
    }

    public List<Reader> getReadersByPhoneNumber(final String phoneNumber, Page page){
        if (page == null) {
            page = new Page(1,5);
        }
        Pageable pageable = PageRequest.of(page.getNumber()-1, page.getLimit());

        if(!phoneNumberVal.validate(phoneNumber))
        {
            throw new ValidationException("Phone number is a numeric value with 9 digits!");
        }

        List<Reader> readers = readerRepo.getByPhoneNumber(phoneNumber, pageable);

        if (readers.isEmpty()) {
            throw new NotFoundException("Reader with phone number " + phoneNumber + " not found");
        }
        return readers;
    }

    public List<Reader> getReadersByEmail(final String email, com.project.psoft.usermanagement.services.@Valid @NotNull Page page){
        if (page == null) {
            page = new com.project.psoft.usermanagement.services.Page(1,5);
        }
        Pageable pageable = PageRequest.of(page.getNumber()-1, page.getLimit());

        List<Reader> readers = readerRepo.getByUsername(email, pageable);

        if (readers.isEmpty()) {
            throw new NotFoundException("Reader with email " + email + " not found");
        }
        return readers;
    }

    private void Validate(final Reader reader){

        if(fwRepo.isForbiddenword(reader.getName()))
        {
            throw new ValidationException("Forbidden word!");
        }
        if(!nameVal.validate(reader.getName()))
        {
            throw new ValidationException("Name is not valid! It must contain no special characters and there is a limit of 150 characters.");
        }
        if(!phoneNumberVal.validate(reader.getPhoneNumber()))
        {
            throw new ValidationException("Phone number is a numeric value with 9 digits!");
        }
        if(!reader.getGdprConsent().equalsIgnoreCase("yes")){
            throw new ValidationException("GDPR consent must be accpeted with a yes.");
        }
        if(reader.getAge()<12){
            throw new ValidationException("Reader must be at least 12YO.");
        }
        if(!dateVal.validate(reader.getDateOfBirth())){
            throw new ValidationException("Date must be valid! (dd/mm/yyyy)");
        }
        if(!interestList.validate(reader.getInterestList(),genreRepo))
        {
            throw new ValidationException("Invalid genres");
        }
    }
}
