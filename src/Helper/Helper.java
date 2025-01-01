package Helper;

import javax.swing.JOptionPane;

public class Helper {
    
    // Confirm metodu
    public static boolean confirm(String message) {
        int option = JOptionPane.showConfirmDialog(null, message, "Onay", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return option == JOptionPane.YES_OPTION;
    }
    
    // ShowMsg metodu
    public static void showMsg(String str) {
        String msg;
        
        switch(str) {
        case "fill":
            msg = "Lütfen tüm alanları doldurunuz!";
            break;
        case "success":
            msg = "İşlem Başarılı.";
            break;
        case "error":
            msg = "Bir hata oluştu. Lütfen tekrar deneyiniz.";
            break;
        default:
            msg = str; // Kendi mesajını göster
        }
        
        JOptionPane.showMessageDialog(null, msg, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
