package pjt01.component;

import org.springframework.stereotype.Component;
import pjt01.PaymentType;
import pjt01.service.PaymentFeeCalculator;

import java.util.EnumMap;
import java.util.List;

@Component
public class PaymentFeeCalculatorFactory {

    private final EnumMap<PaymentType, PaymentFeeCalculator> feeMap
            = new EnumMap<>(PaymentType.class);

    //spring 이 모든 paymentFeeCalculator 구현체를 list 로 넣어줌
    //생성자에서 list 로 받는 부분
    /*
    List<PaymentFeeCalculator> calculators = [
         CardFeeCalculator,       // 구현체 1
        SimplePayFeeCalculator,  // 구현체 2
        BankFeeCalculator,       // 구현체 3
        MobileFeeCalculator      // 구현체 4
    ]
     */
    public PaymentFeeCalculatorFactory(List<PaymentFeeCalculator> calculators) {
        // 각 calculator 를 꺼내서
        calculators.forEach(calculator ->
            calculator.getSupportedTypes().forEach(type -> feeMap.put(type, calculator))
        );
        // 인터페이스가 value 타입이지만 실제로 들어가는 건 구현체
        // feeMap.put(CREDIT_CARD, CardFeeCalculator 객체)
        // feeMap.put(KAKAO_PAY, SimplePayFeeCalculator 객체)
    }

    //타입 주면 해당 calculator 반환
    public PaymentFeeCalculator get(PaymentType type) {
        return feeMap.get(type);
    }
}
