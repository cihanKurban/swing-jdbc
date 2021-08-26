package tr.com.kurban.interfaces;

import java.util.List;

public interface IDataAccessLayer<T> {

	public void insert(T contract);
	public void update(T contract);
	public void delete(T contract);
	public List<T> getCustomerContractList();
	public T getCustomerContractById(int id);
}
