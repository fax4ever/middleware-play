/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

/**
 * Created by fabio on 18/08/2017.
 */
package it.redhat.demo.rest.mapper;

public class Error {

    private final String exception;
    private final String message;

    public Error(Throwable ex) {

        this.exception = ex.getClass().getName();
        this.message = ex.getMessage();

    }

    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

}
