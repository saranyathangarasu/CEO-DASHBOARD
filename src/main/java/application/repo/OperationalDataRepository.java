package application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import application.model.OperationalData;

import java.util.List;
import java.util.UUID;

public interface OperationalDataRepository extends JpaRepository<OperationalData, Long> {
    OperationalData getOperationalDataById(Long id);
    @Query("SELECT o FROM OperationalData o WHERE o.name = :nameValue")
    List<OperationalData> customQueryMethod(@Param("nameValue") String nameValue);
}