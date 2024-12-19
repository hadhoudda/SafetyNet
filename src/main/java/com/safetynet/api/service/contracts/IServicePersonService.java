package com.safetynet.api.service.contracts;

import com.safetynet.api.dto.ChildAlertDto;
import com.safetynet.api.dto.PersonAndMedicalRecordDto;
import com.safetynet.api.dto.PersonInfos;
import com.safetynet.api.dto.PersonListByFireStationWithCountDto;
import com.safetynet.api.model.Person;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IServicePersonService {

    List<Person> filtrePersonByFirestation(String firestation);
    PersonListByFireStationWithCountDto findAllPersonListByFireStationWithCount(String stationNumber) throws IOException;
    List<String> findAllListMailPersonByCity(String city);
    List<String> findAllListPhonePersonByFireStation(String firestation);
    Map<List<ChildAlertDto>, List<Person>> findAllChildByAdress(String adress);
    List<PersonAndMedicalRecordDto> findAllPersonAndMedicalByAdress(String adress);
    Map<String, List<PersonAndMedicalRecordDto>> findAllPersonGroupedByAddress(String station);
    List<PersonInfos> findAllPersonInfos(String lastName);

}
