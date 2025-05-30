package org.digitalLibrary.repository.jpa;

import org.digitalLibrary.entities.output.MembershipOutputEntity;
import org.digitalLibrary.enums.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipJpaRepository extends JpaRepository<MembershipOutputEntity, Long> {
    List<MembershipOutputEntity>  findByStatus(MembershipStatus status);
    Optional<MembershipOutputEntity> findByUserUseridAndStatus(long userid, MembershipStatus status);
}
