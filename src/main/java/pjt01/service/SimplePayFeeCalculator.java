package pjt01.service;

import org.springframework.stereotype.Component;
import pjt01.PaymentType;

import java.util.List;

@Component
public class SimplePayFeeCalculator implements PaymentFeeCalculator {
    @Override
    public List<PaymentType> getSupportedTypes() {
        return List.of(PaymentType.KAKAO_PAY, PaymentType.NAVER_PAY);
    }
    @Override
    public double calculateFee(int amount) {
        return amount * 0.015;
    }
}