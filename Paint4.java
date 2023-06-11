package daihatikai;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Paint4 extends Frame implements MouseListener,MouseMotionListener, ActionListener {

	int x, y;
	ArrayList<Figure> objList; // 描画する全オブジェクトを管理する
	CheckboxGroup cbg,color,fill;  // メニュー
	Checkbox c1, c2, c3, c4,c5,c6,red,blue,green,black,yes,no; // メニューの要素
	Button end; // 終了ボタン
	Button save1;//保存ボタン
	Button open;//読み込みボタン
	Button Undo;//図形削除
	Button AllUndo;//全図形削除
	static int num,change;
	int mode = 0; // 描画モード (1: 1 点指定図形 2: 2 点指定図形)
	Figure obj; // 実際に描画するオブジェクト
	public static void main(String[] args){
		Paint4 f = new Paint4();
		f.setSize(640,480);
		f.setTitle("Paint Sample");
		f.addWindowListener(new WindowAdapter(){ //ウィンドウを閉じたとき
			@Override public void windowClosing(WindowEvent e){
				System.exit(0);
			}});
		f.setVisible(true); // ウィンドウの表示

		if(args.length == 1) f.load(args[0]); // ファイル読み込み
	}
	Paint4(){
		objList = new ArrayList<Figure>();
		setLayout(null);
		// 画面作成
		cbg = new CheckboxGroup(); // Checkbox の集合を作成
		c1 = new Checkbox("丸", cbg, true); // 「丸」メニューの作成
		c1.setBounds(560, 30, 60, 30); // 「丸」メニューの座標設定
		add(c1); // 「丸」メニューの追加
		c2 = new Checkbox("円", cbg, false); // 「円」メニューの作成
		c2.setBounds(560, 60, 60, 30); // 「円」メニューの座標設定
		add(c2); // 「円」メニューの追加
		c3 = new Checkbox("四角", cbg, false);// 「四角」メニューの作成
		c3.setBounds(560, 90, 60, 30); // 「四角」メニューの座標設定
		add(c3); // 「四角」メニューの追加
		c4 = new Checkbox("線", cbg, false); // 「線」メニューの作成
		c4.setBounds(560, 120, 60, 30);  // 「線」メニューの座標設定
		add(c4); // 「線」メニューの追加
		c5 = new Checkbox("三角", cbg, false); // 「三角」メニューの作成
		c5.setBounds(560, 150, 60, 30);  // 「三角」メニューの座標設定
		add(c5); // 「三角」メニューの追加
		c6 = new Checkbox("楕円", cbg, false); // 「楕円」メニューの作成
		c6.setBounds(560, 180, 60, 30);  // 「楕円」メニューの座標設定
		add(c6); // 「楕円」メニューの追加

		end = new Button("終了");  // 「終了」ボタンの作成
		end.setBounds(560, 280, 60, 30); // 「終了」ボタンの座標設定
		add(end);  // 「終了」メニューの追加
		save1 = new Button("保存");//保存ボタンの作成
		save1.setBounds(560,250,60,30);// 「保存」ボタンの座標設定
		add(save1);// 「保存」メニューの追加
		open = new Button("読み込み");//読み込みボタンの作成
		open.setBounds(560,220,60,30);// 「読み込み」ボタンの座標設定
		add(open);// 「読み込み」メニューの追加
		Undo = new Button("削除");//削除ボタンの作成
		Undo.setBounds(560,310,60,30);// 「削除」ボタンの座標設定
		add(Undo);// 「削除」メニューの追加
		AllUndo = new Button("全削除");//全削除ボタンの作成
		AllUndo.setBounds(560,340,60,30);// 「全削除」ボタンの座標設定
		add(AllUndo);// 「全削除」メニューの追加

		color = new CheckboxGroup(); // Checkbox の集合を作成
		black = new Checkbox("黒",color,true);//「黒」メニューの作成
		black.setBounds(500,30,60,30);//「黒」メニューの座標設定
		add(black);//「」メニューの追加
		red = new Checkbox("赤",color,false);//「赤」メニューの作成
		red.setBounds(500,60,60,30);//「赤」メニューの座標設定
		add(red);//「」メニューの追加
		blue = new Checkbox("青",color,false);//「青」メニューの作成
		blue.setBounds(500,90,60,30);//「青」メニューの座標設定
		add(blue);//「」メニューの追加
		green = new Checkbox("緑",color,false);//「緑」メニューの作成
		green.setBounds(500,120,60,30);//「緑」メニューの座標設定
		add(green);//「」メニューの追加

		fill=new CheckboxGroup();//塗りつぶしのon,off
		yes = new Checkbox("塗る",fill,false);
		yes.setBounds(470,150,60,30);
		add(yes);
		no = new Checkbox("塗らない",fill,true);
		no.setBounds(470,180,60,30);
		add(no);
		// マウス処理の登録
		addMouseListener(this);
		addMouseMotionListener(this);
		// 終了ボタン処理の登録
		end.addActionListener(this);
		save1.addActionListener(this);
		open.addActionListener(this);
		Undo.addActionListener(this);
		AllUndo.addActionListener(this);
	}

	public void save(String fname){
		try {
			FileOutputStream fos = new FileOutputStream(fname);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(objList);
			oos.close();
			fos.close();
		} catch(IOException e){
		}
	}
	@SuppressWarnings("unchecked")
	public void load(String fname){
		try {
			FileInputStream fis = new FileInputStream(fname);
			ObjectInputStream ois = new ObjectInputStream(fis);
			objList = (ArrayList<Figure>)ois.readObject();
			ois.close();
			fis.close();
		} catch(IOException e){
		} catch(ClassNotFoundException e){
		}
		repaint(); // 再描画
	}
	@Override public void paint(Graphics g){
		Figure f;
		for(int i = 0; i <objList.size(); i++){ //objList の全要素について
			f= objList.get(i); // i 番目の要素取得．
			f.paint(g); // その要素の paint メソッド呼び出し
		}
		if(mode >= 1) obj.paint(g); // 現在作成中の要素も描画
	}
	@Override public void actionPerformed(ActionEvent e){//終了ボタンと保存ボタン

		if( e.getSource()==open) {
			System.out.println("読み込みました");
			load("paint.dat");
		}else if(e.getSource()==end) {
			System.out.println("終了します");
			System.exit(0);
		}else if(e.getSource()==save1) {
			save("paint.dat");
			System.out.println("保存しました");
		}else if(e.getSource()==Undo) {
			objList.remove(objList.size()-1);
			repaint();
			System.out.println("削除しました");
		}else if(e.getSource()==AllUndo) {
			objList.removeAll(objList);
			repaint();
			System.out.println("全削除しました");
		}

	}

	@Override public void mousePressed(MouseEvent e){// マウスボタン押下
		Checkbox c;
		Checkbox co;
		Checkbox f;

		x = e.getX();
		y = e.getY();
		c = cbg.getSelectedCheckbox();// 選択されたチェックボックスの取得
		co =color.getSelectedCheckbox();
		f=fill.getSelectedCheckbox();
		obj = null;  // obj をなにも参照していない状態に

		if(f==yes) {
			Paint4.change=0;
		}else if(f==no) {
			Paint4.change=1;
		}

		if(co==black) {//黒
			Paint4.num=0;
		}else if(co==red) {//赤
			Paint4.num=1;
		}else if(co==blue) {//青
			Paint4.num=2;
		}else if(co==green) {//緑
			Paint4.num=3;
		}

		if(c == c1) {// 丸
			mode =1;
			obj = new Dot();
		} else if(c == c2) { // 円
			mode = 2;
			obj = new Circle();
		} else if(c == c3) { // 四角
			mode = 2;
			obj = new Rect();
		} else if(c == c4) { // 線
			mode = 2;
			obj = new Line();
		}else if(c==c5) {//三角
			mode=1;
			obj=new Polygon();
		}else if(c==c6) {//楕円
			mode=2;
			obj=new Ellipse();
		}

		if(obj != null){
			obj.moveto(x,y);
			repaint();  // 再描画
		}
	}
	@Override public void mouseReleased(MouseEvent e){//マウスボタンが離された
		x = e.getX();
		y = e.getY();
		if(mode == 1)  obj.moveto(x, y);
		else if(mode == 2)  obj.setWH(x - obj.x, y - obj.y);
		if(mode >= 1){
			objList.add(obj);
			obj = null;
		}
		mode= 0;
		repaint();
	}
	@Override public void mouseClicked(MouseEvent e){}// クリックされた
	@Override public void mouseEntered(MouseEvent e){}// Window に入った
	@Override public void mouseExited(MouseEvent e){} // Window から出た
	@Override public void mouseDragged(MouseEvent e){ // ボタンを押したまま移動
		x = e.getX();
		y = e.getY();
		if(mode == 1){
			obj.moveto(x, y);
		} else if(mode == 2){
			obj.setWH(x -obj.x, y - obj.y); // 幅と高さの設定
		}
		repaint();
	}
	@Override public void mouseMoved(MouseEvent e){} // 移動
}