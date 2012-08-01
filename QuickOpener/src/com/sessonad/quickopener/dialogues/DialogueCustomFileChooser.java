package com.sessonad.quickopener.dialogues;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JScrollPane;
import org.eclipse.ui.IWorkbenchWindow;
import com.sessonad.oscommands.commands.Commands;
import com.sessonad.quickopener.PathFinder;
import com.sessonad.quickopener.preferences.PreferenceUtils;

public class DialogueCustomFileChooser extends JDialog {


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField cmdText;
	private JTable table;	
    public static final int CHARSNUMBER = 80;
    private IWorkbenchWindow window;
    private static String command;
    
    private String selectioPath;
    private String workbenchPath;
    private String mainProjectPath;
	
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
	}
	
	


	private String getPathLongerThan(String path){
        if(path.length()>=CHARSNUMBER){            
            String intpath = path.substring(path.length()-CHARSNUMBER);
            int idx = intpath.indexOf("\\");
            if(idx!=-1){
                return "..." + intpath.substring(idx);
            }
            int adx = intpath.indexOf("/");
            if(adx!=-1){
                return "..." + intpath.substring(adx);
            }
            return "..." + intpath;
        }else{
            return path;
        }
    }
	
	public static String getCommand(){
        return command;
    }
    
    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    
    
    private void doClose() {        
        setVisible(false);
        dispose();
    }
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogueCustomFileChooser dialog = new DialogueCustomFileChooser(null,false, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogueCustomFileChooser(java.awt.Frame parent, boolean modal,final IWorkbenchWindow window) {
		super(parent,modal);
		setTitle("Choose file...");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogueCustomFileSystem.class.getResource("/com/sessonad/quickopener/icons/folder-documents-icon-cu.png")));
		this.window=window;
		setBounds(100, 100, 497, 441);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblOopenShellIn = new JLabel("Selected file:");
		lblOopenShellIn.setBounds(91, 20, 80, 14);
		contentPanel.add(lblOopenShellIn);
		
		cmdText = new JTextField();
		cmdText.setEditable(false);
		cmdText.setBounds(91, 39, 357, 20);
		contentPanel.add(cmdText);
		cmdText.setColumns(10);
		
		JLabel lblSelectedFile = new JLabel("Selected file:");
		lblSelectedFile.setFocusable(false);
		lblSelectedFile.setForeground(Color.GRAY);
		lblSelectedFile.setFont(new Font("Arial", Font.BOLD, 11));
		lblSelectedFile.setBounds(21, 82, 71, 14);
		contentPanel.add(lblSelectedFile);
		
		JLabel lblNewLabel_1 = new JLabel("Main Project:");
		lblNewLabel_1.setFocusable(false);
		lblNewLabel_1.setForeground(Color.GRAY);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 11));
		lblNewLabel_1.setBounds(21, 101, 71, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel label = new JLabel("");
		label.setBounds(10, 132, 46, 14);
		contentPanel.add(label);
		
		final JLabel lblSelFile = new JLabel("(not available)");
		lblSelFile.setFocusable(false);
		lblSelFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(lblSelFile.isEnabled()){
		            cmdText.setText(selectioPath);
		        }
			}
		});
		lblSelFile.setForeground(Color.BLUE);
		lblSelFile.setEnabled(false);
		lblSelFile.setBounds(101, 82, 370, 14);
		contentPanel.add(lblSelFile);
		
		final JLabel lblMainProj = new JLabel("(not available)");
		lblMainProj.setFocusable(false);
		lblMainProj.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(lblMainProj.isEnabled()){
		            cmdText.setText(mainProjectPath);
		        }
			}
		});
		lblMainProj.setForeground(Color.BLUE);
		lblMainProj.setEnabled(false);
		lblMainProj.setBounds(101, 101, 370, 14);
		contentPanel.add(lblMainProj);

		
		JLabel lblFavo = new JLabel("Favorite places:");
		lblFavo.setForeground(Color.GRAY);
		lblFavo.setFont(new Font("Arial", Font.BOLD, 11));
		lblFavo.setBounds(20, 165, 92, 14);
		contentPanel.add(lblFavo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 184, 461, 175);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		table.setFocusable(false);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		table.setAutoCreateRowSorter(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 1) {
		            final int thisrow=table.getSelectedRow();
		            final int row = table.getRowSorter().convertRowIndexToModel(thisrow);
		            String path = (String) table.getModel().getValueAt(row, 1);
		            cmdText.setText(path);
		        }
			}
		});
		table.setForeground(Color.BLUE);
		table.setRowSelectionAllowed(false);
		table.setShowVerticalLines(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
			},
			new String[] {
				"New column", "New column"
			}
		));
		table.setBackground(UIManager.getColor("Button.background"));
		
		table.setModel(new PropertyTableModel(PreferenceUtils.PLACES));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		JLabel lblWorkspace = new JLabel("Workspace:");
		lblWorkspace.setFocusable(false);
		lblWorkspace.setForeground(Color.GRAY);
		lblWorkspace.setFont(new Font("Arial", Font.BOLD, 11));
		lblWorkspace.setBounds(21, 120, 71, 14);
		contentPanel.add(lblWorkspace);
		
		final JLabel lblWorksp = new JLabel("(not available)");
		lblWorksp.setFocusable(false);
		lblWorksp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(lblWorksp.isEnabled()){
		            cmdText.setText(workbenchPath);
		        }
			}
		});
		lblWorksp.setForeground(Color.BLUE);
		lblWorksp.setEnabled(false);
		lblWorksp.setBounds(101, 120, 370, 14);
		
		
		try {
			selectioPath=PathFinder.getPathFromSelection(true,window);
			if(selectioPath!=null){
				lblSelFile.setEnabled(true);
				lblSelFile.setText(getPathLongerThan(selectioPath));
	        }
			workbenchPath=PathFinder.getPathFromWorkBench(true,window);
			if(workbenchPath!=null){
				lblWorksp.setEnabled(true);
				lblWorksp.setText(getPathLongerThan(workbenchPath));
	        }
			mainProjectPath=PathFinder.getPathFromProject(true,window);
			if(mainProjectPath!=null){
				lblMainProj.setEnabled(true);
				lblMainProj.setText(getPathLongerThan(mainProjectPath));
	        }
		} catch (Exception e1) {}
		
		contentPanel.add(lblWorksp);
		
		JLabel lblIcon = new JLabel(" ");
		lblIcon.setIcon(new ImageIcon(DialogueCustomFileChooser.class.getResource("/com/sessonad/quickopener/icons/folder-documents-icon48.png")));
		lblIcon.setBounds(20, 11, 61, 60);
		contentPanel.add(lblIcon);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMaxWidth(400);
		table.getColumnModel().getColumn(0).setMinWidth(100);		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFocusPainted(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						File file= new File(cmdText.getText());
				        if(file.exists()){
				        	command = cmdText.getText();
				        	doClose();
				        }else{
				        	doClose();
				        }
					}
				});
				
				JLabel label_1 = new JLabel("");
				label_1.setToolTipText("<html><span color=\"blue\">Click on any path to set the input box.</span><br/>\r\n<br/>\r\nYou can customize the your preferred places in:<br/>\r\n<span color=\"blue\">Window > Preferences >  QuickOpener\r\n</html>");
				label_1.setIcon(new ImageIcon(DialogueCustomFileChooser.class.getResource("/com/sessonad/quickopener/icons/help.png")));
				buttonPane.add(label_1);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFocusPainted(false);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						command = null;
						doClose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}
}
