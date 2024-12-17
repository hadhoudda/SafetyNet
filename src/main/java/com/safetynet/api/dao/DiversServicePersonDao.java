package com.safetynet.api.dao;

import com.safetynet.api.dto.PersonListByFireStationWithCountDto;

import java.io.IOException;
import java.util.List;

public interface DiversServicePersonDao {

    PersonListByFireStationWithCountDto findAllPersonListByFireStationWithCount(String stationNumber) throws IOException;
    List<String> findAllListMailPersonByCity(String city);
    List<String> findAllListPhonePersonByFireStation(String firestation);

}
