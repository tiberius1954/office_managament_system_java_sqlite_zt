package classes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.JTable.PrintMode;
import javax.swing.border.AbstractBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DateFormatter;
import classes.Task;

public class Hhelper {
	public LineBorder line = new LineBorder(Color.blue, 1, true);
	public LineBorder linep = new LineBorder(Color.red, 1, true);
	public LineBorder lineo = new LineBorder(Color.orange, 2, true);
	public Border borderz = BorderFactory.createLineBorder(Color.GREEN, 2);
	public Border borderp = BorderFactory.createLineBorder(Color.RED, 2);
	public Border borderf = BorderFactory.createLineBorder(Color.BLACK, 1);
	public Border borderf2 = BorderFactory.createLineBorder(Color.BLACK, 2);
	public Border border3 = BorderFactory.createLineBorder(new Color(135, 135, 135), 1);

	public Border myRaisedBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.BLACK);

	public Color neonzold = new Color(102, 255, 102);
	public Color zold = new Color(24, 189, 79);
	public Color tablavszurke = new Color(220, 222, 227);
	public Color tegla = new Color(249, 123, 62);
	public Color cian = new Color(0, 153, 102);
	public Color kzold = new Color(112, 171, 105);
	public Color vpiros = new Color(255, 91, 30);
	public Color svoros = new Color(199, 17, 17);
	public Color vzold = new Color(0, 141, 54);
	public Color vvzold = new Color(85, 240, 86);
	public Color vvvzold = new Color(164, 253, 123);
	public Color szold = new Color(12, 78, 12);
	public Color piros = new Color(249, 73, 58);
	public Color kekes = new Color(21, 44, 82);
	public Color vkek = new Color(32, 165, 255);
	public Color vvkek = new Color(225, 234, 243);
	public Color vvlilla = new Color(127, 123, 242);
	public Color vvvkek = new Color(175, 201, 207);
	public Color narancs = new Color(254, 179, 0);
	public Color homok = new Color(200, 137, 60);
	public Color vbarna = new Color(223, 196, 155);
	public Color narancs1 = new Color(251, 191, 99);
	public Color narancs2 = new Color(249, 105, 14);
	public Color lnarancs = new Color(255, 142, 1);
	public Color feher = new Color(255, 255, 255);
	public Color sarga = new Color(252, 210, 1);
	public Color skek = new Color(1, 1, 99);
	public Color lpiros = new Color(255, 102, 75);
	public Color piroska = new Color(213, 78, 97);
	public Color sotetkek = new Color(1, 50, 67);
	public Color vpiros1 = new Color(255, 91, 30);
	public Color slilla = new Color(103, 97, 224);
	public Color fekete = new Color(0, 0, 0);
	public Color warning = new Color(243, 95, 38);
	public Color neonzold1 = new Color(0, 248, 144);
	public Color neonzold2 = new Color(16, 254, 20);

	public Border borders = BorderFactory.createLineBorder(Color.yellow, 2);
	public Font textf = new Font("Tahoma", Font.BOLD, 16);
	public Font textf1 = new Font("Tahoma", Font.BOLD, 12);

	private String[] years = { " ", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028" };
	private String[] months = { " ", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
	private String[] statuss = { " ", "Normal", "Overtime", "Half-time", "Full-time", "Part-time", "Homeoffice",
			"Remote" };
	private String[] leaves = { " ", "Sick", "Unpaid", "Casual", "Family", "Medical", "Vacation", "Study", "Time off",
			"Military" };
	private String[] priorities = {" ", "High","Medium","Low"};
	private String[] taskstatus = {" ","Completed","Waiting","In progress","Cancelled","Finish","Not started", "On hold"};
	
	public String[] tstatus() {
		Arrays.sort(taskstatus);		
		return taskstatus;
	}
	
	public String[] prior() {
		return priorities;
	}

	public String[] yearso(int remove) {
		Arrays.sort(years);
		if (remove == 0) {
			years = removefromarray(years, 0);
		}
		return years;
	}

	public String[] monthso(int remove) {
		if (remove == 0) {
			months = removefromarray(months, 0);
		}
		return months;
	}

	public String[] statusso() {
		// Arrays.sort(months);
		return statuss;
	}

	public String[] leaveso() {
		Arrays.sort(leaves);
		return leaves;
	}

	public void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages) {
		double total = 0;
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			total += percentages[i];
		}
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			if (percentages[i] > 0.0) {
				int seged = (int) (tablePreferredWidth * (percentages[i] / total));
				column.setPreferredWidth(seged);
			} else {
				// column.setPreferredWidth(0);
				column.setMinWidth(0);
				column.setMaxWidth(0);
				column.setWidth(0);
			}
		}
	}

	public int stoi(String szam) {
		int seged = 0;
		try {
			if (!zempty(szam)) {
				seged = Integer.parseInt(szam);
			}
		} catch (Exception e) {
			System.out.println("Error ! string to int convert !!!");
		}
		return seged;
	}

	public Boolean zempty(String itext) {
		Boolean log = false;
		if (itext == null || itext.isEmpty() || itext.trim().isEmpty() || itext == "null" || itext.equals("")) {
			log = true;
		}
		return log;
	}

	public static void setSelectedValue(JComboBox comboBox, int value) {
		Employee employee;
		Task task;
		Title title;
		Department department;

		if ("employee".equals(comboBox.getName())) {
			for (int i = 0; i < comboBox.getItemCount(); i++) {
				employee = (Employee) comboBox.getItemAt(i);
				if (employee.getEmp_id() == value) {
					comboBox.setSelectedIndex(i);
					break;
				}
			}
		
		} else if ("task".equals(comboBox.getName())) {
			for (int i = 0; i < comboBox.getItemCount(); i++) {
				task = (Task) comboBox.getItemAt(i);
				if (task.getTco_id() == value) {
					comboBox.setSelectedIndex(i);
					break;
				}
			}
		} else if ("title".equals(comboBox.getName())) {
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			title = (Title) comboBox.getItemAt(i);
			if (title.getTitle_id() == value) {
				comboBox.setSelectedIndex(i);
				break;
			}
		}
	}else if ("department".equals(comboBox.getName())) {
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			department = (Department) comboBox.getItemAt(i);
			if (department.getDep_id() == value) {
				comboBox.setSelectedIndex(i);
				break;
			}
		}
	}
	}

	public String currentDate() {
		LocalDate date = LocalDate.now();
		String sdate = date.format(DateTimeFormatter.ISO_DATE);
		return sdate;
	}

	public String currenttime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime time = LocalTime.now();
		String stime = time.format(formatter);
		return stime;
	}

	public JTable ztable() {
		JTable tabla = new JTable();
		// tabla.setBorder(borderf2);
		tabla.setSelectionBackground(narancs2);
		tabla.setSelectionForeground(Color.WHITE);
		tabla.setBackground(sarga);
		tabla.setForeground(Color.BLACK);
		tabla.setGridColor(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setFocusable(false);
//			tabla.setSelectionForeground(Color .BLACK);
//			tabla.setSelectionBackground(vvzold);		
		tabla.setRowHeight(25);
	//	tabla.setRowMargin(1);
		 Dimension dim = new Dimension(5,1);
		  tabla.setIntercellSpacing(new Dimension(dim));
		tabla.setFont(new Font("tahoma", Font.BOLD, 14));
		// tabla.setFont(new java.awt.Font("Times New Roman", 1, 14));
		tabla.setAutoscrolls(true);
		// to make a JTable non-editable
		tabla.setDefaultEditor(Object.class, null);
		tabla.setFocusable(false);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.getTableHeader().setReorderingAllowed(false);
		return tabla;
	}

	public JTableHeader madeheader(JTable table) {
		JTableHeader header = table.getTableHeader();
		// Font font = new Font("tahoma", Font.BOLD, 12);
		// header.setFont(font);
		header.setBackground(sotetkek);
		header.setForeground(Color.white);
		header.setBorder(new LineBorder(Color.BLACK, 1));
		Font headerFont = new Font("tahoma", 1, 16);
		header.setFont(headerFont);
		table.getTableHeader().setReorderingAllowed(false);
		return header;
	}
	
	public JTable ptable() {
     JTable tabla = new JTable();	
		tabla.setForeground(Color.BLACK);
		tabla.setGridColor(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setFocusable(false);
		tabla.setRowHeight(25);
		tabla.setRowMargin(1);
		tabla.setFont(new Font("tahoma", Font.BOLD, 12));	
		tabla.setAutoscrolls(true);	
		tabla.setDefaultEditor(Object.class, null);
		 Dimension dim = new Dimension(5,1);
		  tabla.setIntercellSpacing(new Dimension(dim));
		return tabla;
	}
	public JTableHeader madepheader(JTable table) {
		JTableHeader header = table.getTableHeader();	
		header.setBackground(Color.WHITE);
		header.setForeground(Color.BLACK);
		header.setBorder(new LineBorder(Color.BLACK, 2));
		Font headerFont = new Font("tahoma", 1, 12);
		header.setFont(headerFont);
		table.getTableHeader().setReorderingAllowed(false);
		return header;
	}


	public JButton cbutton(String string) {
		JButton bbutton = new JButton(string);
		bbutton.setFont(new Font("Tahoma", Font.BOLD, 16));
		// bbutton.setBorder(BorderFactory.createLineBorder(Color.white, 2));
		bbutton.setBorder(myRaisedBorder);
		bbutton.setForeground(new Color(255, 255, 255));
		bbutton.setPreferredSize(new Dimension(100, 30));
		bbutton.setMargin(new Insets(10, 10, 10, 10));
		bbutton.setFocusable(false);
		bbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// bbutton.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.GRAY));
		return bbutton;
	}

	public JLabel llabel(String string) {
		JLabel llabel = new JLabel(string);
		llabel.setFont(new Font("Tahoma", 0, 10));
		llabel.setForeground(new Color(0, 0, 0));
		// llabel.setForeground(szold);
		llabel.setPreferredSize(new Dimension(60, 30));
		return llabel;
	}

	public JLabel clabel(String string) {
		JLabel llabel = new JLabel(string);
		llabel.setFont(new Font("Tahoma", 1, 16));
		// llabel.setForeground(new Color (0, 0, 0));
		llabel.setForeground(szold);
		llabel.setPreferredSize(new Dimension(120, 30));
		llabel.setHorizontalAlignment(JLabel.RIGHT);
		return llabel;
	}

	public JLabel flabel(String string) {
		JLabel llabel = new JLabel(string);
		llabel.setFont(new Font("serif", Font.BOLD, 30));
		// llabel.setBackground(Color.YELLOW);
		llabel.setForeground(feher);
		return llabel;
	}

	public JComboBox cbcombo() {
		JComboBox ccombo = new JComboBox();
		ccombo.setFont(textf);
		ccombo.setBorder(borderf);
		ccombo.setBackground(feher);
		ccombo.setPreferredSize(new Dimension(250, 30));
		ccombo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ccombo.setSelectedItem("");
		return ccombo;
	}

	public String countdays(String startdate, String enddate) {
		String days = "";
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate sdate = LocalDate.parse(startdate, formatter);
			LocalDate vdate = LocalDate.parse(enddate, formatter);
			long ldays = ChronoUnit.DAYS.between(sdate, vdate) + 1;
			days = Long.toString(ldays);
		} catch (Exception e) {
			System.err.println("date error");
		}
		return days;
	}

	public Date stringtodate(String sdate) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public LocalDate stringtoldate(String indate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(indate, formatter);
		return date;
	}

	public String itos(int szam) {
		String ss = "";
		try {
			ss = Integer.toString(szam);
		} catch (Exception e) {
			System.out.println("itos hiba");
		}
		return ss;
	}

	public boolean twodate(String startdate, String enddate) {
		String mess = "";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = simpleDateFormat.parse(startdate);
			Date date2 = simpleDateFormat.parse(enddate);
			if (date2.compareTo(date1) < 0) {
				mess = " startdate bigger then enddate !";
				JOptionPane.showMessageDialog(null, mess, "Alert", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (Exception e) {
			mess = "Date error!";
			return false;
		}
		return true;
	}

	public void ztmessage(String mess, String header) {
		JOptionPane op = new JOptionPane(mess, JOptionPane.INFORMATION_MESSAGE);
		final JDialog dialog = op.createDialog(header);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				dialog.setVisible(false);
				dialog.dispose();
			}
		}, 1000);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setAlwaysOnTop(true);
		dialog.setModal(false);
		dialog.setVisible(true);
	}

	public KeyListener AllUpper() {
		KeyListener keyListener = new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (Character.isLowerCase(c))
					e.setKeyChar(Character.toUpperCase(c));
			}
		};
		return keyListener;
	}

	public KeyListener MUpper() {
		KeyListener keyListener = new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				JTextField tf = (JTextField) e.getSource();
				if (tf.getText().length() == 0) {
					if (Character.isLowerCase(c))
						e.setKeyChar(Character.toUpperCase(c));
				}
			}
		};
		return keyListener;
	}

	public KeyListener Onlydate() {
		KeyListener keyListener = new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				JTextField tf = (JTextField) e.getSource();
				if (tf.getText().length() > 9) {
					e.consume();
				}
				if (c == '-') {
				} else if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		};
		return keyListener;
	}

	public KeyListener Onlynum() {
		KeyListener keyListener = new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == '-' || c == '.') {
				} else if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		};
		return keyListener;
	}

	public KeyListener Onlyphone() {
		KeyListener keyListener = new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == '-' || c == '+' || c == '/' || c == ' ' || c == '(' || c == ')') {
				} else if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		};
		return keyListener;
	}

	public boolean validatedate(String strDate, String write) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
		if (zempty(strDate)) {
			if (write == "Y") {
				JOptionPane.showMessageDialog(null, "Empty date !");
			}
			return false;
		}
		sdfrmt.setLenient(false);
		String ss = strDate.trim();
		try {
			Date javaDate = sdfrmt.parse(ss);
		} catch (ParseException e) {
			if (write == "I") {
				JOptionPane.showMessageDialog(null, "Incorrect date !");
			}
			return false;
		}
		return true;
	}

	public String padLeftZeros(String ss, int length) {
		if (ss.length() >= length) {
			return ss;
		}
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length - ss.length()) {
			sb.append('0');
		}
		sb.append(ss);
		return sb.toString();
	}

	public Double stodd(String szam) {
		Double seged = 0.0;
		try {
			if (!zempty(szam)) {
				seged = Double.parseDouble(szam);
			}
		} catch (Exception e) {
			System.out.println("cnvert error !!!");
		}
		return seged;
	}

	public String ddtos(double szam) {
		String seged = "";
		try {
			seged = Double.toString(szam);
		} catch (Exception e) {
			System.out.println("convert error !!!");
		}
		return seged;
	}

	public static String repeat(String s, int times) {
		if (times <= 0)
			return "";
		else if (times % 2 == 0)
			return repeat(s + s, times / 2);
		else
			return s + repeat(s + s, times / 2);
	}

	public String padr(String ss, int length) {
		if (ss.length() >= length) {
			return ss;
		}
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length - ss.length()) {
			sb.append(" ");
		}
		String sss = ss + sb;
		return sss.toString();
	}

	public String padl(String ss, int length) {
		if (ss.length() >= length) {
			return ss;
		}
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length - ss.length()) {
			sb.append(" ");
		}
		sb.append(ss);
		return sb.toString();
	}

	public String sf(Double num) {
		String output = "";
		try {
			Locale.setDefault(Locale.ENGLISH);
			DecimalFormat ddd = new DecimalFormat("#########0.00");
			output = ddd.format(num);
		} catch (Exception e) {
			System.out.println("convert error !!!");
		}
		return output;
	}
	public static String tformat(long s){		
        if (s < 10)      
        	return "0" + s;
        else 
        	return "" + s;
    }

	public String bsf(String number) {
		Locale.setDefault(Locale.ENGLISH);
		String sc = "";
		if (!zempty(number)) {
			try {
				BigDecimal bb = new BigDecimal(number);
				sc = String.format("%.2f", bb);
				return sc;
			} catch (Exception e) {
				System.out.println("convert error !!!");
			}
		}
		return sc;
	}

	public String zmultiply(String num1, String num2) {
		String result = "";
		Locale.setDefault(Locale.ENGLISH);
		if (!zempty(num1) && !zempty(num2)) {
			try {
				BigDecimal b1 = new BigDecimal(num1);
				BigDecimal b2 = new BigDecimal(num2);
				b1 = b1.multiply(b2);
				result = String.format("%.2f", b1);
			} catch (Exception e) {
				System.out.println("convert error !!!");
			}
		}
		return result;
	}

	public String whichpanel(JPanel ppanel) {
		JPanel card = null;
		for (Component comp : ppanel.getComponents()) {
			if (comp.isVisible() == true) {
				card = (JPanel) comp;
			}
		}
		return card.getName();
	}

	class RoundedBorder extends LineBorder {
		private int radius;

		RoundedBorder(Color c, int thickness, int radius) {
			super(c, thickness, true);
			this.radius = radius;
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			// adapted code of LineBorder class
			if ((this.thickness > 0) && (g instanceof Graphics2D)) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				Color oldColor = g2d.getColor();
				g2d.setColor(this.lineColor);

				Shape outer;
				Shape inner;

				int offs = this.thickness;
				int size = offs + offs;
				outer = new RoundRectangle2D.Float(x, y, width, height, 0, 0);
				inner = new RoundRectangle2D.Float(x + offs, y + offs, width - size, height - size, radius, radius);
				Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
				path.append(outer, false);
				path.append(inner, false);
				g2d.fill(path);
				g2d.setColor(oldColor);
			}
		}
	}

	public Border ztroundborder(Color scolor) {
		JRoundedCornerBorder border = new JRoundedCornerBorder(scolor);
		return border;
	}

	class JRoundedCornerBorder extends AbstractBorder {
		// private static final long serialVersionUID = 7644739936531926341L;
		private static final int THICKNESS = 2;
		private Color lcolor;

		JRoundedCornerBorder(Color ccolor) {
			super();
			lcolor = ccolor;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			Graphics2D g2 = (Graphics2D) g.create();

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (c.hasFocus()) {
				g2.setColor(Color.BLUE);
			} else {
				// g2.setColor(Color.BLACK);
				g2.setColor(lcolor);
			}
			g2.setStroke(new BasicStroke(THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2.drawRoundRect(THICKNESS, THICKNESS, width - THICKNESS - 2, height - THICKNESS - 2, 20, 20);

			g2.dispose();
		}

		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(THICKNESS, THICKNESS, THICKNESS, THICKNESS);
		}

		@Override
		public Insets getBorderInsets(Component c, Insets insets) {
			insets.left = insets.top = insets.right = insets.bottom = THICKNESS;
			return insets;
		}

		public boolean isBorderOpaque() {
			return false;
		}
	}

	public static void gotolastrow(JTable dtable) {
		dtable.addComponentListener((ComponentListener) new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
	}	

	public void iconhere(JFrame that) {
		ImageIcon ImageIcon = new ImageIcon(ClassLoader.getSystemResource("images/office2.png"));
		Image Image = ImageIcon.getImage();
		that.setIconImage(Image);
	}

	public void madexxx(JSpinner xspinner, String jel) {
		JSpinner.DateEditor editor;
		DateFormatter formatter;
		if (jel == "T") {
			editor = new JSpinner.DateEditor(xspinner, "HH:mm");

		} else {
			editor = new JSpinner.DateEditor(xspinner, "yyyy-MM-dd");
		}
		formatter = (DateFormatter) editor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false); // this makes what you want
		formatter.setOverwriteMode(true);
		xspinner.setEditor(editor);
		Component c = xspinner.getEditor().getComponent(0);
		c.setForeground(Color.RED);
		c.setBackground(Color.yellow);
	}

	public JSpinner cspinner(SpinnerDateModel model) {
		JSpinner bspinner = new JSpinner(model);
		bspinner.setFont(new Font("Tahoma", Font.BOLD, 16));
		bspinner.setPreferredSize(new Dimension(55, 30));
		bspinner.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bspinner.setBorder(borderf);
		return bspinner;
	}

	public String datediff(String Start, String End) {
		String timediff = "";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			Date startTime = sdf.parse(Start);
			Date endTime = sdf.parse(End);
			long duration = endTime.getTime() - startTime.getTime();
			long hours = TimeUnit.MILLISECONDS.toHours(duration);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
			timediff = String.format("%02d", hours) + ":" + String.format("%02d", minutes);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timediff;
	}

	private static String formatDuration(long duration) {
		long hours = TimeUnit.MILLISECONDS.toHours(duration);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;
		long milliseconds = duration % 1000;
		return String.format("%02d:%02d:%02d,%03d", hours, minutes, seconds, milliseconds);
	}

	public Date stringtotime(String stime) {
		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			date = df.parse(stime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public String timetostring(Date ddate) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String date = df.format(ddate);
		return date;
	}

	public String datetostring(Date ddate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(ddate);
		return date;
	}

	public int tombekeres(String[] tomb, String mit) {
		int value = -1;
		for (int i = 0; i < tomb.length; i++) {
			if (mit.equals(tomb[i])) {
				// if (Objects.equals(mit, tomb[i])) {
				// if (mit.compareTo(tomb[i]) == 0) {
				value = i;
				break;
			}
		}
		return value;
	}

	public int hohossza(int ev, int ho) {
		LocalDate datum = LocalDate.of(ev, ho, 1);
		int length = datum.lengthOfMonth();
		return length;
	}

	public static String kerescombo(JComboBox comboBox, int value) {
		Employee item;
		String name = "";
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			item = (Employee) comboBox.getItemAt(i);
			if (item.getEmp_id() == value) {
				name = item.getName();
				break;
			}
		}
		return name;
	}

	public String[] removefromarray(String[] arr, int row) {
		String[] arr_new = new String[arr.length - 1];
		int j = 3;
		for (int i = 0, k = 0; i < arr.length; i++) {
			if (i != row) {
				arr_new[k] = arr[i];
				k++;
			}
		}
		return arr_new;
	}

	public class SimpleHeaderRenderer extends JLabel implements TableCellRenderer {
		public SimpleHeaderRenderer() {
			setFont(new Font("Consolas", Font.BOLD, 14));
			setBorder(border3);
			setHorizontalAlignment(JLabel.CENTER);
			setForeground(Color.white);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value.toString());
			return this;
		}
	}

	public int evelsonapja(int ev, int ho) {
		LocalDate datum = LocalDate.of(ev, ho, 1);
		DayOfWeek dofw = DayOfWeek.from(datum);
		int val = dofw.getValue();
		return val;
	}

	public void table_show(JTable btable) {
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) btable.getDefaultRenderer(Object.class);
		btable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				btable.scrollRectToVisible(btable.getCellRect(btable.getRowCount() - 1, 0, true));
			}
		});
	}
	public class StringUtils {

	    public static String center(String s, int size) {
	        return center(s, size, ' ');
	    }

	    public static String center(String s, int size, char pad) {
	        if (s == null || size <= s.length())
	            return s;

	        StringBuilder sb = new StringBuilder(size);
	        for (int i = 0; i < (size - s.length()) / 2; i++) {
	            sb.append(pad);
	        }
	        sb.append(s);
	        while (sb.length() < size) {
	            sb.append(pad);
	        }
	        return sb.toString();
	    }
	}	
	
	public class MyTablePrintable implements Printable {

		/**
		 * The table to print.
		 */
		private JTable table;

		/**
		 * For quick reference to the table's header.
		 */
		private JTableHeader header;

		/**
		 * For quick reference to the table's column model.
		 */
		private TableColumnModel colModel;

		/**
		 * To save multiple calculations of total column width.
		 */
		private int totalColWidth;

		/**
		 * The printing mode of this printable.
		 */
		private JTable.PrintMode printMode;

		/**
		 * Provides the header text for the table.
		 */
		private MessageFormat[] headerFormat;

		/**
		 * Provides the footer text for the table.
		 */
		private MessageFormat[] footerFormat;

		/**
		 * The most recent page index asked to print.
		 */
		private int last = -1;

		/**
		 * The next row to print.
		 */
		private int row = 0;

		/**
		 * The next column to print.
		 */
		private int col = 0;

		/**
		 * Used to store an area of the table to be printed.
		 */
		private final Rectangle clip = new Rectangle(0, 0, 0, 0);

		/**
		 * Used to store an area of the table's header to be printed.
		 */
		private final Rectangle hclip = new Rectangle(0, 0, 0, 0);

		/**
		 * Saves the creation of multiple rectangles.
		 */
		private final Rectangle tempRect = new Rectangle(0, 0, 0, 0);

		/**
		 * Vertical space to leave between table and header/footer text.
		 */
		private static final int H_F_SPACE = 8;

		/**
		 * Font size for the header text.
		 */
		private static final float HEADER_FONT_SIZE = 15.0f;

		/**
		 * Font size for the footer text.
		 */
		private static final float FOOTER_FONT_SIZE = 10.0f;

		/**
		 * The font to use in rendering header text.
		 */
		private Font headerFont;

		/**
		 * The font to use in rendering footer text.
		 */
		private Font footerFont;

		/**
		 * Create a new <code>TablePrintable</code> for the given <code>JTable</code>.
		 * Header and footer text can be specified using the two
		 * <code>MessageFormat</code> parameters. When called upon to provide a String,
		 * each format is given the current page number.
		 *
		 * @param table        the table to print
		 * @param printMode    the printing mode for this printable
		 * @param headerFormat a <code>MessageFormat</code> specifying the text to be
		 *                     used in printing a header, or null for none
		 * @param footerFormat a <code>MessageFormat</code> specifying the text to be
		 *                     used in printing a footer, or null for none
		 * @throws IllegalArgumentException if passed an invalid print mode
		 */
			
		public MyTablePrintable(JTable table, JTable.PrintMode printMode, MessageFormat[] headerFormat,
				MessageFormat[] footerFormat) {

			this.table = table;

			header = table.getTableHeader();
			colModel = table.getColumnModel();
			totalColWidth = colModel.getTotalColumnWidth();

			if (header != null) {
				// the header clip height can be set once since it's unchanging
				hclip.height = header.getHeight();
			}

			this.printMode = printMode;

			this.headerFormat = headerFormat;
			this.footerFormat = footerFormat;

			// derive the header and footer font from the table's font
			headerFont = table.getFont().deriveFont(Font.BOLD, HEADER_FONT_SIZE);
			footerFont = table.getFont().deriveFont(Font.PLAIN, FOOTER_FONT_SIZE);
		}

		/**
		 * Prints the specified page of the table into the given {@link Graphics}
		 * context, in the specified format.
		 *
		 * @param graphics   the context into which the page is drawn
		 * @param pageFormat the size and orientation of the page being drawn
		 * @param pageIndex  the zero based index of the page to be drawn
		 * @return PAGE_EXISTS if the page is rendered successfully, or NO_SUCH_PAGE if
		 *         a non-existent page index is specified
		 * @throws PrinterException if an error causes printing to be aborted
		 */
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

			// for easy access to these values
			final int imgWidth = (int) pageFormat.getImageableWidth();
			final int imgHeight = (int) pageFormat.getImageableHeight();

			if (imgWidth <= 0) {
				throw new PrinterException("Width of printable area is too small.");
			}

			// to pass the page number when formatting the header and footer text
	//		Object[] pageNumber = new Object[] { new Integer(pageIndex + 1) };
			Object[] pageNumber = new Object[] { Integer.valueOf(pageIndex + 1) };
		
			// fetch the formatted header text, if any
			String[] headerText = null;
			if (headerFormat != null) {
				headerText = new String[headerFormat.length];
				for (int i = 0; i < headerFormat.length; i++) {
					headerText[i] = headerFormat[i].format(pageNumber)+ " ";
				}
			}

			// fetch the formatted footer text, if any
			String[] footerText = null;
			if (footerFormat != null) {
				footerText = new String[footerFormat.length];
				for (int i = 0; i < footerFormat.length; i++) {
					footerText[i] = footerFormat[i].format(pageNumber);					
				}
			    System.out.print(pageIndex);
			}

			// to store the bounds of the header and footer text
			Rectangle2D[] hRect = null;
			Rectangle2D[] fRect = null;

			// the amount of vertical space needed for the header and footer text

			int headerTextSpace = 15;
			int footerTextSpace = 0;

			// the amount of vertical space available for printing the table
			int availableSpace = imgHeight;

			// if there's header text, find out how much space is needed for it
			// and subtract that from the available space
			if (headerText != null) {
				graphics.setFont(headerFont);
				hRect = new Rectangle2D[headerText.length];
				for (int i = 0; i < headerText.length; i++) {
					hRect[i] = graphics.getFontMetrics().getStringBounds(headerText[i], graphics);
					headerTextSpace += (int) Math.ceil(hRect[i].getHeight());
				}
				availableSpace -= headerTextSpace + H_F_SPACE;
			}

			// if there's footer text, find out how much space is needed for it
			// and subtract that from the available space
			if (footerText != null) {
				graphics.setFont(footerFont);
				fRect = new Rectangle2D[footerText.length];
				for (int i = 0; i < footerText.length; i++) {
					fRect[i] = graphics.getFontMetrics().getStringBounds(footerText[i], graphics);
					footerTextSpace += (int) Math.ceil(fRect[i].getHeight());
				}
				availableSpace -= footerTextSpace + H_F_SPACE;
			}

			if (availableSpace <= 0) {
				throw new PrinterException("Height of printable area is too small.");
			}

			// depending on the print mode, we may need a scale factor to
			// fit the table's entire width on the page
			double sf = 1.0D;
			if (printMode == JTable.PrintMode.FIT_WIDTH && totalColWidth > imgWidth) {

				// if not, we would have thrown an acception previously
				assert imgWidth > 0;

				// it must be, according to the if-condition, since imgWidth > 0
				assert totalColWidth > 1;

				sf = (double) imgWidth / (double) totalColWidth;
			}

			// dictated by the previous two assertions
			assert sf > 0;

			// This is in a loop for two reasons:
			// First, it allows us to catch up in case we're called starting
			// with a non-zero pageIndex. Second, we know that we can be called
			// for the same page multiple times. The condition of this while
			// loop acts as a check, ensuring that we don't attempt to do the
			// calculations again when we are called subsequent times for the
			// same page.
			while (last < pageIndex) {
				// if we are finished all columns in all rows
				if (row >= table.getRowCount() && col == 0) {
					return NO_SUCH_PAGE;
				}

				// rather than multiplying every row and column by the scale factor
				// in findNextClip, just pass a width and height that have already
				// been divided by it
				int scaledWidth = (int) (imgWidth / sf);
				int scaledHeight = (int) ((availableSpace - hclip.height) / sf);

				// calculate the area of the table to be printed for this page
				findNextClip(scaledWidth, scaledHeight);

				last++;
			}

			// translate into the co-ordinate system of the pageFormat
			Graphics2D g2d = (Graphics2D) graphics;
			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

			// to save and store the transform
			AffineTransform oldTrans;

			// if there's footer text, print it at the bottom of the imageable area
			if (footerText != null) {
				oldTrans = g2d.getTransform();

				g2d.translate(0, imgHeight - footerTextSpace);
				for (int i = 0; i < footerText.length; i++) {
					printText(g2d, footerText[i], fRect[i], footerFont, i, imgWidth);
				}

				g2d.setTransform(oldTrans);
			}

			// if there's header text, print it at the top of the imageable area
			// and then translate downwards
			if (headerText != null) {
				for (int i = 0; i < headerText.length; i++) {
					printText(g2d, headerText[i], hRect[i], headerFont, i, imgWidth);
				}

				g2d.translate(0, headerTextSpace + H_F_SPACE);
			}

			// constrain the table output to the available space
			tempRect.x = 0;
			tempRect.y = 0;
			tempRect.width = imgWidth;
			tempRect.height = availableSpace;
			g2d.clip(tempRect);

			// if we have a scale factor, scale the graphics object to fit
			// the entire width
			if (sf != 1.0D) {
				g2d.scale(sf, sf);

				// otherwise, ensure that the current portion of the table is
				// centered horizontally
			} else {
				int diff = (imgWidth - clip.width) / 2;
				g2d.translate(diff, 0);
			}

			// store the old transform and clip for later restoration
			oldTrans = g2d.getTransform();
			Shape oldClip = g2d.getClip();

			// if there's a table header, print the current section and
			// then translate downwards
			if (header != null) {
				hclip.x = clip.x;
				hclip.width = clip.width;

				g2d.translate(-hclip.x, 0);
				g2d.clip(hclip);
				header.print(g2d);

				// restore the original transform and clip
				g2d.setTransform(oldTrans);
				g2d.setClip(oldClip);

				// translate downwards
				g2d.translate(0, hclip.height);
			}

			// print the current section of the table
			g2d.translate(-clip.x, -clip.y);
			g2d.clip(clip);
			table.print(g2d);

			// restore the original transform and clip
			g2d.setTransform(oldTrans);
			g2d.setClip(oldClip);

			// draw a box around the table
			g2d.setColor(Color.BLACK);
			g2d.drawRect(0, 0, clip.width, hclip.height + clip.height);

			return PAGE_EXISTS;
		}

		/**
		 * A helper method that encapsulates common code for rendering the header and
		 * footer text.
		 *
		 * @param g2d      the graphics to draw into
		 * @param text     the text to draw, non null
		 * @param rect     the bounding rectangle for this text, as calculated at the
		 *                 given font, non null
		 * @param font     the font to draw the text in, non null
		 * @param imgWidth the width of the area to draw into
		 */
		private void printText(Graphics2D g2d, String text, Rectangle2D rect, Font font, int textIndex, int imgWidth) {

			// int tx = -(int)(Math.ceil(rect.getWidth()) - imgWidth); // for right allign
			int tx = 0; // for left allign

		//	int ty = textIndex * (int) Math.ceil(Math.abs(rect.getY()));
			int ty = (textIndex  +1) * (int) Math.ceil(Math.abs(rect.getY()));
			g2d.setColor(Color.BLACK);
			g2d.setFont(font);
			g2d.drawString(text, tx, ty);
		}

		/**
		 * Calculate the area of the table to be printed for the next page. This should
		 * only be called if there are rows and columns left to print.
		 *
		 * To avoid an infinite loop in printing, this will always put at least one cell
		 * on each page.
		 *
		 * @param pw the width of the area to print in
		 * @param ph the height of the area to print in
		 */
		private void findNextClip(int pw, int ph) {
			final boolean ltr = table.getComponentOrientation().isLeftToRight();

			// if we're ready to start a new set of rows
			if (col == 0) {
				if (ltr) {
					// adjust clip to the left of the first column
					clip.x = 0;
				} else {
					// adjust clip to the right of the first column
					clip.x = totalColWidth;
				}

				// adjust clip to the top of the next set of rows
				clip.y += clip.height;

				// adjust clip width and height to be zero
				clip.width = 0;
				clip.height = 0;

				// fit as many rows as possible, and at least one
				int rowCount = table.getRowCount();
				int rowHeight = table.getRowHeight(row);
				do {
					clip.height += rowHeight;

					if (++row >= rowCount) {
						break;
					}

					rowHeight = table.getRowHeight(row);
				} while (clip.height + rowHeight <= ph);
			}

			// we can short-circuit for JTable.PrintMode.FIT_WIDTH since
			// we'll always fit all columns on the page
			if (printMode == JTable.PrintMode.FIT_WIDTH) {
				clip.x = 0;
				clip.width = totalColWidth;
				return;
			}

			if (ltr) {
				// adjust clip to the left of the next set of columns
				clip.x += clip.width;
			}

			// adjust clip width to be zero
			clip.width = 0;

			// fit as many columns as possible, and at least one
			int colCount = table.getColumnCount();
			int colWidth = colModel.getColumn(col).getWidth();
			do {
				clip.width += colWidth;
				if (!ltr) {
					clip.x -= colWidth;
				}

				if (++col >= colCount) {
					// reset col to 0 to indicate we're finished all columns
					col = 0;

					break;
				}
				colWidth = colModel.getColumn(col).getWidth();
			} while (clip.width + colWidth <= pw);
		}
	}



}
