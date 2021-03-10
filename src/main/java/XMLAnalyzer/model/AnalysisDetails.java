package XMLAnalyzer.model;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;

public class AnalysisDetails {

    private LocalDateTime firstPost;
    private LocalDateTime lastPost;
    private int totalPosts;
    private int totalAcceptedPosts;
    private int sumScores;

    public AnalysisDetails() {}

    public int getTotalPosts(){ return totalPosts; }

    public int getAvgScore(){
        //Average score is the sum of scores divided by the total number of posts
        return sumScores/totalPosts;
    }

    public LocalDateTime getFirstPost() {
        return firstPost;
    }

    public LocalDateTime getLastPost() {
        return lastPost;
    }

    public int getTotalAcceptedPosts() {
        return totalAcceptedPosts;
    }

    //Fetches the details by reading through the XML with XMLStreamReader
    public AnalysisDetails fetch(URL url) throws IOException, XMLStreamException {
        InputStream input = url.openStream();
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        try{
            reader = inputFactory.createXMLStreamReader(input);
            return readXML(reader);
        }finally {
            if(reader != null){
                reader.close();
            }
        }
    }

    //Finds element 'posts' to find the actual posts
    private AnalysisDetails readXML(XMLStreamReader reader) throws XMLStreamException {
        while(reader.hasNext()){
            int eventType = reader.next();
            switch (eventType){
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getLocalName();
                    //Element posts consists of the posts
                    if(elementName.equals("posts"))
                        return readPosts(reader);
                    break;
                case XMLStreamReader.END_ELEMENT:
                    break;
            }
        }
        throw new XMLStreamException("Premature end of XML file");
    }

    //Reads through the rows (posts)
    private AnalysisDetails readPosts(XMLStreamReader reader) throws XMLStreamException {
        AnalysisDetails result = this;
        while(reader.hasNext()){
            int eventType = reader.next();
            switch (eventType){
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getLocalName();
                    //A post starts with 'row' (table structure)
                    if(elementName.equals("row"))
                        //Add a post to the number of posts
                        result.addPost();
                        //Update first and last posts
                        String creationDate = reader.getAttributeValue(null, "CreationDate");
                        result.updateFirstPost(creationDate);
                        result.updateLastPost(creationDate);
                        //Update average score
                        String score = reader.getAttributeValue(null, "Score");
                        result.updateTotalScore(score);
                        //Count accepted posts
                        String acceptedAnswerId = reader.getAttributeValue(null, "AcceptedAnswerId");
                        result.addAcceptedPost(acceptedAnswerId);
                        //Go to the end of the element
                        reader.next();
                    break;
                case XMLStreamReader.END_ELEMENT:
                    return result;
            }
        }
        throw new XMLStreamException("Premature end of XML file");
    }

    public void addPost(){
        this.totalPosts += 1;
    }

    public void updateFirstPost(String creation_date){
        LocalDateTime creationDate = LocalDateTime.parse(creation_date);
        if(this.firstPost == null || creationDate.isBefore(this.firstPost)){
            this.firstPost = creationDate;
        }
    }

    public void updateLastPost(String creation_date){
        LocalDateTime creationDate = LocalDateTime.parse(creation_date);
        if(this.lastPost == null || creationDate.isAfter(this.lastPost)){
            this.lastPost = creationDate;
        }
    }

    public void updateTotalScore(String score){
        int scoreInt = Integer.parseInt(score);
        this.sumScores = sumScores += scoreInt;
    }

    public void addAcceptedPost(String acceptedAnswerId){
        if (acceptedAnswerId != null){
            this.totalAcceptedPosts += 1;
        }
    }

}
