package application.service;

import java.util.List;

import application.model.OperationalData;

public interface OperationalDataService {

    List<OperationalData> getAllOperationalData();

    OperationalData getOperationalDataById(Long id);

    void saveOperationalData(OperationalData operationalData);

    void updateOperationalData(OperationalData operationalData);

    void deleteOperationalData(Long id);
    
    OperationalData getLatestOperationalData();
    
    void deleteOperationalDataById(Long id);
    
    void deleteById(Long id);

}