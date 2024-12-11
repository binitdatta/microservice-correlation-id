package com.manh.debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Order(2)
public class VerboseResponseFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper(); // Use Jackson for JSON formatting

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Wrap the response to enable caching
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpResponse);

        chain.doFilter(request, responseWrapper);

        // Get the debug messages from the RequestContext
        RequestEntity requestEntity = RequestContext.get();
        if (requestEntity != null && requestEntity.isXverbose()) {
            // Read the original response body
            String originalResponseBody = new String(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding());

            // Format debug messages as a JSON array
            List<String> debugMessages = requestEntity.getDebugMessages();
            String formattedDebugMessages = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(debugMessages);

            // Construct the final JSON response
            String modifiedResponseBody = String.format(
                    "{\n  \"originalResponse\": %s,\n  \"debugMessages\": %s\n}",
                    originalResponseBody,
                    formattedDebugMessages
            );

            // Write the modified response back to the wrapper
            responseWrapper.resetBuffer(); // Clear the existing buffer
            responseWrapper.getWriter().write(modifiedResponseBody);
        }

        // Copy the response content back to the original response
        responseWrapper.copyBodyToResponse();
    }
}
