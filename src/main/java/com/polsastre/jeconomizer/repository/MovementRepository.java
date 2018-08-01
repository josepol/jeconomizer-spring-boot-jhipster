package com.polsastre.jeconomizer.repository;

import com.polsastre.jeconomizer.domain.Movement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Movement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    @Query("select movement from Movement movement where movement.userId.login = ?#{principal.username}")
    List<Movement> findByUserIdIsCurrentUser();

}
