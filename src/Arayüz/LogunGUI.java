package Arayüz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;

public class LogunGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel t_pane;
	private JTextField fld_hastaTc;
	private JTextField fldDocTc;
	private JPasswordField fldDocPass;
	private DBConnection conn=new DBConnection();
	private JPasswordField fld_hastaPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogunGUI frame = new LogunGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogunGUI() {
		setResizable(false);
		setTitle("HASTANE OTOMASYONU");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		t_pane = new JPanel();
		t_pane.setBackground(new Color(255, 255, 255));
		t_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(t_pane);
		t_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Merkezi Hastane Yönetim Sistemine Hoşgeldiniz!");
		lblNewLabel.setBounds(85, 10, 440, 40);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		t_pane.add(lblNewLabel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(18, 82, 552, 265);
		t_pane.add(tabbedPane);
		
		JPanel hastaGiris = new JPanel();
		tabbedPane.addTab("Hasta Girişi", null, hastaGiris, null);
		hastaGiris.setLayout(null);
		
		JLabel lblTc = new JLabel("TC No:");
		lblTc.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTc.setBounds(24, 31, 114, 30);
		hastaGiris.add(lblTc);
		
		JLabel fld_hastaSifre = new JLabel("Şifre:");
		fld_hastaSifre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fld_hastaSifre.setBounds(24, 83, 114, 30);
		hastaGiris.add(fld_hastaSifre);
		
		fld_hastaTc = new JTextField();
		fld_hastaTc.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fld_hastaTc.setBounds(98, 31, 251, 30);
		hastaGiris.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);
		
		JButton btnHasLog = new JButton("Giriş Yap");
		btnHasLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_hastaTc.getText().length()==0 || fld_hastaPass.getText().length()==0) {
					Helper.showMsg("fill");
				}else {
					boolean key=true;
					try {
						Connection con = conn.connDb();
						java.sql.Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(fld_hastaTc.getText().equals(rs.getString("tc")) && fld_hastaPass.getText().equals(rs.getString("sifre"))) {
								if(rs.getString("tip").equals("hasta")) {
									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setSifre("sifre");
									hasta.setTc(rs.getString("tc"));
									hasta.setAd(rs.getString("ad"));
									hasta.setTip(rs.getString("tip"));
									HastaGUI hGUI = new HastaGUI(hasta);
									hGUI.setVisible(true);
									dispose();
									key=false;
								}
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(key) {
						Helper.showMsg("Kayıtlı hasta bulunamadı.Lütfen bilgilerinizi kontrol edip tekrar deneyiniz.");
					}
				}
			}
		});
		btnHasLog.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnHasLog.setBounds(40, 150, 200, 50);
		hastaGiris.add(btnHasLog);
		
		JButton btnHasReg = new JButton("Kayıt Ol");
		btnHasReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI=new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		btnHasReg.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnHasReg.setBounds(300, 150, 200, 50);
		hastaGiris.add(btnHasReg);
		
		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setBounds(98, 83, 250, 30);
		hastaGiris.add(fld_hastaPass);
		
		JPanel doktorGiris = new JPanel();
		tabbedPane.addTab("Doktor Girişi", null, doktorGiris, null);
		doktorGiris.setLayout(null);
		
		JLabel lblTc_1 = new JLabel("TC No:");
		lblTc_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTc_1.setBounds(31, 31, 114, 32);
		doktorGiris.add(lblTc_1);
		
		JLabel lblifre_1 = new JLabel("Şifre:");
		lblifre_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblifre_1.setBounds(31, 81, 114, 30);
		doktorGiris.add(lblifre_1);
		
		fldDocTc = new JTextField();
		fldDocTc.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fldDocTc.setColumns(10);
		fldDocTc.setBounds(105, 31, 250, 30);
		doktorGiris.add(fldDocTc);
		
		JButton btnDocLog = new JButton("Giriş Yap");
		btnDocLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(fldDocTc.getText().length()==0 || fldDocPass.getText().length()==0) {
					Helper.showMsg("fill");
				}else {
					try {
					Connection con = conn.connDb();
					java.sql.Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM user");
					while(rs.next()) {
						if(fldDocTc.getText().equals(rs.getString("tc")) && fldDocPass.getText().equals(rs.getString("sifre"))) {
							if(rs.getString("tip").equals("bashekim")) {
								Bashekim bhekim = new Bashekim();
								bhekim.setId(rs.getInt("id"));
								bhekim.setSifre("sifre");
								bhekim.setTc(rs.getString("tc"));
								bhekim.setAd(rs.getString("ad"));
								bhekim.setTip(rs.getString("tip"));
								BashekimGUI bGUI = new BashekimGUI(bhekim);
								bGUI.setVisible(true);
								dispose();
							}
							
							if(rs.getString("tip").equals("doktor")) {
								Doctor doctor=new Doctor();
								doctor.setId(rs.getInt("id"));
								doctor.setSifre("sifre");
								doctor.setTc(rs.getString("tc"));
								doctor.setAd(rs.getString("ad"));
								doctor.setTip(rs.getString("tip"));
								DoctorGUI dGUI=new DoctorGUI(doctor);
								dGUI.setVisible(true);
								dispose();
							}
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				}
			}
		});
		btnDocLog.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDocLog.setBounds(128, 150, 269, 50);
		doktorGiris.add(btnDocLog);
		
		fldDocPass = new JPasswordField();
		fldDocPass.setBounds(105, 78, 250, 30);
		doktorGiris.add(fldDocPass);
	}
}
