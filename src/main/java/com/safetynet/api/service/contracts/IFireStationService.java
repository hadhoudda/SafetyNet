package com.safetynet.api.service.contracts;

import com.safetynet.api.model.FireStation;

public interface IFireStationService {

    boolean existsFireStation(FireStation fireStation, String pathFile);

    boolean addFireStation(FireStation fireStation, String pathFile);

    boolean updateFireStation(FireStation fireStation, String pathFile);

    boolean deleteFireStation(FireStation fireStation, String pathFile);

}