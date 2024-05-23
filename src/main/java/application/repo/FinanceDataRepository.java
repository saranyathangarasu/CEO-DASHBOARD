package application.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import application.model.FinanceData;

public interface FinanceDataRepository extends JpaRepository<FinanceData, Long> {
	 FinanceData findFirstByDate(Date date);
	 @Query("SELECT fd FROM FinanceData fd WHERE fd.date = :date")
	 FinanceData findByFinanceDataDate(@Param("date") Date date);
	 FinanceData findByDateEquals(Date date);
	 FinanceData findByDate(Date date);
	 List<FinanceData> findAllByDate(Date date);
	 FinanceData findFirstByOrderByDateDesc();
	 FinanceData findTopByOrderByDateDesc();
}
