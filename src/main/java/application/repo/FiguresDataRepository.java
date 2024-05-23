package application.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import application.model.FiguresData;

public interface FiguresDataRepository extends JpaRepository<FiguresData, Long> {
    @Query("SELECT f FROM FiguresData f WHERE f.date = ?1")
    List<FiguresData> getRevenueForDate(LocalDate date);

    @Query("SELECT date, SUM(totalRevenue) FROM FiguresData GROUP BY date")
    List<Object[]> getTotalRevenueByDate();

    @Query("SELECT f FROM FiguresData f WHERE f.date BETWEEN ?1 AND ?2")
    List<FiguresData> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT date, SUM(totalRevenue) FROM FiguresData WHERE date BETWEEN :startDate AND :endDate GROUP BY date")
    List<Object[]> getRevenueChartData(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}