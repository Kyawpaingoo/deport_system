package Infra.UnitOfWork;

import Infra.Repository.IRepository;
import Model.CollectedParcelModel;
import Model.CustomerModel;
import Model.ParcelModel;
import Model.StaffModel;

public interface IUnitOfWork {
    IRepository<StaffModel> _staffRepository();
    IRepository<CustomerModel> _customerRepository();
    IRepository<ParcelModel> _parcelRepository();
    IRepository<CollectedParcelModel> _collectedParcelRepository();
}
