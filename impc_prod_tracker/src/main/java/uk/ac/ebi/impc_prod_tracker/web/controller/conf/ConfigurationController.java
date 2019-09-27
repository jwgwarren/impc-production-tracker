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
package uk.ac.ebi.impc_prod_tracker.web.controller.conf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.impc_prod_tracker.service.conf.ConfigurationService;
import java.util.List;
import java.util.Map;

/**
 * Provides information recurrently needed as work groups, work units, etc.
 *
 * @author Mauricio Martinez
 */
@RestController
@RequestMapping("/api")
public class ConfigurationController
{
    private ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService)
    {
        this.configurationService = configurationService;
    }

    @GetMapping(value = {"/conf"})
    public Map<String, List<String>> getConfiguration()
    {
        return configurationService.getConfiguration();
    }
}
