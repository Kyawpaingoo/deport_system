package Model.Dtos;

import Model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

public class QueueOfCustomer {
    public List<CustomerModel> CustomerList;

    public QueueOfCustomer()
    {
        this.CustomerList = new ArrayList<>();
    }

    public List<CustomerModel> getCustomerList()
    {
        return CustomerList;
    }

    public void setCustomerList(List<CustomerModel> customerList)
    {
        this.CustomerList = customerList;
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder("QueueOfCustomer{ CustomerList=[");
//        for (CustomerModel customer : CustomerList) {
//            sb.append(customer.toString()).append(", ");
//        }
//        if (!CustomerList.isEmpty()) {
//            sb.setLength(sb.length() - 2);
//        }
//        sb.append("] }");
//        return sb.toString();
//    }
}

