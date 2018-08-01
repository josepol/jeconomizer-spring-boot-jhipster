package com.polsastre.jeconomizer.repository;

import com.polsastre.jeconomizer.domain.Amount;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Amount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmountRepository extends JpaRepository<Amount, Long> {

    @Query("select amount from Amount amount where amount.userId.login = ?#{principal.username}")
    List<Amount> findByUserIdIsCurrentUser();

}
