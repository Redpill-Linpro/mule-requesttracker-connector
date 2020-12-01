package org.mule.extension.requesttracker.integration;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;

public abstract class RequestTrackerOperationsBaseTest extends MuleArtifactFunctionalTestCase {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8080);

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;
}
