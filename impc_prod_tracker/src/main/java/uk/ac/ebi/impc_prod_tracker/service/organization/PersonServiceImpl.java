/******************************************************************************
 Copyright 2019 EMBL - European Bioinformatics Institute

 Licensed under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License. You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 either express or implied. See the License for the specific
 language governing permissions and limitations under the
 License.
 */
package uk.ac.ebi.impc_prod_tracker.service.organization;

import org.springframework.stereotype.Component;
import uk.ac.ebi.impc_prod_tracker.conf.exceptions.UserOperationFailedException;
import uk.ac.ebi.impc_prod_tracker.conf.security.SystemSubject;
import uk.ac.ebi.impc_prod_tracker.conf.security.abac.spring.ContextAwarePolicyEnforcement;
import uk.ac.ebi.impc_prod_tracker.conf.security.abac.spring.SubjectRetriever;
import uk.ac.ebi.impc_prod_tracker.data.organization.person.Person;
import uk.ac.ebi.impc_prod_tracker.data.organization.person.PersonRepository;
import uk.ac.ebi.impc_prod_tracker.service.conf.AAPService;
import uk.ac.ebi.impc_prod_tracker.service.conf.PermissionService;
import java.util.List;

/**
 * Service to manage the creation of an user in the system.
 */

@Component
public class PersonServiceImpl implements PersonService
{
    private PersonRepository personRepository;
    private AAPService aapService;
    private SubjectRetriever subjectRetriever;
    private final ContextAwarePolicyEnforcement policyEnforcement;

    public static final String PERSON_ALREADY_EXISTS_ERROR =
        "The user with email [%s] already exists in the system.";

    public PersonServiceImpl(
        PersonRepository personRepository,
        AAPService aapService,
        SubjectRetriever subjectRetriever, ContextAwarePolicyEnforcement policyEnforcement)
    {
        this.personRepository = personRepository;
        this.aapService = aapService;
        this.subjectRetriever = subjectRetriever;
        this.policyEnforcement = policyEnforcement;
    }

    public List<Person> getAllPeople()
    {
        List<Person> people = null;
        SystemSubject systemSubject = subjectRetriever.getSubject();
        if (systemSubject != null)
        {
            if (systemSubject.isAdmin())
            {
                people = personRepository.findAll();
            }
            else
            {
                people = null;
            }
        }

        return people;
    }

    @Override
    public Person getLoggedPerson()
    {
        Person person = null;
        SystemSubject systemSubject = subjectRetriever.getSubject();
        if (systemSubject != null)
        {
            person = systemSubject.getPerson();
        }

        return person;
    }

    @Override
    public Person getPersonByEmail(String email)
    {
        return personRepository.findPersonByEmail(email);
    }

    @Override
    public Person createPerson(Person person)
    {
        validatePersonNotExists(person);
        String authId = aapService.createUser(person);
        person.setAuthId(authId);
        personRepository.save(person);
        return person;
    }

    @Override
    public Person updatePerson(Person person, String token)
    {
        validatePermissions(PermissionService.UPDATE_USER, person);
        return null;
    }

    private void validatePermissions(String updateUser, Person person)
    {
        if (!policyEnforcement.hasPermission(person, updateUser))
        {
            throw new UserOperationFailedException("You don't have permissions to update the user.");
        }
    }

    private void validatePersonNotExists(Person person)
    {
        String email = person.getEmail();
        if (personRepository.findPersonByEmail(email) != null)
        {
            throw new UserOperationFailedException(String.format(PERSON_ALREADY_EXISTS_ERROR, email));
        }
    }

}
