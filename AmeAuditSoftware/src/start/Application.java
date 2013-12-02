package start;

import java.io.File;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import mvc.ManagerMVC;
import mvc.manager.view.ApplicationView;

import org.apache.log4j.Logger;

import properties.PropertiesLoader;
import util.FileHelper;
import util.UniqueInstance;

public class Application {
	private static Logger logger = Logger.getLogger(Application.class);
	private static String sep = File.separator;
	private static ApplicationView mainFrame = null;


	// Port à utiliser pour communiquer avec l'instance de l'application lancée.
	final static int PORT = 32145;
	// Message à envoyer à l'application lancée lorsqu'une autre instance essaye de démarrer.
	final static String MESSAGE = "AmeAuditSoftware";
	// Actions à effectuer lorsqu'une autre instance essaye de démarrer.
	final static Runnable RUN_ON_RECEIVE = new Runnable() {
		@Override
		public void run() {
			// On exécute ce runnable dans l'EDT
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if(mainFrame != null) {
						// Si la fenêtre n'est pas visible (uniquement dans le systray par exemple), on la rend visible.
						if(!mainFrame.isVisible())
							mainFrame.setVisible(true);
						// On demande à la mettre au premier plan.
						mainFrame.toFront();
					}
				}
			});
		}                   
	};



	public static void main(String[] args) {

		System.setProperty("file.encoding", "\"UTF-8\"");
		Locale.setDefault(Locale.FRANCE);

		//Load Properties file
		String propertiesPath = "properties"+sep+"config.properties";
		if(FileHelper.testFileExist(propertiesPath) ){
			PropertiesLoader.setPropertiesFiles(propertiesPath);

			UniqueInstance uniqueInstance = new UniqueInstance(PORT, MESSAGE, RUN_ON_RECEIVE);
			// Si aucune autre instance n'est lancée...
			if(uniqueInstance.launch()) {
				// On démarre l'application.
				ManagerMVC mvc = new ManagerMVC();
				mainFrame = mvc.getApplicationView();
			}else{
				JOptionPane.showMessageDialog(
						mainFrame,
						"L'application est déjà lancée.",
						"Une instance de l'application existe déjà",
						JOptionPane.ERROR_MESSAGE);
				logger.warn("Une instance de l'application existe déjà");
			}
		}else{
			JOptionPane.showMessageDialog(
					mainFrame,
					"Impossible de charger le fichier suivant :\n"
							+propertiesPath+
							" fichier introuvable",
							"Impossible de charger le fichier de properties",
							JOptionPane.ERROR_MESSAGE);
			logger.error("Properties file: "+ propertiesPath + "doesn't exit");
		}



	}


}
