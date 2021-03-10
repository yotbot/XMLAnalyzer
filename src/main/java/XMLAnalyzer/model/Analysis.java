package XMLAnalyzer.model;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;

public class Analysis {

    private final ZonedDateTime analyseDate;
    private AnalysisDetails details;

    public Analysis(){
        this.analyseDate = ZonedDateTime.now();
        this.details = new AnalysisDetails();
    }

    public ZonedDateTime getAnalyseDate(){
        return analyseDate;
    }

    public AnalysisDetails getDetails(){
        return details;
    }

    public void run(URL url) throws XMLStreamException, IOException {
        details = details.fetch(url);
    }

}
