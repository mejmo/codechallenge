package com.develmagic.codechallenge.integration;

import com.develmagic.codechallenge.Neo4jCodeChallengeApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 6/1/16.
 */
@SpringApplicationConfiguration(Neo4jCodeChallengeApplication.class)
@ActiveProfiles({"neo4j"})
public class Neo4jIntegrationTest extends AbstractIntegrationTest {


}
