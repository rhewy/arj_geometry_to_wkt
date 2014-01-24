package gist8010;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConnectionInfoDialog extends JDialog {

	private BindingGroup m_bindingGroup;
	private JPanel m_contentPane;
	private gist8010.ConnectionInfo connectionInfo = new gist8010.ConnectionInfo();
	private JTextField connectivityTypeJTextField;
	private JTextField dbmsVendorJTextField;
	private JTextField serverNameOrIPJTextField;
	private JTextField serverPortJTextField;
	private JTextField defaultDatabaseJTextField;
	private JTextField databaseURLJTextField;
	private JTextField userNameJTextField;
	private JPasswordField userPasswordJPasswordField;
	private JButton btnOk;
	private JButton btnCancel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConnectionInfoDialog dialog = new ConnectionInfoDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConnectionInfoDialog() {
		setBounds(100, 100, 450, 349);
		m_contentPane = new JPanel();
		setContentPane(m_contentPane);
		//
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0E-4 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 1.0E-4 };
		m_contentPane.setLayout(gridBagLayout);

		JLabel connectivityTypeLabel = new JLabel("ConnectivityType:");
		GridBagConstraints labelGbc_0 = new GridBagConstraints();
		labelGbc_0.insets = new Insets(5, 5, 5, 5);
		labelGbc_0.gridx = 0;
		labelGbc_0.gridy = 0;
		m_contentPane.add(connectivityTypeLabel, labelGbc_0);

		connectivityTypeJTextField = new JTextField();
		GridBagConstraints componentGbc_0 = new GridBagConstraints();
		componentGbc_0.insets = new Insets(5, 0, 5, 0);
		componentGbc_0.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_0.gridx = 1;
		componentGbc_0.gridy = 0;
		m_contentPane.add(connectivityTypeJTextField, componentGbc_0);

		JLabel dbmsVendorLabel = new JLabel("DbmsVendor:");
		GridBagConstraints labelGbc_1 = new GridBagConstraints();
		labelGbc_1.insets = new Insets(5, 5, 5, 5);
		labelGbc_1.gridx = 0;
		labelGbc_1.gridy = 1;
		m_contentPane.add(dbmsVendorLabel, labelGbc_1);

		dbmsVendorJTextField = new JTextField();
		GridBagConstraints componentGbc_1 = new GridBagConstraints();
		componentGbc_1.insets = new Insets(5, 0, 5, 0);
		componentGbc_1.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_1.gridx = 1;
		componentGbc_1.gridy = 1;
		m_contentPane.add(dbmsVendorJTextField, componentGbc_1);

		JLabel serverNameOrIPLabel = new JLabel("ServerNameOrIP:");
		GridBagConstraints labelGbc_2 = new GridBagConstraints();
		labelGbc_2.insets = new Insets(5, 5, 5, 5);
		labelGbc_2.gridx = 0;
		labelGbc_2.gridy = 2;
		m_contentPane.add(serverNameOrIPLabel, labelGbc_2);

		serverNameOrIPJTextField = new JTextField();
		GridBagConstraints componentGbc_2 = new GridBagConstraints();
		componentGbc_2.insets = new Insets(5, 0, 5, 0);
		componentGbc_2.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_2.gridx = 1;
		componentGbc_2.gridy = 2;
		m_contentPane.add(serverNameOrIPJTextField, componentGbc_2);

		JLabel serverPortLabel = new JLabel("ServerPort:");
		GridBagConstraints labelGbc_3 = new GridBagConstraints();
		labelGbc_3.insets = new Insets(5, 5, 5, 5);
		labelGbc_3.gridx = 0;
		labelGbc_3.gridy = 3;
		m_contentPane.add(serverPortLabel, labelGbc_3);

		serverPortJTextField = new JTextField();
		GridBagConstraints componentGbc_3 = new GridBagConstraints();
		componentGbc_3.insets = new Insets(5, 0, 5, 0);
		componentGbc_3.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_3.gridx = 1;
		componentGbc_3.gridy = 3;
		m_contentPane.add(serverPortJTextField, componentGbc_3);

		JLabel defaultDatabaseLabel = new JLabel("DefaultDatabase:");
		GridBagConstraints labelGbc_4 = new GridBagConstraints();
		labelGbc_4.insets = new Insets(5, 5, 5, 5);
		labelGbc_4.gridx = 0;
		labelGbc_4.gridy = 4;
		m_contentPane.add(defaultDatabaseLabel, labelGbc_4);

		defaultDatabaseJTextField = new JTextField();
		GridBagConstraints componentGbc_4 = new GridBagConstraints();
		componentGbc_4.insets = new Insets(5, 0, 5, 0);
		componentGbc_4.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_4.gridx = 1;
		componentGbc_4.gridy = 4;
		m_contentPane.add(defaultDatabaseJTextField, componentGbc_4);

		JLabel databaseURLLabel = new JLabel("DatabaseURL:");
		GridBagConstraints labelGbc_5 = new GridBagConstraints();
		labelGbc_5.insets = new Insets(5, 5, 5, 5);
		labelGbc_5.gridx = 0;
		labelGbc_5.gridy = 5;
		m_contentPane.add(databaseURLLabel, labelGbc_5);

		databaseURLJTextField = new JTextField();
		GridBagConstraints componentGbc_5 = new GridBagConstraints();
		componentGbc_5.insets = new Insets(5, 0, 5, 0);
		componentGbc_5.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_5.gridx = 1;
		componentGbc_5.gridy = 5;
		m_contentPane.add(databaseURLJTextField, componentGbc_5);

		JLabel userNameLabel = new JLabel("UserName:");
		GridBagConstraints labelGbc_6 = new GridBagConstraints();
		labelGbc_6.insets = new Insets(5, 5, 5, 5);
		labelGbc_6.gridx = 0;
		labelGbc_6.gridy = 6;
		m_contentPane.add(userNameLabel, labelGbc_6);

		userNameJTextField = new JTextField();
		GridBagConstraints componentGbc_6 = new GridBagConstraints();
		componentGbc_6.insets = new Insets(5, 0, 5, 0);
		componentGbc_6.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_6.gridx = 1;
		componentGbc_6.gridy = 6;
		m_contentPane.add(userNameJTextField, componentGbc_6);

		JLabel userPasswordLabel = new JLabel("UserPassword:");
		GridBagConstraints labelGbc_7 = new GridBagConstraints();
		labelGbc_7.insets = new Insets(5, 5, 5, 5);
		labelGbc_7.gridx = 0;
		labelGbc_7.gridy = 7;
		m_contentPane.add(userPasswordLabel, labelGbc_7);

		userPasswordJPasswordField = new JPasswordField();
		GridBagConstraints componentGbc_7 = new GridBagConstraints();
		componentGbc_7.insets = new Insets(5, 0, 5, 0);
		componentGbc_7.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_7.gridx = 1;
		componentGbc_7.gridy = 7;
		m_contentPane.add(userPasswordJPasswordField, componentGbc_7);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 5, 0);
		gbc_btnOk.anchor = GridBagConstraints.EAST;
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 8;
		m_contentPane.add(btnOk, gbc_btnOk);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.EAST;
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 9;
		m_contentPane.add(btnCancel, gbc_btnCancel);

		if (connectionInfo != null) {
			m_bindingGroup = initDataBindings();
		}
	}

	protected BindingGroup initDataBindings() {
		BeanProperty<gist8010.ConnectionInfo, java.lang.String> connectivityTypeProperty = BeanProperty
				.create("connectivityType");
		BeanProperty<javax.swing.JTextField, java.lang.String> textProperty = BeanProperty
				.create("text");
		AutoBinding<gist8010.ConnectionInfo, java.lang.String, javax.swing.JTextField, java.lang.String> autoBinding = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						connectionInfo, connectivityTypeProperty,
						connectivityTypeJTextField, textProperty);
		autoBinding.bind();
		//
		BeanProperty<gist8010.ConnectionInfo, java.lang.String> dbmsVendorProperty = BeanProperty
				.create("dbmsVendor");
		BeanProperty<javax.swing.JTextField, java.lang.String> textProperty_1 = BeanProperty
				.create("text");
		AutoBinding<gist8010.ConnectionInfo, java.lang.String, javax.swing.JTextField, java.lang.String> autoBinding_1 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						connectionInfo, dbmsVendorProperty,
						dbmsVendorJTextField, textProperty_1);
		autoBinding_1.bind();
		//
		BeanProperty<gist8010.ConnectionInfo, java.lang.String> serverNameOrIPProperty = BeanProperty
				.create("serverNameOrIP");
		BeanProperty<javax.swing.JTextField, java.lang.String> textProperty_2 = BeanProperty
				.create("text");
		AutoBinding<gist8010.ConnectionInfo, java.lang.String, javax.swing.JTextField, java.lang.String> autoBinding_2 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						connectionInfo, serverNameOrIPProperty,
						serverNameOrIPJTextField, textProperty_2);
		autoBinding_2.bind();
		//
		BeanProperty<gist8010.ConnectionInfo, java.lang.String> serverPortProperty = BeanProperty
				.create("serverPort");
		BeanProperty<javax.swing.JTextField, java.lang.String> textProperty_3 = BeanProperty
				.create("text");
		AutoBinding<gist8010.ConnectionInfo, java.lang.String, javax.swing.JTextField, java.lang.String> autoBinding_3 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						connectionInfo, serverPortProperty,
						serverPortJTextField, textProperty_3);
		autoBinding_3.bind();
		//
		BeanProperty<gist8010.ConnectionInfo, java.lang.String> defaultDatabaseProperty = BeanProperty
				.create("defaultDatabase");
		BeanProperty<javax.swing.JTextField, java.lang.String> textProperty_4 = BeanProperty
				.create("text");
		AutoBinding<gist8010.ConnectionInfo, java.lang.String, javax.swing.JTextField, java.lang.String> autoBinding_4 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						connectionInfo, defaultDatabaseProperty,
						defaultDatabaseJTextField, textProperty_4);
		autoBinding_4.bind();
		//
		BeanProperty<gist8010.ConnectionInfo, java.lang.String> databaseURLProperty = BeanProperty
				.create("databaseURL");
		BeanProperty<javax.swing.JTextField, java.lang.String> textProperty_5 = BeanProperty
				.create("text");
		AutoBinding<gist8010.ConnectionInfo, java.lang.String, javax.swing.JTextField, java.lang.String> autoBinding_5 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ,
						connectionInfo, databaseURLProperty,
						databaseURLJTextField, textProperty_5);
		autoBinding_5.bind();
		//
		BeanProperty<gist8010.ConnectionInfo, java.lang.String> userNameProperty = BeanProperty
				.create("userName");
		BeanProperty<javax.swing.JTextField, java.lang.String> textProperty_6 = BeanProperty
				.create("text");
		AutoBinding<gist8010.ConnectionInfo, java.lang.String, javax.swing.JTextField, java.lang.String> autoBinding_6 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						connectionInfo, userNameProperty, userNameJTextField,
						textProperty_6);
		autoBinding_6.bind();
		//
		BeanProperty<gist8010.ConnectionInfo, java.lang.String> userPasswordProperty = BeanProperty
				.create("userPassword");
		BeanProperty<javax.swing.JPasswordField, java.lang.String> textProperty_7 = BeanProperty
				.create("text");
		AutoBinding<gist8010.ConnectionInfo, java.lang.String, javax.swing.JPasswordField, java.lang.String> autoBinding_7 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						connectionInfo, userPasswordProperty,
						userPasswordJPasswordField, textProperty_7);
		autoBinding_7.bind();
		//
		BindingGroup bindingGroup = new BindingGroup();
		bindingGroup.addBinding(autoBinding);
		bindingGroup.addBinding(autoBinding_1);
		bindingGroup.addBinding(autoBinding_2);
		bindingGroup.addBinding(autoBinding_3);
		bindingGroup.addBinding(autoBinding_4);
		bindingGroup.addBinding(autoBinding_5);
		bindingGroup.addBinding(autoBinding_6);
		bindingGroup.addBinding(autoBinding_7);
		//
		return bindingGroup;
	}

	public gist8010.ConnectionInfo getConnectionInfo() {
		return connectionInfo;
	}

	public void setConnectionInfo(gist8010.ConnectionInfo newConnectionInfo) {
		setConnectionInfo(newConnectionInfo, true);
	}

	public void setConnectionInfo(gist8010.ConnectionInfo newConnectionInfo,
			boolean update) {
		connectionInfo = newConnectionInfo;
		if (update) {
			if (m_bindingGroup != null) {
				m_bindingGroup.unbind();
				m_bindingGroup = null;
			}
			if (connectionInfo != null) {
				m_bindingGroup = initDataBindings();
			}
		}
	}
	
	private void close()
	{
		try {
			setVisible(false);
			this.setConnectionInfo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
