package com.ipiecoles.java.java320;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringWebApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringWebApplication.class, args);
    }

    
    // manque lien autogenéré pour supprimer un tec d'une equipe de manager
    
    // manque lien pour ajouter tec dans equip manager
    
    // manque modif save 
    
    // manque delete employe --> a faire depuis les sous classes car joined strategy
    
    // manque lien pour supprimer un manager d'un tec
    
    // manque lien pour changer asc desc tri
    
    // reindenter fichier details
}
