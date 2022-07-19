package TestElek;

import java.time.LocalDate;
import java.util.List;

public class FreedomReporting {
    private int id;
    private String name;
    private List<String> dates;


    public FreedomReporting(int id, String name, List<String> dates) {
        super();
        this.id = id;
        this.name = name;
        this.dates = dates;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
}
