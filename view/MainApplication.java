//package view;
//import java.awt.event.KeyEvent;
//
//import javax.swing.JFrame;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.KeyStroke;
//import javax.swing.SwingUtilities;
//
//public class MainApplication extends JFrame { 
//	private HelpDialog helpDialog;
//    
//    public MainApplication() {
//        setTitle("Ứng dụng của tôi");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        helpDialog = new HelpDialog(this, "Trợ giúp");
//        
//        // Tạo menu
//        JMenuBar menuBar = new JMenuBar();
//        JMenu helpMenu = new JMenu("Help");
//        JMenuItem helpItem = new JMenuItem("Hiển thị help");
//        
//        helpItem.addActionListener(e -> showHelp());
//        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
//        
//        helpMenu.add(helpItem);
//        menuBar.add(helpMenu);
//        setJMenuBar(menuBar);
//    }
//    
//    private void showHelp() {
//        helpDialog.setVisible(true);
//    }
//    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new MainApplication().setVisible(true);
//        });
//    }
//}