package org.example.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.example.RegistrarIPDetails;
import org.example.exceptions.JSONDataUpdationFailed;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@UtilityClass
public class RegistrarsJSONDataHelper {

    /***
     * This method updates the Registrars IP JSON file with the servers data
     * @param RegistrarIPDetailsList List of RegistrarIPDetails class objects
     * @param registrarsIPDetailsJSONFilePath Path to JSON file containing Registrar IP details
     * @throws JSONDataUpdationFailed Custom Exception thrown
     */
    public static void appendRegistrarstoJSON(
            @NotNull final List<RegistrarIPDetails> RegistrarIPDetailsList,
            @NonNull final String registrarsIPDetailsJSONFilePath
    ) throws JSONDataUpdationFailed {
        JSONParser jsonParser = new JSONParser();

        try {
            // Read existing JSON file
            FileReader jsonReader = new FileReader(registrarsIPDetailsJSONFilePath);
            JSONObject registrarIPDetailsJSONData = (JSONObject) jsonParser.parse(jsonReader);

            for (RegistrarIPDetails registrarIPDetailsObj : RegistrarIPDetailsList) {

                // Find the environment in the JSON data
                JSONObject environmentData = (JSONObject) ((JSONObject) registrarIPDetailsJSONData.get("Environment"))
                        .get(registrarIPDetailsObj.getEnv());

                if (environmentData == null) {
                    throw new JSONDataUpdationFailed("Failed to find Environment: " + registrarIPDetailsObj.getEnv()
                            + " Please make sure that you have added the environment related data in the JSON file");
                }

                // Find the "Servers" array in the environment data
                JSONObject servers = (JSONObject) environmentData.get("Servers");

                JSONArray server1Array = (JSONArray) servers.get("Server1");
                JSONArray server2Array = (JSONArray) servers.get("Server2");

                JSONObject server1Instance = new JSONObject();
                server1Instance.put("Eip" + server1Array.size(), registrarIPDetailsObj.getServer1PublicIP());
                server1Instance.put("PrivateL", registrarIPDetailsObj.getServer1PrivateIP1());
                server1Instance.put("PrivateR", registrarIPDetailsObj.getServer1PrivateIP2());

                JSONObject server2Instance = new JSONObject();
                server2Instance.put("Eip" + server2Array.size(), registrarIPDetailsObj.getServer2PublicIP());
                server2Instance.put("PrivateL", registrarIPDetailsObj.getServer2PrivateIP1());
                server2Instance.put("PrivateR", registrarIPDetailsObj.getServer2PrivateIP2());

                // Append the server instance object to the "Servers" array
                server1Array.add(server1Instance);
                server2Array.add(server2Instance);
            }

            // Write the updated data back to the JSON file
            FileWriter jsonWriter = new FileWriter(registrarsIPDetailsJSONFilePath);
            jsonWriter.write(registrarIPDetailsJSONData.toJSONString());
            jsonWriter.flush();

            // Close readers and writers
            jsonReader.close();
            jsonWriter.close();
        } catch (IOException | ParseException | NullPointerException e) {
            e.printStackTrace();
            throw new JSONDataUpdationFailed("Failed to add the servers to Registrars JSON Data");
        }

    }
}
