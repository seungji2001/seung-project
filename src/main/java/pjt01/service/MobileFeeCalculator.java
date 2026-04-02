package pjt01.service;

import org.springframework.stereotype.Component;
import pjt01.PaymentType;

import java.util.List;

@Component
public class MobileFeeCalculator implements PaymentFeeCalculator {
    @Override
    public List<PaymentType> getSupportedTypes() {
        return List.of(PaymentType.MOBILE);
    }
    @Override
    public double calculateFee(int amount) {
        return amount * 0.030;
    }
}