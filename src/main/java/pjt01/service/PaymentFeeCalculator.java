package pjt01.service;

import pjt01.PaymentType;

import java.util.List;

public interface PaymentFeeCalculator {
    List<PaymentType> getSupportedTypes(); // 담당 결제수단
    double calculateFee(int amount);       // 수수료 계산
}
