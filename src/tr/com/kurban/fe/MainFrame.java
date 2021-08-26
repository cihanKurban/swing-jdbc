package tr.com.kurban.fe;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tr.com.kurban.abs.AFrame;
import tr.com.kurban.dal.CustomerDal;
import tr.com.kurban.type.CustomerContract;

public class MainFrame extends AFrame {

	public MainFrame() {
		initFrame("Müþteri Ekle", initPanel());
	}

	@Override
	public JPanel initPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel buttonsJPanel = new JPanel(new GridLayout(5, 2));
		JLabel searcJLabel = new JLabel("Kiþi Ara:",JLabel.RIGHT);
		buttonsJPanel.add(searcJLabel);
		JTextField searchField = new JTextField(10);
		buttonsJPanel.add(searchField);
		JLabel nameJLabel = new JLabel("Adý: ", JLabel.RIGHT);
		buttonsJPanel.add(nameJLabel);
		JTextField nameField = new JTextField(10);
		buttonsJPanel.add(nameField);

		JLabel surnameJlaJLabel = new JLabel("Soyadý: ", JLabel.RIGHT);
		buttonsJPanel.add(surnameJlaJLabel);
		JTextField surnameField = new JTextField(10);
		buttonsJPanel.add(surnameField);

		JButton saveButton = new JButton("Kaydet");
		buttonsJPanel.add(saveButton);
		JButton updateButton = new JButton("Düzenle");
		buttonsJPanel.add(updateButton);
		JButton deleteButton = new JButton("Sil");
		buttonsJPanel.add(deleteButton);

		// get customer
		JList list = new JList(new CustomerDal().getCustomerContractList().toArray());
		JScrollPane custListPane = new JScrollPane(list);

		// save customer
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerContract contract = new CustomerContract();
				if ("".equals(nameField.getText()) || "".equals(surnameField.getText())) {
					JOptionPane.showMessageDialog(nameField, "Lütfen tüm alanlarý doldurunuz!");
				} else {
					contract.setName(nameField.getText());
					contract.setSurname(surnameField.getText());

					new CustomerDal().insert(contract);
					JOptionPane.showMessageDialog(nameField, contract.getName() + " " + contract.getSurname()
							+ " adlý kiþi baþarýlý bir þekilde kaydedilmiþtir.");
					list.setListData(new CustomerDal().getCustomerContractList().toArray());
					list.setSelectedIndex(0);
				}
			}
		});

		// show selected row in text fields
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				CustomerContract customerContract = (CustomerContract) list.getSelectedValue();
				if(customerContract != null) {
					CustomerContract contDal = new CustomerDal().getCustomerContractById(customerContract.getId());
					nameField.setText(contDal.getName());
					surnameField.setText(contDal.getSurname());
				}else {
					list.setSelectedIndex(0);
				}
				
			}
		});

		searchField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			// Search CustomerContract data in contract list for nameFiled
			@Override
			public void keyReleased(KeyEvent e) {
				list.setListData(new CustomerDal().getCustomerContractSearchByName(searchField.getText()).toArray());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerContract customerContract = (CustomerContract) list.getSelectedValue();
				CustomerContract updatedCustomerContract = new CustomerContract();
				updatedCustomerContract.setId(customerContract.getId());
				updatedCustomerContract.setName(nameField.getText());
				updatedCustomerContract.setSurname(surnameField.getText());
				new CustomerDal().updateCustomerContract(updatedCustomerContract);
				JOptionPane.showMessageDialog(nameField, updatedCustomerContract.getName() + " "
						+ updatedCustomerContract.getSurname() + " adlý kiþi baþarýlý bir þekilde düzenlenmiþtir.");
				list.setListData(new CustomerDal().getCustomerContractList().toArray());
			}
		});
		
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerContract customerContract = (CustomerContract) list.getSelectedValue();	
				if(customerContract != null) {
					new CustomerDal().delete(customerContract);
					JOptionPane.showMessageDialog(nameField, customerContract.getName() + " "
							+ customerContract.getSurname() + " adlý kiþi baþarýlý bir þekilde silinmiþtir.");
					list.setListData(new CustomerDal().getCustomerContractList().toArray());
				}
				
			}
		});

		panel.add(buttonsJPanel, BorderLayout.NORTH);
		panel.add(custListPane, BorderLayout.CENTER);

		return panel;
	}

}
