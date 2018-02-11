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
    
    // reindenter fichier details
    
    // commenter tout ce qui est utile
    
    // probleme avec verif forms de Bootstrap ? salaire bizarre
    
    // je me suis permis de changer le lien pour ajouter supprimer manager d'un technicien factorisation de code 3 methode au lieu de 4
    
    // solution pour le count : rediriger vers une servlet qui ajoute juste nombre d'employe au model ?
    
    // pagination : regler le problem de page mini 
}
