package tech.buildrun.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import tech.buildrun.lambda.request.LoginRequest;
import tech.buildrun.lambda.response.LoginResponse;

public class Handler implements
    RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final ObjectMapper objectMapper;

    static { // lambda reaproveita em novas requisições
        objectMapper = new ObjectMapper(); // instância manual, pois não é spring boot
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request,
        Context context) {
        try {
            var logger = context.getLogger();

            logger.log("Request received - " + request.getBody());

            var loginRequest = objectMapper.readValue(request.getBody(), LoginRequest.class);

            var loginResponse = new LoginResponse(isAuthorized(loginRequest, logger));

            var bodyResponse = objectMapper.writeValueAsString(loginResponse);

            return getApiGatewayProxyResponseEvent(200, bodyResponse);
        } catch (Exception e) {
            return getApiGatewayProxyResponseEvent(500, e.getMessage());
        }
    }

    private static APIGatewayProxyResponseEvent getApiGatewayProxyResponseEvent(final int statusCode,
        final String bodyResponse) {
        return new APIGatewayProxyResponseEvent()
            .withStatusCode(statusCode)
            .withBody(bodyResponse)
            .withIsBase64Encoded(false);
    }

    private static boolean isAuthorized(final LoginRequest loginRequest, final LambdaLogger logger) {
        if (loginRequest.username().equals("admin") && loginRequest.password().equals("123")) {
            logger.log("Login successful");
            return true;
        }
        logger.log("Login failed");
        return false;
    }

}
