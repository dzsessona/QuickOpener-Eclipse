package com.sessonad.quickopener.handlers;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

public class DialogueCommands extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField cmdText;
	private JTable table;

	
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogueCommands dialog = new DialogueCommands();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogueCommands() {
		setBounds(100, 100, 497, 441);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\sessonad\\git\\QuickOpener-Eclipse\\QuickOpener\\icons\\terminal48-cu2.png"));
		lblNewLabel.setBounds(21, 11, 60, 48);
		contentPanel.add(lblNewLabel);
		
		JLabel lblOopenShellIn = new JLabel("Open shell in:");
		lblOopenShellIn.setBounds(91, 14, 80, 14);
		contentPanel.add(lblOopenShellIn);
		
		cmdText = new JTextField();
		cmdText.setBounds(91, 39, 380, 20);
		contentPanel.add(cmdText);
		cmdText.setColumns(10);
		
		JLabel lblSelectedFile = new JLabel("Selected file:");
		lblSelectedFile.setForeground(Color.GRAY);
		lblSelectedFile.setFont(new Font("Arial", Font.BOLD, 11));
		lblSelectedFile.setBounds(21, 82, 71, 14);
		contentPanel.add(lblSelectedFile);
		
		JLabel lblNewLabel_1 = new JLabel("Main Project:");
		lblNewLabel_1.setForeground(Color.GRAY);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 11));
		lblNewLabel_1.setBounds(21, 101, 71, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel label = new JLabel("");
		label.setBounds(10, 132, 46, 14);
		contentPanel.add(label);
		
		JLabel lblNewLabel_2 = new JLabel("(not available)");
		lblNewLabel_2.setEnabled(false);
		lblNewLabel_2.setBounds(101, 82, 370, 14);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblnotAvailable = new JLabel("(not available)");
		lblnotAvailable.setEnabled(false);
		lblnotAvailable.setBounds(101, 101, 370, 14);
		contentPanel.add(lblnotAvailable);
		
		JLabel lblFavo = new JLabel("Favorite places:");
		lblFavo.setForeground(Color.GRAY);
		lblFavo.setFont(new Font("Arial", Font.BOLD, 11));
		lblFavo.setBounds(20, 165, 92, 14);
		contentPanel.add(lblFavo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 184, 461, 175);
		contentPanel.add(scrollPane);
		
		table = new JTable();
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
		
		table.setModel(new PropertyTableModel("folder"));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		JLabel lblWorkspace = new JLabel("Workspace:");
		lblWorkspace.setForeground(Color.GRAY);
		lblWorkspace.setFont(new Font("Arial", Font.BOLD, 11));
		lblWorkspace.setBounds(21, 120, 71, 14);
		contentPanel.add(lblWorkspace);
		
		JLabel lblNewLabel_3 = new JLabel("(not available)");
		lblNewLabel_3.setEnabled(false);
		lblNewLabel_3.setBounds(101, 120, 370, 14);
		contentPanel.add(lblNewLabel_3);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMaxWidth(400);
		table.getColumnModel().getColumn(0).setMinWidth(100);		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
