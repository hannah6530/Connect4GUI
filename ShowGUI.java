
package c4_GUI;

import gui_simple.MyFirstJFrame;

public class ShowGUI {


	public static void main(String[] args) {
		System.out.println("test test");
		
		javax.swing.SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run() {
			      ConnectFourGui gui = new ConnectFourGui();
			}
		});
	}

}
