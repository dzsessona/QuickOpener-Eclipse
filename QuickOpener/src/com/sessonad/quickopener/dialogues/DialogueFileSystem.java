package com.sessonad.quickopener.dialogues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.eclipse.ui.IWorkbenchWindow;
import com.sessonad.oscommands.commands.Commands;
import com.sessonad.quickopener.PathFinder;
import com.sessonad.quickopener.preferences.PreferenceUtils;

public class DialogueFileSystem extends JDialog {

	private static final long serialVersionUID = 1L;
	public static final int CHARSNUMBER = 80;
	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JButton cancelButton;
	private JTextField cmdText;
	private JTable table;	
    private JPanel panel_2;
    private JLabel lblSelectedFile;
    private JLabel lblMain;
    private JLabel lblWorkspace;
    private JLabel lblSelFile;
    private JLabel lblSelProj;
    private JLabel lblSelWks;
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
	
		
	public DialogueFileSystem(java.awt.Frame parent, boolean modal,final IWorkbenchWindow window){
		super(parent,modal);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogueShell.class.getResource("/com/sessonad/quickopener/icons/folder-documents-icon-cu.png")));
		setTitle("Open FileSystem in...");
		initComponents();
		
		try {
			selectioPath=PathFinder.getPathFromSelection(false,window);
			if(selectioPath!=null){
				lblSelFile.setEnabled(true);
				lblSelFile.setText(getPathLongerThan(selectioPath));
	        }
			workbenchPath=PathFinder.getPathFromWorkBench(false,window);
			if(workbenchPath!=null){
				lblSelWks.setEnabled(true);
				lblSelWks.setText(getPathLongerThan(workbenchPath));
	        }
			mainProjectPath=PathFinder.getPathFromProject(false,window);
			if(mainProjectPath!=null){
				lblSelProj.setEnabled(true);
				lblSelProj.setText(getPathLongerThan(mainProjectPath));
	        }
		} catch (Exception e1) {}
		
		
		table.setModel(new PropertyTableModel(PreferenceUtils.PLACES));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMaxWidth(400);
		table.getColumnModel().getColumn(0).setMinWidth(100);
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
		lblSelFile.addMouseListener(new MouseAdapter() {@Override public void mouseClicked(MouseEvent arg0) {if(lblSelFile.isEnabled()){cmdText.setText(selectioPath);}}});
		lblSelProj.addMouseListener(new MouseAdapter() {@Override public void mouseClicked(MouseEvent arg0) {if(lblSelProj.isEnabled()){cmdText.setText(mainProjectPath);}}});
		lblSelWks.addMouseListener(new MouseAdapter()  {@Override public void mouseClicked(MouseEvent arg0) {if(lblSelWks.isEnabled()){ cmdText.setText(workbenchPath);}}});
		
		okButton.addActionListener(new ActionListener() 	{ public void actionPerformed(ActionEvent e) {okActionPerformed(e);}});
		cancelButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {cancelActionPerformed(e);}});
		
		cmdText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {checkOkButton();}            
            @Override
            public void removeUpdate(DocumentEvent e) {checkOkButton();}
            @Override
            public void insertUpdate(DocumentEvent e) {checkOkButton();}
        });
		checkOkButton();
	}
	
	public void checkOkButton(){		
		try{
			String text=cmdText.getText();
			if(text == null || text.isEmpty()){okButton.setEnabled(false);return;}
			File f= new File(text);
			okButton.setEnabled(f.exists() && f.isDirectory());
		}catch(Exception e){}		
	}
	
	public void initComponents() {
		setBounds(100, 100, 609, 394);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			panel_2 = new JPanel();
			GridBagConstraints gbc_panel_2 = new GridBagConstraints();
			gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
			gbc_panel_2.insets = new Insets(0, 0, 5, 0);
			gbc_panel_2.gridx = 0;
			gbc_panel_2.gridy = 0;
			contentPanel.add(panel_2, gbc_panel_2);
			GridBagLayout gbl_panel_2 = new GridBagLayout();
			gbl_panel_2.columnWidths = new int[]{0, 0, 0};
			gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0};
			gbl_panel_2.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel_2.setLayout(gbl_panel_2);
			{
				lblSelectedFile = new JLabel("Selected file:");
				lblSelectedFile.setForeground(Color.GRAY);
				lblSelectedFile.setFont(new Font("Arial", Font.BOLD, 11));
				GridBagConstraints gbc_lblSelectedFile = new GridBagConstraints();
				gbc_lblSelectedFile.anchor = GridBagConstraints.WEST;
				gbc_lblSelectedFile.insets = new Insets(0, 15, 5, 5);
				gbc_lblSelectedFile.gridx = 0;
				gbc_lblSelectedFile.gridy = 0;
				panel_2.add(lblSelectedFile, gbc_lblSelectedFile);
			}
			{
				lblSelFile = new JLabel("(not avilable)");
				lblSelFile.setEnabled(false);
				lblSelFile.setForeground(Color.BLUE);
				GridBagConstraints gbc_lblSelFile = new GridBagConstraints();
				gbc_lblSelFile.anchor = GridBagConstraints.WEST;
				gbc_lblSelFile.insets = new Insets(0, 0, 5, 0);
				gbc_lblSelFile.gridx = 1;
				gbc_lblSelFile.gridy = 0;
				panel_2.add(lblSelFile, gbc_lblSelFile);
			}
			{
				lblMain = new JLabel("Main Project:");
				lblMain.setForeground(Color.GRAY);
				lblMain.setFont(new Font("Arial", Font.BOLD, 11));
				GridBagConstraints gbc_lblMain = new GridBagConstraints();
				gbc_lblMain.anchor = GridBagConstraints.WEST;
				gbc_lblMain.insets = new Insets(0, 15, 5, 5);
				gbc_lblMain.gridx = 0;
				gbc_lblMain.gridy = 1;
				panel_2.add(lblMain, gbc_lblMain);
			}
			{
				lblSelProj = new JLabel("(not avilable)");
				lblSelProj.setForeground(Color.BLUE);
				lblSelProj.setEnabled(false);
				GridBagConstraints gbc_lblSelProj = new GridBagConstraints();
				gbc_lblSelProj.anchor = GridBagConstraints.WEST;
				gbc_lblSelProj.insets = new Insets(0, 0, 5, 0);
				gbc_lblSelProj.gridx = 1;
				gbc_lblSelProj.gridy = 1;
				panel_2.add(lblSelProj, gbc_lblSelProj);
			}
			{
				lblWorkspace = new JLabel("Workspace:");
				lblWorkspace.setForeground(Color.GRAY);
				lblWorkspace.setFont(new Font("Arial", Font.BOLD, 11));
				GridBagConstraints gbc_lblWorkspace = new GridBagConstraints();
				gbc_lblWorkspace.insets = new Insets(0, 15, 0, 5);
				gbc_lblWorkspace.anchor = GridBagConstraints.WEST;
				gbc_lblWorkspace.gridx = 0;
				gbc_lblWorkspace.gridy = 2;
				panel_2.add(lblWorkspace, gbc_lblWorkspace);
			}
			{
				lblSelWks = new JLabel("(not avilable)");
				lblSelWks.setForeground(Color.BLUE);
				lblSelWks.setEnabled(false);
				GridBagConstraints gbc_lblSelWks = new GridBagConstraints();
				gbc_lblSelWks.anchor = GridBagConstraints.WEST;
				gbc_lblSelWks.gridx = 1;
				gbc_lblSelWks.gridy = 2;
				panel_2.add(lblSelWks, gbc_lblSelWks);
			}
		}
		{
			JLabel lblFavoriteCommands = new JLabel("Favorite places:");
			lblFavoriteCommands.setForeground(Color.GRAY);
			lblFavoriteCommands.setFont(new Font("Arial", Font.BOLD, 11));
			GridBagConstraints gbc_lblFavoriteCommands = new GridBagConstraints();
			gbc_lblFavoriteCommands.anchor = GridBagConstraints.WEST;
			gbc_lblFavoriteCommands.insets = new Insets(10, 15, 5, 0);
			gbc_lblFavoriteCommands.gridx = 0;
			gbc_lblFavoriteCommands.gridy = 1;
			contentPanel.add(lblFavoriteCommands, gbc_lblFavoriteCommands);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 2;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				table = new JTable();
				table.setShowVerticalLines(false);
				table.setBackground(UIManager.getColor("Panel.background"));
				table.setRowSelectionAllowed(false);
				table.setFocusable(false);
				table.setRequestFocusEnabled(false);
				table.setAutoCreateRowSorter(true);
				table.setForeground(Color.BLUE);
				table.setFillsViewportHeight(true);
				table.setModel(new DefaultTableModel(
					new Object[][] {
						{null, null},
						{null, null},
					},
					new String[] {
						"New column", "New column"
					}
				));
				scrollPane.setViewportView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			GridBagLayout gbl_buttonPane = new GridBagLayout();
			gbl_buttonPane.columnWidths = new int[]{312, 0, 0, 47, 65, 0};
			gbl_buttonPane.rowHeights = new int[]{23, 0};
			gbl_buttonPane.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_buttonPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			buttonPane.setLayout(gbl_buttonPane);
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
				gbc_horizontalGlue.insets = new Insets(0, 0, 0, 5);
				gbc_horizontalGlue.gridx = 1;
				gbc_horizontalGlue.gridy = 0;
				buttonPane.add(horizontalGlue, gbc_horizontalGlue);
			}
			{
				JLabel lblNewLabel = new JLabel("");
				lblNewLabel.setIcon(new ImageIcon(DialogueCommands.class.getResource("/com/sessonad/quickopener/icons/help.png")));
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
				gbc_lblNewLabel.gridx = 2;
				gbc_lblNewLabel.gridy = 0;
				buttonPane.add(lblNewLabel, gbc_lblNewLabel);
			}
			{
				okButton = new JButton("OK");
				okButton.setBorderPainted(false);
				okButton.setFocusable(false);
				okButton.setFocusPainted(false);
				okButton.setActionCommand("OK");
				GridBagConstraints gbc_okButton = new GridBagConstraints();
				gbc_okButton.anchor = GridBagConstraints.NORTHEAST;
				gbc_okButton.insets = new Insets(5, 0, 0, 5);
				gbc_okButton.gridx = 3;
				gbc_okButton.gridy = 0;
				buttonPane.add(okButton, gbc_okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setBorderPainted(false);
				cancelButton.setFocusable(false);
				cancelButton.setFocusPainted(false);
				cancelButton.setActionCommand("Cancel");
				GridBagConstraints gbc_cancelButton = new GridBagConstraints();
				gbc_cancelButton.insets = new Insets(5, 0, 5, 5);
				gbc_cancelButton.anchor = GridBagConstraints.NORTHEAST;
				gbc_cancelButton.gridx = 4;
				gbc_cancelButton.gridy = 0;
				buttonPane.add(cancelButton, gbc_cancelButton);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.NORTH);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblNewLabel_1 = new JLabel("");
				lblNewLabel_1.setIcon(new ImageIcon(DialogueCommands.class.getResource("/com/sessonad/quickopener/icons/folder-documents-icon48-cu.png")));
				GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
				gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTH;
				gbc_lblNewLabel_1.insets = new Insets(15, 20, 0, 5);
				gbc_lblNewLabel_1.gridx = 0;
				gbc_lblNewLabel_1.gridy = 0;
				panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
			}
			{
				JPanel panel_1 = new JPanel();
				GridBagConstraints gbc_panel_1 = new GridBagConstraints();
				gbc_panel_1.fill = GridBagConstraints.BOTH;
				gbc_panel_1.gridx = 1;
				gbc_panel_1.gridy = 0;
				panel.add(panel_1, gbc_panel_1);
				GridBagLayout gbl_panel_1 = new GridBagLayout();
				gbl_panel_1.columnWidths = new int[]{0, 0};
				gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
				gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
				gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
				panel_1.setLayout(gbl_panel_1);
				{
					JLabel lblNewLabel_2 = new JLabel("Open FileSystem in:");
					GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
					gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
					gbc_lblNewLabel_2.insets = new Insets(14, 20, 5, 0);
					gbc_lblNewLabel_2.gridx = 0;
					gbc_lblNewLabel_2.gridy = 0;
					panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);
				}
				{
					cmdText = new JTextField();
					GridBagConstraints gbc_cmdText = new GridBagConstraints();
					gbc_cmdText.insets = new Insets(0, 15, 5, 20);
					gbc_cmdText.fill = GridBagConstraints.HORIZONTAL;
					gbc_cmdText.gridx = 0;
					gbc_cmdText.gridy = 1;
					panel_1.add(cmdText, gbc_cmdText);
					cmdText.setColumns(10);
				}
			}
		}
	}
	
	private void okActionPerformed(java.awt.event.ActionEvent evt){
		File file= new File(cmdText.getText());
        if(file.exists()&& file.isDirectory()){
        	try {
				Commands.getPlatform().browseInFileSystem(file);
			} catch (Exception e) {e.printStackTrace();}
        	setVisible(false);
	        dispose();
        }else{
        	return;
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
	
	private void cancelActionPerformed(java.awt.event.ActionEvent evt){
		setVisible(false);
        dispose();
	}

	public static void main(String[] args) {
		try {
			DialogueFileSystem dialog = new DialogueFileSystem(null,true,null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
