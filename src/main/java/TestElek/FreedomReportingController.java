package TestElek;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class FreedomReportingController {

    @GetMapping("/FreedomReport")
    public FreedomReporting getFreedomReporting() { //lejelentés 1 emberre
        return new FreedomReporting(1, "Dávid", new ArrayList<String>(Collections.singleton("2020-10-10")));
    }
    //return repository.findAll();

    @GetMapping("/allFreedomsReport")
    public List<FreedomReporting> getFreedomReportingList() { //összes lejelentés

        List<FreedomReporting> Freedom = new ArrayList<>();
        Freedom.add(new FreedomReporting(1, "Dávid", new ArrayList<String>(2020-10-10)));
        return Freedom;
    }
    @GetMapping("/FreedomReport/{id}")
    public FreedomReporting getFreedomReportingPathVariable(@PathVariable("id") Integer id){
        return new FreedomReporting( id, "Dávid" , new ArrayList<String>(2020-10-10) );
    }
    @GetMapping("/FreedomReport/")
    //localhost:8080/FreedomReport
    public FreedomReporting getFreedomReportingQuery(@RequestParam(name = "id") Integer id){
        return new FreedomReporting( id, "Dávid" , new ArrayList<String>(2020-10-10) );
    }

    //region  irás a file ba

    //localhost:8080/PrintReport
    @PostMapping("/PrintReport")
    public void PrintFreedomReporting(@RequestBody FreedomReporting data)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("C:\\xampp\\htdocs\\TestElek\\fileName"+ data.getName()+".Json"), data);
    }

    @GetMapping("/OpenReport")
    public FreedomReporting getOpenFreedom()
            throws IOException {
        Path filePath = Path.of("C:\\xampp\\htdocs\\TestElek\\fileNameJson.Json");
        String content = Files.readString(filePath);
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        FreedomReporting userHoliday = gson.fromJson(content, FreedomReporting.class); // deserializes json into target2
        return userHoliday;
    }

    //endregion
}




