package com.example.preonboarding.repository.reviews;

import com.example.preonboarding.dto.RatingDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RatingRepositoryImpl implements RatingRepository{
    private final EntityManager em;

    @Override
    public RatingDTO getRatingSummary(Long productId){
        List<Object[]> results = em.createQuery(
                        "SELECT r.rating, COUNT(r)" +
                                "FROM Reviews r " +
                                "WHERE r.products.id = :productId " +
                                "GROUP BY r.rating", Object[].class
                ).setParameter("productId", productId)
                .getResultList();

        Map<Integer, Long> distribution = new LinkedHashMap<>();

        for(int i = 5; i >= 1; i--) distribution.put(i, 0l);

        for(Object[] row : results){
            Integer rating = (Integer) row[0];
            Long count = (Long) row[1];
            distribution.put(rating, count);
        }


        Object[] result = em.createQuery(
                        "SELECT AVG(r.rating), COUNT(r.id) " +
                                "FROM Reviews r " +
                                "WHERE r.products.id = :productId " , Object[].class
                ).setParameter("productId", productId)
                .getSingleResult();

        Double avg = (Double) result[0];
        Long count = (Long) result[1];

       return new RatingDTO(avg,count,distribution);
    }

}
