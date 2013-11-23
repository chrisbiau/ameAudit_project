package dao;

import java.util.ArrayList;
import java.util.Map;


//http://cyrille-herby.developpez.com/tutoriels/java/mapper-sa-base-donnees-avec-pattern-dao/

public abstract class AbtractDAO<T> {

	/**
	 * Permet de recuperer un objet via son ID
	 * @param id
	 * @return
	 */
	public abstract T find(int id);

	/**
	 * Permet de creer une entree dans la base de donnees
	 * par rapport a un objet
	 * @param obj
	 */
	public abstract T create(T obj);

	/**
	 * Permet de mettre a jour les donnees d'une entree dans la base
	 * @param obj
	 */
	public abstract T update(T obj);

	/**
	 * Permet la suppression d'une entree de la base
	 * @param obj
	 */
	public abstract void remove(T obj);

	/**
	 * Permet de recuperer tous ID
	 */
	public abstract ArrayList<Integer> getAllIdInDataBase();

	/**
	 * Permet de recuperer tous les objets
	 */
	public abstract ArrayList<T> getAllValuesDataBase();

	/**
	 * Permet de recuperer tous les objets avec leur ID
	 */
	public abstract Map<Integer, T>  getAllDataBase();

}

