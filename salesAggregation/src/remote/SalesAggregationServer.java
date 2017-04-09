/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remote;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * A server for running the Sales Aggregation service.
 * 
 * @author adamstom97
 */
public class SalesAggregationServer {
    public static void main(String[] args) throws RemoteException {
        SalesAggregationImpl object = new SalesAggregationImpl();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("sales", object);
        System.out.println("Server is ready.");
    }
}
