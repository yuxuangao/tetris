package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

public class iconChooser extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private String[] filename;
	Vector<String> element = new Vector<String>();
	JList<String> list;
	private int index = 0;
	private Point lastPoint;
	private int currentIndex = -1;
	
	private int icon;

	/**
	 * Create the dialog.
	 */
	public iconChooser(int Icon) {
		icon = Icon;
		setModal(true);
		setTitle("选择头像");
		setBounds(150, 150, 619, 500);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		File path = new File(getClass().getResource("/icon/").getPath());
		filename = path.list(new FilenameFilter(){
			public boolean accept(File dir, String name) {
				if (name.contains(".png"))
					return true;
				return false;
			}
		});
		for (int i=0;i<filename.length;i++)
			element.addElement("");
		list = new JList<String>(element);
		Task task = new Task();
		task.execute();
		list.setVisibleRowCount(40);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new ListCellRenderer<Object>(){
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JPanel pane = new JPanel();
				pane.setOpaque(false);
				if (!value.equals("")){
					pane.setLayout(new BorderLayout());
					pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
					ImageIcon icon = new ImageIcon(getClass().getResource("/icon/"+value.toString()));
					icon.setImage(icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
					JLabel iconLabel = new JLabel(icon);
					if (isSelected){
						pane.setOpaque(true);
						pane.setBackground(new Color(255,230,230));
					}
					else{
						pane.setOpaque(false);
					}
					
					if (currentIndex==index){
						pane.setBorder(BorderFactory.createLineBorder(Color.blue));
					}
					
					pane.removeAll();
					pane.add(iconLabel,BorderLayout.CENTER);
				}
				return pane;
			}
		});
		list.setForeground(Color.black);
		list.addMouseListener(new MouseAdapter(){
		    public void mouseEntered(MouseEvent e){
		    	currentIndex = list.locationToIndex(e.getPoint());
		    	list.repaint();
		        lastPoint = e.getPoint();
		    }
		});
		list.addMouseMotionListener(new MouseMotionAdapter(){
		    public void mouseMoved(MouseEvent e){
		        int lastIndex = list.locationToIndex(lastPoint);
		        Point current = e.getPoint();
		        currentIndex = list.locationToIndex(current);
		        if (currentIndex == lastIndex) return;
		        list.repaint();
		        lastPoint = current;
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(list);
		contentPanel.add(scrollPane, BorderLayout.CENTER);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("确定");
				okButton.setActionCommand("OK");
				okButton.setPreferredSize(new Dimension(80,27));
				okButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						icon = list.getSelectedIndex();
						setVisible(false);
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.setActionCommand("Cancel");
				cancelButton.setPreferredSize(new Dimension(80,27));
				cancelButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public int getIconserial(){
		return icon;
	}
	
	class Task extends SwingWorker<Void,Void>{
		public Void doInBackground(){
			element.set(index, filename[index]);
			index++;
			return null;
		}
		public void done(){
			repaint();
			if (index!=filename.length+1){
				Task task = new Task();
				task.execute();
			}
			else{
				list.setSelectedIndex(icon);
			}
		}
	}

}
