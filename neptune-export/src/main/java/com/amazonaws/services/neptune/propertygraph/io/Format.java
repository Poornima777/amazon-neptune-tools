/*
Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
Licensed under the Apache License, Version 2.0 (the "License").
You may not use this file except in compliance with the License.
A copy of the License is located at
    http://www.apache.org/licenses/LICENSE-2.0
or in the "license" file accompanying this file. This file is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
*/

package com.amazonaws.services.neptune.propertygraph.io;

import com.amazonaws.services.neptune.io.FileExtension;
import com.amazonaws.services.neptune.propertygraph.metadata.PropertyTypeInfo;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public enum Format implements FileExtension {
    json {
        @Override
        public String suffix() {
            return "json";
        }

        @Override
        Printer createPrinter(PrintWriter writer, Map<Object, PropertyTypeInfo> metadata, boolean includeTypeDefinitions) throws IOException {
            JsonGenerator generator = new JsonFactory().createGenerator(writer);
            generator.setPrettyPrinter(new MinimalPrettyPrinter(System.lineSeparator()));
            return new JsonPrinter(generator, metadata);
        }

        @Override
        public String description() {
            return "JSON";
        }
    },
    csv {
        @Override
        public String suffix() {
            return "csv";
        }

        @Override
        Printer createPrinter(PrintWriter writer, Map<Object, PropertyTypeInfo> metadata, boolean includeTypeDefinitions) {
            return new CsvPrinter(writer, metadata, true, includeTypeDefinitions);
        }

        @Override
        public String description() {
            return "CSV";
        }
    },
    csvNoHeaders {
        @Override
        public String suffix() {
            return "csv";
        }

        @Override
        Printer createPrinter(PrintWriter writer, Map<Object, PropertyTypeInfo> metadata, boolean includeTypeDefinitions) {
            return new CsvPrinter(writer, metadata, false, includeTypeDefinitions);
        }

        @Override
        public String description() {
            return "CSV (no headers)";
        }
    };

    abstract Printer createPrinter(PrintWriter writer, Map<Object, PropertyTypeInfo> metadata, boolean includeTypeDefinitions) throws IOException;

    public abstract String description();
}
