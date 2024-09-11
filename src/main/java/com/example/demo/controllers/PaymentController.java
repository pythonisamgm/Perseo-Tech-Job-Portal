package com.example.demo.controllers;

import com.example.demo.dto.payment.PaymentConverter;
import com.example.demo.dto.payment.PaymentDTO;
import com.example.demo.models.Payment;
import com.example.demo.services.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private final PaymentServiceImpl paymentService;
    @Autowired
    private final PaymentConverter paymentConverter;


    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        Payment payment = paymentConverter.dtoToPayment(paymentDTO);
        Payment createdPayment = paymentService.createPayment(payment);
        PaymentDTO createdPaymentDTO = paymentConverter.paymentToDto(createdPayment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaymentDTO);
    }


    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        List<PaymentDTO> paymentDTOs = payments.stream()
                .map(paymentConverter::paymentToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentDTOs);
    }


    @GetMapping("/payment/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        Optional<Payment> paymentOpt = paymentService.getPaymentById(id);
        if (paymentOpt.isPresent()) {
            PaymentDTO paymentDTO = paymentConverter.paymentToDto(paymentOpt.get());
            return ResponseEntity.ok(paymentDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/payment/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        Payment payment = paymentConverter.dtoToPayment(paymentDTO);
        payment.setId(id); // Asegurarse de que el ID sea el correcto
        Payment updatedPayment = paymentService.updatePayment(payment);
        if (updatedPayment != null) {
            PaymentDTO updatedPaymentDTO = paymentConverter.paymentToDto(updatedPayment);
            return ResponseEntity.ok(updatedPaymentDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping("/payment/{id}")
    public ResponseEntity<Void> deletePaymentById(@PathVariable Long id) {
        paymentService.deletePaymentById(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteAllPayments() {
        paymentService.deleteAllPayments();
        return ResponseEntity.noContent().build();
    }
}
