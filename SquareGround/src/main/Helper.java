package main;

import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

public final class Helper {
	
	static public String Name="Square Ground";
	static public String Version="v1.0";
	static public String name="SQ";
	
	static public Color ColorBackground=Color.LIGHT_GRAY;
	static public Color ColorString=Color.BLACK;
	static public Color ColorBoardEdge=Color.DARK_GRAY;
	static public java.util.List<Color> ColorPlayer=new ArrayList<Color>();
	static public java.util.List<Color> ColorPlayer_road=new ArrayList<Color>();
	
	static{
		Color[] tmp=new Color[]{
				new Color(220,20,60),	new Color(255,105,180),	new Color(218,112,214),	new Color(238,130,238),	new Color(139,0,139),
				new Color(148,0,211),	new Color(138,43,226),	new Color(106,90,205),	new Color(248,248,255),	new Color(25,25,112),
				new Color(65,105,225),	new Color(119,136,153),	new Color(240,248,255),	new Color(135,206,235),	new Color(176,224,230),
				new Color(225,255,255),	new Color(0,255,255),	new Color(0,139,139),	new Color(32,178,170),	new Color(0,250,154),
				new Color(60,179,113),	new Color(144,238,144),	new Color(50,205,50),	new Color(0,128,0),		new Color(124,252,0),
				new Color(107,142,35),	new Color(255,255,224),	new Color(189,183,107),	new Color(240,230,140),	new Color(218,165,32),
				new Color(245,222,179),	new Color(255,239,213),	new Color(250,235,215),	new Color(255,228,196),	new Color(205,133,63),
				new Color(210,105,30),	new Color(160,82,45),	new Color(255,69,0),	new Color(255,228,225),	new Color(240,128,128),
				new Color(255,0,0),		new Color(139,0,0),		new Color(245,245,245),	new Color(192,192,192),	new Color(105,105,105)
		};
		for(Color c:tmp) ColorPlayer.add(c);
		for(Color c:tmp) ColorPlayer_road.add(new Color(c.getRed(),c.getGreen(),c.getBlue(),155));
		Collections.shuffle(ColorPlayer);
	}
	
			
	
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
