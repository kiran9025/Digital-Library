package org.digitalLibrary.Mappers.output;

import lombok.RequiredArgsConstructor;
import org.digitalLibrary.entities.output.PaymentOutputEntity;
import org.digitalLibrary.entities.output.UserOutputentity;
import org.digitalLibrary.model.PaymentModel;
import org.digitalLibrary.repository.jpa.UserJpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentOutputMapper {
    private final UserJpaRepository userJpaRepository;

    public PaymentModel maptoModel(PaymentOutputEntity paymentOutputEntity){
        return PaymentModel.builder()
                .amountpaid(paymentOutputEntity.getAmountpaid())
                .paymentDate(paymentOutputEntity.getPayementdate())
                .successful(paymentOutputEntity.isSuccessful())
                .build();
    }

    public PaymentOutputEntity mapfromModel(PaymentModel payementModel){
        return org.digitalLibrary.entities.output.PaymentOutputEntity.builder()
                .payementdate(payementModel.getPaymentDate())
                .amountpaid(payementModel.getAmountpaid())
                .successful(payementModel.isSuccessful())
                .build();
    }
}
