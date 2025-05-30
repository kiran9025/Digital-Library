package org.digitalLibrary.model;

import lombok.*;


import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {

    private int amountpaid;
    private LocalDate paymentDate;
    private boolean successful;

}
