package com.codebuzz.hostel_management.controller;

import com.codebuzz.hostel_management.model.Payment;
import com.codebuzz.hostel_management.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> makePayment(@RequestBody Payment payment) {
        return new ResponseEntity<>(paymentService.makePayment(payment), HttpStatus.CREATED);
    }

    @GetMapping("/{residentId}/history")
    public ResponseEntity<List<Payment>> getPaymentHistory(@PathVariable Long residentId) {
        return new ResponseEntity<>(paymentService.getPaymentHistory(residentId), HttpStatus.OK);
    }
}

