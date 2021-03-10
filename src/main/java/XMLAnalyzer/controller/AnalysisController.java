package XMLAnalyzer.controller;

import XMLAnalyzer.model.Analysis;
import XMLAnalyzer.model.ApiError;
import XMLAnalyzer.model.xmlUrl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.MalformedURLException;

@RestController
public class AnalysisController {

    @PostMapping("/analyze/")
    Analysis analysis(@RequestBody xmlUrl xmlurl) throws IOException, XMLStreamException {
        Analysis analysis =  new Analysis();
        analysis.run(xmlurl.getUrl());
        return analysis;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MalformedURLException.class)
    public ApiError urlError() {
        return new ApiError("Malformed URL", HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IOException.class)
    public ApiError resourceError() {
        return new ApiError("Error processing resource", HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(XMLStreamException.class)
    public ApiError xmlError() {
        return new ApiError("Premature end of file", HttpStatus.BAD_REQUEST);
    }

}
