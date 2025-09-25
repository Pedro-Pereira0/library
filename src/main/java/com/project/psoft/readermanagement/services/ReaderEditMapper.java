package com.project.psoft.readermanagement.services;

import com.project.psoft.readermanagement.model.Reader;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ReaderEditMapper {
    public abstract Reader create(CreateReaderRequest request);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(UpdateReaderRequest request, @MappingTarget Reader reader);
}
