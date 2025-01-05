package Model.SharedModel;

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
        setChanged();
        notifyObservers();
    }

    public void removeCustomer(CustomerModel customer) {
        customerList.remove(customer);
        setChanged();
        notifyObservers();
    }
}