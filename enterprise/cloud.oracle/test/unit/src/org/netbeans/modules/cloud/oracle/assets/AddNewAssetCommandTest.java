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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockStatic;
import org.netbeans.api.project.Project;
import org.netbeans.modules.cloud.oracle.actions.OCIItemCreator;
import static org.netbeans.modules.cloud.oracle.assets.AddNewAssetCommandSetUp.*;
import org.netbeans.modules.cloud.oracle.items.OCID;
import org.netbeans.modules.cloud.oracle.items.OCIItem;
import org.netbeans.modules.cloud.oracle.steps.CompartmentStep;
import org.netbeans.modules.cloud.oracle.steps.DatabaseConnectionStep;
import org.netbeans.modules.cloud.oracle.steps.ItemTypeStep;
import org.netbeans.modules.cloud.oracle.steps.ProjectStep;
import org.netbeans.modules.cloud.oracle.steps.SuggestedStep;
import org.netbeans.modules.cloud.oracle.steps.TenancyStep;
import org.netbeans.modules.cloud.oracle.developer.ContainerRepositoryItem;

/**
 *
 * @author Dusan Petrovic
 */

public class AddNewAssetCommandTest {
    
    private MockedStatic<Steps> mockedSteps;
    private MockedStatic<CloudAssets> mockedCloudAssets;
    private MockedStatic<DependencyUtils> mockedDependencyUtils;
    private MockedStatic<OCIItemCreator> mockedItemCreator;

    @Before
    public void setUp() {
        mockedSteps = mockStatic(Steps.class);
        mockedCloudAssets = mockStatic(CloudAssets.class);
        mockedDependencyUtils = mockStatic(DependencyUtils.class);
        mockedItemCreator = mockStatic(OCIItemCreator.class);
    }

    @After
    public void tearDown() {
        if (mockedSteps != null) {
            mockedSteps.close();
            mockedCloudAssets.close();
            mockedDependencyUtils.close();
            mockedItemCreator.close();
        }
    }
    
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
        mock(ProjectStep.class);
        assertEquals(ItemTypeStep.class, steps.nextStepFor(new ProjectStep()).getClass());
        assertEquals(TenancyStep.class, steps.nextStepFor(itstep).getClass());
        assertEquals(CompartmentStep.class, steps.nextStepFor(new TenancyStep()).getClass());
        assertEquals(SuggestedStep.class, steps.nextStepFor(new CompartmentStep()).getClass());
        assertEquals(null, steps.nextStepFor(new SuggestedStep(null)));
    } 
    
    @Test
    public void shouldAddExistingContainerRepository() throws Exception {
        Steps stepsMock = mock(Steps.class);
        CloudAssets cloudAssetsMock = mock(CloudAssets.class);
        mockedSteps.when(Steps::getDefault).thenReturn(stepsMock);
        mockedCloudAssets.when(CloudAssets::getDefault).thenReturn(cloudAssetsMock);

        Steps.Values valuesMock = mock(Steps.Values.class);
        Project projectMock = mock(Project.class);
        OCIItem containerItemMock = mock(OCIItem.class);
        OCID dummyOCID = mock(OCID.class);
        
        when(dummyOCID.getPath()).thenReturn(CONTAINER_REPOSITORY_PATH);
        when(containerItemMock.getKey()).thenReturn(dummyOCID);

        when(valuesMock.getValueForStep(ProjectStep.class)).thenReturn(projectMock);
        when(valuesMock.getValueForStep(ItemTypeStep.class)).thenReturn(CONTAINER_REPOSITORY_PATH);
        when(valuesMock.getValueForStep(SuggestedStep.class)).thenReturn(containerItemMock);
        when(stepsMock.executeMultistep(any(), any())).thenReturn(CompletableFuture.completedFuture(valuesMock));

        AddNewAssetCommand addNewAssetCommand = new AddNewAssetCommand();
        CompletableFuture response = addNewAssetCommand.runCommand(DUMMY_COMMAND, DUMMY_ARGUMENTS);
        
        Object value = response.get();
        assertEquals(null, value);
        verify(cloudAssetsMock, times(1)).addItem(containerItemMock);
        mockedDependencyUtils.verifyNoInteractions();
    }
    
    @Test
    public void shouldAddExistingMetricsNamespace() throws Exception {
        Steps stepsMock = mock(Steps.class);
        CloudAssets cloudAssetsMock = mock(CloudAssets.class);
        mockedSteps.when(Steps::getDefault).thenReturn(stepsMock);
        mockedCloudAssets.when(CloudAssets::getDefault).thenReturn(cloudAssetsMock);

        Steps.Values valuesMock = mock(Steps.Values.class);
        Project projectMock = mock(Project.class);
        OCIItem metricsItemMock = mock(OCIItem.class);
        OCID dummyOCID = mock(OCID.class);
        
        when(dummyOCID.getPath()).thenReturn(METRICS_NAMESPACE_PATH);
        when(metricsItemMock.getKey()).thenReturn(dummyOCID);

        when(valuesMock.getValueForStep(ProjectStep.class)).thenReturn(projectMock);
        when(valuesMock.getValueForStep(ItemTypeStep.class)).thenReturn(METRICS_NAMESPACE_PATH);
        when(valuesMock.getValueForStep(SuggestedStep.class)).thenReturn(metricsItemMock);
        when(stepsMock.executeMultistep(any(), any())).thenReturn(CompletableFuture.completedFuture(valuesMock));

        AddNewAssetCommand addNewAssetCommand = new AddNewAssetCommand();
        CompletableFuture response = addNewAssetCommand.runCommand(DUMMY_COMMAND, DUMMY_ARGUMENTS);
        
        Object value = response.get();
        assertEquals(null, value);
        verify(cloudAssetsMock, times(1)).addItem(metricsItemMock);
        mockedDependencyUtils.verify(() -> DependencyUtils.addDependency(projectMock, expectedMetricsNamespaceDependency()));
        mockedDependencyUtils.verify(() -> DependencyUtils.addAnnotationProcessor(
                projectMock,
                EXPECTED_METRICS_ANNOTATION_PROCESSOR_GROUP_ID,
                EXPECTED_METRICS_ANNOTATION_PROCESSOR_ARTIFACT_ID));
    }
    
    @Test
    public void shouldCreateNewContainerRepository() throws Exception {
        Steps stepsMock = mock(Steps.class);
        CloudAssets cloudAssetsMock = mock(CloudAssets.class);
        OCIItemCreator itemCreatorMock = mock(OCIItemCreator.class);
        mockedSteps.when(Steps::getDefault).thenReturn(stepsMock);
        mockedCloudAssets.when(CloudAssets::getDefault).thenReturn(cloudAssetsMock);
        mockedItemCreator.when(() -> OCIItemCreator.getCreator(CONTAINER_REPOSITORY_PATH)).thenReturn(itemCreatorMock);

        Steps.Values valuesMock = mock(Steps.Values.class);
        Project projectMock = mock(Project.class);
        OCIItem containerItemMock = mock(OCIItem.class);
        CreateNewResourceItem createNewItemMock = mock(CreateNewResourceItem.class);
        
        OCID dummyOCID = mock(OCID.class);
        when(dummyOCID.getPath()).thenReturn(CONTAINER_REPOSITORY_PATH);
        when(containerItemMock.getKey()).thenReturn(dummyOCID);

        Map<String, Object> params = new HashMap<>();
        when(itemCreatorMock.steps()).thenReturn(CompletableFuture.completedFuture(params));
        
        CompletableFuture retVal = CompletableFuture.completedFuture(containerItemMock);
        when(itemCreatorMock.create(valuesMock, params)).thenReturn(retVal);
        
        when(valuesMock.getValueForStep(ProjectStep.class)).thenReturn(projectMock);
        when(valuesMock.getValueForStep(ItemTypeStep.class)).thenReturn(CONTAINER_REPOSITORY_PATH);
        when(valuesMock.getValueForStep(SuggestedStep.class)).thenReturn(createNewItemMock);
        when(stepsMock.executeMultistep(any(), any())).thenReturn(CompletableFuture.completedFuture(valuesMock));

        AddNewAssetCommand addNewAssetCommand = new AddNewAssetCommand();
        CompletableFuture response = addNewAssetCommand.runCommand(DUMMY_COMMAND, DUMMY_ARGUMENTS);
        
        Object value = response.get();
        assertEquals(null, value);
        verify(cloudAssetsMock, times(1)).addItem(containerItemMock);
        mockedDependencyUtils.verifyNoInteractions();
    }
}
