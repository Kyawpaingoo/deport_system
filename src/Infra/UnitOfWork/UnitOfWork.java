package Infra.UnitOfWork;

import Infra.Repository.IRepository;
import Infra.Repository.Repository;
import Model.CustomerModel;
import Model.ParcelModel;
import Model.StaffModel;

public class UnitOfWork implements IUnitOfWork {

    private final IRepository<StaffModel> staffRepository;
    private final IRepository<CustomerModel> customerRepository;
    private final IRepository<ParcelModel> parcelRepository;


    public UnitOfWork() {
        this.staffRepository = new Repository<>("./src/DataFile/staff.csv", new StaffModel(), false);
        this.customerRepository = new Repository<>("./src/DataFile/customer.csv", new CustomerModel(), false);
        this.parcelRepository = new Repository<>("./src/DataFile/parcel.csv", new ParcelModel(), true);
    }

    @Override
    public IRepository<StaffModel> _staffRepository() {
        return staffRepository;
    }

    @Override
    public IRepository<CustomerModel> _customerRepository() {
        return customerRepository;
    }

    @Override
    public IRepository<ParcelModel> _parcelRepository() {
        return parcelRepository;
    }
}
