package com.example.demo.dto.payment;

import com.example.demo.dto.course.CourseDTO;
import com.example.demo.models.Course;
import com.example.demo.models.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentConverter {
    @Autowired
    ModelMapper modelMapper;

    public PaymentDTO paymentToDto (Payment payment){
        return modelMapper.map (payment, PaymentDTO.class);
    }
    public Payment dtoToPayment (PaymentDTO paymentDTO){
        return modelMapper.map(paymentDTO, Payment.class);
    }
}
