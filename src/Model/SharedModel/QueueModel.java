package Model.SharedModel;

import Logger.Logger;
import Model.CustomerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class QueueModel extends Observable {
    private List<CustomerModel> customerList = new ArrayList<>();

    public List<CustomerModel> getCustomerList() {
        return customerList;
    }

    public void addCustomer(CustomerModel customer) {
        customerList.add(customer);
        Logger.getInstance().log("Customer added to Queue: " + customer.toString());
        setChanged();
        notifyObservers();
    }

    public void removeCustomer(CustomerModel customer) {
        Logger.getInstance().log("Customer removed from Queue: " + customer.toString());
        customerList.remove(customer);
        setChanged();
        notifyObservers();
    }
}