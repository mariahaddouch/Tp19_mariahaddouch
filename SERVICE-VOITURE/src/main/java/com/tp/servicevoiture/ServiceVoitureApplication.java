package com.tp.servicevoiture;

import com.tp.servicevoiture.entity.Client;
import com.tp.servicevoiture.entity.Voiture;
import com.tp.servicevoiture.repository.VoitureRepository;
import com.tp.servicevoiture.service.ClientService;
import com.tp.servicevoiture.service.VoitureService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ServiceVoitureApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceVoitureApplication.class, args);
    }

    @Bean
    CommandLineRunner initialiserBaseH2(VoitureRepository voitureRepository,
                                        ClientService clientService,
                                        VoitureService voitureService) {
        return args -> {
            // Attendre que Eureka synchronise
            Thread.sleep(5000); // 5 secondes

            Client c1 = clientService.clientById(2L);
            Client c2 = clientService.clientById(1L);
            System.out.println("**************************");
            System.out.println("Client id est :" + c2.getId());
            System.out.println("Client nom est :" + c2.getNom());

            // Créer des objets Voiture
            Voiture v1 = new Voiture();
            v1.setId_client(2L);
            v1.setMarque("Toyota");
            v1.setMatricule("A 25 333");
            v1.setModel("Corolla");

            Voiture v2 = new Voiture();
            v2.setId_client(2L);
            v2.setMarque("Renault");
            v2.setMatricule("B 6 3456");
            v2.setModel("Megane");

            Voiture v3 = new Voiture();
            v3.setId_client(1L);
            v3.setMarque("Peugeot");
            v3.setMatricule("A 55 4444");
            v3.setModel("301");

            voitureService.enregistrerVoiture(v1);
            voitureService.enregistrerVoiture(v2);
            voitureService.enregistrerVoiture(v3);
        };
    }
}