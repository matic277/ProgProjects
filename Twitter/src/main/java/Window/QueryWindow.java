package Window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QueryWindow extends JFrame implements /*ComponentListener,*/ ActionListener {

    private static final long serialVersionUID = 7831001409134634647L;

    JPanel panel;
    
    MainWindow mainWindow;
    
    int width, height;
    
    int buttonSpacing;

    ArrayList<TextField> inputs;
    ArrayList<Button> buttons;
    ArrayList<JLabel> labels;
    Button addNewFieldButton;
    TextField filePathInput;
    
    Dimension topPanelSize;
    Dimension bottomPanelSize;
    
    Dimension buttonSize;
    
    Dimension textFieldSize;
    Dimension addTextFieldSize;
    
    Rectangle leftPanel;
    Rectangle topPanel;
    Rectangle bottomPanel;
    Rectangle rightPanel;

    Color labelColor = Color.white;
    Color bgColor = Color.decode("#f2f2f2");
    Color borderColor = Color.decode("#b7b7b7");
    
    public QueryWindow(MainWindow mainWindow, ArrayList<String> presetQueries) {
        width = 500;
        this.mainWindow = mainWindow;
        
        buttons = new ArrayList<Button>(10);
        labels = new ArrayList<JLabel>(10);
        inputs = new ArrayList<TextField>(10);
        
        if (presetQueries != null) {
            for (String s : presetQueries)
            inputs.add(new TextField(s));
        }
        inputs.add(new TextField(""));

        filePathInput = new TextField("");
        
        addNewFieldButton = new Button("+");
        
        buttonSpacing = 15;
        
        buttonSize = new Dimension(110, 30);
        textFieldSize = new Dimension(150, 30);
        addTextFieldSize = new Dimension(35, 30);
        
        initPanels();
        initButtons();
        initPanel();
    }
    
    private void initPanels() {
        topPanelSize = new Dimension(width - 2 * buttonSpacing, 100);
        topPanel = new Rectangle(
            buttonSpacing,
            buttonSpacing,
            topPanelSize.width,
            topPanelSize.height
        );
        
        int numOfInputs = inputs.size();
        int leftPanelHeight = numOfInputs * textFieldSize.height + ((numOfInputs + 1) * buttonSpacing);
        
        leftPanel = new Rectangle(
            buttonSpacing,
            topPanel.y + topPanel.height + buttonSpacing,
            (int)((width / 2) - (buttonSpacing * 1.5)),
            leftPanelHeight
        );
        
        rightPanel = new Rectangle(
            leftPanel.x + leftPanel.width + (buttonSpacing / 2),
            leftPanel.y,
            (int)((width / 2) - (buttonSpacing * 1.5)),
            leftPanelHeight
        );
        
        
        bottomPanelSize = new Dimension(topPanelSize.width, buttonSize.height + (buttonSpacing * 2));
        
        bottomPanel = new Rectangle(
            buttonSpacing,
            leftPanel.y + leftPanel.height + buttonSpacing,
            bottomPanelSize.width,
            bottomPanelSize.height
        );
        
        this.height = (4 * buttonSpacing) + topPanel.height + leftPanel.height + bottomPanel.height;
    }
    
    private void initButtons() {
        // add fields
        Rectangle tfbounds = new Rectangle(
            leftPanel.x + buttonSpacing,
            leftPanel.y + buttonSpacing,
            textFieldSize.width,
            textFieldSize.height
        );
        inputs.get(0).setBounds(tfbounds);
        for (int i=1; i<inputs.size(); i++) {
            Rectangle newBounds = new Rectangle(tfbounds);
            newBounds.y = tfbounds.y + i * (tfbounds.height + buttonSpacing);
            inputs.get(i).setBounds(newBounds);
            System.out.println("set bounds at: " + newBounds.toString());
        }
        
        // add new field button
        Rectangle lastBounds = new Rectangle(inputs.get(inputs.size()-1).getBounds());
        addNewFieldButton.setBounds(
            lastBounds.getBounds().x + lastBounds.getBounds().width + buttonSpacing,
            lastBounds.getBounds().y,
            addTextFieldSize.width,
            addTextFieldSize.height
        );
        addNewFieldButton.addActionListener(this);
        addNewFieldButton.setAction(() -> {
            TextField lastField = inputs.get(inputs.size()-1);
            Rectangle bounds = lastField.getBounds();
            TextField newField = new TextField("");
            newField.setBounds(
                bounds.x,
                bounds.y + bounds.height + buttonSpacing,
                textFieldSize.width,
                textFieldSize.height
            );
            addNewFieldButton.setBounds(
                newField.getBounds().x + newField.getBounds().width + buttonSpacing,
                lastField.getBounds().y,
                addTextFieldSize.width,
                addTextFieldSize.height
            );
            inputs.add(newField);
            panel.add(newField);

            // TODO works
            leftPanel.height += buttonSpacing + textFieldSize.height;
            rightPanel.height = leftPanel.height;
            bottomPanel.y += buttonSpacing + textFieldSize.height;
            buttons.forEach(b -> b.setBounds(b.getBounds().x, b.getBounds().y + buttonSpacing + textFieldSize.height, b.getBounds().width, b.getBounds().height));
            this.height += buttonSpacing + textFieldSize.height;
            this.panel.setPreferredSize(new Dimension(width, height));
            this.pack();
        });
        buttons.add(addNewFieldButton);
        
        // cancel button
        Button cancel = new Button("Cancel");
        cancel.setBounds(
            bottomPanel.x + bottomPanel.width - buttonSpacing - buttonSize.width,
            bottomPanel.y + buttonSpacing,
            buttonSize.width,
            buttonSize.height
        );
        cancel.addActionListener(this);
        cancel.setAction(() -> this.dispose());
        buttons.add(cancel);
        
        // ok button
        Button ok = new Button("OK");
        ok.setBounds(
            cancel.getBounds().x - buttonSpacing - buttonSize.width,
            bottomPanel.y + buttonSpacing,
            buttonSize.width,
            buttonSize.height
        );
        ok.addActionListener(this);
        ok.setAction(() -> {
            // if path to query file is set, ignore the inputs
            String file = filePathInput.getText();
            
            if (file != null && file.length() > 0) {
                if (new File(file).exists()) {
                    ArrayList<String> queries = readQueryFile(file);
                    mainWindow.setQueryFile(queries);
                    this.dispose();
                } else {
                    filePathInput.setBackground(Color.red);
                }
            }
            else {
                ArrayList<String> list = new ArrayList<String>(inputs.size());
                for (TextField tf : inputs) if (!tf.getText().trim().isEmpty()) list.add(tf.getText());
                mainWindow.setQueryList(list);
                this.dispose();
            }
        });
        buttons.add(ok);
        
        // file path field
        JLabel fileInputLabel = new JLabel("Path to queries:");
        fileInputLabel.setBounds(
            rightPanel.x + buttonSpacing,
            rightPanel.y + buttonSpacing,
            90,
            30
        );
        filePathInput = new TextField("");
        filePathInput.setBounds(
            rightPanel.x + buttonSpacing + fileInputLabel.getBounds().width,
            fileInputLabel.getBounds().y,
            100,
            textFieldSize.height
        );
        labels.add(fileInputLabel);
        
        // top title
        JLabel label = new JLabel("Query");
        label.setBounds(
            topPanel.x + buttonSpacing,
            topPanel.y + buttonSpacing,
            topPanel.width - 2*buttonSpacing,
            (topPanel.height - 2*buttonSpacing)
        );
        labels.add(label);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 40));
    }
    
    private ArrayList<String> readQueryFile(String path) {
        ArrayList<String> list = new ArrayList<String>(10);
        try (Stream<String> lines = Files.lines(Paths.get(path), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> {
                list.add(line);
            });
        } catch (IOException e) {
            System.out.println("Error reading file '"+path+"'.");
            e.printStackTrace();
        }
        return list;
    }

    private void initPanel() {
        panel = new JPanel() {
            private static final long serialVersionUID = -7047375427827166299L;
            protected void paintComponent(Graphics g){
                g.setColor(bgColor);
                g.fillRect(0, 0, width, height);
                
                g.setColor(labelColor);
                g.fillRect(leftPanel.x, leftPanel.y, leftPanel.width, leftPanel.height);
                g.fillRect(rightPanel.x, rightPanel.y, rightPanel.width, rightPanel.height);
                g.fillRect(topPanel.x, topPanel.y, topPanel.width, topPanel.height);
                g.fillRect(bottomPanel.x, bottomPanel.y, bottomPanel.width, bottomPanel.height);
                
                g.setColor(borderColor);
                g.drawRect(leftPanel.x, leftPanel.y, leftPanel.width, leftPanel.height);
                g.drawRect(rightPanel.x, rightPanel.y, rightPanel.width, rightPanel.height);
                g.drawRect(topPanel.x, topPanel.y, topPanel.width, topPanel.height);
                g.drawRect(bottomPanel.x, bottomPanel.y, bottomPanel.width, bottomPanel.height);
                
                try { Thread.sleep(1000/300); }
                catch (Exception e) { e.printStackTrace(); }
                super.repaint();
            }
        };
        this.add(panel);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(width, height));
        //panel.addComponentListener(this);
        
        labels.forEach(l -> panel.add(l));
        buttons.forEach(b -> panel.add(b));
        inputs.forEach(i -> panel.add(i));
        panel.add(filePathInput);
        
        this.setResizable(false);
        this.setTitle("Query");
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        
    }
    
    

    
    
    public void componentHidden(ComponentEvent arg0) {}
    public void componentMoved(ComponentEvent arg0) {}
    public void componentShown(ComponentEvent arg0) {}

    @Override
    public void actionPerformed(ActionEvent event) {
        ((IComponentFunction) event.getSource()).performAction();
    }
}
