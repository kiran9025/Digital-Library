package org.digitalLibrary.entities.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.digitalLibrary.entities.output.PaymentOutputEntity;
import org.digitalLibrary.entities.output.UserOutputentity;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.enums.Plans;
import org.digitalLibrary.enums.Plantype;
import org.digitalLibrary.model.PaymentModel;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipInputEntity {

    private long membershipid;
    private UserOutputentity user;
    private Instant startdate;
    private MembershipStatus status;
    @NotNull(message = "plans must be provide")
    private Plantype plantype;
    private PaymentOutputEntity paymentOutputEntity;
    private boolean active;

}
