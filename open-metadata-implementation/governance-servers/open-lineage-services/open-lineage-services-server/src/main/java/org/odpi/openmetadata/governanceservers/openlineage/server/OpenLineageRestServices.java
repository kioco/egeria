/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.governanceservers.openlineage.server;

import org.odpi.openmetadata.commonservices.ffdc.rest.VoidResponse;
import org.odpi.openmetadata.frameworks.connectors.ffdc.InvalidParameterException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.UserNotAuthorizedException;
import org.odpi.openmetadata.governanceservers.openlineage.ffdc.OpenLineageException;
import org.odpi.openmetadata.governanceservers.openlineage.handlers.OpenLineageHandler;
import org.odpi.openmetadata.governanceservers.openlineage.model.Scope;
import org.odpi.openmetadata.governanceservers.openlineage.model.View;
import org.odpi.openmetadata.governanceservers.openlineage.responses.LineageResponse;
import org.odpi.openmetadata.governanceservers.openlineage.util.OpenLineageExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OpenLineageRestServices {

    private static final Logger log = LoggerFactory.getLogger(OpenLineageRestServices.class);
    private final OpenLineageInstanceHandler instanceHandler = new OpenLineageInstanceHandler();
    private OpenLineageExceptionHandler openLineageExceptionHandler = new OpenLineageExceptionHandler();


    public VoidResponse dumpGraph(String serverName, String userId) {
        VoidResponse response = new VoidResponse();
        final String methodName = "OpenLineageRestServices.dumpGraph";
        try {
            OpenLineageHandler openLineageHandler = instanceHandler.getOpenLineageHandler(userId,
                    serverName,
                    methodName);
            openLineageHandler.dumpMainGraph();
        } catch (InvalidParameterException error) {
            openLineageExceptionHandler.captureInvalidParameterException(response, error);
        } catch (org.odpi.openmetadata.frameworks.connectors.ffdc.PropertyServerException error) {
            openLineageExceptionHandler.capturePropertyServerException(response, error);
        } catch (UserNotAuthorizedException error) {
            openLineageExceptionHandler.captureUserNotAuthorizedException(response, error);
        } catch (Throwable error) {
            openLineageExceptionHandler.captureThrowable(response, error, methodName);
        }
        return response;
    }

    public String exportMainGraph(String serverName, String userId) {
        String response;
        final String methodName = "OpenLineageRestServices.exportGraph";
        try {
            OpenLineageHandler graphServices = instanceHandler.getOpenLineageHandler(userId,
                    serverName,
                    methodName);
            response = graphServices.exportMainGraph();
        } catch (Exception e) {
            response = e.getMessage();
        }
        return response;
    }

    public LineageResponse lineage(String serverName, String userId, Scope scope, View view, String guid, String displayNameMustContain, boolean includeProcesses) {
        LineageResponse response = new LineageResponse();
        final String methodName = "OpenLineageRestServices.lineage";
        try {
            OpenLineageHandler openLineageHandler = instanceHandler.getOpenLineageHandler(userId,
                    serverName,
                    methodName);
            response = openLineageHandler.lineage(scope, view, guid, displayNameMustContain, includeProcesses);
        } catch (InvalidParameterException error) {
            openLineageExceptionHandler.captureInvalidParameterException(response, error);
        } catch (org.odpi.openmetadata.frameworks.connectors.ffdc.PropertyServerException error) {
            openLineageExceptionHandler.capturePropertyServerException(response, error);
        } catch (UserNotAuthorizedException error) {
            openLineageExceptionHandler.captureUserNotAuthorizedException(response, error);
        } catch (OpenLineageException error) {
            openLineageExceptionHandler.captureOpenLineageException(response, error);
        }catch (Throwable error) {
            openLineageExceptionHandler.captureThrowable(response, error, methodName);
        }
        return response;
    }
}