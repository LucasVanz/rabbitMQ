package com.microsservicos.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microsservicos.Entity.Saga;

public interface SagaRepository extends JpaRepository<Saga, Long> {
    @Query ("SELECT s FROM Saga s WHERE s.status = 'OPEN' AND s.createdAt < :limite")
    List<Saga> listByStatusAndCreatedAt(@Param ("limite") LocalDateTime limite);
}
