package com.example.diagnose.repositories;

import com.example.diagnose.models.Diagnose;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnoseRepository extends MongoRepository<Diagnose, String> {
}
