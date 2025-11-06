package com.benchmark.rest;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.io.IOException;
import java.net.URI;

public class Main {

    // URL de base du serveur
    public static final String BASE_URI = "http://localhost:8080/api/";

    public static void main(String[] args) throws IOException {
        // 1️⃣ Initialisation JPA
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("benchmarkPU");
        System.out.println("EntityManagerFactory created: " + emf);

        // 2️⃣ Configuration de Jersey + Grizzly
        final ResourceConfig rc = new ResourceConfig()
                .packages("com.benchmark.rest.resource","com.benchmark.rest.config"); // package où se trouvent les resources

        // 3️⃣ Création du serveur Grizzly
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        System.out.println("Jersey server started at " + BASE_URI);
        System.out.println("Press Ctrl+C to stop...");

        // Garde le serveur actif
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Stopping server...");
            server.shutdownNow();
            emf.close();
        }));

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
