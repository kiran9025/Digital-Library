package org.digitalLibrary.Mappers.Input;

import org.digitalLibrary.entities.input.PaymentInputEntity;
import org.digitalLibrary.model.PaymentModel;
import org.springframework.stereotype.Component;

@Component
public class PaymentInputMapper {
    public PaymentModel MaptoModel(PaymentInputEntity payementInputEntity){

        return PaymentModel.builder()

                .amountpaid(payementInputEntity.getAmountpaid())
                .paymentDate(payementInputEntity.getPayemntdate())
                .successful(payementInputEntity.isSuccessful())
                .build();
    }
}
