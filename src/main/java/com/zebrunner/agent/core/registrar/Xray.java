package com.zebrunner.agent.core.registrar;

import com.zebrunner.agent.core.exception.TestAgentException;
import com.zebrunner.agent.core.registrar.domain.TcmType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Xray {

    public static final String SYNC_ENABLED = "com.zebrunner.app/tcm.xray.sync.enabled";
    public static final String SYNC_REAL_TIME = "com.zebrunner.app/tcm.xray.sync.real-time";

    public static final String EXECUTION_KEY = "com.zebrunner.app/tcm.xray.test-execution-key";

    private static final TestCasesRegistry TEST_CASES_REGISTRY = TestCasesRegistry.getInstance();

    public static void disableSync() {
        verifyTestsStart();
        Label.attachToTestRun(SYNC_ENABLED, "false");
    }

    public static void enableRealTimeSync() {
        verifyTestsStart();
        Label.attachToTestRun(SYNC_REAL_TIME, "true");
    }

    public static void setExecutionKey(String executionKey) {
        verifyTestsStart();
        Label.attachToTestRun(EXECUTION_KEY, executionKey);
    }

    private static void verifyTestsStart() {
        if (RunContext.hasTests()) {
            throw new TestAgentException("The Xray configuration must be provided before start of tests. Hint: move the configuration to the code block which is executed before all tests.");
        }
    }

    public static void setTestKey(String testKey) {
        TEST_CASES_REGISTRY.addTestCasesToCurrentTest(TcmType.XRAY, Collections.singleton(testKey));
    }

    /**
     * Sets the given status for the given test in Xray execution.
     *
     * @param testKey      key of the Xray test
     * @param resultStatus name of the status to be set for the test
     * @see SystemTestStatus.Cloud
     * @see SystemTestStatus.Server
     */
    public static void setTestStatus(String testKey, String resultStatus) {
        TEST_CASES_REGISTRY.setCurrentTestTestCaseStatus(TcmType.XRAY, testKey, resultStatus);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class SystemTestStatus {

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Cloud {

            public static final String PASSED = "PASSED";
            public static final String EXECUTING = "EXECUTING";
            public static final String FAILED = "FAILED";
            public static final String NOT_EXECUTED = "NOT_EXECUTED";

        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Server {

            public static final String PASS = "PASS";
            public static final String FAIL = "FAIL";

        }

    }

}
