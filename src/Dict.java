import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * PACKAGE_NAME
 * Created by 19127338_Nguyễn Huỳnh Thế Bảo
 * Date 11/12/2021 - 7:35 CH
 * Description: ...
 */

public class Dict extends JPanel implements ItemListener {
    static HashMap<String,String> dictionary = new HashMap<String, String>();

    JPanel cards;

    public  class searchWord extends  JPanel{
        public  searchWord(){
            JPanel card1 = new JPanel();
            card1.setLayout(new GridBagLayout());
            GridBagConstraints c1 = new GridBagConstraints();
            //search label
            JLabel searchWord = new JLabel("Word: ");
            c1.anchor = GridBagConstraints.WEST;
            c1.gridx = 0;
            c1.gridy = 0;
            card1.add(searchWord,c1);
            //search text field
            JTextField inputWord = new JTextField(15);
            c1.gridx = 1;
            card1.add(inputWord,c1);
            //Definition label
            JLabel definition = new JLabel("Definition: ");
            c1.gridx = 0;
            c1.gridy = 1;
            card1.add(definition,c1);
            //search button
            JButton search = new JButton("Search");
            c1.gridx = 2;
            c1.gridy = 0;
            JLabel result = new JLabel();
            result.setVisible(false);
            search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    result.setText("");
                    String word = inputWord.getText();
                    if(dictionary.get(word)!=null){
                        result.setText(dictionary.get(word));

                    }else {
                        result.setText("Word does not exist !!!");
                    }
                    c1.gridx = 1;
                    c1.gridy = 1;
                    card1.add(result,c1);
                    result.setVisible(true);
                }
            });
            card1.add(search,c1);
            add(card1);
        }
    }

    public class searchDefinition extends JPanel{
        public  searchDefinition(){
            setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            //search label
            JLabel searchWord = new JLabel("Definition: ");
            constraints.anchor = GridBagConstraints.WEST;
            constraints.gridx = 0;
            constraints.gridy = 0;
            add(searchWord,constraints);
            //search text field
            JTextField inputWord = new JTextField(15);
            constraints.gridx = 1;
            add(inputWord,constraints);
            //Definition label
            JLabel definition = new JLabel("Word: ");
            constraints.gridx = 0;
            constraints.gridy = 1;
            add(definition,constraints);
            //search button
            JButton search = new JButton("Search");
            constraints.gridx = 2;
            constraints.gridy = 0;
            JLabel result = new JLabel();
            result.setVisible(false);
            search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    result.setText("");
                    String def = inputWord.getText();
                    if(dictionary.get(def)!=null){
                        result.setText(dictionary.get(def));

                    }else {
                        result.setText("Definition incorrect  !!!");
                    }
                    constraints.gridx = 1;
                    constraints.gridy = 1;
                    add(result,constraints);
                    result.setVisible(true);
                }
            });
            add(search,constraints);
        }
    }

    public Dict(){
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JLabel label1 = new JLabel("Your choice: ");
        String[] choiceList = {"Search by word ","Search by definition","Show history","Add slang word","Edit slang word","Delete slang word","Reset","Random a word","Quiz by word","Quiz by definition"};
        JComboBox box = new JComboBox(choiceList);
        box.setEditable(false);
        box.addItemListener(this);
        topPanel.add(label1);
        topPanel.add(box);

        //search by word
        searchWord choice1 = new searchWord();


        //search by definition
        String[] data2 = {"d","e","f"};
        JList list2 = new JList();
        list2.setListData(data2);
        JPanel card2 = new JPanel();
        card2.add(list2);

        String[] data3 = {"g","h","i","j"};
        JList list3 = new JList();
        list3.setListData(data3);
        JPanel card3 = new JPanel();
        card3.add(list3);

        cards = new JPanel(new CardLayout());
        cards.add(choice1,choiceList[0]);
        cards.add(card2,choiceList[1]);
        cards.add(card3,choiceList[2]);

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer,BoxLayout.LINE_AXIS));

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        footer.add(okButton);
        footer.add(Box.createRigidArea(new Dimension(20,0)));
        footer.add(cancelButton);
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);


        add(topPanel, BorderLayout.PAGE_START);
        add(cards, BorderLayout.CENTER);
        add(footer, BorderLayout.PAGE_END);
    }

    public void itemStateChanged(ItemEvent evt)
    {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }

    private static void createAndShowGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("district");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dict component = new Dict();
        component.setOpaque(true);
        frame.setContentPane(component);

        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args){

        try {
            BufferedReader br = new BufferedReader(new FileReader("slang.txt"));
            String line = br.readLine();
            while (line != null) {
                if(line.contains("`")){
                    String[] word = line.split("`");
                    System.out.println(word[0] + "," + word[1]);
                    dictionary.put(word[0],word[1]);
                    line = br.readLine();
                }else {
                    line = br.readLine();
                }
            }
            br.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(dictionary.size());
        createAndShowGUI();
    }
}

