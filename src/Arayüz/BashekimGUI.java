package Arayüz;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import Model.Bashekim;
import Model.Clinic;
import Model.User;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Point;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import Helper.*;
import javax.swing.JComboBox;

public class BashekimGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    static Bashekim bashekim = new Bashekim();
    Clinic clinic=new Clinic();
    private JPanel contentPane;
    private JTextField textadsoyad;
    private JTextField texttc;
    private JTextField textsifre;
    private JTextField textid;
    private JTable table_doctor;
    private DefaultTableModel doctorModel = null;
    private Object[] doctorData = null;
    private JTable table_clinic;
    private JTextField textclinicname;
    private DefaultTableModel clinicModel=null;
    private Object[] clinicData=null;
    private JPopupMenu clinicMenu;
    private JTable table_worker;
    

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BashekimGUI frame = new BashekimGUI(bashekim);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     * 
     * @throws SQLException
     */
    public BashekimGUI(Bashekim bashekim) throws SQLException {
    	
    	
        doctorModel = new DefaultTableModel();
        Object[] colDoctorName = new Object[4];
        colDoctorName[0] = "id";
        colDoctorName[1] = "tc";
        colDoctorName[2] = "sifre";
        colDoctorName[3] = "ad";
        doctorModel.setColumnIdentifiers(colDoctorName);
        doctorData = new Object[4];
        
        clinicModel = new DefaultTableModel();
        Object[] colClinic=new Object[2];
        colClinic[0]="ID";
        colClinic[1]="Poliklinik Adı";
        clinicModel.setColumnIdentifiers(colClinic);
        clinicData = new Object[2];
        for(int i=0;i<clinic.getList().size();i++) {
        	clinicData[0]=clinic.getList().get(i).getId();
        	clinicData[1]=clinic.getList().get(i).getName();
        	clinicModel.addRow(clinicData);
        	
        }
        
        DefaultTableModel workerModel = new DefaultTableModel();
        Object[]colWorker=new Object[2];
        colWorker[0]="ID";
        colWorker[1]="Ad-Soyad";
        workerModel.setColumnIdentifiers(colWorker);
        Object[] workerData=new Object[2];

        updateDoctorTable();

        setTitle("Merkezi Hastane Yönetim Sistemi");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Hoşgeldiniz, Sayın " + bashekim.getAd());
        lblNewLabel.setBounds(29, 10, 275, 31);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        contentPane.add(lblNewLabel);

        JButton btnNewButton = new JButton("Çıkış Yap");
        btnNewButton.setBounds(566, 10, 160, 31);
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                System.exit(0);  
            }
        });
        btnNewButton.setBounds(566, 10, 160, 31);
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(btnNewButton);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(21, 78, 692, 375);
        contentPane.add(tabbedPane);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(192, 192, 192));
        tabbedPane.addTab("Doktor Yönetimi", null, panel, null);
        panel.setLayout(null);

        JLabel labeladsoyad = new JLabel("Ad Soyad");
        labeladsoyad.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labeladsoyad.setBounds(513, 10, 152, 27);
        panel.add(labeladsoyad);

        textadsoyad = new JTextField();
        textadsoyad.setBounds(513, 36, 152, 27);
        panel.add(textadsoyad);
        textadsoyad.setColumns(10);

        JLabel labeltcno = new JLabel("TC No:");
        labeltcno.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labeltcno.setBounds(513, 73, 152, 27);
        panel.add(labeltcno);

        texttc = new JTextField();
        texttc.setColumns(10);
        texttc.setBounds(513, 99, 152, 27);
        panel.add(texttc);

        JLabel labelsifre = new JLabel("Şifre:");
        labelsifre.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelsifre.setBounds(513, 136, 152, 27);
        panel.add(labelsifre);

        textsifre = new JTextField();
        textsifre.setColumns(10);
        textsifre.setBounds(513, 164, 152, 27);
        panel.add(textsifre);

        JButton btnekle = new JButton("Ekle");
        btnekle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (texttc.getText().length() == 0 || textsifre.getText().length() == 0 || textadsoyad.getText().length() == 0) {
                    Helper.showMsg("fill");
                } else {
                    try {
                        boolean control = bashekim.addDoctor(texttc.getText(), textadsoyad.getText(), textsifre.getText());
                        if (control) {
                            Helper.showMsg("success");
                            updateDoctorTable();
                            textadsoyad.setText(null);
                            texttc.setText(null);
                            textsifre.setText(null);
                            //updateDoctorModel();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        btnekle.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnekle.setBounds(513, 201, 152, 27);
        panel.add(btnekle);

        JLabel labelkulllanıcıid = new JLabel("Kullanıcı ID:");
        labelkulllanıcıid.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelkulllanıcıid.setBounds(513, 238, 152, 27);
        panel.add(labelkulllanıcıid);

        textid = new JTextField();
        textid.setColumns(10);
        textid.setBounds(513, 263, 152, 27);
        panel.add(textid);

        JButton btnSil = new JButton("Sil");
        btnSil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int doctorId = Integer.parseInt(textid.getText());  // Doktor ID'si alınıyor

                    // Öncelikle doktorun var olup olmadığını kontrol ediyoruz
                    boolean doctorExists = false;
                    for (User doctor : bashekim.getDoctorList()) {
                        if (doctor.getId() == doctorId) {
                            doctorExists = true;
                            break;
                        }
                    }

                    if (doctorExists) {
                        // Eğer doktor mevcutsa, onay penceresi gösteriyoruz
                        int response = JOptionPane.showConfirmDialog(BashekimGUI.this, 
                                "Bu işlemi onaylamak istediğinizden emin misiniz?", 
                                "Silme Onayı", 
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null);

                        if (response == JOptionPane.YES_OPTION) {
                            // Eğer kullanıcı "Evet" derse, doktor siliniyor
                            boolean isDeleted = bashekim.deleteDoctor(doctorId);
                            if (isDeleted) {
                                Helper.showMsg("success");
                                updateDoctorTable();
                            } else {
                                Helper.showMsg("Bu Kullanıcı ID'si mevcut değil.");  // Silme başarısızsa hata mesajı
                            }
                        }
                    } else {
                        // Doktor bulunmazsa hata mesajı gösteriyoruz
                        Helper.showMsg("Bu Kullanıcı ID'si mevcut değil.");
                    }
                } catch (NumberFormatException ex) {
                    Helper.showMsg("invalid_id");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnSil.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnSil.setBounds(513, 300, 152, 27);
        panel.add(btnSil);

        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 488, 328);
        panel.add(scrollPane);
        
        

        table_doctor = new JTable(doctorModel);
        scrollPane.setViewportView(table_doctor);
        table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table_doctor.getSelectedRow();  
                    if (selectedRow != -1) {
                        Object selectedDoctorId = table_doctor.getValueAt(selectedRow, 0);  
                        textid.setText(selectedDoctorId.toString());  
                    }
                }
            }
        });
        
        /*
        public void updateDoctorModel() {
        	DefaultTableModel clearModel = table_doctor.getModel();
        	clearModel.setRowCount(0);
        	for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
            doctorData[0] = bashekim.getDoctorList().get(i).getId();
            doctorData[1] = bashekim.getDoctorList().get(i).getTc();
            doctorData[2] = bashekim.getDoctorList().get(i).getSifre();
            doctorData[3] = bashekim.getDoctorList().get(i).getAd();
            doctorModel.addRow(doctorData);
        }
        }
        */
        
        JPanel clinic1 = new JPanel();
        clinic1.setBackground(new Color(255, 255, 255));
        tabbedPane.addTab("Poliklinikler", null, clinic1, null);
        clinic1.setLayout(null);
        
        JScrollPane scroll_clinic = new JScrollPane();
        scroll_clinic.setBounds(10, 10, 250, 328);
        clinic1.add(scroll_clinic);
        
        
        
        clinicMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        clinicMenu.add(updateMenu);
        clinicMenu.add(deleteMenu);
        
        
        table_clinic = new JTable(clinicModel);
        table_clinic.setComponentPopupMenu(clinicMenu);
        table_clinic.addMouseListener(new MouseAdapter(){
        	
        	@Override
        	public void mousePressed(MouseEvent e) {
        		Point point = e.getPoint();
        		int selectedRow = table_clinic.rowAtPoint(point);
        		table_clinic.setRowSelectionInterval(selectedRow, selectedRow);
        	}
        });
        scroll_clinic.setViewportView(table_clinic);
        
        JLabel lblPoliklinikAd = new JLabel("Poliklinik Adı:");
        lblPoliklinikAd.setBounds(270, 10, 152, 27);
        lblPoliklinikAd.setFont(new Font("Tahoma", Font.PLAIN, 15));
        clinic1.add(lblPoliklinikAd);
        
        textclinicname = new JTextField();
        textclinicname.setBounds(270, 36, 152, 27);
        textclinicname.setColumns(10);
        clinic1.add(textclinicname);
        
        JButton btnekle_1 = new JButton("Ekle");
        btnekle_1.setBounds(270, 72, 152, 27);
        btnekle_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(textclinicname.getText().length()==0){
                	Helper.showMsg("fill");
                }else {
                	if(clinic1.addClinic(textclinicname.getText().toString())) {
                		Helper.showMsg("success");
                		textclinicname.setText(null);
                	}
                }
        }
        });
        
        btnekle_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        clinic1.add(btnekle_1);
        
        JScrollPane w_scrollWorker = new JScrollPane();
        w_scrollWorker.setBounds(427, 10, 250, 328);
        clinic1.add(w_scrollWorker);
    
        
    
        
     
     table_worker = new JTable();
        w_scrollWorker.setViewportView(table_worker);
        
        JComboBox select_doctor = new JComboBox();
        select_doctor.setBounds(270, 258, 152, 39);
        for(int i=0 ; i<bashekim.getDoctorList().size();i++) {
        	select_doctor.addItem(new Item(bashekim.getDoctorList().get(i).getId(), bashekim.getDoctorList().get(i).getAd()));
        }
        select_doctor.addActionListener(e -> {
        	JComboBox c= (JComboBox) e.getSource();
        	Item item =(Item) c.getSelectedItem();
        	System.out.println(item.getKey()+" : "+ item.getValue());
        	
        });
        clinic1.add(select_doctor);
        
        JButton btn_addWorker = new JButton("Ekle");
        btn_addWorker.setBounds(270, 307, 152, 27);
        btn_addWorker.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selRow = table_clinic.getSelectedRow();
        		if(selRow >= 0) {
        			String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
        			int selClinicID =Integer.parseInt(selClinic);
        			Item doctorItem=(Item) select_doctor.getSelectedItem();
        			
        			try {
						boolean control=bashekim.addWorker(doctorItem.getKey(),selClinicID);
						if(control) {
							Helper.showMsg("success");
						}
						else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
        			
        		}
        		else {
        			Helper.showMsg("Lütfen bir poliklinik seçiniz!");
        		}
        	}
        });
        btn_addWorker.setFont(new Font("Tahoma", Font.PLAIN, 18));
        clinic1.add(btn_addWorker);
        
        JButton btn_workerSelect = new JButton("Görüntüle");
        btn_workerSelect.setBounds(270, 152, 152, 33);
        btn_workerSelect.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selRow =  table_clinic.getSelectedRow();
        		if(selRow>=0){
        			String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
        			int selClinicID =Integer.parseInt(selClinic);
        			DefaultTableModel clearModel=(DefaultTableModel) table_worker.getModel();
        			clearModel.setRowCount(0);
        			
        			try {
        			    for (int i = 0; i < bashekim.getClinicDoctorList(selClinicID).size(); i++) {
        			        workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
        			        workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getAd();
        			        workerModel.addRow(workerData);
        			    }
        			} catch (SQLException e1) {
        			    e1.printStackTrace();
        			}
        			table_worker.setModel(workerModel);
        					
        		}else {
        			Helper.showMsg("Lütfen bir poliklinik seçiniz!");
        		}
        	}
        });
        btn_workerSelect.setFont(new Font("Tahoma", Font.PLAIN, 15));
        clinic1.add(btn_workerSelect);
        
        JLabel lblSeiliPolikliniinDoktorlar = new JLabel("Seçili Polikliniğin Doktorları:");
        lblSeiliPolikliniinDoktorlar.setBounds(265, 124, 171, 27);
        lblSeiliPolikliniinDoktorlar.setFont(new Font("Tahoma", Font.PLAIN, 13));
        clinic1.add(lblSeiliPolikliniinDoktorlar);
    }

    
    
    
    private void updateDoctorTable() throws SQLException {
        doctorModel.setRowCount(0); // Tabloyu temizle
        for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
            doctorData[0] = bashekim.getDoctorList().get(i).getId();
            doctorData[1] = bashekim.getDoctorList().get(i).getTc();
            doctorData[2] = bashekim.getDoctorList().get(i).getSifre();
            doctorData[3] = bashekim.getDoctorList().get(i).getAd();
            doctorModel.addRow(doctorData);
        }
    }
    
    public void updateClinicTable() {
        try {
            Clinic clinicObj = new Clinic();
            ArrayList<Clinic> clinics = clinicObj.getList(); // Klinikleri veritabanından alıyoruz
            
            // Klinikleri tablo modeline ekle
            clinicModel.setRowCount(0); // Tablodaki mevcut verileri temizle
            for (Clinic clinic : clinics) {
                clinicModel.addRow(new Object[] { clinic.getId(), clinic.getName() });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage());
        }
    }
}
