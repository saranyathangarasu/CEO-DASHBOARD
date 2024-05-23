package application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Budgeting;
import application.repo.BudgetingRepository;


@Service
public class BudgetingServiceImpl implements BudgetingService {

    @Autowired
    private BudgetingRepository budgetingRepository;

    @Override
    public List<Budgeting> getAllBudgetingData() {
        return budgetingRepository.findAll();
    }

    @Override
    public Optional<Budgeting> findById(Long id) {
        return budgetingRepository.findById(id);
    }

    @Override
    public void saveBudgeting(Budgeting budgeting) {
        budgetingRepository.save(budgeting);
    }

    @Override
    public void deleteById(Long id) {
        budgetingRepository.deleteById(id);
    }

}
