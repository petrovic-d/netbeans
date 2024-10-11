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

import java.util.List;
import java.util.Set;

/**
 *
 * @author Dusan Petrovic
 */
public class AddNewAssetCommandSetUp {
    
    private static final String ADD_NEW_ASSET_COMMAND = "nbls.cloud.assets.add.new"; //NOI18N
    static final String OBJECT_STORAGE_ITEM_TYPE = "Object Storage Bucket"; //NOI18N
    static final String AUTONOMOUS_DB_ITEM_TYPE = "Oracle Autonomous Database"; //NOI18N
    static final String CONTAINER_REPOSITORY_PATH = "ContainerRepository"; //NOI18N
    static final String METRICS_NAMESPACE_PATH = "MetricsNamespace"; //NOI18N
    static final String DUMMY_COMMAND = "DUMMY-COMMAND"; //NOI18N
    static final List<Object> DUMMY_ARGUMENTS = List.of(); //NOI18N

    static final String EXPECTED_METRICS_ANNOTATION_PROCESSOR_GROUP_ID = "io.micronaut.micrometer"; //NOI18N
    static final String EXPECTED_METRICS_ANNOTATION_PROCESSOR_ARTIFACT_ID = "micronaut-micrometer-annotation"; //NOI18N

    static Set<String> expectedCommands() {
        return Set.of(ADD_NEW_ASSET_COMMAND);
    }
    
    static String[] expectedMetricsNamespaceDependency() {
        return new String[] {"io.micronaut.oraclecloud", "micronaut-oraclecloud-micrometer"};
    }
}
