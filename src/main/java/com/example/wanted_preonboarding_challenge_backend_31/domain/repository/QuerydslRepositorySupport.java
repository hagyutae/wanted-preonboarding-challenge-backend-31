package com.example.wanted_preonboarding_challenge_backend_31.domain.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public abstract class QuerydslRepositorySupport {

    private final Class<?> domainClass;
    private EntityManager entityManager;
    private Querydsl querydsl;
    private JPAQueryFactory queryFactory;

    protected QuerydslRepositorySupport(Class<?> domainClass) {
        Assert.notNull(domainClass, "Domain class must not be null!");
        this.domainClass = domainClass;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        this.entityManager = entityManager;
        initQuerydslComponents();
    }

    @PostConstruct
    public void validate() {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        Assert.notNull(querydsl, "Querydsl must not be null!");
        Assert.notNull(queryFactory, "QueryFactory must not be null!");
    }

    private void initQuerydslComponents() {
        JpaEntityInformation<?, ?> entityInformation =
                JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
        EntityPath<?> path = SimpleEntityPathResolver.INSTANCE.createPath(entityInformation.getJavaType());

        this.querydsl = new Querydsl(entityManager, new PathBuilder<>(path.getType(), path.getMetadata()));
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    protected JPAQueryFactory queryFactory() {
        return queryFactory;
    }

    protected Querydsl querydsl() {
        return querydsl;
    }

    protected EntityManager entityManager() {
        return entityManager;
    }

    protected <T> JPAQuery<T> select(Expression<T> expression) {
        return queryFactory().select(expression);
    }

    protected <T> JPAQuery<T> selectFrom(EntityPath<T> from) {
        return queryFactory().selectFrom(from);
    }
}