package org.bookshare.recommender.rec;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRecommender implements CommandLineRunner {

    @Override
    public void run(final String... arg0)
            throws Exception
    {
        final Integer userId = Integer.valueOf(arg0[0]);
        final Integer recommendationCount = Integer.valueOf(arg0[1]);
        
        final DataModel model = new FileDataModel(new File("/Users/john/work/rec/src/main/resources/user_interactions.csv"));
        
        final UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        final UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
        
        final UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        
        final List<RecommendedItem> recommendations = recommender.recommend(userId, recommendationCount);
        for (final RecommendedItem item : recommendations) {
            System.out.println(item);
        }
    }

}
