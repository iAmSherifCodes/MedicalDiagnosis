package com.example.diagnose.repositories;

import com.example.diagnose.models.MedicalRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends MongoRepository<MedicalRecord, String> {

    List<MedicalRecord> findBySymptoms(List<Integer> symptoms);
}
