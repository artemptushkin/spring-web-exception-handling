package io.github.artemptushkin.demo.springwebexceptionhandling.filter;

import io.github.artemptushkin.demo.springwebexceptionhandling.controller.CustomExceptionInFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ExampleFilterThatThrows implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpServletRequest) {
            String headerThatBreaks = httpServletRequest.getHeader("header-that-breaks");
            if (headerThatBreaks != null) {
                throw new CustomExceptionInFilter("I'm exception from filter that setup after the exception filter");
            }
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
