package com.develmagic.codechallenge.integration;

import com.develmagic.codechallenge.TreemapCodeChallengeApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 6/1/16.
 */
@SpringApplicationConfiguration(TreemapCodeChallengeApplication.class)
@ActiveProfiles({"treemap"})
public class TreemapIntegrationTest extends AbstractIntegrationTest {

}
