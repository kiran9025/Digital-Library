package org.digitalLibrary.repository.jpa;

import org.digitalLibrary.entities.output.UserOutputentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserOutputentity, Long> {
    Optional<UserOutputentity> findByEmail(String email);
}
