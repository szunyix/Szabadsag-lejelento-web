package TestElek;

import TestElek.api.FreedomReportApi;
import TestElek.model.FreedomReporting;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class FreedomReportApiController implements FreedomReportApi {


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return FreedomReportApi.super.getRequest();
    }

    @Override
    public ResponseEntity<FreedomReporting> getFreedomReporting() {
        FreedomReporting freedomReporting = new FreedomReporting();
        freedomReporting.id(1);
        freedomReporting.name("Dávid");
        freedomReporting.dates(new ArrayList<String>(Collections.singleton("2020-9-11")));
        return ResponseEntity.ok(freedomReporting);
    }
    @Override
    public ResponseEntity<List<FreedomReporting>> getFreedomReportingList() {
        List<TestElek.model.FreedomReporting> Freedom = new ArrayList<>();

        FreedomReporting freedomReporting1 = new FreedomReporting();
        FreedomReporting freedomReporting2 = new FreedomReporting();

        freedomReporting1.id(1);
        freedomReporting1.name("Dávid");
        freedomReporting1.dates(new ArrayList<String>(Collections.singleton("2020-9-11")));

        Freedom.add(freedomReporting1);

        freedomReporting2.id(2);
        freedomReporting2.name("Feri");
        freedomReporting2.dates(new ArrayList<String>(Collections.singleton("2020-9-11")));

        Freedom.add(freedomReporting2);

        return ResponseEntity.ok(Freedom);
    }

    @Override
    public ResponseEntity<FreedomReporting> getFreedomReportingById(Integer id) {
        Path filePath = Path.of("C:\\xampp\\htdocs\\TestElek\\fileName"+ id+".Json");
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        String content = null;

        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TestElek.model.FreedomReporting userHoliday = gson.fromJson(content, TestElek.model.FreedomReporting.class); // deserializes json into target2
        return  new ResponseEntity<>(userHoliday, new HttpHeaders(), HttpStatus.OK);
        //return ResponseEntity.ok(freedomReporting);
    }

    @Override
    public ResponseEntity<FreedomReporting> getReadFreedom(FreedomReporting freedomReporting) {

        Path filePath = Path.of("C:\\xampp\\htdocs\\TestElek\\fileName"+ freedomReporting.getId()+".Json");
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        String content = null;

        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TestElek.model.FreedomReporting userHoliday = gson.fromJson(content, TestElek.model.FreedomReporting.class); // deserializes json into target2
        return  new ResponseEntity<>(userHoliday, new HttpHeaders(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> printFreedomReporting(FreedomReporting freedomObject) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("C:\\xampp\\htdocs\\TestElek\\fileName"+ freedomObject.getId()+".Json"), freedomObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>( new HttpHeaders(), HttpStatus.CREATED);
    }
}
