package XMLAnalyzer.controller;

import XMLAnalyzer.model.Analysis;
import XMLAnalyzer.model.AnalysisDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class XmlApplicationTests {

	@Autowired
	private AnalysisController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	void testAnalysis(){
		Analysis analysis = new Analysis();
		ZonedDateTime analyseDate = analysis.getAnalyseDate();
		assertThat(analyseDate).isNotNull();
	}

	@Test
	void testAnalysisDetailsAddPost(){
		AnalysisDetails analysisDetails = new AnalysisDetails();
		analysisDetails.addPost();
		assertThat(analysisDetails.getTotalPosts()).isEqualTo(1);
	}

	@Test
	void testAnalysisDetailsUpdateFirstPost(){
		AnalysisDetails analysisDetails = new AnalysisDetails();
		String creation_date = "2013-12-17T20:23:39.243";
		LocalDateTime creation_date_parsed = LocalDateTime.parse(creation_date);
		analysisDetails.updateFirstPost(creation_date);
		assertThat(analysisDetails.getFirstPost()).isEqualTo(creation_date_parsed);
	}

	@Test
	void testAnalysisDetailsUpdateLastPost(){
		AnalysisDetails analysisDetails = new AnalysisDetails();
		String creation_date = "2013-12-17T20:23:39.243";
		LocalDateTime creation_date_parsed = LocalDateTime.parse(creation_date);
		analysisDetails.updateFirstPost(creation_date);
		assertThat(analysisDetails.getFirstPost()).isEqualTo(creation_date_parsed);
	}

	@Test
	void testAnalysisDetailsAverageScore(){
		AnalysisDetails analysisDetails = new AnalysisDetails();
		//Add two posts
		analysisDetails.addPost();
		analysisDetails.addPost();
		//Update scores
		analysisDetails.updateTotalScore("4");
		analysisDetails.updateTotalScore("2");
		assertThat(analysisDetails.getAvgScore()).isEqualTo(3);
	}

	@Test
	void testAnalysisDetailsAcceptedPosts(){
		AnalysisDetails analysisDetails = new AnalysisDetails();
		//Update Accepted Posts
		analysisDetails.addAcceptedPost("4");
		analysisDetails.addAcceptedPost(null);
		assertThat(analysisDetails.getTotalAcceptedPosts()).isEqualTo(1);
	}

}
