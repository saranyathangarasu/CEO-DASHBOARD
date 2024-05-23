package application.repo;

import application.model.Budgeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetingRepository extends JpaRepository<Budgeting, Long> {
}