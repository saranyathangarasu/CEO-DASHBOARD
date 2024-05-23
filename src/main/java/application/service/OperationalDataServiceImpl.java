package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import application.model.OperationalData;
import application.repo.OperationalDataRepository;

@Service
public class OperationalDataServiceImpl implements OperationalDataService {

    @Autowired
    private OperationalDataRepository operationalDataRepository;

    public List<OperationalData> getAllOperationalData() {
        return operationalDataRepository.findAll();
    }

    @Override
    public OperationalData getLatestOperationalData() {
        return operationalDataRepository.getOperationalDataById(null);
    }

    @Override
    public void saveOperationalData(OperationalData operationalData) {
        operationalDataRepository.save(operationalData);
    }

    @Override
    public OperationalData getOperationalDataById(Long id) {
        return operationalDataRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteOperationalData(Long id) {
        operationalDataRepository.deleteById(id);
    }
    
    @Override
    public void deleteOperationalDataById(Long id) {
        operationalDataRepository.deleteById(id);
    }
    
    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<OperationalData> data = operationalDataRepository.findById(id);
        data.ifPresent(operationalDataRepository::delete);
    }

    @Override
    public void updateOperationalData(OperationalData operationalData) {
        if (operationalDataRepository.existsById(operationalData.getId())) {
            operationalDataRepository.save(operationalData);
        } else {
            throw new IllegalArgumentException("Operational data with ID " + operationalData.getId() + " not found.");
        }
    }
}