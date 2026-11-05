package edu.upc.dsa;

import io.swagger.jaxrs.config.BeanConfig;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);
    public static final String BASE_URI = "http://localhost:8080/dsaApp/";

    public static HttpServer startServer() {
        ResourceConfig rc = new ResourceConfig().packages("edu.upc.dsa.services");
        rc.register(io.swagger.jaxrs.listing.ApiListingResource.class);
        rc.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/dsaApp");
        beanConfig.setDescription("MINIM1 Drones");
        beanConfig.setResourcePackage("edu.upc.dsa.services");
        beanConfig.setTitle("MINIM1");
        beanConfig.setVersion("1.0.0");
        beanConfig.setScan(true);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = startServer();
        StaticHttpHandler staticHttpHandler = new StaticHttpHandler("./public/");
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/");

        logger.info("Servidor en " + BASE_URI);
        logger.info("Swagger en http://localhost:8080/swagger/");

        System.in.read();
        server.shutdownNow();
    }
}
