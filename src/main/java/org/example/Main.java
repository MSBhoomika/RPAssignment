package org.example;

import org.example.exceptions.JSONDataUpdationFailed;
import org.example.utils.CSVtoObjectListConverter;
import org.example.utils.RegistrarsJSONDataHelper;

import java.util.List;

public class Main {
    public static void main(String[] args) throws JSONDataUpdationFailed {
        List<RegistrarIPDetails> RegistrarIPDetailsList = CSVtoObjectListConverter.convertCSVtoClassObjects(
                "src/main/resources/IpAddressInput.csv", 3);
        for (RegistrarIPDetails RegistrarIPDetailsObj : RegistrarIPDetailsList) {
            System.out.println(RegistrarIPDetailsObj.toString());
        }
        RegistrarsJSONDataHelper.appendRegistrarstoJSON(
                RegistrarIPDetailsList, "src/main/resources/RegistrarIPAddrDetails.json");
    }
}


