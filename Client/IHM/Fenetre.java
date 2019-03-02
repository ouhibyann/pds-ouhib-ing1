
package ihm;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;

public class Fenetre extends JFrame {

	
	JPanel titre = new JPanel();
	private JButton jbProfil = new JButton("Profils");
	private JTextPane showProfil = new JTextPane();
	private JScrollPane sbProfil = new JScrollPane(showProfil);
	private static final long serialVersionUID = -4897594027287541463L;
	private JTabbedPane crud = new JTabbedPane();
	private JPanel print = new JPanel();
	

	public Fenetre() {
		
		this.setTitle("pds-I1-dette");
		this.setSize(1200, 1080);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.add(titre, BorderLayout.NORTH);
		this.setVisible(true);
		this.getContentPane().add(sbProfil, BorderLayout.CENTER);
		this.getContentPane().add(print, BorderLayout.CENTER);
		this.getContentPane().add(crud, BorderLayout.CENTER);
		
		
		sbProfil.setPreferredSize(new Dimension(100, 100));
		print.setPreferredSize(new Dimension(900, 550));

		print.setLayout(new BoxLayout(print, BoxLayout.X_AXIS));
		print.add(sbProfil);
		print.add(jbProfil);
		crud.addTab("Profils", print);
		
		JPanel titre = new JPanel();
		TitledBorder border = new TitledBorder("M@m3N");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitleColor(Color.red);
		titre.setBorder(border);

		
	}
	
	

	
	
	public JButton getJbProfil() {
		return jbProfil;
	}
	public void setJbProfil(JButton jbProfil) {
		this.jbProfil = jbProfil;
	}
	public JScrollPane getSbProfil() {
		return sbProfil;
	}
	public void setSbProfil(JScrollPane sbProfil) {
		this.sbProfil = sbProfil;
	}
	public JTextPane getshowProfil() {
		return showProfil;
	}
	public void setshowProfil(JTextPane showProfil) {
		this.showProfil = showProfil;
	}
	
}
