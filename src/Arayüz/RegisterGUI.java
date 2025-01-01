package Arayüz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Hasta;
import Model.User;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JTextField fld_pass;
	private Hasta hasta=new Hasta();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setResizable(false);
		setTitle("Merkezi Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 330);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Ad Soyad");
		label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label.setBounds(10, 10, 152, 27);
		contentPane.add(label);
		
		fld_name = new JTextField();
		fld_name.setColumns(10);
		fld_name.setBounds(10, 36, 266, 27);
		contentPane.add(fld_name);
		
		JLabel lblTcNo = new JLabel("TC No");
		lblTcNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTcNo.setBounds(10, 73, 152, 27);
		contentPane.add(lblTcNo);
		
		fld_tcno = new JTextField();
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(10, 99, 266, 27);
		contentPane.add(fld_tcno);
		
		JLabel lblifre = new JLabel("Şifre");
		lblifre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblifre.setBounds(10, 139, 152, 27);
		contentPane.add(lblifre);
		
		fld_pass = new JTextField();
		fld_pass.setColumns(10);
		fld_pass.setBounds(10, 165, 266, 27);
		contentPane.add(fld_pass);
		
		JButton btn_register = new JButton("Kayıt Ol");
		btn_register.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e) {
			if(fld_tcno.getText().length()==0 || fld_pass.getText().length()==0 || fld_name.getText().length()==0){
				Helper.showMsg("fill");
			} 
			else {
				try {
					boolean control=hasta.register(fld_tcno.getText(), fld_pass.getText(),fld_name.getText());
					if(control) {
						Helper.showMsg("success");
						LogunGUI login = new LogunGUI();
						login.setVisible(true);
						dispose();
					}else {
						Helper.showMsg("error");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		}
	});
			
		btn_register.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_register.setBounds(10, 202, 266, 36);
		contentPane.add(btn_register);
		
		JButton btn_backto = new JButton("Geri Dön");
		btn_backto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogunGUI login = new LogunGUI();
				login.setVisible(true);
				dispose();
		}
	});
			
			
		btn_backto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_backto.setBounds(10, 248, 266, 35);
		contentPane.add(btn_backto);
		}
}
