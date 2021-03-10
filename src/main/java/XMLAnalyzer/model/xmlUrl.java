package XMLAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.MalformedURLException;
import java.net.URL;

public class xmlUrl {

    private URL url;

    public xmlUrl(@JsonProperty("url") String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public URL getUrl() {
        return url;
    }
}
