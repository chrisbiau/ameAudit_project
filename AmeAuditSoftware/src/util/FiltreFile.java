package util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltreFile extends FileFilter{
	   //Description et extension acceptée par le filtre
	   private final String description;
	   private final String extension;
	   //Constructeur à partir de la description et de l'extension acceptée
	   
	   public FiltreFile(String description, String extension){
	      if(description == null || extension ==null){
	         throw new NullPointerException("La description (ou extension) ne peut être null.");
	      }
	      this.description = description;
	      this.extension = extension;
	   }
	   
	   //Implémentation de FileFilter
	   @Override
	   public boolean accept(File file){
	      if(file.isDirectory()) { 
	         return true; 
	      } 
	      String nomFichier = file.getName().toLowerCase(); 
	 
	      return nomFichier.endsWith(extension);
	   }
	      @Override
		public String getDescription(){
	      return description;
	   }
		

	}
