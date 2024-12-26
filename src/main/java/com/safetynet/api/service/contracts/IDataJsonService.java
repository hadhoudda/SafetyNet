package com.safetynet.api.service.contracts;

import com.safetynet.api.container.DataJsonContainer;

import java.io.IOException;

public interface IDataJsonService {
    DataJsonContainer readFileJson(String path) throws IOException;
    void writeFileJson(DataJsonContainer dataJsonContainer);
}
