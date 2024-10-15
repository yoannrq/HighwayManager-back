package com.example.HighwayManager.repository;

import com.example.HighwayManager.model.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {
}
