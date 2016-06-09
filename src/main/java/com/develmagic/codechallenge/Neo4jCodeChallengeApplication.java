package com.develmagic.codechallenge;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/30/16.
 */
@ComponentScan
@EnableConfigurationProperties
@EnableAutoConfiguration
@EnableNeo4jRepositories
@SpringBootApplication
public class Neo4jCodeChallengeApplication {

    private static final Logger logger = LoggerFactory.getLogger(Neo4jCodeChallengeApplication.class);

    @Autowired
    private Environment env;

    @PostConstruct
    public void initApplication() throws IOException {

        if (env.getActiveProfiles().length == 0) {
            logger.warn("No Spring profile configured, running with default configuration");
        } else {
            logger.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        }
    }

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Neo4jCodeChallengeApplication.class);
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);

        // Check if the selected profile has been set as argument.
        // if not the development profile will be added
        addDefaultProfile(app, source);
        app.run(args);

    }

    private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
        app.setAdditionalProfiles(source.getProperty("spring.profiles.active"), "neo4j");
    }



}