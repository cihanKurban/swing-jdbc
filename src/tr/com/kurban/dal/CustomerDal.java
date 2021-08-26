package tr.com.kurban.dal;

import java.util.ArrayList;
import java.util.List;

import tr.com.kurban.core.ObjectHelper;
import tr.com.kurban.interfaces.IDataAccessLayer;
import tr.com.kurban.type.CustomerContract;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDal extends ObjectHelper implements IDataAccessLayer<CustomerContract> {

	@Override
	public void insert(CustomerContract contract) {

		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("insert customer (adi, soyadi) values ('" + contract.getName() + "' , '"
					+ contract.getSurname() + "' )");

			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(CustomerContract contract) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(CustomerContract contract) {
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("delete from customer where id="+contract.getId());
			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<CustomerContract> getCustomerContractList() {

		Connection connection = getConnection();
		List<CustomerContract> customerContractList = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from customer");
			customerContractList.addAll(populateCustomerContractList(rs));
			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerContractList;
	}

	@Override
	public CustomerContract getCustomerContractById(int id) {
		Connection connection = getConnection();
		CustomerContract customerContract = new CustomerContract();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from customer where id=" + id);
			customerContract = populateCustomerContract(rs);

			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerContract;
	}

	public CustomerContract populateCustomerContract(ResultSet rs) {
		CustomerContract customerContract = new CustomerContract();
		try {
			if (rs.next()) {
				customerContract.setId(rs.getInt("id"));
				customerContract.setName(rs.getString("adi"));
				customerContract.setSurname(rs.getString("soyadi"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerContract;

	}

	public List<CustomerContract> populateCustomerContractList(ResultSet rs) {
		List<CustomerContract> customerContractList = new ArrayList<>();
		try {
			while (rs.next()) {
				CustomerContract customerContract = new CustomerContract();
				customerContract.setId(rs.getInt("id"));
				customerContract.setName(rs.getString("adi"));
				customerContract.setSurname(rs.getString("soyadi"));
				customerContractList.add(customerContract);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerContractList;
	}

	public List<CustomerContract> getCustomerContractSearchByName(String name) {

		List<CustomerContract> dataContract = new ArrayList<CustomerContract>();
		Connection connection = getConnection();

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from customer where adi like '%" + name + "%' ");
			dataContract.addAll(populateCustomerContractList(rs));
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dataContract;
	}

	public void updateCustomerContract(CustomerContract customerContract) {
		Connection connection = getConnection();

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("update customer set adi='" + customerContract.getName() + "', soyadi='"
					+ customerContract.getSurname() + "' where id=" + customerContract.getId());
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
