package com.codebuzz.hostel_management.repository;

import com.codebuzz.hostel_management.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByResidentId(Long residentId);
}
