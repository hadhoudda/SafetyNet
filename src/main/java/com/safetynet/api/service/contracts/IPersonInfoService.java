package com.safetynet.api.service.contracts;

import com.safetynet.api.dto.*;
import com.safetynet.api.model.Person;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IPersonInfoService {

    List<Person> filterPersonByFirestation(String firestation, String pathFile);

    PersonListByFireStationWithCountDto findAllPersonListByFireStationWithCount(String stationNumber, String pathFile) throws IOException;

    List<String> findAllListPhonePersonByFireStation(String firestation, String pathFile);

    Map<List<ChildAlertDto>, List<Person>> findAllChildByAddressAndPersonFromEvenHouse(String address, String pathFile);

    Map<String, List<PersonAndMedicalByAddressDto>> findAllPersonAndMedicalByAddress(String address, String pathFile);

    Map<String, List<PersonAndMedicalByAddressDto>> findAllPersonGroupedByAddress(String station, String pathFile);

    List<PersonInfosDto> findAllPersonInfos(String lastName, String pathFile);

    List<String> findAllListMailPersonByCity(String city);
}