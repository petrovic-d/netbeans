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
package org.netbeans.spi.project;

import java.util.Objects;

/**
 * Structure representing an identification of a single method/function
 * in a file.
 * 
 * @author Dusan Petrovic
 * 
 * @since 1.90
 */
public final class NestedClass {
 
    private String className;

    /**
     * Creates a new instance holding the specified identification
     * of a nested class in a file.
     *
     * @param className name of a class inside the file
     * @exception  java.lang.IllegalArgumentException
     *             if the file or class name is {@code null}
     * @since 1.90
     */
    public NestedClass(String className) {
        super();
        if (className == null) {
            throw new IllegalArgumentException("className is <null>");
        }
        this.className = className;
    }
    
    /**
     * Returns name of a nested class.
     *
     * @return class name held by this object
     * @since 1.90
     */
    public String getClassName() {
        return className;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.className);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj.getClass() != SingleMethod.class)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        final NestedClass other = (NestedClass) obj;
        return other.className.equals(className);
    }
}
