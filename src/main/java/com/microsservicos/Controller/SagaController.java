package com.microsservicos.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsservicos.Entity.Saga;
import com.microsservicos.Enum.SagaStatus;
import com.microsservicos.Repository.SagaRepository;

@RestController
@RequestMapping (value = "saga")
public class SagaController {
    
    private final SagaRepository sagaRepository;

    public SagaController(SagaRepository sagaRepository) {
        this.sagaRepository = sagaRepository;
    }

    @PutMapping("/{id}")
    private ResponseEntity<Object> updateSaga(Long id){
            Saga saga = sagaRepository.findById(id).orElseThrow();
            saga.setStatus(SagaStatus.COMPLETED);
            sagaRepository.save(saga);
            return new ResponseEntity<>(HttpStatus.OK);
    }

}
