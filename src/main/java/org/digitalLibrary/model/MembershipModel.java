package org.digitalLibrary.model;

import lombok.*;
import org.digitalLibrary.entities.output.PaymentOutputEntity;
import org.digitalLibrary.entities.output.UserOutputentity;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.enums.Plans;
import org.digitalLibrary.enums.Plantype;

import java.time.Instant;
@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
public class MembershipModel {
    private long membershipid;
    private UserOutputentity user;
    private Instant startdate;
    private Instant enddate;
    private MembershipStatus status;
    private Plantype plantype;
    private PaymentOutputEntity paymentOutputEntity;
    private boolean active;
}
