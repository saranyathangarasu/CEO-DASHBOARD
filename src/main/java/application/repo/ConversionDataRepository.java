package application.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.model.ConversionData;

@Repository
public interface ConversionDataRepository extends JpaRepository<ConversionData, Long> {
	List<ConversionData> findByDateBetween(LocalDate startDate, LocalDate endDate);
}