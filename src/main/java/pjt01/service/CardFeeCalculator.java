package pjt01.service;

import org.springframework.stereotype.Component;
import pjt01.PaymentType;

import java.util.List;

@Component
public class CardFeeCalculator implements PaymentFeeCalculator {
    @Override
    public List<PaymentType> getSupportedTypes() {
        return List.of(PaymentType.CREDIT_CARD); // 신용카드 담당
    }

    @Override
    public double calculateFee(int amount) {
        return amount * 0.025; // 수수료 2.5%
    }
}