package mvc.manager.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import mvc.ManagerMVC;
import mvc.common.util.DataListenerInterface;

public class ApplicationView extends JFrame{


	//	private JFrame frame;
	private final MenuBar menuBar = null;
	private final JComponent lastJpanelCenter = null;
	private JComponent lastJpanelNorth = null;
	private JComponent lastJpanelRight = null;
	private JComponent lastJpanelSouth = null;

	private final JPanel logInfoPanel = new JPanel(new BorderLayout());
	
	private final JProgressBar appProgressBar = new JProgressBar(SwingConstants.HORIZONTAL);;
	private final progressBarModelListener barModelListener = new progressBarModelListener();
	
	private final ManagerMVC managerMVC ;
	private final JTabbedPane centerTabbedPanel = new JTabbedPane();
	/**
	 * Create the application.
	 * @param managerMVC 
	 * @param allModelMVC
	 */

	public ApplicationView(final ManagerMVC managerMVC) {
		super();
		this.managerMVC =managerMVC;
		initialize();
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
//TODO: A ameliorer la sauvegarde avant de quitter
//            	int resultat=JOptionPane.showConfirmDialog(
//            			managerMVC.getApplicationView(),
//            			"Voulez vous quitter l'application ? \n",
//            			"Voulez vous quitter l'application ?",
//            			JOptionPane.OK_OPTION);

//            	if(resultat == JOptionPane.YES_OPTION){
            		if(managerMVC.getManagerController().checkSaveDialog()){
            			System.exit(0);
            		}
//            	}
            }
        });
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		managerMVC.getProgressBarValue().addListener(barModelListener);
		logInfoPanel.add(appProgressBar,BorderLayout.EAST);
		this.getContentPane().add(centerTabbedPanel, BorderLayout.CENTER);
		this.setSouthPanel(logInfoPanel);
	}




	public void setMenuPanel( JPanel menuPanel) {
		if(lastJpanelNorth != null){
			this.getContentPane().remove(lastJpanelNorth);
		}
		lastJpanelNorth = menuPanel;
		if(menuPanel != null){
			this.getContentPane().add(menuPanel, BorderLayout.NORTH);
		}
		this.validate();
		this.repaint();
	}

	public void addCenterPanel(String nameTabPanel, JComponent applicationPanel ) {
		centerTabbedPanel.add(nameTabPanel, applicationPanel);
		this.validate();
		this.repaint();
	}
	
	public void removeCenterPanel(JComponent applicationPanel ) {
//		if(centerTabbedPanel.is
		centerTabbedPanel.remove(applicationPanel);
		this.validate();
		this.repaint();
	}
	
	public void setRightPanel(JComponent panelRight) {
		if(lastJpanelRight != null){
			this.getContentPane().remove(lastJpanelRight);
		}
		lastJpanelRight = panelRight;
		if(panelRight != null){
			this.getContentPane().add(panelRight, BorderLayout.EAST);
		}
		this.validate();
		this.repaint();
	}

	private void setSouthPanel(JComponent panelSouth) {
		if(lastJpanelSouth != null){
			this.getContentPane().remove(lastJpanelSouth);
		}
		lastJpanelSouth = panelSouth;
		if(panelSouth != null){
			this.getContentPane().add(panelSouth, BorderLayout.SOUTH);
		}
		this.validate();
		this.repaint();
	}

	public JPanel getLogInfoPanel() {
		return logInfoPanel;
	}

	public JProgressBar getAppProgressBar() {
		return appProgressBar;
	}

	@Override
	public void setTitle(String title){
		super.setTitle("AME Audit - "+title);
	}

	
	private class progressBarModelListener implements DataListenerInterface {
		@Override
		public void dataChange(Object ojbUpdated) {
			JProgressBar progBarFromModel = (JProgressBar) ojbUpdated;
			appProgressBar.setMaximum(progBarFromModel.getMaximum());
			appProgressBar.setValue(progBarFromModel.getValue());
		}

	}
	
	
	

}
