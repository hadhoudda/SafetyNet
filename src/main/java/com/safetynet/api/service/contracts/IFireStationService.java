package com.safetynet.api.service.contracts;

import com.safetynet.api.model.FireStation;

public interface IFireStationService {
    boolean addFireStation(FireStation fireStation);
    boolean updateFireStation(FireStation fireStation);
    boolean deleteFireStation(FireStation fireStation);
}
