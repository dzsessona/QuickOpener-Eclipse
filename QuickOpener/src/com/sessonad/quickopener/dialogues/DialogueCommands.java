package com.sessonad.quickopener.dialogues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.sessonad.oscommands.commands.Commands;
import com.sessonad.quickopener.PathFinder;
import com.sessonad.quickopener.preferences.PreferenceUtils;

public class DialogueCommands extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JButton cancelButton;
	private JTextField cmdText;
	private JTextField p1text;
	private JTextField p4text;
	private JTextField p2text;
	private JTextField p5text;
	private JTextField p3text;
	private JTextField p6text;
	private JButton p1btn;
	private JButton p2btn;
	private JButton p3btn;
	private JButton p4btn;
	private JButton p5btn;
	private JButton p6btn;
	private JLabel p1Label;
	private JLabel p2Label;
	private JLabel p3Label;
	private JLabel p4Label;
	private JLabel p5Label;
	private JLabel p6Label;
	private JLabel lblforYourOs;
	private JTable table;
	private JCheckBox addPrefixChk;
	private JCheckBox chckbxcurrentfile;
	private JCheckBox chckbxcurrentfolder;
	private final String cmdos;
	private IWorkbenchWindow window;
	private String currentFile;
    private String currentFolder;
	

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
	}
	
		
	public DialogueCommands(java.awt.Frame parent, boolean modal,final IWorkbenchWindow window){
		super(parent,modal);
		this.window=window;
		initComponents();
		try {
			currentFile=PathFinder.getPathFromSelection(true, window);
			currentFolder = PathFinder.getPathFromSelection(false, window);
		} catch (Exception e1) {
			currentFile = null;
			currentFolder=null;
		}
		table.setModel(new PropertyTableModel(PreferenceUtils.COMMANDS));
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
		cmdText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {checkParameters();}            
            @Override
            public void removeUpdate(DocumentEvent e) {checkParameters();}
            @Override
            public void insertUpdate(DocumentEvent e) {checkParameters();}
        });
		cmdos=Commands.getPlatform().getOperatingSystem().getShellPrefix();
		lblforYourOs.setText("(for your OS is: \'"+cmdos+"\')");
		p1btn.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {fileParamButtonActionPerformed(e, p1text);}});
		p2btn.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {fileParamButtonActionPerformed(e, p2text);}});
		p3btn.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {fileParamButtonActionPerformed(e, p3text);}});
		p4btn.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {fileParamButtonActionPerformed(e, p4text);}});
		p5btn.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {fileParamButtonActionPerformed(e, p5text);}});
		p6btn.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {fileParamButtonActionPerformed(e, p6text);}});
		
		addPrefixChk.addActionListener(new ActionListener() 		{public void actionPerformed(ActionEvent e) {addPrefixActionPerformed(e);}});
		chckbxcurrentfile.addActionListener(new ActionListener() 	{public void actionPerformed(ActionEvent e) {addCurrFileActionPerformed(e);}});
		chckbxcurrentfolder.addActionListener(new ActionListener() 	{public void actionPerformed(ActionEvent e) {addCurrFolderActionPerformed(e);}});
		
		okButton.addActionListener(new ActionListener() 	{ public void actionPerformed(ActionEvent e) {okActionPerformed(e);}});
		cancelButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {cancelActionPerformed(e);}});
	}
	
	public void initComponents() {
		setBounds(100, 100, 609, 394);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblFavoriteCommands = new JLabel("Favorite Commands:");
			lblFavoriteCommands.setForeground(Color.GRAY);
			lblFavoriteCommands.setFont(new Font("Arial", Font.BOLD, 11));
			GridBagConstraints gbc_lblFavoriteCommands = new GridBagConstraints();
			gbc_lblFavoriteCommands.anchor = GridBagConstraints.WEST;
			gbc_lblFavoriteCommands.insets = new Insets(10, 20, 5, 0);
			gbc_lblFavoriteCommands.gridx = 0;
			gbc_lblFavoriteCommands.gridy = 0;
			contentPanel.add(lblFavoriteCommands, gbc_lblFavoriteCommands);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 1;
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
				JPanel panel = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel.getLayout();
				flowLayout.setAlignment(FlowLayout.LEFT);
				GridBagConstraints gbc_panel = new GridBagConstraints();
				gbc_panel.insets = new Insets(0, 0, 0, 5);
				gbc_panel.fill = GridBagConstraints.BOTH;
				gbc_panel.gridx = 0;
				gbc_panel.gridy = 0;
				buttonPane.add(panel, gbc_panel);
				{
					addPrefixChk = new JCheckBox("Add prefix");
					addPrefixChk.setFocusable(false);
					addPrefixChk.setFocusPainted(false);
					panel.add(addPrefixChk);
				}
				{
					lblforYourOs = new JLabel("(for your OS is '')");
					lblforYourOs.setEnabled(false);
					panel.add(lblforYourOs);
				}
				{
					chckbxcurrentfile = new JCheckBox("${currentFile}");
					chckbxcurrentfile.setFocusable(false);
					chckbxcurrentfile.setFocusPainted(false);
					panel.add(chckbxcurrentfile);
				}
				{
					chckbxcurrentfolder = new JCheckBox("${currentFolder}");
					chckbxcurrentfolder.setFocusable(false);
					chckbxcurrentfolder.setFocusPainted(false);
					panel.add(chckbxcurrentfolder);
				}
			}
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
				gbc_cancelButton.insets = new Insets(5, 0, 0, 5);
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
				lblNewLabel_1.setIcon(new ImageIcon(DialogueCommands.class.getResource("/com/sessonad/quickopener/icons/run48.png")));
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
					JLabel lblNewLabel_2 = new JLabel("Launch custom command:");
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
				{
					JPanel panel_2 = new JPanel();
					GridBagConstraints gbc_panel_2 = new GridBagConstraints();
					gbc_panel_2.fill = GridBagConstraints.BOTH;
					gbc_panel_2.gridx = 0;
					gbc_panel_2.gridy = 2;
					panel_1.add(panel_2, gbc_panel_2);
					GridBagLayout gbl_panel_2 = new GridBagLayout();
					gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
					gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0};
					gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
					gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
					panel_2.setLayout(gbl_panel_2);
					{
						p1Label = new JLabel("${param1}:");
						p1Label.setEnabled(false);
						p1Label.setFont(new Font("Arial", Font.BOLD, 11));
						GridBagConstraints gbc_p1Label = new GridBagConstraints();
						gbc_p1Label.anchor = GridBagConstraints.EAST;
						gbc_p1Label.insets = new Insets(0, 20, 5, 5);
						gbc_p1Label.gridx = 0;
						gbc_p1Label.gridy = 0;
						panel_2.add(p1Label, gbc_p1Label);
					}
					{
						p1text = new JTextField();
						p1text.setEnabled(false);
						p1text.setMaximumSize(new Dimension(177, 2147483647));
						GridBagConstraints gbc_p1text = new GridBagConstraints();
						gbc_p1text.anchor = GridBagConstraints.WEST;
						gbc_p1text.insets = new Insets(0, 0, 5, 5);
						gbc_p1text.gridx = 1;
						gbc_p1text.gridy = 0;
						panel_2.add(p1text, gbc_p1text);
						p1text.setColumns(10);
					}
					{
						p1btn = new JButton("file");
						p1btn.setEnabled(false);
						p1btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
						p1btn.setBorderPainted(false);
						p1btn.setFocusPainted(false);
						p1btn.setFocusable(false);
						GridBagConstraints gbc_p1btn = new GridBagConstraints();
						gbc_p1btn.insets = new Insets(0, 0, 5, 5);
						gbc_p1btn.gridx = 2;
						gbc_p1btn.gridy = 0;
						panel_2.add(p1btn, gbc_p1btn);
					}
					{
						Component horizontalGlue = Box.createHorizontalGlue();
						GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
						gbc_horizontalGlue.insets = new Insets(0, 0, 5, 5);
						gbc_horizontalGlue.gridx = 3;
						gbc_horizontalGlue.gridy = 0;
						panel_2.add(horizontalGlue, gbc_horizontalGlue);
					}
					{
						p4Label = new JLabel("${param4}:");
						p4Label.setEnabled(false);
						p4Label.setFont(new Font("Arial", Font.BOLD, 11));
						GridBagConstraints gbc_p4Label = new GridBagConstraints();
						gbc_p4Label.anchor = GridBagConstraints.EAST;
						gbc_p4Label.insets = new Insets(0, 0, 5, 5);
						gbc_p4Label.gridx = 4;
						gbc_p4Label.gridy = 0;
						panel_2.add(p4Label, gbc_p4Label);
					}
					{
						p4text = new JTextField();
						p4text.setEnabled(false);
						GridBagConstraints gbc_p4text = new GridBagConstraints();
						gbc_p4text.anchor = GridBagConstraints.WEST;
						gbc_p4text.insets = new Insets(0, 0, 5, 5);
						gbc_p4text.gridx = 5;
						gbc_p4text.gridy = 0;
						panel_2.add(p4text, gbc_p4text);
						p4text.setColumns(10);
					}
					{
						p4btn = new JButton("file");
						p4btn.setEnabled(false);
						p4btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
						p4btn.setBorderPainted(false);
						p4btn.setFocusPainted(false);
						p4btn.setFocusable(false);
						GridBagConstraints gbc_p4btn = new GridBagConstraints();
						gbc_p4btn.insets = new Insets(0, 0, 5, 25);
						gbc_p4btn.gridx = 6;
						gbc_p4btn.gridy = 0;
						panel_2.add(p4btn, gbc_p4btn);
					}
					{
						p2Label = new JLabel("${param2}:");
						p2Label.setFont(new Font("Arial", Font.BOLD, 11));
						p2Label.setEnabled(false);
						GridBagConstraints gbc_p2Label = new GridBagConstraints();
						gbc_p2Label.anchor = GridBagConstraints.EAST;
						gbc_p2Label.insets = new Insets(0, 0, 5, 5);
						gbc_p2Label.gridx = 0;
						gbc_p2Label.gridy = 1;
						panel_2.add(p2Label, gbc_p2Label);
					}
					{
						p2text = new JTextField();
						p2text.setMaximumSize(new Dimension(177, 2147483647));
						p2text.setEnabled(false);
						p2text.setColumns(10);
						GridBagConstraints gbc_p2text = new GridBagConstraints();
						gbc_p2text.anchor = GridBagConstraints.WEST;
						gbc_p2text.insets = new Insets(0, 0, 5, 5);
						gbc_p2text.gridx = 1;
						gbc_p2text.gridy = 1;
						panel_2.add(p2text, gbc_p2text);
					}
					{
						p2btn = new JButton("file");
						p2btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
						p2btn.setFocusable(false);
						p2btn.setFocusPainted(false);
						p2btn.setEnabled(false);
						p2btn.setBorderPainted(false);
						GridBagConstraints gbc_p2btn = new GridBagConstraints();
						gbc_p2btn.insets = new Insets(0, 0, 5, 5);
						gbc_p2btn.gridx = 2;
						gbc_p2btn.gridy = 1;
						panel_2.add(p2btn, gbc_p2btn);
					}
					{
						Component horizontalGlue = Box.createHorizontalGlue();
						GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
						gbc_horizontalGlue.insets = new Insets(0, 0, 5, 5);
						gbc_horizontalGlue.gridx = 3;
						gbc_horizontalGlue.gridy = 1;
						panel_2.add(horizontalGlue, gbc_horizontalGlue);
					}
					{
						p5Label = new JLabel("${param5}:");
						p5Label.setFont(new Font("Arial", Font.BOLD, 11));
						p5Label.setEnabled(false);
						GridBagConstraints gbc_p5Label = new GridBagConstraints();
						gbc_p5Label.anchor = GridBagConstraints.EAST;
						gbc_p5Label.insets = new Insets(0, 0, 5, 5);
						gbc_p5Label.gridx = 4;
						gbc_p5Label.gridy = 1;
						panel_2.add(p5Label, gbc_p5Label);
					}
					{
						p5text = new JTextField();
						p5text.setEnabled(false);
						p5text.setColumns(10);
						GridBagConstraints gbc_p5text = new GridBagConstraints();
						gbc_p5text.anchor = GridBagConstraints.WEST;
						gbc_p5text.insets = new Insets(0, 0, 5, 5);
						gbc_p5text.gridx = 5;
						gbc_p5text.gridy = 1;
						panel_2.add(p5text, gbc_p5text);
					}
					{
						p5btn = new JButton("file");
						p5btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
						p5btn.setFocusable(false);
						p5btn.setFocusPainted(false);
						p5btn.setEnabled(false);
						p5btn.setBorderPainted(false);
						GridBagConstraints gbc_p5btn = new GridBagConstraints();
						gbc_p5btn.insets = new Insets(0, 0, 5, 0);
						gbc_p5btn.anchor = GridBagConstraints.WEST;
						gbc_p5btn.gridx = 6;
						gbc_p5btn.gridy = 1;
						panel_2.add(p5btn, gbc_p5btn);
					}
					{
						p3Label = new JLabel("${param3}:");
						p3Label.setFont(new Font("Arial", Font.BOLD, 11));
						p3Label.setEnabled(false);
						GridBagConstraints gbc_p3Label = new GridBagConstraints();
						gbc_p3Label.anchor = GridBagConstraints.EAST;
						gbc_p3Label.insets = new Insets(0, 0, 0, 5);
						gbc_p3Label.gridx = 0;
						gbc_p3Label.gridy = 2;
						panel_2.add(p3Label, gbc_p3Label);
					}
					{
						p3text = new JTextField();
						p3text.setMaximumSize(new Dimension(177, 2147483647));
						p3text.setEnabled(false);
						p3text.setColumns(10);
						GridBagConstraints gbc_p3text = new GridBagConstraints();
						gbc_p3text.anchor = GridBagConstraints.WEST;
						gbc_p3text.insets = new Insets(0, 0, 0, 5);
						gbc_p3text.gridx = 1;
						gbc_p3text.gridy = 2;
						panel_2.add(p3text, gbc_p3text);
					}
					{
						p3btn = new JButton("file");
						p3btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
						p3btn.setFocusable(false);
						p3btn.setFocusPainted(false);
						p3btn.setEnabled(false);
						p3btn.setBorderPainted(false);
						GridBagConstraints gbc_p3btn = new GridBagConstraints();
						gbc_p3btn.insets = new Insets(0, 0, 0, 5);
						gbc_p3btn.gridx = 2;
						gbc_p3btn.gridy = 2;
						panel_2.add(p3btn, gbc_p3btn);
					}
					{
						Component horizontalGlue = Box.createHorizontalGlue();
						GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
						gbc_horizontalGlue.insets = new Insets(0, 0, 0, 5);
						gbc_horizontalGlue.gridx = 3;
						gbc_horizontalGlue.gridy = 2;
						panel_2.add(horizontalGlue, gbc_horizontalGlue);
					}
					{
						p6Label = new JLabel("${param6}:");
						p6Label.setFont(new Font("Arial", Font.BOLD, 11));
						p6Label.setEnabled(false);
						GridBagConstraints gbc_p6Label = new GridBagConstraints();
						gbc_p6Label.anchor = GridBagConstraints.EAST;
						gbc_p6Label.insets = new Insets(0, 0, 0, 5);
						gbc_p6Label.gridx = 4;
						gbc_p6Label.gridy = 2;
						panel_2.add(p6Label, gbc_p6Label);
					}
					{
						p6text = new JTextField();
						p6text.setEnabled(false);
						p6text.setColumns(10);
						GridBagConstraints gbc_p6text = new GridBagConstraints();
						gbc_p6text.anchor = GridBagConstraints.WEST;
						gbc_p6text.insets = new Insets(0, 0, 0, 5);
						gbc_p6text.gridx = 5;
						gbc_p6text.gridy = 2;
						panel_2.add(p6text, gbc_p6text);
					}
					{
						p6btn = new JButton("file");
						p6btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
						p6btn.setFocusable(false);
						p6btn.setFocusPainted(false);
						p6btn.setEnabled(false);
						p6btn.setBorderPainted(false);
						GridBagConstraints gbc_p6btn = new GridBagConstraints();
						gbc_p6btn.anchor = GridBagConstraints.WEST;
						gbc_p6btn.gridx = 6;
						gbc_p6btn.gridy = 2;
						panel_2.add(p6btn, gbc_p6btn);
					}
				}
			}
		}
	}
	
	private void okActionPerformed(java.awt.event.ActionEvent evt){
		String command = cmdText.getText();
		if(command==null || command.isEmpty())return;
		else {
			if (command.contains("${param1}"))command = command.replace("${param1}", p1text.getText());
			if (command.contains("${param2}"))command = command.replace("${param2}", p2text.getText());
			if (command.contains("${param3}"))command = command.replace("${param3}", p3text.getText());
			if (command.contains("${param4}"))command = command.replace("${param4}", p4text.getText());
			if (command.contains("${param5}"))command = command.replace("${param5}", p5text.getText());
			if (command.contains("${param6}"))command = command.replace("${param6}", p6text.getText());
			if (command.contains("${currentFile}") && currentFile != null && !currentFile.isEmpty())
				command = command.replace("${currentFile}", currentFile);
			if (command.contains("${currentFolder}") && currentFolder != null && !currentFolder.isEmpty())
				command = command.replace("${currentFolder}", currentFolder);			
			
			if (command != null && command.isEmpty()) {
				String msg=QuickMessages.CONFIRM_COMMAND_PREFIX+command+QuickMessages.CONFIRM_COMMAND_SUFFIX;
				boolean result = MessageDialog.openConfirm(null, "Confirm command", msg);
				if(result){
					try {Runtime.getRuntime().exec(command);} catch (IOException e) {}
					setVisible(false);
			        dispose();
				}else{
					return;
				}
            }
		}		
	}
	
	private void cancelActionPerformed(java.awt.event.ActionEvent evt){
		setVisible(false);
        dispose();
	}
	
	private void fileParamButtonActionPerformed(java.awt.event.ActionEvent evt,JTextField field) {  
		DialogueCustomFileChooser dialogue = new DialogueCustomFileChooser(null,true,window);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int x = (screenSize.width - dialogue.getWidth()) / 2;
        final int y = (screenSize.height - dialogue.getHeight()) / 2;
        dialogue.setLocation(x, y);
        dialogue.setVisible(true);
        String userCommand = (DialogueCustomFileChooser.getCommand().isEmpty())?null:DialogueCustomFileChooser.getCommand();
        field.setText(userCommand);
	}
	
	private void addPrefixActionPerformed(java.awt.event.ActionEvent evt) { 
		if (cmdos==null) return;
	    boolean value = addPrefixChk.isSelected();
	    String text= cmdText.getText();
	    if(value && text!=null && !text.startsWith(cmdos)){
	        text=cmdos + text;
	    }else if(!value && text!=null && text.startsWith(cmdos)){
	        text=text.replaceAll(cmdos, "");
	    }
	    cmdText.setText(text);
	}
	
	private void addCurrFileActionPerformed(java.awt.event.ActionEvent evt) { 
		boolean value = chckbxcurrentfile.isSelected();
        String text= cmdText.getText();
        if(value && !text.contains("${currentFile}"))
            text=text + " ${currentFile}";  
        else if(!value && text.contains("${currentFile}"))
            text=text.replaceAll("\\$\\{currentFile\\}", ""); 
        cmdText.setText(text);	
	}
	
	private void addCurrFolderActionPerformed(java.awt.event.ActionEvent evt) { 
		boolean value = chckbxcurrentfolder.isSelected();
        String text= cmdText.getText();
        if(value && !text.contains("${currentFolder}"))
            text=text + " ${currentFolder}";  
        else if(!value && text.contains("${currentFolder}"))
            text=text.replaceAll("\\$\\{currentFolder\\}", ""); 
        cmdText.setText(text);	
	}

	public static void main(String[] args) {
		try {
			DialogueCommands dialog = new DialogueCommands(null,true,null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkParameters() {
        String text=cmdText.getText();
        boolean p1 = text.contains("${param1}");
        p1Label.setEnabled(p1);
        p1text.setEnabled(p1);
        p1btn.setEnabled(p1);
        if(!p1)p1text.setText("");
        boolean p2 = text.contains("${param2}");
        p2Label.setEnabled(p2);
        p2text.setEnabled(p2);
        p2btn.setEnabled(p2);
        if(!p2)p2text.setText("");        
        boolean p3 = text.contains("${param3}");
        p3Label.setEnabled(p3);
        p3text.setEnabled(p3);
        p3btn.setEnabled(p3);
        if(!p3)p3text.setText("");        
        boolean p4 = text.contains("${param4}");
        p4Label.setEnabled(p4);
        p4text.setEnabled(p4);
        p4btn.setEnabled(p4);
        if(!p4)p4text.setText("");
        boolean p5 = text.contains("${param5}");
        p5Label.setEnabled(p5);
        p5text.setEnabled(p5);
        p5btn.setEnabled(p5);
        if(!p5)p5text.setText("");
        boolean p6 = text.contains("${param6}");
        p6Label.setEnabled(p6);
        p6text.setEnabled(p6);
        p6btn.setEnabled(p6);
        if(!p6)p6text.setText("");
    }
}
