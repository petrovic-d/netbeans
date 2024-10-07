/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.cloud.oracle.assets;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.netbeans.modules.cloud.oracle.assets.AddNewAssetCommandSetUp.expectedCommands;
import static org.netbeans.modules.cloud.oracle.assets.AddNewAssetCommandSetUp.AUTONOMOUS_DB_ITEM_TYPE;
import static org.netbeans.modules.cloud.oracle.assets.AddNewAssetCommandSetUp.OBJECT_STORAGE_ITEM_TYPE;
import org.netbeans.modules.cloud.oracle.steps.CompartmentStep;
import org.netbeans.modules.cloud.oracle.steps.DatabaseConnectionStep;
import org.netbeans.modules.cloud.oracle.steps.ItemTypeStep;
import org.netbeans.modules.cloud.oracle.steps.ProjectStep;
import org.netbeans.modules.cloud.oracle.steps.SuggestedStep;
import org.netbeans.modules.cloud.oracle.steps.TenancyStep;

/**
 *
 * @author Dusan Petrovic
 */
public class AddNewAssetCommandTest {
    
    public AddNewAssetCommandTest() {
    }
    
    @Test
    public void shouldRetreiveAvailableCommands() {
        AddNewAssetCommand addNewAssetCommand = new AddNewAssetCommand();

        Set<String> actualCommands = addNewAssetCommand.getCommands();
        
        assertEquals(expectedCommands(), actualCommands);
    }

    @Test
    public void shouldGetStepsWhenDBSelected() {
        AddNewAssetCommand addNewAssetCommand = new AddNewAssetCommand();

        Steps.NextStepProvider steps = addNewAssetCommand.getSteps();
        
        ItemTypeStep itstep = new ItemTypeStep();
        itstep.setValue(AUTONOMOUS_DB_ITEM_TYPE);
        assertEquals(ItemTypeStep.class, steps.nextStepFor(new ProjectStep()).getClass());
        assertEquals(DatabaseConnectionStep.class, steps.nextStepFor(itstep).getClass());
    }
    
    @Test
    public void shouldGetSteps() {
        AddNewAssetCommand addNewAssetCommand = new AddNewAssetCommand();

        Steps.NextStepProvider steps = addNewAssetCommand.getSteps();
        
        ItemTypeStep itstep = new ItemTypeStep();
        itstep.setValue(OBJECT_STORAGE_ITEM_TYPE);
        assertEquals(ItemTypeStep.class, steps.nextStepFor(new ProjectStep()).getClass());
        assertEquals(TenancyStep.class, steps.nextStepFor(itstep).getClass());
        assertEquals(CompartmentStep.class, steps.nextStepFor(new TenancyStep()).getClass());
        assertEquals(SuggestedStep.class, steps.nextStepFor(new CompartmentStep()).getClass());
        assertEquals(null, steps.nextStepFor(new SuggestedStep(null)));
    }
}
