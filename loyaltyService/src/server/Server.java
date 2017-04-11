/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.URI;
import java.net.URISyntaxException;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * A server for running the Loyalty Service application.
 * 
 * @author adamstom97
 */
public class Server {
    public static void main(String[] args) throws URISyntaxException {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        
        ResourceConfig config = new ResourceConfig();
        
        //config.register
        
        URI baseUri = new URI("http://localhost:8081");
        JdkHttpServerFactory.createHttpServer(baseUri, config);
    }
}
