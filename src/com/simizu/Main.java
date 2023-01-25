package com.simizu;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame implements KeyListener {	
	private static final long serialVersionUID = 1L;
	private static final int scale = 2;
	private static final int width = 500*scale;
	private static final int height = 480*scale;
	
	public static Field fieldClicked;
	public static Field[] fields = new Field[81];
	public static Set<Set<Field>> conflictFields = new HashSet<Set<Field>>();

	
	public static void main(String[] args) {
		Main.setFieldsArray();
		Main frame = createFrame();
		JPanel table = createTable();
		Solver solver = new Solver(fields);
		JButton butt = new JButton("Solve");
		butt.setBounds(50, height-100, 200, 50);
		butt.setBackground(new Color(0x00ff00));
		butt.addActionListener(e -> solver.solve());
		frame.add(table);
		frame.add(butt);
		frame.add(new JButton("Teste1"));
		
//		createGame(1);
//		solver.solve();
	}

	public static Main createFrame() {
		Main frame = new Main();
		frame.setFocusable(true);
		frame.addKeyListener(frame);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		return frame;
	}
	
	public static JPanel createTable() {
		JPanel table = new JPanel();
		table.setBounds(0, 0, (int)(width/1.5), (int)(height/1.5));
		table.setLayout(new GridLayout(9, 9));
		for (Field field : Main.fields) table.add(field.getPanel());
		return table;
	}
	
//	public static void createGame(int difficult) {
//		Set<Field> setFields = new HashSet<Field>(Arrays.asList(fields));
//		Random generator = new Random();
//		int i = 0;
//		for (Field field : setFields) {
////			field.isEd
//			
//			field.setNumber(generator.nextInt(1, 9));
//			while (field.isConflicting()) {
//				field.setNumber(generator.nextInt(1, 9));
//			}
//			field.setUneditable();
//			if (i == 27) return;
//			i++;
//		}
//		
//		
//	}
	
	public static void setFieldsArray() {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				int index = x + 9*y;
				fields[index] = new Field(x, y);

			}
		}
	}
	
	public static boolean isConflict(Field field1, Field field2) {
		return (field1.getNumber() == field2.getNumber() && 
			   (field1.getX() == field2.getX() || 
				field1.getY() == field2.getY() || 
				field1.getBlock() == field2.getBlock()));
	}
	
	public static void checkIfFieldsWereCorrected() {
		Set<Set<Field>> conflictFieldsCopy = new HashSet<Set<Field>>(Main.conflictFields);
		for (Set<Field> conflictPair : conflictFieldsCopy) {
			List<Field> conflictList = new ArrayList<Field>(conflictPair);
			if (!isConflict(conflictList.get(0), conflictList.get(1))) {
				Main.conflictFields.remove(conflictPair);
			}
			
		}
	}
	
	public static void setValid() {
		Set<Field> invalidFields = new HashSet<Field>();
		for (Set<Field> conflict : Main.conflictFields) {
			for (Field field : conflict) {
				invalidFields.add(field);
			}
		}
		for (Field field : Main.fields) {
			field.setValid(!invalidFields.contains(field));

		}
	}
	
	public static void paintPanels() {
		for (Field field : fields) {
			if (!field.getEditability()) continue;
			if (field.getValid()) field.paintPanel(new Color(0xffffff));
			else field.paintPanel(new Color(0xff0000));
			

		}
	}
	
	
	
	
	
	

	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println(e.getKeyCode());
		int keyCode = e.getKeyCode();

		if 	   ((49 <= keyCode && keyCode <= 57) || // Number Keys
				(97 <= keyCode && keyCode <= 105)|| // NumLock number keys
						   		  keyCode == 8) {   // Backspace
			
			int value;
			if (e.getKeyCode() == 8) value = 0;
			else {
				String key = KeyEvent.getKeyText(e.getKeyCode());
				value = Character.getNumericValue(key.charAt(key.length()-1));
			}
			Main.fieldClicked.setNumber(value);
			

		}
		

		
	}
	


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
