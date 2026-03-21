import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

// Jonathan Ayala
// CS-250
public class TopFiveDestinationList {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	TopDestinationListFrame topDestinationListFrame = new TopDestinationListFrame();
                topDestinationListFrame.setTitle("Top 5 Destination List");
                topDestinationListFrame.setVisible(true);
            }
        });
    }
}


class TopDestinationListFrame extends JFrame {
    private DefaultListModel listModel;

    public TopDestinationListFrame() {
        super("Top Five Destination List");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 750);

        listModel = new DefaultListModel();


        //Make updates to your top 5 list below. Import the new image files to resources directory.
        addDestinationNameAndPicture("1. Tokyo, Japan- A neon-lit blend of tradition and futurism.", new ImageIcon(getClass().getResource("/resources/tokyo.jpg")), "https://www.gotokyo.org");
        addDestinationNameAndPicture("2. Maui, Hawaii- Pristine beaches and breathtaking sunsets.", new ImageIcon(getClass().getResource("/resources/maui.jpg")), "https://www.gohawaii.com"); 
        addDestinationNameAndPicture("3. Rome, Italy- Walking through a living museum of history.", new ImageIcon(getClass().getResource("/resources/rome.jpg")), "https://www.turismoroma.it");
        addDestinationNameAndPicture("4. Moab, Utah- Home to iconic red arches and adventures.", new ImageIcon(getClass().getResource("/resources/moab.jpg")), "https://www.discovermoab.com");
        addDestinationNameAndPicture("5. Santorini, Greece- The quintessential blue-domed paradise.", new ImageIcon(getClass().getResource("/resources/santorini.jpg")), "https://www.visitgreece.gr");
        
        JList list = new JList(listModel);
        JScrollPane scrollPane = new JScrollPane(list);
        
        //listSelectionListner was recommended to get URL
        list.addListSelectionListener(
        		evt -> {
        			//Added to open web browser once       	
        			if(!evt.getValueIsAdjusting()) {
        				
        			final TextAndIcon selectedModel = (TextAndIcon) list.getSelectedValue();
        			try {
        			Desktop.getDesktop().browse(new URI(selectedModel.getUrl()));
        			} catch(IOException | URISyntaxException e) {
        				
        				e.printStackTrace();
        			}	
        			}     		
        		}
        );

        TextAndIconListCellRenderer renderer = new TextAndIconListCellRenderer(2);

        list.setCellRenderer(renderer);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void addDestinationNameAndPicture(String text, Icon icon, String url) {
        TextAndIcon tai = new TextAndIcon(text, icon, url);
        listModel.addElement(tai);
    }
}


class TextAndIcon {
    private String text;
    private Icon icon;
    private String url;
    
    
    public TextAndIcon(String text, Icon icon, String url) {
        this.text = text;
        this.icon = icon;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }
    //Getter method
    public String getUrl() {
    	return url;
    }
    
    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
    // Setter method
    public void setUrl(String url) {
    	this.url = url;
    }
}


class TextAndIconListCellRenderer extends JLabel implements ListCellRenderer {
    private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    private Border insideBorder;

    public TextAndIconListCellRenderer() {
        this(0, 0, 0, 0);
    }

    public TextAndIconListCellRenderer(int padding) {
        this(padding, padding, padding, padding);
    }

    public TextAndIconListCellRenderer(int topPadding, int rightPadding, int bottomPadding, int leftPadding) {
        insideBorder = BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding);
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value,
    int index, boolean isSelected, boolean hasFocus) {
        // The object from the combo box model MUST be a TextAndIcon.
        TextAndIcon tai = (TextAndIcon) value;

        // Sets text and icon on 'this' JLabel.
        setText(tai.getText());
        setIcon(tai.getIcon());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        Border outsideBorder;

        if (hasFocus) {
            outsideBorder = UIManager.getBorder("List.focusCellHighlightBorder");
        } else {
            outsideBorder = NO_FOCUS_BORDER;
        }

        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        setComponentOrientation(list.getComponentOrientation());
        setEnabled(list.isEnabled());
        setFont(list.getFont());

        return this;
    }

    // The following methods are overridden to be empty for performance
    // reasons. If you want to understand better why, please read:
    //
    // http://java.sun.com/javase/6/docs/api/javax/swing/DefaultListCellRenderer.html#override

    public void validate() {}
    public void invalidate() {}
    public void repaint() {}
    public void revalidate() {}
    public void repaint(long tm, int x, int y, int width, int height) {}
    public void repaint(Rectangle r) {}
}