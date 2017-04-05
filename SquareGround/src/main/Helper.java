package main;

import java.awt.*;
import javax.swing.JPanel;

public final class Helper {
	
	static public String Name="Square Ground";
	static public String Version="v1.0";
	static public String name="SQ";
	
	static public Color ColorBackground=Color.LIGHT_GRAY;
	static public Color ColorString=Color.BLACK;
	
			
	
	public static JPanel createComponent(JPanel back,Component c,int x,int y,int l,int w,boolean b){
		JPanel panel=new JPanel();
		if (c!=null) panel.add(BorderLayout.CENTER,c);
		GridBagConstraints constraints=new GridBagConstraints();
		Helper.gridbagSet(constraints,x,y,l,w,1,b?1:0);
		back.add(panel,constraints);
		return panel;
	}public static void gridbagSet(GridBagConstraints g,int x,int y,int length,int width,int wx,int wy){
		g.gridx=x;
		g.gridy=y;
		g.gridwidth=length;
		g.gridheight=width;
		g.anchor=GridBagConstraints.CENTER;
		g.fill=GridBagConstraints.BOTH;
		g.weightx=wx;
		g.weighty=wy;
		g.insets=new Insets(0,0,0,0);
		g.ipadx=0;
		g.ipady=0;
	}
	
}
