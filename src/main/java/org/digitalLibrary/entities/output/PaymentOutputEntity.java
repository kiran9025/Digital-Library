package org.digitalLibrary.entities.output;

import jakarta.persistence.*;
import lombok.*;
import org.digitalLibrary.enums.Plantype;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentOutputEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int amountpaid;
    private LocalDate payementdate;
    private boolean successful;
}
