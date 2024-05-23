package application.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import application.model.SalesData;

public interface SalesDataRepository extends JpaRepository<SalesData, Long> {
    List<SalesData> findByStatus(String status);
    List<SalesData> findByCompany(String company);
    Optional<SalesData> findTopByOrderByTimestampDesc();
    @Modifying
    @Query("UPDATE SalesData s SET s.status = :status WHERE s.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") String status);
    boolean existsByNameAndEmail(String name, String email);
    @Query("SELECT DISTINCT name FROM SalesData")
    List<String> findAllNames();
    @Query("SELECT DISTINCT company FROM SalesData")
    List<String> findAllCompanies();
    default List<String> getAllExistingData() {
        List<String> names = findAllNames();
        List<String> companies = findAllCompanies();
        names.addAll(companies);
        return names;
    }
}