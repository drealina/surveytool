package de.hsba.bi.Umfrage.survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    Page<Survey> findByUserId(Long userId, Pageable pageable);
    Page<Survey> findByActive(boolean active, Pageable pageable);
}
