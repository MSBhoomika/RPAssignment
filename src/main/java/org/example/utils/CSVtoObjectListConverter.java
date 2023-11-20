package org.example.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.example.RegistrarIPDetails;
import org.example.exceptions.CsvDataExtractionFailed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CSVtoObjectListConverter {

    /***
     * This method converts CSV file to class objects of RegistrarIPDetails
     * @param csvFilePath path to csv file where Registrar IP details are stored
     * @param rowToStart row number after which CSV rows should be considered for updating JSON File.
     * @return list of RegistrarIPDetails objects that can later used for updation in JSON file
     */
    public static List<RegistrarIPDetails> convertCSVtoClassObjects(
            @NonNull final String csvFilePath,
            @NonNull final int rowToStart
    ) {

        try {
            final CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
            final CsvMapper csvMapper = new CsvMapper();
            final File csvFile = new File(csvFilePath);

            final MappingIterator<RegistrarIPDetails> mappingIterator = csvMapper.readerFor(RegistrarIPDetails.class)
                    .with(csvSchema)
                    .readValues(csvFile);

            // Skip rows before the specified row
            for (int i = 1; i < rowToStart; i++) {
                if(mappingIterator.hasNext()) {
                    mappingIterator.next();
                }
            }

            final List<RegistrarIPDetails> RegistrarIPDetailsList = new ArrayList<>();
            while (mappingIterator.hasNext()) {
                RegistrarIPDetails RegistrarIPDetailsObj = mappingIterator.next();
                RegistrarIPDetailsList.add(RegistrarIPDetailsObj);
            }

            return RegistrarIPDetailsList;

        } catch (IOException e) {
            e.printStackTrace();
            throw new CsvDataExtractionFailed("Failed to convert the CSV data into RegistrarDetails Objects List");
        }
    }
}
