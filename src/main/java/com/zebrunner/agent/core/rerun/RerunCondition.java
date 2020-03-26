package com.zebrunner.agent.core.rerun;

import com.zebrunner.agent.core.registrar.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class RerunCondition {

    private final String runId;
    private final Set<Long> testIds;
    private final Set<Status> statuses;

}