package daihatikai;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;


public class Coord implements Serializable{
	int x, y,size;
	Coord(){
		x = y = 0;
	}
	public void move(int dx, int dy){
		x += dx;
		y += dy;
	}
	public void moveto(int x, int y){
		this.x = x;
		this.y = y;
	}
}

class Figure extends Coord {
	int color,num,change;
	public int width = 1;
	int w, h; // 図形の幅と高さ
	Figure(){
		color =Paint4.num;
		change=Paint4.change;
		w=h= 0;
	}
	public void paint(Graphics g){//色の変換
		if(color==0) {
			g.setColor(Color.black);
		}else if(color==1) {
			g.setColor(Color.red);
		}else if(color==2) {
			g.setColor(Color.blue);
		}else if(color==3) {
			g.setColor(Color.green);
		}


	} // このクラスでは空，オーバーライド用
	public void setWH(int w, int h){ // 2 点指定の図形用
		this.w = w;
		this.h = h;
	}
}

class Dot extends Figure{
	Dot(){
		size =10;
	}
	@Override public void paint(Graphics g){
		super.paint(g);
		if(change==0) {
			g.fillOval(x - size/2, y - size/2, size, size);
		}else if(change==1) {
			g.drawOval(x - size/2, y - size/2, size, size);
		}
	}
}

class Polygon extends Figure{
	Polygon(){
	}
	@Override public void paint(Graphics g){
		super.paint(g);
		if(change==0) {
			g.fillPolygon(new int[] {x,x+25,x-25}, new int[] {y,y+50,y+50}, 3);
		}else if(change==1) {
			g.drawPolygon(new int[] {x,x+25,x-25}, new int[] {y,y+50,y+50}, 3);
		}
	}
}

class Circle extends Figure {
	Circle(){}
	@Override public void paint(Graphics g){
		int r = (int)Math.sqrt((double)(w * w + h * h)); // 半径
		super.paint(g);
		if(change==0) {
			g.fillOval(x - r, y - r, r *2 , r * 2);
		}else if(change==1) {
			g.drawOval(x - r, y - r, r *2 , r * 2);
		}
	}
}

class Ellipse extends Figure {
	Ellipse(){}
	@Override public void paint(Graphics g){
		int n = (int)Math.sqrt((double)(2)); 
		super.paint(g);
		if(change==0) {
			if(w<0&&h<0) {
				g.fillOval(x - n*-w, y - n*-h, w *-2 , h * -2);
			}else if(w<0) {
				g.fillOval(x - n*-w, y - n*h, w *-2 , h * 2);
			}else if(h<0) {
				g.fillOval(x - n*w, y - n*-h, w *2 , h * -2);
			}else {
				g.fillOval(x - n*w, y - n*h, w *2 , h * 2);
			}
		}else if(change==1) {
			if(w<0&&h<0) {
				g.drawOval(x - n*-w, y - n*-h, w *-2 , h * -2);
			}else if(w<0) {
				g.drawOval(x - n*-w, y - n*h, w *-2 , h * 2);
			}else if(h<0) {
				g.drawOval(x - n*w, y - n*-h, w *2 , h * -2);
			}else {
				g.drawOval(x - n*w, y - n*h, w *2 , h * 2);
			}
		}
	}
}

class Rect extends Figure { // 四角クラス
	Rect(){}
	@Override public void paint(Graphics g){
		super.paint(g);
		if(change==0) {
			if(w<0&&h<0) {
				g.fillRect(x+w, y+h, -w, -h);
			}else if(w<0) {
				g.fillRect(x+w, y, -w, h);
			}else if(h<0) {
				g.fillRect(x, y+h, w, -h);
			}else {
				g.fillRect(x, y, w, h);
			}
		}else if(change==1) {
			if(w<0&&h<0) {
				g.drawRect(x+w, y+h, -w, -h);
			}else if(w<0) {
				g.drawRect(x+w, y, -w, h);
			}else if(h<0) {
				g.drawRect(x, y+h, w, -h);
			}else {
				g.drawRect(x, y, w, h);
			}
		}
	}
}

class Line extends Figure { // 線クラス
	Line(){}
	@Override public void paint(Graphics g){
		super.paint(g);
		g.drawLine(x,y, x + w, y + h);
	}
}