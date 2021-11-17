package br.com.tfergulha.mvc.mudi.interceptor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class InterceptadorDeAcessos implements HandlerInterceptor {

    public static List<Acesso> acessos = new ArrayList<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) {
        Acesso acesso = new Acesso();
        acesso.path = request.getRequestURI();
        acesso.data = LocalDateTime.now();
        request.setAttribute("acesso", acesso);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) {
        Acesso acesso = (Acesso) request.getAttribute("acesso");
        acesso.duration = Duration.between(acesso.data, LocalDateTime.now());
        acessos.add(acesso);
    }

    public static class Acesso {

        private String path;
        private LocalDateTime data;
        private Duration duration;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public LocalDateTime getData() {
            return data;
        }

        public void setData(LocalDateTime data) {
            this.data = data;
        }

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }
    }
}
