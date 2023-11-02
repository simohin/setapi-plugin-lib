package setapi.plugin.lib.service;

import ru.crystals.pos.api.ui.listener.SumToPayFormListener;
import ru.crystals.pos.spi.ResBundle;
import ru.crystals.pos.spi.plugin.payment.CancelRequest;
import ru.crystals.pos.spi.plugin.payment.PaymentCallback;
import ru.crystals.pos.spi.plugin.payment.PaymentRequest;
import ru.crystals.pos.spi.plugin.payment.RefundRequest;
import ru.crystals.pos.spi.receipt.Receipt;
import ru.crystals.pos.spi.ui.UIForms;
import ru.crystals.pos.spi.ui.payment.SumToPayFormParameters;
import setapi.plugin.lib.util.ErrorHandlingUtil;

import java.math.BigDecimal;
import java.util.function.Consumer;

import static setapi.plugin.lib.config.ResBundleConfig.getResBundle;
import static setapi.plugin.lib.config.UIConfig.getUiForms;

public class UIService {

    private final UIForms ui = getUiForms();
    private static final ResBundle resBundle = getResBundle();
    private static final String ENTER_SUM_TO_PAY = resBundle.getString("enter.sum.to.pay");
    public static final String CAPTION = resBundle.getString("plugin.name");

    public void showSpinner(String key) {
        ui.showSpinnerForm(resBundle.getString(key));
    }

    public void showSumEnterForm(PaymentRequest request, Consumer<BigDecimal> amountConsumer, boolean activeSumEnter) {
        showSumEnterForm(request.getReceipt(), amountConsumer, request.getPaymentCallback(), activeSumEnter);
    }

    public void showSumEnterForm(CancelRequest request, Consumer<BigDecimal> amountConsumer, boolean activeSumEnter) {
        showSumEnterForm(request.getReceipt(), amountConsumer, request.getPaymentCallback(), activeSumEnter);
    }

    public void showSumEnterForm(RefundRequest request, Consumer<BigDecimal> amountConsumer, boolean activeSumEnter) {
        showSumEnterForm(request.getRefundReceipt(), amountConsumer, request.getPaymentCallback(), activeSumEnter);
    }

    private void showSumEnterForm(Receipt receipt, Consumer<BigDecimal> amountConsumer, PaymentCallback callback, boolean activeSumEnter) {

        SumToPayFormParameters parameters = buildFormParameters(receipt, activeSumEnter);
        SumToPayFormListener listener = buildSumToPayFormListener(amountConsumer, callback);

        ui.getPaymentForms().showSumToPayForm(parameters, listener);
    }

    private SumToPayFormListener buildSumToPayFormListener(Consumer<BigDecimal> amountConsumer, PaymentCallback callback) {
        return new SumToPayFormListener() {
            @Override
            public void eventCanceled() {
                callback.paymentNotCompleted();
            }

            @Override
            public void eventSumEntered(BigDecimal amount) {
                ErrorHandlingUtil.runWithHandling(() -> amountConsumer.accept(amount), callback);
            }
        };
    }

    private SumToPayFormParameters buildFormParameters(Receipt receipt, boolean activeSumEnter) {
        SumToPayFormParameters parameters = new SumToPayFormParameters(CAPTION, receipt);
        parameters.setInputHint(resBundle.getString(ENTER_SUM_TO_PAY));
        if (activeSumEnter) {
            parameters.setDefaultSum(receipt.getSurchargeSum());
            parameters.setMinSum(receipt.getSurchargeSum());
        }
        return parameters;
    }
}
