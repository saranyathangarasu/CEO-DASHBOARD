package application.service;

import java.util.List;
import java.util.Optional;

import application.model.Budgeting;

public interface BudgetingService {
    List<Budgeting> getAllBudgetingData();
    Optional<Budgeting> findById(Long id);
    void saveBudgeting(Budgeting budgeting);
    void deleteById(Long id);
}
