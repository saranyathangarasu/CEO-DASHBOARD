package application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import application.model.RatesData;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RatesDataRepository extends JpaRepository<RatesData, Long> {
    List<RatesData> findAllByOrderByDateDesc();
    @Query("SELECT COALESCE(SUM(rd.totalCustomers), 0) FROM RatesData rd WHERE rd.date = :date")
    int findTotalCustomersByDate(@Param("date") Date date);
    RatesData findByDate(Date date);
    @Query("SELECT r FROM RatesData r ORDER BY r.date DESC")
    RatesData findTopByOrderByDateDesc();
    RatesData findFirstByOrderByDateDesc();
    @Query("SELECT MAX(rd.date) FROM RatesData rd WHERE rd.date < :currentDate")
    LocalDate findPreviousDate(@Param("currentDate") LocalDate currentDate);
    List<RatesData> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
