package rts.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rts.filter.AuthFilter;
import rts.servlet.AdminClientsServlet;
import rts.servlet.LoginServlet;
import rts.servlet.LogoutServlet;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration(AuthFilter authFilter) {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>(authFilter);
        registration.addUrlPatterns("/admin/*");
        return registration;
    }

    @Bean
    public ServletRegistrationBean<LoginServlet> loginServletRegistration(LoginServlet servlet) {
        return new ServletRegistrationBean<>(servlet, "/login");
    }

    @Bean
    public ServletRegistrationBean<LogoutServlet> logoutServletRegistration(LogoutServlet servlet) {
        return new ServletRegistrationBean<>(servlet, "/logout");
    }

    @Bean
    public ServletRegistrationBean<AdminClientsServlet> adminClientsServletRegistration(AdminClientsServlet servlet) {
        return new ServletRegistrationBean<>(servlet, "/admin/clients");
    }
}