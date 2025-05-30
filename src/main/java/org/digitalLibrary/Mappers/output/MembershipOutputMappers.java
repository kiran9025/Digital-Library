package org.digitalLibrary.Mappers.output;

import org.digitalLibrary.entities.output.MembershipOutputEntity;
import org.digitalLibrary.entities.output.UserOutputentity;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.model.MembershipModel;
import org.digitalLibrary.repository.jpa.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MembershipOutputMappers {

    private final UserJpaRepository userJpaRepository;

    public MembershipOutputMappers(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public MembershipModel maptoModel(MembershipOutputEntity membershipOutputEntity){
        UserOutputentity user = this.userJpaRepository.findById(membershipOutputEntity.getUser().getUserid())
                .orElseThrow(()  ->  new RuntimeException("User Not found"));
        MembershipStatus status = membershipOutputEntity.getStatus();
        return MembershipModel.builder()
                .membershipid(membershipOutputEntity.getMembershipid())
                .user(user)
                .startdate(Instant.now())
                .enddate(membershipOutputEntity.getEnddate())
                .status(status)
                .plantype(membershipOutputEntity.getPlantype())
                .paymentOutputEntity(membershipOutputEntity.getPaymentOutputEntity())
                .active(membershipOutputEntity.isActive())
                .build();
    }
    public MembershipOutputEntity mapfromModel(MembershipModel membershipModel){
        UserOutputentity user = this.userJpaRepository.findById(membershipModel.getUser().getUserid())
                .orElseThrow(()  ->  new RuntimeException("User Not found"));
        MembershipStatus status = membershipModel.getStatus();
        return MembershipOutputEntity.builder()
                .membershipid(membershipModel.getMembershipid())
                .user(user)
                .startdate(Instant.now())
                .enddate(membershipModel.getEnddate())
                .status(status)
                .plantype(membershipModel.getPlantype())
                .paymentOutputEntity(membershipModel.getPaymentOutputEntity())
                .active(membershipModel.isActive())
                .build();
    }
}
