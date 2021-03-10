package XMLAnalyzer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest
public class MockTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    //Perform a simple test by analyzing an xml file that contains a post
    public void testSimplePosts() throws Exception{
        String json = "{\"url\": \"http://sietsetaams.com/xml/simpleTest-posts.xml\"}";
        mockMvc.perform(post("/analyze/")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.details.firstPost", Matchers.is("2010-12-07T20:55:13.517")))
                .andExpect(jsonPath("$.details.lastPost", Matchers.is("2010-12-07T20:55:13.517")))
                .andExpect(jsonPath("$.details.totalPosts", Matchers.is(1)))
                .andExpect(jsonPath("$.details.totalAcceptedPosts", Matchers.is(1)))
                .andExpect(jsonPath("$.details.avgScore", Matchers.is(4)));
    }

    @Test
    public void testWrongUrl() throws Exception {
        String json = "{\"url\": \"invalidurl\"}";
        mockMvc.perform(post("/analyze/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testEmptyPosts() throws Exception{
        String json = "{\"url\": \"http://sietsetaams.com/xml/empty-test.xml\"}";
        mockMvc.perform(post("/analyze/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

}
