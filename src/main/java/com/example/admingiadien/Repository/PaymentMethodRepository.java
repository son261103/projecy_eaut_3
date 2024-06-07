package com.example.admingiadien.Repository;

import com.example.admingiadien.Entity.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethods, Long> {

}
