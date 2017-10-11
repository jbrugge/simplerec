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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRecommender implements CommandLineRunner {
    @Value("${user.interactions.data}")
    private String modelDataFile;

    @Override
    public void run(final String... arg0)
            throws Exception
    {
        // The basic steps, from Mahout's tutorial at http://mahout.apache.org/users/recommender/userbased-5-minutes.html
        if (arg0.length == 2) {
            final Integer userId = Integer.valueOf(arg0[0]);
            final Integer recommendationCount = Integer.valueOf(arg0[1]);
            
            // 1. Represent users and their interactions: title downloads, packaging requests, add to reading list
            final DataModel model = new FileDataModel(new File(modelDataFile));
            
            // 2. Calculate a similarity score between pairs of users
            final UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            
            // 3. Rank the similarities to find the top user matches for a given user
            final UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
            
            // 4. Recommend items by looking at books of users with similar tastes, using a weighted score
            final UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
            
            final List<RecommendedItem> recommendations = recommender.recommend(userId, recommendationCount);
            for (final RecommendedItem item : recommendations) {
                System.out.println(item);
            }
        } else {
            System.out.println("Usage: java -jar path/to/jarfile.jar <user ID> <number of recommendations>");
        }
    }

}
