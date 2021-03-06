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

package com.amazonaws.services.neptune.rdf.io;

import com.amazonaws.services.neptune.io.Status;
import com.amazonaws.services.neptune.rdf.Prefixes;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.eclipse.rdf4j.rio.turtle.TurtleWriter;

import java.io.IOException;
import java.io.Writer;

public class EnhancedTurtleWriter extends TurtleWriter {

    private final Prefixes prefixes;
    private final Status status = new Status();

    public EnhancedTurtleWriter(Writer writer, Prefixes prefixes) {
        super(writer);
        this.prefixes = prefixes;
    }

    @Override
    public void handleStatement(Statement statement) throws RDFHandlerException {

        prefixes.parse(statement.getSubject().stringValue(), this);
        prefixes.parse(statement.getPredicate().toString(), this);
        prefixes.parse(statement.getObject().stringValue(), this);

        super.handleStatement(statement);

        status.update();
    }

    @Override
    protected void writeNamespace(String prefix, String name)
            throws IOException {
        // Do nothing
    }
}
