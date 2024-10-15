package com.wora.common.domain.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

    @Query("SELECT e FROM #{#entityName} e WHERE e.deletedAt IS NULL")
    List<T> findAll();

    @Query("SELECT e FROM #{#entityName} e")
    List<T> findAllWithTrashed();

    @Query("SELECT e FROM #{#entityName} e WHERE e.deletedAt IS NOT NULL")
    List<T> findAllDeleted();

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.deletedAt IS NULL")
    Optional<T> findById(@Param("id") ID id);

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id")
    Optional<T> findByIdIncludingDeleted(@Param("id") ID id);

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = :id")
    void softDelete(ID id);
}
