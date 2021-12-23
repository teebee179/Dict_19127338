import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.List;

/**
 * PACKAGE_NAME
 * Created by 19127338_Nguyễn Huỳnh Thế Bảo
 * Date 11/12/2021 - 7:35 CH
 * Description: ...
 */

public class Dict extends JPanel implements ItemListener{
    //dictionary using hashmap with key is a string and value is arraylist for store multiple definition
    static HashMap<String, ArrayList<String> > dictionary = new HashMap<String, ArrayList<String>>();
    JPanel cards;
    static List<String> history = new ArrayList<>();
    
    
    public void addToHistory(String word){
        if(word != "") {
            history.add(0, word);
            try {
                FileWriter fw = new FileWriter("history.txt", true);
                BufferedWriter fWrite = new BufferedWriter(fw);
                for (int i = 0; i < history.size(); i++) {
                    fWrite.write(history.get(i) + "\n");
                }
                fWrite.flush();
                fWrite.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

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
                    //add word to history search
                    addToHistory(word);
                    //search for word
                    if(dictionary.get(word)!=null){
                        String def = "";
                        for (int i = 0; i < dictionary.get(word).size(); i++) {
                            def += dictionary.get(word).get(i) + ",";
                        }
                        result.setText(def);

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
            JTextField inputDef = new JTextField(15);
            constraints.gridx = 1;
            add(inputDef,constraints);
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
                    String def = inputDef.getText();
                    //add definition to history
                    addToHistory(def);
                    //search for definition
                    String allDefinition = "";
                    boolean found=false;
                    for (var entry : dictionary.entrySet()) {
                        for (int i = 0; i < entry.getValue().size(); i++) {
                            if(entry.getValue().get(i).equals(def)){
                                found = true;
                                allDefinition += entry.getKey().toString() + ", ";
                            }
                        }
                    }
                    result.setText(allDefinition);
                    if(found==false){
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

    public class showHistorySearch extends JPanel implements ActionListener{
        JTextArea displayHistory;
            public showHistorySearch(){
                setLayout(new BorderLayout());
                displayHistory = new JTextArea(10,10);
                JButton refresh = new JButton("Refresh");
                refresh.setActionCommand("refresh");
                refresh.addActionListener(this);
                JButton clear =new JButton("Clear");
                clear.setActionCommand("clear");
                clear.addActionListener(this);
                add(displayHistory, BorderLayout.CENTER);

                JPanel coverFooter = new JPanel();
                coverFooter.setLayout(new BoxLayout(coverFooter,BoxLayout.PAGE_AXIS));

                JPanel footer = new JPanel();
                footer.setLayout(new BoxLayout(footer,BoxLayout.LINE_AXIS));

                footer.add(refresh);
                footer.add(Box.createRigidArea(new Dimension(20,0)));
                footer.add(clear);
                footer.setAlignmentX(Component.CENTER_ALIGNMENT);
                coverFooter.add(footer);

                add(coverFooter,BorderLayout.PAGE_END);
            }
        public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
            if(cmd.equals("refresh")) {
                displayHistory.setText("");
                for (int i = 0; i < history.size(); i++) {
                    displayHistory.append(history.get(i) + "\n");
                }
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setViewportView(displayHistory);
                add(scrollPane);
            }
            if(cmd.equals("clear")) {
                displayHistory.setText("");
                history.clear();
            }


        }

    }

    public class addSlangWord extends JPanel{
        public addSlangWord(){
            setLayout(new FlowLayout());
            JPanel inputField = new JPanel();
            inputField.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            //add word label
            constraints.anchor = GridBagConstraints.WEST;
            constraints.gridx = 0; constraints.gridy = 0;
            JLabel addWord = new JLabel("Input your word: ");
            inputField.add(addWord,constraints);

            //add definition label
            constraints.anchor = GridBagConstraints.WEST;
            constraints.gridx = 0; constraints.gridy = 1;
            JLabel addDef = new JLabel("Input word definition: ");
            inputField.add(addDef,constraints);

            //input word field
            constraints.anchor = GridBagConstraints.WEST;
            constraints.gridx = 1; constraints.gridy = 0;
            JTextField addWordField = new JTextField(20);
            inputField.add(addWordField,constraints);

            //input definition field
            constraints.anchor = GridBagConstraints.WEST;
            constraints.gridx = 1; constraints.gridy = 1;
            JTextField addDefField = new JTextField(30);
            inputField.add(addDefField,constraints);

            constraints.anchor = GridBagConstraints.WEST;
            constraints.gridx = 1; constraints.gridy = 2;
            JButton addBtn = new JButton("Add");
            addBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String word = addWordField.getText();
                    String definition = addDefField.getText();
                    ArrayList<String> defList = new ArrayList<>();
                    defList.add(definition);
                    if(dictionary.get(word)==null){
                        dictionary.put(word, defList);
                        JOptionPane.showMessageDialog(new JFrame(), "Add successfully", "Add slang word", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        Object[] options = {"Duplicate", "Rewrite"};
                        int n = JOptionPane.showOptionDialog(new JFrame(),
                                "The word u want to add is already exist,  Do you want to duplicate it or rewrite ?",
                                "Add word",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,     //do not use a custom Icon
                                options,  //the titles of buttons
                                options[0]); //default button title
                        //if user choice is duplicate. add to list of definition
                        if(n == JOptionPane.YES_OPTION){
                            dictionary.get(word).add(definition);
                            JOptionPane.showMessageDialog(new JFrame(), "Duplicate successfully", "Add slang word", JOptionPane.INFORMATION_MESSAGE);
                        }
                        //if user choice is rewrite, clear definition list and add new definition
                        if(n == JOptionPane.NO_OPTION){
                            dictionary.get(word).clear();
                            dictionary.get(word).add(definition);
                            JOptionPane.showMessageDialog(new JFrame(), "Rewrite successfully", "Add slang word", JOptionPane.INFORMATION_MESSAGE);

                        }
                    }
                }
            });
            inputField.add(addBtn,constraints);

            add(inputField);
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
        searchDefinition choice2 = new searchDefinition();

        //see search history
        showHistorySearch choice3 = new showHistorySearch();

        //add slang word
        addSlangWord choice4 = new addSlangWord();

        cards = new JPanel(new CardLayout());
        cards.add(choice1,choiceList[0]);
        cards.add(choice2,choiceList[1]);
        cards.add(choice3,choiceList[2]);
        cards.add(choice4,choiceList[3]);

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
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("slang.txt"));
            String line = br.readLine();
            while (line != null) {
                if(line.contains("`")){
                    String[] word = line.split("`");
                    ArrayList<String> means = new ArrayList<>();
                    dictionary.put(word[0],means);
                    if(word[1].contains("|")){
                        String[] meanings = word[1].split("[|]");
                        for (int i = 0; i < meanings.length; i++) {
                            dictionary.get(word[0]).add(meanings[i]);
                        }
                    }
                    else{
                        dictionary.get(word[0]).add(word[1]);
                    }
                    line = br.readLine();
                }else {
                    line = br.readLine();
                }

            }
            br.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("history.txt"));
            String line = br.readLine();
            while (line!=null){
                history.add(line);
                line = br.readLine();
            }
            br.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        System.out.println(dictionary.size());
        createAndShowGUI();
    }
}

