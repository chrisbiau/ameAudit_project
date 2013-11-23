package dao.controller;

import dao.sql.data.AnswerDAO;
import dao.sql.data.AuditDAO;
import dao.sql.data.ColorDAO;
import dao.sql.data.CrecheDAO;
import dao.sql.data.GridDAO;
import dao.sql.data.InputDialogDAO;
import dao.sql.data.NumericRulesDAO;
import dao.sql.data.QueryDAO;
import dao.sql.data.RoomDAO;
import dao.sql.data.TopicDAO;
import data.Answer;
import data.Audit;
import data.Color;
import data.Creche;
import data.Grid;
import data.InputDialog;
import data.NumericRules;
import data.Query;
import data.Room;
import data.Topic;

public class AllControllerDAO {

	public AllControllerDAO() {
	}

	public DaoController<Answer> getAnswerControllerDao() {
		return new DaoController<Answer>(new AnswerDAO(this));
	}

	public DaoController<Grid> getGridControllerDao() {
		return new DaoController<Grid>(new GridDAO(this));
	}

	public DaoController<Audit> getAuditControllerDao() {
		return new DaoController<Audit>(new AuditDAO(this));
	}

	public DaoController<Color> getColorControllerDao() {
		return new DaoController<Color>(new ColorDAO(this));
	}

	public DaoController<InputDialog> getInputDialogControllerDao() {
		return new DaoController<InputDialog>(new InputDialogDAO(this));
	}

	public DaoController<NumericRules> getNumericRulesControllerDao() {
		return new DaoController<NumericRules>(new NumericRulesDAO(this));
	}

	public DaoController<Query> getQueryControllerDao() {
		return new DaoController<Query>(new QueryDAO(this));
	}

	public DaoController<Room> getRoomControllerDao() {
		return new DaoController<Room>(new RoomDAO(this));
	}

	public DaoController<Topic> getTopicControllerDao() {
		return new DaoController<Topic>(new TopicDAO(this));
	}

	public DaoController<Creche> getCrecheControllerDao() {
		return new DaoController<Creche>(new CrecheDAO(this));
	}


}
