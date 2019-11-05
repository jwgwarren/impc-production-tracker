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
package uk.ac.ebi.impc_prod_tracker.conf.security.abac.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uk.ac.ebi.impc_prod_tracker.conf.exceptions.SystemOperationFailedException;
import uk.ac.ebi.impc_prod_tracker.conf.security.AapSystemSubject;
import uk.ac.ebi.impc_prod_tracker.conf.security.SystemSubject;

/**
 * Class in charge of retrieving information about the user currently logged into the system.
 * @author Mauricio Martinez
 */
@Component
public class SubjectRetriever
{
    private static final String ANONYMOUS_USER = "anonymousUser";

    public SystemSubject getSubject()
    {
        SystemSubject systemSubject;

        Object principal = getPrincipal();

        if (ANONYMOUS_USER.equals(principal.toString()))
        {
            systemSubject = buildAnonymousUser();
        }
        else if (principal instanceof SystemSubject)
        {
            systemSubject = (SystemSubject) principal;
        }
        else
        {
            throw new SystemOperationFailedException(
                "Subject cannot be determined", "Error in SubjectRetriever");
        }
        return systemSubject;
    }

    private Object getPrincipal()
    {
        Object principal = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null)
        {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null)
            {
                principal = authentication.getPrincipal();
            }
        }
        return principal;
    }

    private SystemSubject buildAnonymousUser()
    {
        AapSystemSubject anonymousSubject = new AapSystemSubject(ANONYMOUS_USER);
        return anonymousSubject;
    }

    public boolean isUserAnonymous()
    {
        return ANONYMOUS_USER.equals(getPrincipal());
    }
}
