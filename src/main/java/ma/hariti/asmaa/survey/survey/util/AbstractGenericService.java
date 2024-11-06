package ma.hariti.asmaa.survey.survey.util;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract base implementation of GenericService with common CRUD operations
 */
@RequiredArgsConstructor
@Transactional
public abstract class AbstractGenericService<T extends BaseEntity, ID, C extends BaseDTO, U extends BaseDTO, R extends BaseDTO>
        implements GenericService<T, ID, C, U, R> {

    protected final JpaRepository<T, ID> repository;

    /**
     * Convert entity to create DTO
     */
    protected abstract C mapToCreateDto(T entity);

    /**
     * Convert create DTO to entity
     */
    protected abstract T mapToEntity(C createDto);

    /**
     * Convert update DTO to entity
     */
    protected abstract void mapToEntity(U updateDto, T entity);

    /**
     * Convert entity to response DTO
     */
    protected abstract R mapToResponseDto(T entity);

    @Override
    public C create(C createDTO) {
        T entity = mapToEntity(createDTO);
        T savedEntity = repository.save(entity);
        return mapToCreateDto(savedEntity);
    }

    @Override
    public C getById(ID id) {
        T entity = findOrThrow(id);
        return mapToCreateDto(entity);
    }

    @Override
    public Page<C> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToCreateDto);
    }

    @Override
    public R update(ID id, U updateDTO) {
        T entity = findOrThrow(id);
        mapToEntity(updateDTO, entity);
        T updatedEntity = repository.save(entity);
        return mapToResponseDto(updatedEntity);
    }

    @Override
    public void delete(ID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public boolean exists(ID id) {
        return repository.existsById(id);
    }

    protected T findOrThrow(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }
}