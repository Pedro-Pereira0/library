package com.project.psoft.authormanagement.api;

import com.project.psoft.readermanagement.api.ReaderView;
import lombok.Getter;
import lombok.Setter;

public class TopReaderPerGenreDTO {
    @Getter
    @Setter
    private ReaderView reader;
    @Getter
    @Setter
    private Long lendingsCount;

    // getters and setters


    public Long getLendingsCount() {
        return lendingsCount;
    }

    public void setLendingsCount(Long lendingsCount) {
        this.lendingsCount = lendingsCount;
    }
}
