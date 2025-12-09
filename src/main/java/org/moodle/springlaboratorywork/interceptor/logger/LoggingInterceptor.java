package org.moodle.springlaboratorywork.interceptor.logger;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.logger.Logger;
import org.moodle.springlaboratorywork.logger.enums.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {
    private final Logger requestsLogger;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {

        String logMessage = String.format(" %s %s -> %s %s ",
                LogLevel.INFO,
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus()
        );
        requestsLogger.write(LogLevel.INFO.toString(), logMessage, HttpServletRequest.class);

    }

}
