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
package org.gentar.organization.person;

import org.gentar.exceptions.UserOperationFailedException;
import org.gentar.security.AuthorizationHeaderReader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PersonController
{
    private PersonService personService;
    private PersonMapper personMapper;
    private AuthorizationHeaderReader authorizationHeaderReader;

    public PersonController(
        PersonService personService,
        PersonMapper personMapper,
        AuthorizationHeaderReader authorizationHeaderReader)
    {
        this.personService = personService;
        this.personMapper = personMapper;
        this.authorizationHeaderReader = authorizationHeaderReader;
    }

    /**
     * Return a list of users that the logged user can see. If the logged user is a manager, they
     * can see all the users that can be managed by them. If the logged user is an admin they can
     * see all the users in the system.
     * @return List of {@link PersonDTO} with information about the people visible for the current
     * logged user.
     */
    @GetMapping
    public List<PersonDTO> getAllPeople()
    {
        return personMapper.toDtos(personService.getAllPeople());
    }

    @GetMapping(value = {"/{email}"})
    @PreAuthorize("hasPermission(null, 'MANAGE_USERS')")
    public PersonDTO findByEmail(@PathVariable("email") String email)
    {
        Person person = personService.getPersonByEmail(email);
        if (person == null)
        {
            //TODO Not found
            throw new UserOperationFailedException(
                "User with email " + email + " not found.");
        }
        return personMapper.toDto(person);
    }

    @GetMapping(value = {"/currentPerson"})
    public PersonDTO getCurrentPerson()
    {
        return personMapper.toDto(personService.getLoggedPerson());
    }

    /**
     * Creates a person in the system.
     * @param personCreationDTO Request with data of the user to be created.
     * @return {@link Person} entity created
     */
    @PostMapping
    @PreAuthorize("hasPermission(null, 'MANAGE_USERS')")
    public PersonDTO createPerson(
        @RequestBody PersonCreationDTO personCreationDTO)
    {
        Person personToBeCreated = personMapper.personCreationDTOtoEntity(personCreationDTO);
        return personMapper.toDto(personService.createPerson(personToBeCreated));
    }

    @PutMapping
    @PreAuthorize("hasPermission(null, 'UPDATE_USER')")
    public PersonDTO updatePerson(@RequestBody PersonDTO personDTO, HttpServletRequest request)
    {
        String token = authorizationHeaderReader.getAuthorizationToken(request);
        Person personToBeUpdated = personMapper.toEntity(personDTO);
        personService.updatePerson(personToBeUpdated, token);
        return null;
    }

}
