package setapi.plugin.lib.service;

import ru.crystals.pos.spi.plugin.payment.PaymentRequest;
import setapi.plugin.lib.model.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static setapi.plugin.lib.service.TransactionAmountHandler.setTransactionAmount;
import static setapi.plugin.lib.service.TransactionOperationHandler.setOperation;
import static setapi.plugin.lib.service.TransactionStartHandler.setTransactionStart;

public class PaymentService extends SharedResourcesService {

    public void process(PaymentRequest request) {
        if (configProperties.isShowSumEnterForm()) {
            uiService.showSumEnterForm(request, amount -> doProcess(request, amount), configProperties.isActiveSumEnterFormPayment());
            return;
        }

        doProcess(request, request.getReceipt().getSurchargeSum());
    }

    private void doProcess(PaymentRequest request, BigDecimal amount) {
        setTransactionStart();
        setTransactionAmount(amount);
        setOperation(Operation.PAYMENT);
        bankIntegrationService.process(request, amount.setScale(0, RoundingMode.FLOOR).intValue());
    }

}
