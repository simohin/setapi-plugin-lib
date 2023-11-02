package setapi.plugin.lib.service;

import ru.crystals.pos.spi.plugin.payment.CancelRequest;
import setapi.plugin.lib.model.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static setapi.plugin.lib.service.TransactionAmountHandler.setTransactionAmount;
import static setapi.plugin.lib.service.TransactionOperationHandler.setOperation;
import static setapi.plugin.lib.service.TransactionStartHandler.setTransactionStart;

public class CancelService extends SharedResourcesService {

    public void process(CancelRequest request) {
        BigDecimal amount = request.getPayment().getSum();
        setTransactionStart();
        setTransactionAmount(amount);
        setOperation(Operation.CANCEL);
        bankIntegrationService.process(request, amount.setScale(0, RoundingMode.FLOOR).intValue());
    }
}
