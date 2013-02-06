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

package octopus.teamcity.server.ui;

import jetbrains.buildServer.serverSide.BuildFeature;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OctopusReleaseFeature extends BuildFeature {
    public static final String FEATURE_TYPE = "octopus.release";
    private final OctopusReleasePaths myPaths;

    public OctopusReleaseFeature(@NotNull final OctopusReleasePaths paths) {
        myPaths = paths;
    }

    @NotNull
    @Override
    public String getType() {
        return FEATURE_TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Trigger release in Octopus Deploy";
    }

    @Nullable
    @Override
    public String getEditParametersUrl() {
        return myPaths.getControllerPath();
    }

    @NotNull
    @Override
    public String describeParameters(@NotNull Map<String, String> params) {
        return "Create release in Octopus Deploy";
    }

    @Nullable
    @Override
    public PropertiesProcessor getParametersProcessor() {
        final OctopusReleaseConstants c = new OctopusReleaseConstants();
        return new PropertiesProcessor() {
            private void checkNotEmpty(@NotNull final Map<String, String> properties,
                                       @NotNull final String key,
                                       @NotNull final String message,
                                       @NotNull final Collection<InvalidProperty> res) {
                if (jetbrains.buildServer.util.StringUtil.isEmptyOrSpaces(properties.get(key))) {
                    res.add(new InvalidProperty(key, message));
                }
            }

            @NotNull
            public Collection<InvalidProperty> process(@Nullable final Map<String, String> p) {
                final Collection<InvalidProperty> result = new ArrayList<InvalidProperty>();
                if (p == null) return result;

                checkNotEmpty(p, c.getApiKey(), "API key must be specified", result);
                checkNotEmpty(p, c.getServerKey(), "Server must be specified", result);
                checkNotEmpty(p, c.getProjectNameKey(), "Project name must be specified", result);

                return result;
            }
        };
    }

    @Nullable
    @Override
    public Map<String, String> getDefaultParameters() {
        final Map<String, String> map = new HashMap<String, String>();

        return map;
    }

    @Override
    public boolean isMultipleFeaturesPerBuildTypeAllowed() {
        return true;
    }
}