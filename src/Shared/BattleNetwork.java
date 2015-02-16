package Shared;

import javax.swing.JPanel;

import Model.Model;
import View.View;

public class BattleNetwork extends JPanel {

//	private View view;
//	private Model model;

	public BattleNetwork(){
		
		Model tModel = new Model();
		View tView = new View();
		
		tView.setModel(tModel);
		tModel.setView(tView);
	}
	
	public static void main(String[] args) {
		new BattleNetwork();
	}

}
