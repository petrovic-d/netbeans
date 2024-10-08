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

/**
 *
 * @author Dusan Petrovic
 */
public class AddNewAssetCommandSetUp {
    
    private static final String ADD_NEW_ASSET_COMMAND = "nbls.cloud.assets.add.new"; //NOI18N
    static final String OBJECT_STORAGE_ITEM_TYPE = "Object Storage Bucket"; //NOI18N
    static final String AUTONOMOUS_DB_ITEM_TYPE = "Oracle Autonomous Database"; //NOI18N

    public static Set<String> expectedCommands() {
        return Set.of(ADD_NEW_ASSET_COMMAND);
    }
}
