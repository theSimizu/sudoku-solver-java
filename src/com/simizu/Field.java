package com.simizu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class Field extends JButton  {
	private static final long serialVersionUID = 1L;
	private int x, y, block;
	private int num = 0;
	private boolean isValid = true;
	private boolean isEditable = true;
	private JPanel panel = new JPanel();
	private JButton button = new JButton();
	private static Field placeholder = new Field();

	
	
//		Object Methods 
//	##################################################################################################
	public Field(int x, int y) {
		this.x = x;
		this.y = y;
		this.block = Math.floorDiv(x, 3) + Math.floorDiv(y, 3)*3;
		this.setFocusable(false);
		panelSettings();
		buttonSettings();
	}
	
	public Field() {
		
	}
	
	public void verifyInvalidField() {
		for (Field field : Main.fields) {
			if (field.num == 0 ||
				field.num != this.num || 
				field == this) 
				continue;
			
			if (Main.isConflict(this, field)) {
				Set<Field> confs = new HashSet<Field>();
				confs.add(this);
				confs.add(field);
				Main.conflictFields.add(confs);
				
			}
		}
	}
	
	private void panelSettings() {
		int bottom = 1, right = 1;
		if (x == 2 || x == 5) right = 5;
		if (y == 2 || y == 5) bottom = 5;
		
		Border border = new MatteBorder(1, 1, bottom, right, Color.black);
		panel.setLayout(new GridLayout(1, 1));
		panel.setBorder(border);
		panel.add(button);
	}


	private void buttonSettings() {
		button.setFocusable(false);
		button.setOpaque(true);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFont(new Font("Arial", Font.PLAIN, 40));
		button.addActionListener(e -> click());
	}
	
	public JPanel getPanel() {
		return this.panel;
	}

	
	private void click() {
//		if (!this.isEditable) return;
		Main.paintPanels();
		Main.fieldClicked = this;
		paintPanel(new Color(0xbbbbbb));
	}
	

	public int getNumber() {
		return this.num;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getBlock() {
		return this.block;
	}
	
	public boolean getValid() {
		return this.isValid;
	}
	
	public void setValid(boolean valid) {
		this.isValid = valid;
	}
	
	public boolean getEditability() {
		return this.isEditable;
	}
	
	public void setUneditable() {
		this.isEditable = false;
		this.paintPanel(new Color(0x999999));
		
	}

	
	public void setNumber(int num) {
//		if (!this.isEditable) return;
		this.num = num;
		this.verifyInvalidField();
		
		if (num == 0) {
			button.setText("");
			this.isEditable = true;
		}
		else {
			button.setText("" + num);
			this.isEditable = false;
		}

		Main.checkIfFieldsWereCorrected();
		Main.setValid();
		Main.paintPanels();
		Main.fieldClicked = placeholder;


	}
		
	public void paintPanel(Color color) {
		this.panel.setBackground(color);
	}
	
	public boolean isConflicting() {
		for (Field field : Main.fields) {
			if (field == this) continue;
			if (Main.isConflict(this, field)) return true;
		}
		return false;
	}
	
	public boolean tryNextNum() {
		this.num++;
		button.setText("" + num);
		if (this.num > 9) {
			this.num = 0;
			return false;
		}
		return true;
	}
	
	
	
	
//		Static methods
//	#####################################################################################################

	
}
