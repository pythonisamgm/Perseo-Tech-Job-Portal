package com.example.demo.services.interfaces;

import com.example.demo.models.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    public Payment createPayment(Payment payment);

    public List<Payment> getAllPayments();

    public Optional<Payment> getPaymentById(Long id);

    public Payment updatePayment(Payment updatedPayment);

    public void deletePaymentById(Long id);

    public void deleteAllPayments();
}

