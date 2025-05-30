package org.digitalLibrary.entities.input;

import lombok.*;
import org.digitalLibrary.enums.Plantype;
import org.digitalLibrary.model.UserModel;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInputEntity {

    private int amountpaid;
    private LocalDate payemntdate;
    private boolean successful;

}
