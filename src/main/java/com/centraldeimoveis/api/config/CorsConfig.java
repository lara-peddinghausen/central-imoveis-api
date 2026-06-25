package com.centraldeimoveis.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")           // aplica em todas as rotas
        .allowedOrigins("*")        // qualquer origem
        .allowedMethods(
            "GET", "POST", "PUT", "DELETE", "OPTIONS"
          )
        .allowedHeaders("*")        // permite o header Authorization
        .maxAge(3600);              // cache do preflight por 1h
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**")
        // Caminho do diretório deve ser atualizado conforme pasta que será usada para salvar as fotos. Atualizar em: CorsFilter e ImovelController (métodos: cadastrar e /atualizar/{id})
        // A pasta DEVE se chamar uploads a fim de evitar mais alterações no código
        // .addResourceLocations("file:///C:/Users/LaraP/OneDrive/Desktop/Central de imóveis - Backend/uploads/");
        .addResourceLocations("file:///C:/Users/36129382024.2n/Desktop/central_de_imoveis/uploads/");
  }
}