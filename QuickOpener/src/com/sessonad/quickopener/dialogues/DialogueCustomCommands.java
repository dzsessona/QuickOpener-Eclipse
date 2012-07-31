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

public class DialogueCustomCommands extends JDialog {


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField cmdText;
	private JTable table;	
    public static final int CHARSNUMBER = 80;
    private IWorkbenchWindow window;

	
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
	
	public String getCommand(){
        return cmdText.getText();
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
			DialogueCustomCommands dialog = new DialogueCustomCommands(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogueCustomCommands(IWorkbenchWindow window) {
		setTitle("Run command...");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogueCustomCommands.class.getResource("/com/sessonad/quickopener/icons/run.png")));
		this.window=window;
		setBounds(100, 100, 497, 441);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblOopenShellIn = new JLabel("Launch custom command:");
		lblOopenShellIn.setBounds(91, 20, 137, 14);
		contentPanel.add(lblOopenShellIn);
		
		cmdText = new JTextField();
		cmdText.setBounds(91, 39, 357, 20);
		contentPanel.add(cmdText);
		cmdText.setColumns(10);
		
		JLabel label = new JLabel("");
		label.setBounds(10, 132, 46, 14);
		contentPanel.add(label);

		
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
		
		table.setModel(new PropertyTableModel("folder"));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		

		
		JLabel lblIcon = new JLabel(" ");
		lblIcon.setIcon(new ImageIcon(DialogueCustomCommands.class.getResource("/com/sessonad/quickopener/icons/run48.png")));
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
				        if(file.exists()&& file.isDirectory()){
				        	try {
				        		Commands.getPlatform().browseInFileSystem(file);								
							} catch (Exception e) {								
								e.printStackTrace();
							}
				        	doClose();
				        }else{
				        	
				        }
					}
				});
				
				JLabel label_1 = new JLabel("");
				label_1.setToolTipText("<html><span color=\"blue\">Click on any path to set the input box.</span><br/>\r\n<br/>\r\nYou can customize the your preferred commands in:<br/>\r\n<span color=\"blue\">Window > Preferences >  QuickOpener\r\n</html>");
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
						doClose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
