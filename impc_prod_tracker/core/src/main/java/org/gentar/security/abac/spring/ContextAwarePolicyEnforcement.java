/*******************************************************************************
 * Copyright 2019 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 *******************************************************************************/
package org.gentar.security.abac.spring;

import org.gentar.security.abac.subject.SubjectRetriever;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.gentar.security.abac.policy.PolicyEnforcement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ContextAwarePolicyEnforcement
{
    private PolicyEnforcement policy;
    private SubjectRetriever subjectRetriever;

    public ContextAwarePolicyEnforcement(PolicyEnforcement policy, SubjectRetriever subjectRetriever)
    {
        this.policy = policy;
        this.subjectRetriever = subjectRetriever;
    }

    public void checkPermission(Object resource, String permission)
    {
        Map<String, Object> environment = new HashMap<>();

        environment.put("time", new Date());

        if(!policy.check(subjectRetriever.getSubject(), resource, permission, environment))
            throw new AccessDeniedException("Access is denied");
    }

    public boolean hasPermission(Object resource, String permission)
    {
        Map<String, Object> environment = new HashMap<>();

        environment.put("time", new Date());
        return policy.check(subjectRetriever.getSubject(), resource, permission, environment);
    }

    public boolean isUserAnonymous()
    {
        return subjectRetriever.isUserAnonymous();
    }
}
