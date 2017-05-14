/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dao.CustomerDao;
import domain.Customer;
import filters.CorsFilter;
import filters.DebugFilter;
import filters.ExceptionLogger;
import filters.ExceptionMessageHandler;
import java.net.URI;
import java.net.URISyntaxException;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;
import resource.CouponResource;
import resource.CouponsResource;
import resource.PointsResource;
import resource.TransactionResource;
import resource.TransactionsResource;

/**
 * A server for running the Loyalty Service application.
 *
 * @author adamstom97
 */
public class LoyaltyServer {

    public static void main(String[] args) throws URISyntaxException {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        ResourceConfig config = new ResourceConfig();

        config.register(DebugFilter.class);
        config.register(CorsFilter.class);
        config.register(ExceptionLogger.class);
        config.register(ExceptionMessageHandler.class);

        config.register(CouponResource.class);
        config.register(CouponsResource.class);
        config.register(PointsResource.class);
        config.register(TransactionResource.class);
        config.register(TransactionsResource.class);

        URI baseUri = new URI("http://localhost:8081/");
        JdkHttpServerFactory.createHttpServer(baseUri, config);

        // Hard coded customer for testing.
        Customer customer = new Customer(
					 "06bf537b-c77f-11e7-ff13-0c871e86361a");
        CustomerDao.createCustomer(customer);
        
        System.out.println("Loyalty service ready on " + baseUri);
    }
}
