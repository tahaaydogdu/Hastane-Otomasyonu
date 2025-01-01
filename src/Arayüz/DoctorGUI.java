package Arayüz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Doctor;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class DoctorGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private static Doctor doctor = new Doctor();
	private JTable table_whour;
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public DoctorGUI(Doctor doctor) throws SQLException {
		
		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object [2];
		for(int i = 0; i< doctor.getWhourList(doctor.getId()).size();i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
		}
		
		
		setResizable(false);
		setTitle("Merkezi Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setBounds(432, 123, 248, 0);
		contentPane.add(table);
		
		JLabel lblNewLabel = new JLabel("Hoşgeldiniz, Sayın "+doctor.getAd());
		lblNewLabel.setBounds(10, 20, 483, 31);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Çıkış Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(547, 20, 160, 31);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(btnNewButton);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(20, 61, 706, 392);
		contentPane.add(tabbedPane);
		
		JPanel w_whour = new JPanel();
		w_whour.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Çalışma Saatleri", null, w_whour, null);
		w_whour.setLayout(null);
		
		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(10, 10, 130, 30);
		w_whour.add(select_date);
		
		JComboBox select_time = new JComboBox();
		select_time.setFont(new Font("Tahoma", Font.PLAIN, 15));
		select_time.setModel(new DefaultComboBoxModel(new String[] {"9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "13:30", "14:00", "14:30", "15:00", "15:30"}));
		select_time.setBounds(150, 10, 110, 30);
		w_whour.add(select_time);
		
		JButton btnEkle = new JButton("Ekle");
		btnEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date ="";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {
					
				}
				if(date.length() == 0) {
					Helper.showMsg("Lütfen geçerli bir tarih giriniz!");
				}
				else {
				String time = " " + select_time.getSelectedItem().toString() + ":00";
				String selectDate = date + time;
				try {
					boolean control = doctor.addWhour(doctor.getId(), doctor.getAd(), selectDate);
					if(control) {
						Helper.showMsg("success");
						updateWhourModel(doctor);
					}
					else {
						Helper.showMsg("error");
					}
				} catch(Exception e2) {
					
				}
				}
				
			}
		});
		btnEkle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnEkle.setBounds(270, 10, 120, 30);
		w_whour.add(btnEkle);
		
		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(10, 50, 681, 305);
		w_whour.add(w_scrollWhour);
		
		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		
		JButton btnSil = new JButton("Sil");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if(selRow >=0) {
					String selectRow = table_whour.getModel().getValueAt(selRow,0).toString();
					int selID=Integer.parseInt(selectRow);
					boolean control;
					try {
						control = doctor.deleteWhour(selID);
						if(control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						}
						else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					Helper.showMsg("Lütfen bir tarih seçiniz!");
				}
			}
		});
		btnSil.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSil.setBounds(555, 10, 120, 30);
		w_whour.add(btnSil);
	}
	
	public void updateWhourModel(Doctor doctor) throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel(); 
        clearModel.setRowCount(0);
        for(int i = 0; i< doctor.getWhourList(doctor.getId()).size();i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
		}
    }
}
