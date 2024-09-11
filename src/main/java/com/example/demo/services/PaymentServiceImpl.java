package com.example.demo.services;

import com.example.demo.models.Payment;
import com.example.demo.repositories.IPaymentRepository;
import com.example.demo.services.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        try {
            return paymentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all payments", e);
        }
    }

    public Optional<Payment> getPaymentById(Long id) {
        try {
            return paymentRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving payment by id", e);
        }
    }

    public Payment updatePayment(Payment updatedPayment) {
        Optional<Payment> existingPayment = paymentRepository.findById(updatedPayment.getId());
        if (existingPayment.isPresent()) {
            Payment payment = existingPayment.get();
            payment.setTotalAmount(updatedPayment.getTotalAmount());
            payment.setPaymentDate(updatedPayment.getPaymentDate());
            payment.setPaymentStatus(updatedPayment.getPaymentStatus());
            payment.setMethod(updatedPayment.getMethod());
            payment.setUser(updatedPayment.getUser());
            payment.setOnlineCourse(updatedPayment.getOnlineCourse());
            return paymentRepository.save(payment);
        }
        return null;
    }

    public void deletePaymentById(Long id) {
        paymentRepository.deleteById(id);
    }

    public void deleteAllPayments() {
        paymentRepository.deleteAll();
    }
}

