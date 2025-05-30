package org.digitalLibrary.Mappers.Input;

import org.digitalLibrary.entities.input.MembershipInputEntity;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.model.MembershipModel;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MembershipInputMapper {


    public MembershipModel maptoModel(MembershipInputEntity membershipInputEntity){
        MembershipStatus status = membershipInputEntity.getStatus();
        return MembershipModel.builder()
                .membershipid(membershipInputEntity.getMembershipid())
                .user(membershipInputEntity.getUser())
                .status(status)
                .plantype(membershipInputEntity.getPlantype())
                .paymentOutputEntity(membershipInputEntity.getPaymentOutputEntity())
                .active(membershipInputEntity.isActive())
                .build();
    }
}
