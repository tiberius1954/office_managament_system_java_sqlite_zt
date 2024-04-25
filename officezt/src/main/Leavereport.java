package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import classes.Employee;
import classes.Hhelper;
import classes.Hhelper.SimpleHeaderRenderer;
import databaseop.DatabaseHelper;
import databaseop.Databaseop;

public class Leavereport extends JFrame {
	Connection con;
	PreparedStatement pst;
	Statement stmt;
	ResultSet rs;
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();	
	
	Color zold = new Color(26, 194, 82);
	Color vzold = new Color(102, 255, 102);
	Color narancs = new Color(255, 211, 31);
	
	static ArrayList<ArrayList> Arr2d = new ArrayList<ArrayList>();
	ArrayList<Integer> f = new ArrayList<Integer>();
	ArrayList<Integer> jelek = new ArrayList<Integer>();

	int aktev;
	String tavid = "";
	static int szinfajta = 0;
	public int[] hoelsok = new int[12];
	public int[] ossz = new int[12];
	String[] romho = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
	final static Border border = new BevelBorder(BevelBorder.RAISED);
	String[] days = { "Month", "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su", "Mo", "Tu",
			"We", "Th", "Fr", "Sa", "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su", "Mo",
			"Tu", "Total" };
	Object[][] data = {
			{ "01", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "" },
			{ "02", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "" },
			{ "03", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "" },
			{ "04", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "" },
			{ "05", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "" },
			{ "06", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "" },
			{ "07", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "" },
			{ "08", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "" },
			{ "09", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "" },
			{ "10", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "" },
			{ "11", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "" },
			{ "12", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "" }, };

	String[] types = hh.leaveso();
	Leavereport() {
		super("Leave of employee");
		cp = getContentPane();
		cp.setBackground(hh.neonzold);
		initComponents();
		try {
			dd.employeecombofill(cmbemployees);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Arr2d.clear();
		table_update("");
		  hh.iconhere(this);
	}

	private void initComponents() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		this.setLayout(null);
		setBounds(10, 10, 1180, 660);
		lbamount = hh.clabel("");
		lbamount.setFont(new Font("tahoma", Font.BOLD, 12));
		lbamount.setBorder(hh.borderf2);
		lbamount.setHorizontalAlignment(JLabel.CENTER);
		lbamount.setBounds(1040, 422, 79, 25);
		lbamount.setBackground(new Color(255, 255, 255));
		lbamount.setOpaque(true);

		setFont(new Font("Tahoma", Font.BOLD, 14));
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
	
		fejpanel = new JPanel(null);
		fejpanel.setBounds(0, 0, 1165, 60);
		fejpanel.setBackground(hh.zold);
		
		lbheader = hh.flabel("LEAVE OF EMPLOYEE");
		lbheader.setBounds(430, 5, 350, 40);
		fejpanel.add(lbheader);
		
		fejpanel.add(lbheader);
		add(fejpanel);

		panel1 = new JPanel(null);
		panel1.setBounds(20, 70, 1130, 530);
		panel1.setBackground(hh.neonzold);
		panel1.setBorder(hh.borderf);
		add(panel1);

		panel1.add(lbamount);		

		lbemployee = hh.clabel("Employee");
		lbemployee.setBounds(240, 30, 120, 25);
		lbemployee.setHorizontalAlignment(JLabel.RIGHT);
		panel1.add(lbemployee);

		cmbemployees = hh.cbcombo();
		cmbemployees.setBounds(370, 30, 200, 25);
		cmbemployees.setName("employee");
		panel1.add(cmbemployees);

		lbyear = hh.clabel("Years:");
		lbyear.setBounds(580, 30, 60, 25);
		lbyear.setHorizontalAlignment(JLabel.RIGHT);
		panel1.add(lbyear);

		cmbyears = hh.cbcombo();
		cmbyears.setModel(new DefaultComboBoxModel(hh.yearso(1)));
		cmbyears.setBounds(650, 30, 90, 25);
		cmbyears.setName("Year");
		panel1.add(cmbyears);

		btnrajz = hh.cbutton("Show");
		btnrajz.setForeground(Color.black);
		btnrajz.setBackground(Color.magenta);
		btnrajz.setBounds(746, 30, 80, 25);
		btnrajz.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		panel1.add(btnrajz);

		btnrajz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbyears.getSelectedIndex() != -1) {
					String year = String.valueOf(cmbyears.getSelectedItem());
					Arrays.fill(ossz, 0);
					aktev = hh.stoi(year);
					hoeleje(aktev);
					String jel = "I";
					data = kigyujtes();
					// kigyujtes();
					table_update(jel);
					Employee employee = (Employee) cmbemployees.getSelectedItem();
					String Empname = employee.getName();
					if (!hh.zempty(Empname)) {
						datgyujt();
					}
				}
			}
		});

		model = new DefaultTableModel(data, days);
		tabla = new JTable(model) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		tabla.setBorder(hh.borderf);
		Font fontt = new Font("tahoma", Font.BOLD, 12);
		tabla.setFont(fontt);
		tabla.setGridColor(Color.black);
		tabla.setRowHeight(25);
		// tabla.setRowMargin(1);
		tabla.setShowGrid(true);
		tabla.setFocusable(false);
		tabla.setSelectionBackground(narancs);
		// tabla.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		JTableHeader header = tabla.getTableHeader();
		header.setDefaultRenderer(hh.new SimpleHeaderRenderer());
		header.setBackground(hh.sotetkek);
		header.setForeground(Color.white);
	
		hh.setJTableColumnsWidth(tabla, 1115, 4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4,
				2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4,
				2.4, 2.4, 7);

		tabla.setShowVerticalLines(false);
		tabla.setDefaultRenderer(Object.class, new BorderColorRenderer());
		jScrollPane1 = new JScrollPane();
		jScrollPane1.setBounds(5, 90, 1115, 335);
		jScrollPane1.setViewportView(tabla);
		jScrollPane1.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 32;
				return d;
			}
		});
		panel1.add(jScrollPane1);
		magyaraz();
	}

	private String[][] kigyujtes() {
		String vtomb[];
		String[][] tomb = new String[12][39];
		for (int k = 0; k < 12; k++) {
			tomb[k][0] = romho[k];
		}
		for (int i = 0; i < tomb.length; i++) {
			vtomb = sorom(i);
			for (int j = 0; j < vtomb.length - 1; j++) {
				tomb[i][j + 1] = vtomb[j];
			}
		}
		return tomb;
	}

	private String[] sorom(int honap) {
		String mtomb[];
		mtomb = new String[38];
		int ho = honap + 1;
		int j = 0;
		String ev =  String.valueOf(cmbyears.getSelectedItem());
		int evem = hh.stoi(ev);
		int napszam = hh.evelsonapja(evem, ho) - 1;
		int hossz = hh.hohossza(evem, ho);
		for (int i = 0; i < mtomb.length; i++) {
			mtomb[i] = " ";
			if (i >= napszam) {
				j++;
				if (j > hossz) {
					break;
				}
				mtomb[i] = hh.itos(j);
			}
		}
		return mtomb;
	}

	private void table_update(String jel) {
		model = new DefaultTableModel(data, days);
		tabla.setModel(model);
		hh.setJTableColumnsWidth(tabla, 1115, 4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4,
				2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4, 2.4,
				2.4, 2.4, 7);
	}

	private void datgyujt() {
		int emp_id = ((Employee) cmbemployees.getSelectedItem()).getEmp_id();
		String year = String.valueOf(cmbyears.getSelectedItem());
		Arr2d.clear();
		jelek.clear();
		labelurit();
		try {
			String sql = "select type, start, end from leave where emp_id= " + hh.itos(emp_id)
	       + " and (strftime('%Y', start)= '" + year+"' or strftime('%Y', end) = '" + year + "')";	
			ResultSet rs = dh.GetData(sql);
			while (rs.next()) {
				String start = rs.getString("start");
				String end = rs.getString("end");
				String tip = rs.getString("type");
				LocalDate kdate = hh.stringtoldate(start);
				LocalDate vdate = hh.stringtoldate(end);
				int typ = hh.tombekeres(types, tip);
				datsorfv(kdate, vdate, typ);
			}
			dh.CloseConnection();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		} finally {
			kitoltes();

			DefaultTableModel model = (DefaultTableModel) tabla.getModel();
			String sss;
			int osszesen = 0;
			for (int i = 0; i < 12; i++) {
				sss = hh.itos(ossz[i]);		
				model.setValueAt(sss, i, 38);
				osszesen = osszesen + ossz[i];
			}
			lbamount.setText(hh.itos(osszesen));
			jelekrajz();
		}
	}

	private void datsorfv(LocalDate kdate, LocalDate vvdate, int tipus) {
		LocalDate idate;
		int ii = 0, iho, iev, iday;
		int kev = kdate.getYear();
		int vev = kdate.getYear();
		LocalDate vdate = vvdate.plusDays(1);
		idate = kdate;
		do {
			DayOfWeek dofw = DayOfWeek.from(idate);
			int napsz = dofw.getValue();
			if (!(idate.isBefore(vdate))) {
				break;
			}
			if ((idate.getYear() == aktev) && (napsz != 6 && napsz != 7)) {
				// iev = idate.getYear();
				iday = idate.getDayOfMonth();
				iho = idate.getMonthValue();
				int tip = tipus;
				if (!jelek.contains(tip)) {
					jelek.add(tip);
				}
				ArrayList row = new ArrayList();
				row.add(iho);
				row.add(iday);
				row.add(tip);
				Arr2d.add(row);
				ossz[iho - 1]++;
			}
			ii++;
			idate = kdate.plusDays(ii);
		} while (true);
	}

	private void kitoltes() {
		for (int i = 0; i < Arr2d.size(); i++) {
			int ii = (int) Arr2d.get(i).get(0);
			int hoelso = hoelsok[ii - 1];
			int iii = (int) Arr2d.get(i).get(1);
			Arr2d.get(i).set(1, hoelso + iii);
		}
	}

	private static class BorderColorRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Border border;
			Color vzold = new Color(102, 255, 102);
			 Color sotetkek = new Color(1, 50, 67);
			Color ideg = new Color(255, 255, 255);
			border = BorderFactory.createMatteBorder(0, 2, 1, 0, Color.BLACK);
			int szam = 0;
			int i0 = 0;
			int i1 = 0;
			int i2 = 0;
			JComponent comp = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			comp.setBorder(border);
			((JLabel) comp).setHorizontalAlignment(JLabel.CENTER);
			Boolean found = keresss(row, column);
			if (found == true) {
				int[] sr = szinvalasztas();
				i0 = sr[0];
				i1 = sr[1];
				i2 = sr[2];
			}
			szam = column + 1;
			comp.setForeground(Color.black);
			if (column == 0) {
				comp.setForeground(Color.white);
				comp.setBackground(sotetkek);		
			} else if (column % 7 == 0) {
				comp.setBackground(Color.ORANGE);
			} else if (szam % 7 == 0) {
				comp.setBackground(Color.ORANGE);
			} else if (found == true) {
				comp.setBackground(new Color(i0, i1, i2));
				comp.setForeground(Color.white);
			} else {
				comp.setBackground(Color.white);
			}
			return comp;
		}
	}

	private static Boolean keresss(int row, int col) {
		Boolean found = false;
		int irow = row + 1;
		int icol = col + 1;
		for (int i = 0; i < Arr2d.size(); i++) {
			if ((int) Arr2d.get(i).get(0) == irow) {
				for (int j = 0; j < Arr2d.get(i).size(); j++) {
					if ((int) Arr2d.get(i).get(1) == icol) {
						found = true;
						szinfajta = (int) Arr2d.get(i).get(2);
						break;
					}
				}			
			}
		}
		return found;
	}

	public static int[] szinvalasztas() {
		int[] arr = new int[3];
		switch (szinfajta) {
		case 0:
			// skek
			arr[0] = 72;
			arr[1] = 75;
			arr[2] = 126;
			break;
		case 1:
			// piros
			arr[0] = 249;
			arr[1] = 73;
			arr[2] = 58;
			break;
		case 2:
			// zold
			arr[0] = 111;
			arr[1] = 191;
			arr[2] = 138;
			break;
		case 3:
			// barna
			arr[0] = 160;
			arr[1] = 82;
			arr[2] = 45;
			break;
		case 4:
			// snarancs
			arr[0] = 255;
			arr[1] = 69;
			arr[2] = 0;
			break;
		case 5:
			// rozsaszin
			arr[0] = 255;
			arr[1] = 138;
			arr[2] = 173;
			break;
		case 6:
			// teglavoros
			arr[0] = 243;
			arr[1] = 89;
			arr[2] = 39;
			break;
		case 7:
			// slilla
			arr[0] = 128;
			arr[1] = 0;
			arr[2] = 128;
			break;
		case 8:
			// warning
			arr[0] = 243;
			arr[1] = 95;
			arr[2] = 38;
			break;			
		case 9:
			// homok
			arr[0] = 200;
			arr[1] = 137;
			arr[2] = 60;
			break;
		case 10:
			// kekes 
			arr[0] = 21;
			arr[1] = 44;
			arr[2] = 82;
			break;
		case 11:
			// cian
			arr[0] = 0;
			arr[1] = 153;
			arr[2] = 102;
			break;			
		default:
			arr[0] = 0;
			arr[1] = 0;
			arr[2] = 0;
		}
		return arr;
	}	

	private void hoeleje(int evem) {
		for (int i = 0; i < hoelsok.length; i++)
			hoelsok[i] =hh.evelsonapja(evem, i + 1);
	}

	private void jelekrajz() {
		int meret = jelek.size();
		int valt, i0, i1, i2;
		String szov;
		if (meret > 0) {
			for (int i = 0; i < meret; i++) {
				valt = jelek.get(i);
				szov = types[valt];
				szinfajta = valt;
				int[] sr = szinvalasztas();
				i0 = sr[0];
				i1 = sr[1];
				i2 = sr[2];	
				
				switch (i) {
				case 0:
					cimkek(i, szov, i0, i1, i2);
					break;
				case 1:
					cimkek(i, szov, i0, i1, i2);
					break;
				case 2:
					cimkek(i, szov, i0, i1, i2);
					break;
				case 3:
					cimkek(i, szov, i0, i1, i2);
					break;
				case 4:
					cimkek(i, szov, i0, i1, i2);
					break;
				case 5:
					cimkek(i, szov, i0, i1, i2);
					break;
				case 6:
					cimkek(i, szov, i0, i1, i2);
					break;
				case 7:
					cimkek(i, szov, i0, i1, i2);
					break;
				case 8:
					cimkek(i, szov, i0, i1, i2);
					break;
				case 9:
					cimkek(i, szov, i0, i1, i2);
					break;
				case 10:
					cimkek(i, szov, i0, i1, i2);
					break;
				case 11:
					cimkek(i, szov, i0, i1, i2);
					break;
				}
			}
		}
	}

	private void cimkek(int i, String szov, int n1, int n2, int n3) {
		labels[i].setBackground(new Color(n1, n2, n3));
		labels[i].setText(szov);
		labels[i].setVisible(true);
	}

	private void magyaraz() {
		labels = new JLabel[12];
		for (int i = 0; i < 12; i++) {
			labels[i] = new JLabel("");
			labels[i].setFont(new Font("serif", Font.BOLD, 16));
			labels[i].setBorder(hh.borderf);
			labels[i].setForeground(new Color(255, 255, 255));
			labels[i].setOpaque(true);
			labels[i].setHorizontalAlignment(JLabel.CENTER);
			panel1.add(labels[i]);
		}
		labels[0].setBounds(40, 450, 150, 25);
		labels[1].setBounds(200, 450, 150, 25);
		labels[2].setBounds(360, 450, 150, 25);
		labels[3].setBounds(520, 450, 150, 25);
		labels[4].setBounds(680, 450, 150, 25);		
		labels[5].setBounds(840, 450, 150, 25);	
		
		labels[6].setBounds(40, 485, 150, 25);
		labels[7].setBounds(200, 485, 150, 25);
		labels[8].setBounds(360, 485, 150, 25);
		labels[9].setBounds(520, 485, 150, 25);
		labels[10].setBounds(680, 485, 150, 25);
		labels[11].setBounds(840, 485, 150, 25);
		
		labelurit();
	}

	private void labelurit() {
		for (int i = 0; i < 12; i++) {
			labels[i].setVisible(false);
			labels[i].setText("");
		}
	}

	public static void main(String args[]) {
		Leavereport lev = new Leavereport();
		lev.setVisible(true);
	}

	private JLabel lbyear, lbheader, lbamount, lbemployee;
	private JLabel[] labels;
	private JPanel panel1, fejpanel;
	private JTable tabla;
	private JScrollPane jScrollPane1;
	private DefaultTableModel model;
	JButton btnrajz;
	JComboBox cmbemployees, cmbyears;
	Container cp;
}
