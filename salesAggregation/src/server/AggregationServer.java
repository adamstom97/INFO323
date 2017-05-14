/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * A server for running the Sales Aggregation service.
 *
 * @author adamstom97
 */
public class AggregationServer {

    public static void main(String[] args) throws RemoteException {
        SalesAggImpl object = new SalesAggImpl();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("sales", object);
        System.out.println("Aggregation service ready.");
    }
}
