/*
 * Copyright 2000-2012 Octopus Deploy Pty. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package octopus.teamcity.agent;

import jetbrains.buildServer.agent.*;
import octopus.teamcity.common.OctopusConstants;
import org.jetbrains.annotations.NotNull;

public class OctopusMetadataRunner implements AgentBuildRunner {

    @NotNull
    public BuildProcess createBuildProcess(@NotNull AgentRunningBuild runningBuild, @NotNull BuildRunnerContext context) {
        return new OctopusMetadataBuildProcess(runningBuild, context);
    }

    @NotNull
    public AgentBuildRunnerInfo getRunnerInfo() {
        return new AgentBuildRunnerInfo() {
            @NotNull
            public String getType() {
                return OctopusConstants.METADATA_RUNNER_TYPE;
            }

            public boolean canRun(@NotNull BuildAgentConfiguration agentConfiguration) {
                return true;
            }
        };
    }
}
