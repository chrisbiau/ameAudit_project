package mvc.form.view;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import mvc.ManagerMVC;

import org.apache.log4j.Logger;

import start.AppProgressBar;
import data.Query;

public class AnswerTreeViewJPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private final JTree tree;
	private final ManagerMVC managerMVC;

	private static Logger logger = Logger.getLogger(AnswerTreeViewJPanel.class);

	public AnswerTreeViewJPanel(ManagerMVC managerMVC, ArrayList<Query> listQuery) {
		super(new BorderLayout());
		this.managerMVC=managerMVC;
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Liste questions: ");

		//Trie par numero de question 
		Map<Integer, Query> treeMapQuery = new TreeMap<Integer, Query>();
		for(Query query : listQuery){
			treeMapQuery.put(query.getId(), query);
		}

		//
		for(Entry<Integer, Query> entry : treeMapQuery.entrySet()){
			//			int idQuery= entry.getKey();
			Query query = entry.getValue();

			DefaultMutableTreeNode rootQuery = new DefaultMutableTreeNode(query);
			DefaultMutableTreeNode containerAnswerPanel = new DefaultMutableTreeNode(new AnswersTacPanel(managerMVC, query));
			rootQuery.add(containerAnswerPanel);
			root.add(rootQuery);
			AppProgressBar.getInstance().setOneMoreProgressBar();
		}

		tree = new JTree(root);
		tree.setEditable(true);
		tree.setRootVisible(true); //TODO : A ajouter dans les options
		tree.setCellRenderer(new MyTableInTreeCellRenderer());
		tree.setRowHeight(0);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellEditor(new MyTreeCellEditor(tree, (DefaultTreeCellRenderer) tree.getCellRenderer()));
		TreeListener treeListener= new TreeListener();
		tree.addMouseListener(treeListener);
		JScrollPane jsp = new JScrollPane(tree);
		add(jsp );
	}



	private class MyTableInTreeCellRenderer extends DefaultTreeCellRenderer   {

		public MyTableInTreeCellRenderer() {
			super();
		}

		@Override
		public Component getTreeCellRendererComponent(
				JTree tree,
				Object value,
				boolean sel,
				boolean expanded,
				boolean leaf,
				int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			final Object obj = ((DefaultMutableTreeNode) value).getUserObject();

			JComponent comp = null;
			if(obj != null){
				if(obj instanceof AnswersTacPanel){
					comp= (JComponent) obj;
				}
				else if(obj instanceof Query){
					Query query = (Query) obj;
					this.setText("["+query.getId()+"] "+query.getQueryText()+" ?");
					if(!managerMVC.getModelToSave().getResultByQueryId().getValue().containsKey(query.getId())){
						this.setFont(getFont().deriveFont(Font.BOLD));
					}else{
						this.setFont(getFont().deriveFont(Font.ITALIC));
					}
					
					comp = this;
				}else if(obj instanceof String){
					this.setFont(getFont().deriveFont(Font.ROMAN_BASELINE));
					comp = this;
				}else{
					logger.warn("obj not define");
				}
			}

			return comp;
		}
	}



	private class MyTreeCellEditor extends DefaultTreeCellEditor
	{
	
		public MyTreeCellEditor ( JTree tree, DefaultTreeCellRenderer renderer )
		{
			super(tree, renderer);
		}

		@Override
		public Component getTreeCellEditorComponent ( JTree tree, Object obj, boolean isSelected, boolean expanded, boolean leaf, int row )
		{
			Component comp = super.getTreeCellEditorComponent(tree, obj, isSelected, expanded, leaf, row);
			if(obj != null){
				if(leaf){
					comp= renderer.getTreeCellRendererComponent(tree, obj, true, expanded, leaf, row, true);;
				}
			}
			return comp;
		}
		@Override
		public boolean isCellEditable ( EventObject anEvent )
		{
			boolean returnValue = false;
			if (anEvent instanceof MouseEvent) {
				MouseEvent mouseEvent = (MouseEvent) anEvent;
				TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
				if (path != null) {
					Object node = path.getLastPathComponent();
					if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
						DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
						Object userObject = treeNode.getUserObject();
						returnValue = ((treeNode.isLeaf()) && (userObject instanceof AnswersTacPanel));
					}
				}
			}
			return returnValue;
		}
	}
	
	
	private class TreeListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			TreePath selec = tree.getSelectionPath();
			if(tree.getSelectionRows().length >1 ){
				int selecrowm = tree.getSelectionRows()[0];
				for (int i = tree.getRowCount() - 1; i >= 0; i--) {
					tree.collapseRow(i);
				}
				tree.expandPath(selec);
				tree.setSelectionRow(selecrowm);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

}
