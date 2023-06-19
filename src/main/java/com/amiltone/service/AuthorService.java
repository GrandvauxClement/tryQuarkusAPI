package com.amiltone.service;

import com.amiltone.domain.Author;
import com.amiltone.entity.AuthorEntity;
import com.amiltone.exceptions.ServiceException;
import com.amiltone.mapper.AuthorMapper;
import com.amiltone.repository.AuthorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    public List<Author> findAll() {
        return this.authorMapper.toDomainList(authorRepository.findAll().list());
    }

    public Optional<Author> findById(@NonNull UUID authorId){
        return authorRepository.findByIdOptional(authorId).map(authorMapper::toDomain);
    }

    @Transactional
    public void save(@Valid Author author){
        log.debug("Saving author : {}", author);
        AuthorEntity entity = authorMapper.toEntity(author);
        authorRepository.persist(entity);
        authorMapper.updateDomainFromEntity(entity, author);
    }

    @Transactional
    public void update(@Valid Author author){
        log.debug("Updating author : {}", author);
        if (Objects.isNull(author.getId())){
            throw new ServiceException("Author does not have author id");
        }

        AuthorEntity entity = authorRepository.findByIdOptional(author.getId())
                .orElseThrow(() -> new ServiceException("No author found for authorId[%s]", author.getId()));
        authorMapper.updateEntityFromDomain(author, entity);
        authorRepository.persist(entity);
        authorMapper.updateDomainFromEntity(entity, author);
    }
}
