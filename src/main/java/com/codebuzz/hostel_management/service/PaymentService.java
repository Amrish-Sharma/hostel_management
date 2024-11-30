package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.model.Payment;
import com.codebuzz.hostel_management.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment makePayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus("Paid");
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentHistory(Long residentId) {
        return paymentRepository.findByResidentId(residentId);
    }
}
