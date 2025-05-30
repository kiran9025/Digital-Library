package org.digitalLibrary.entities.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.enums.Plans;
import org.digitalLibrary.enums.Plantype;

import java.time.Instant;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "membership")
public class MembershipOutputEntity {

    @Id
//    @SequenceGenerator(name = "membership_seq_gen", sequenceName = "membership_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membership_seq_gen")
    private long membershipid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserOutputentity user;

    @Column(name  = "start_date")
    private Instant startdate;

    @Column(name  = "end_date")
    private Instant enddate;

    @Column(name= "status")
    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private MembershipStatus status;

    @Column(name = "plan-type")
    @Enumerated(EnumType.STRING)
    private Plantype plantype;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment")
    private PaymentOutputEntity paymentOutputEntity;

    @Column(name = "is_active")
    private boolean active;

}
