package com.example.wanted_preonboarding_challenge_backend_31.config;

import com.querydsl.jpa.DefaultQueryHandler;
import com.querydsl.jpa.Hibernate5Templates;
import com.querydsl.jpa.QueryHandler;

public class CustomHibernate5Templates extends Hibernate5Templates {

    @Override
    public QueryHandler getQueryHandler() {
        return DefaultQueryHandler.DEFAULT;
    }
}
