package com.sessonad.quickopener.dialogues;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
import java.awt.Component;

public class DialogueCustomCommands extends JDialog {


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField cmdText;
	private JTable table;	
    public static final int CHARSNUMBER = 80;
    private IWorkbenchWindow window;
    private JTextField p1text;
    private JTextField p2text;
    private JTextField p3text;
    private JTextField p4text;
    private JTextField p5text;
    private JTextField p6text;
    private JLabel p1label;
    private JLabel p2label;
    private JLabel p3label;
    private JLabel p4label;
    private JLabel p5label;
    private JLabel p6label;
    private JButton p1btn;
    private JButton p2btn;
    private JButton p3btn;
    private JButton p4btn;
    private JButton p5btn;
    private JButton p6btn;
    

	
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
	public DialogueCustomCommands(final IWorkbenchWindow window) {
		setTitle("Run command...");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogueCustomCommands.class.getResource("/com/sessonad/quickopener/icons/run.png")));
		this.window=window;
		setBounds(100, 100, 574, 455);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblOopenShellIn = new JLabel("Launch custom command:");
		lblOopenShellIn.setBounds(91, 20, 137, 14);
		contentPanel.add(lblOopenShellIn);
		
		cmdText = new JTextField();
		cmdText.setBounds(91, 39, 417, 20);
		contentPanel.add(cmdText);
		cmdText.setColumns(10);
		cmdText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {checkParameters();}            
            @Override
            public void removeUpdate(DocumentEvent e) {checkParameters();}
            @Override
            public void insertUpdate(DocumentEvent e) {checkParameters();}
        });
		
		JLabel label = new JLabel("");
		label.setBounds(10, 132, 46, 14);
		contentPanel.add(label);

		
		JLabel lblFavo = new JLabel("Favorite places:");
		lblFavo.setForeground(Color.GRAY);
		lblFavo.setFont(new Font("Arial", Font.BOLD, 11));
		lblFavo.setBounds(20, 165, 92, 14);
		contentPanel.add(lblFavo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 184, 538, 175);
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
		
		JPanel panel = new JPanel();
		panel.setBounds(91, 70, 417, 84);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		p1label = new JLabel("${param1}:");
		p1label.setEnabled(false);
		p1label.setForeground(Color.BLACK);
		p1label.setFont(new Font("Arial", Font.BOLD, 11));
		p1label.setBounds(0, 3, 64, 14);
		panel.add(p1label);
		
		p1text = new JTextField();
		p1text.setEnabled(false);
		p1text.setBounds(64, 0, 78, 20);
		panel.add(p1text);
		p1text.setColumns(10);
		
		p1btn = new JButton("file");
		p1btn.setEnabled(false);
		p1btn.setBorderPainted(false);
		p1btn.setFocusable(false);
		p1btn.setFocusPainted(false);
		p1btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DialogueCustomFileChooser dialogue = new DialogueCustomFileChooser(window);
		        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		        final int x = (screenSize.width - dialogue.getWidth()) / 2;
		        final int y = (screenSize.height - dialogue.getHeight()) / 2;
		        dialogue.setLocation(x, y);
		        dialogue.setVisible(true);
		        String userCommand = (DialogueCustomFileChooser.getCommand().isEmpty())?null:DialogueCustomFileChooser.getCommand();
		        p1text.setText(userCommand);
			}
		});
		p1btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
		p1btn.setBounds(148, -1, 47, 23);
		panel.add(p1btn);
		
		p2label = new JLabel("${param2}:");
		p2label.setEnabled(false);
		p2label.setForeground(Color.BLACK);
		p2label.setFont(new Font("Arial", Font.BOLD, 11));
		p2label.setBounds(0, 27, 64, 14);
		panel.add(p2label);
		
		p2text = new JTextField();
		p2text.setEnabled(false);
		p2text.setColumns(10);
		p2text.setBounds(64, 25, 78, 20);
		panel.add(p2text);
		
		p2btn = new JButton("file");
		p2btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DialogueCustomFileChooser dialogue = new DialogueCustomFileChooser(window);
		        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		        final int x = (screenSize.width - dialogue.getWidth()) / 2;
		        final int y = (screenSize.height - dialogue.getHeight()) / 2;
		        dialogue.setLocation(x, y);
		        dialogue.setVisible(true);
		        String userCommand = (DialogueCustomFileChooser.getCommand().isEmpty())?null:DialogueCustomFileChooser.getCommand();
		        p2text.setText(userCommand);
			}
		});
		p2btn.setEnabled(false);
		p2btn.setBorderPainted(false);
		p2btn.setFocusable(false);
		p2btn.setFocusPainted(false);
		p2btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
		p2btn.setBounds(148, 23, 47, 23);
		panel.add(p2btn);
		
		p3label = new JLabel("${param3}:");
		p3label.setEnabled(false);
		p3label.setForeground(Color.BLACK);
		p3label.setFont(new Font("Arial", Font.BOLD, 11));
		p3label.setBounds(0, 52, 64, 14);
		panel.add(p3label);
		
		p3text = new JTextField();
		p3text.setEnabled(false);
		p3text.setColumns(10);
		p3text.setBounds(64, 49, 78, 20);
		panel.add(p3text);
		
		p3btn = new JButton("file");
		p3btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogueCustomFileChooser dialogue = new DialogueCustomFileChooser(window);
		        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		        final int x = (screenSize.width - dialogue.getWidth()) / 2;
		        final int y = (screenSize.height - dialogue.getHeight()) / 2;
		        dialogue.setLocation(x, y);
		        dialogue.setVisible(true);
		        String userCommand = (DialogueCustomFileChooser.getCommand().isEmpty())?null:DialogueCustomFileChooser.getCommand();
		        p3text.setText(userCommand);
			}
		});
		p3btn.setEnabled(false);
		p3btn.setBorderPainted(false);
		p3btn.setFocusable(false);
		p3btn.setFocusPainted(false);
		p3btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
		p3btn.setBounds(148, 48, 47, 23);
		panel.add(p3btn);
		
		p4label = new JLabel("${param4}:");
		p4label.setEnabled(false);
		p4label.setForeground(Color.BLACK);
		p4label.setFont(new Font("Arial", Font.BOLD, 11));
		p4label.setBounds(225, 3, 58, 14);
		panel.add(p4label);
		
		p4text = new JTextField();
		p4text.setEnabled(false);
		p4text.setColumns(10);
		p4text.setBounds(289, 0, 78, 20);
		panel.add(p4text);
		
		p4btn = new JButton("file");
		p4btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogueCustomFileChooser dialogue = new DialogueCustomFileChooser(window);
		        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		        final int x = (screenSize.width - dialogue.getWidth()) / 2;
		        final int y = (screenSize.height - dialogue.getHeight()) / 2;
		        dialogue.setLocation(x, y);
		        dialogue.setVisible(true);
		        String userCommand = (DialogueCustomFileChooser.getCommand().isEmpty())?null:DialogueCustomFileChooser.getCommand();
		        p4text.setText(userCommand);
			}
		});
		p4btn.setEnabled(false);
		p4btn.setBorderPainted(false);
		p4btn.setFocusable(false);
		p4btn.setFocusPainted(false);
		p4btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
		p4btn.setBounds(370, -1, 47, 23);
		panel.add(p4btn);
		
		p5label = new JLabel("${param5}:");
		p5label.setEnabled(false);
		p5label.setForeground(Color.BLACK);
		p5label.setFont(new Font("Arial", Font.BOLD, 11));
		p5label.setBounds(225, 27, 58, 14);
		panel.add(p5label);
		
		p5text = new JTextField();
		p5text.setEnabled(false);
		p5text.setColumns(10);
		p5text.setBounds(289, 24, 78, 20);
		panel.add(p5text);
		
		p5btn = new JButton("file");
		p5btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogueCustomFileChooser dialogue = new DialogueCustomFileChooser(window);
		        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		        final int x = (screenSize.width - dialogue.getWidth()) / 2;
		        final int y = (screenSize.height - dialogue.getHeight()) / 2;
		        dialogue.setLocation(x, y);
		        dialogue.setVisible(true);
		        String userCommand = (DialogueCustomFileChooser.getCommand().isEmpty())?null:DialogueCustomFileChooser.getCommand();
		        p5text.setText(userCommand);
			}
		});
		p5btn.setEnabled(false);
		p5btn.setBorderPainted(false);
		p5btn.setFocusable(false);
		p5btn.setFocusPainted(false);
		p5btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
		p5btn.setBounds(370, 23, 47, 23);
		panel.add(p5btn);
		
		p6label = new JLabel("${param6}:");
		p6label.setEnabled(false);
		p6label.setForeground(Color.BLACK);
		p6label.setFont(new Font("Arial", Font.BOLD, 11));
		p6label.setBounds(225, 52, 58, 14);
		panel.add(p6label);
		
		p6text = new JTextField();
		p6text.setEnabled(false);
		p6text.setColumns(10);
		p6text.setBounds(289, 49, 78, 20);
		panel.add(p6text);
		
		p6btn = new JButton("file");
		p6btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogueCustomFileChooser dialogue = new DialogueCustomFileChooser(window);
		        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		        final int x = (screenSize.width - dialogue.getWidth()) / 2;
		        final int y = (screenSize.height - dialogue.getHeight()) / 2;
		        dialogue.setLocation(x, y);
		        dialogue.setVisible(true);
		        String userCommand = (DialogueCustomFileChooser.getCommand().isEmpty())?null:DialogueCustomFileChooser.getCommand();
		        p6text.setText(userCommand);
			}
		});
		p6btn.setEnabled(false);
		p6btn.setBorderPainted(false);
		p6btn.setFocusable(false);
		p6btn.setFocusPainted(false);
		p6btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
		p6btn.setBounds(370, 48, 47, 23);
		panel.add(p6btn);
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
		checkParameters();
	}
	
	private void checkParameters() {
        String text=cmdText.getText();
        boolean p1 = text.contains("${param1}");
        p1label.setEnabled(p1);
        p1text.setEnabled(p1);
        p1btn.setEnabled(p1);
        if(!p1)p1text.setText("");
        boolean p2 = text.contains("${param2}");
        p2label.setEnabled(p2);
        p2text.setEnabled(p2);
        p2btn.setEnabled(p2);
        if(!p2)p2text.setText("");        
        boolean p3 = text.contains("${param3}");
        p3label.setEnabled(p3);
        p3text.setEnabled(p3);
        p3btn.setEnabled(p3);
        if(!p3)p3text.setText("");        
        boolean p4 = text.contains("${param4}");
        p4label.setEnabled(p4);
        p4text.setEnabled(p4);
        p4btn.setEnabled(p4);
        if(!p4)p4text.setText("");
        boolean p5 = text.contains("${param5}");
        p5label.setEnabled(p5);
        p5text.setEnabled(p5);
        p5btn.setEnabled(p5);
        if(!p5)p5text.setText("");
        boolean p6 = text.contains("${param6}");
        p6label.setEnabled(p6);
        p6text.setEnabled(p6);
        p6btn.setEnabled(p6);
        if(!p6)p6text.setText("");
    }
}
