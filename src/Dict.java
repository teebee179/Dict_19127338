import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
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

    public  void resetDictionary(){
        try {
            //read original file to dictionary
            BufferedReader br = new BufferedReader(new FileReader("slang.txt"));
            //write original file to changed file
            BufferedWriter writer = new BufferedWriter(new FileWriter("slang_changed.txt"));
            String line = br.readLine();
            while (line != null) {
                writer.write(line + "\n");
                if(line.contains("`")){
                    String[] word = line.split("`");
                    ArrayList<String> means = new ArrayList<>();
                    dictionary.put(word[0],means);
                    if(word[1].contains("|")){
                        String[] meanings = word[1].split("[|]");
                        for (int i = 0; i < meanings.length; i++) {
                            dictionary.get(word[0]).add(meanings[i].trim());
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
            writer.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //add word to file history
    public void addToHistory(String word){
        if(word != "") {
                history.add(0, word);
                try {
                    FileWriter fw = new FileWriter("history.txt", true);
                    BufferedWriter fWrite = new BufferedWriter(fw);
                    fWrite.write(word + "\n");
                    fWrite.flush();
                    fWrite.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
    }

    public String[] generateQuiz(String word){
        String[] quizCase = new String[4];
        quizCase[0] = dictionary.get(word).toString();
        for (int i = 1; i < 4; i++) {
            quizCase[i] = dictionary.get(randomAWord()).toString();
        }
        return quizCase;
    }

    //random a word
    public String randomAWord(){
        Random generator = new Random();
        String[] keyList = dictionary.keySet().toArray(new String[0]);
        String randomWord = keyList[generator.nextInt(keyList.length)];
        return randomWord;
    }
    //random a definition
    public String randomDefinition(){
        String word = randomAWord();
        List<String> definition = dictionary.get(word);
        return definition.toString();
    }

    public void handleFileChange(String word, String definition, int choice){
        try {
            FileWriter fw = new FileWriter("slang_changed.txt");
            BufferedWriter fWrite = new BufferedWriter(fw);
            String line = "";
            //add word that not exist before
            if(choice == 0){
                for (var entry : dictionary.entrySet()) {
                    line = "";
                    line += entry.getKey() + "`";
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        line += entry.getValue().get(i) + "|";
                    }
                    fWrite.write(line + "\n");
                }
                fWrite.write(word + "`" +definition + "\n");
            }
            //add word and duplicate it
            if(choice == 1){
                for (var entry : dictionary.entrySet()) {
                    line = "";
                    line += entry.getKey() + "`";
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        line += entry.getValue().get(i) + "|";
                    }
                    if(entry.getKey().equals(word)){line += "|" + definition;}
                    fWrite.write(line + "\n");
                }
            }
            //add word and rewrite it
            if(choice == 2){
                for (var entry : dictionary.entrySet()) {
                    line = "";
                    line += entry.getKey() + "`";
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        line += entry.getValue().get(i) + "|";
                    }
                    if(!entry.getKey().equals(word)){
                        fWrite.write(line + "\n");
                    }
                }
                fWrite.write(word + "`" + definition + "\n");
            }
            //edit slang word
            if(choice == 3){
                for (var entry : dictionary.entrySet()) {
                    line = "";
                    line += entry.getKey() + "`";
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        line += entry.getValue().get(i) + "|";
                    }
                    if(!entry.getKey().equals(word)){
                        fWrite.write(line + "\n");
                    }
                }
                fWrite.write(word + "`" + definition + "\n");
            }
            //delete slang word
            if(choice == 4){
                for (var entry : dictionary.entrySet()) {
                    line = "";
                    line += entry.getKey() + "`";
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        line += entry.getValue().get(i) + "|";
                    }
                    if(!entry.getKey().equals(word)){
                        fWrite.write(line + "\n");
                    }
                }
            }

            fWrite.flush();
            fWrite.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    public  class searchWord extends  JPanel{
        public  searchWord(){
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
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
            //history log
            JPanel historyLog = new JPanel();
            historyLog.setLayout(new BorderLayout());
            JLabel label = new JLabel("Recently search");
            historyLog.add(label, BorderLayout.PAGE_START);
            JTextArea displayHistory = new JTextArea(10,10);
            JScrollPane scrollPane = new JScrollPane(displayHistory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            for (int i = 0; i < history.size(); i++) {
                displayHistory.append(history.get(i) + "\n");
            }
            historyLog.add(scrollPane, BorderLayout.CENTER);
            JButton clearBtn = new JButton("Clear");
            clearBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayHistory.setText("");
                    history.clear();
                    try {
                        PrintWriter writer = new PrintWriter("history.txt");
                        writer.print("");
                        writer.close();
                    }
                    catch (Exception ex){
                        System.out.println(ex.getMessage());
                    }
                }
            });
            historyLog.add(clearBtn, BorderLayout.PAGE_END);

            //result display
            JTextArea result = new JTextArea(10,15);
            JScrollPane resultScrollPane = new JScrollPane(result, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            c1.gridx = 1; c1.gridy = 1;
            card1.add(resultScrollPane, c1);
            //search button
            JButton search = new JButton("Search");
            c1.gridx = 2;
            c1.gridy = 0;
            search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    result.setText("");
                    String word = inputWord.getText();
                    //update history log
                    if(!word.equals("")) displayHistory.append(word + "\n");
                    //write to file for next time
                    addToHistory(word);
                    //search for word
                    if(dictionary.get(word)!=null){
                        String def = "";
                        for (int i = 0; i < dictionary.get(word).size(); i++) {
                            result.append(dictionary.get(word).get(i) + "\n");
                        }
                    }else {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Word does not exist !!!",
                                "Search slang word",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            showHistorySearch historySearch = new showHistorySearch();


            card1.add(search,c1);
            add(card1);
            add(historyLog, BorderLayout.EAST);

        }
    }

    public class searchDefinition extends JPanel{
        public  searchDefinition(){
            setSize(500,500);
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
            JTextArea result = new JTextArea(10,15);
            JScrollPane scrollPane = new JScrollPane(result, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            constraints.gridx = 1; constraints.gridy = 1;
            add(scrollPane, constraints);
            search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    result.setText("");
                    String def = inputDef.getText();
                    //search for definition
                    boolean found=false;
                    for (var entry : dictionary.entrySet()) {
                        for (int i = 0; i < entry.getValue().size(); i++) {
                            if(entry.getValue().get(i).equals(def)){
                                found = true;
                                result.append(entry.getKey().toString()+"\n");
                            }
                        }
                    }
                    if(found==false){
                        result.setText("Definition incorrect  !!!");

                    }
                }
            });
            constraints.gridx = 2; constraints.gridy = 0;
            add(search,constraints);
        }
    }

    public class showHistorySearch extends JPanel implements ActionListener{
        JTextArea displayHistory;
            public showHistorySearch(){
                JLabel label = new JLabel("Recently search");
                add(label);

                setLayout(new BorderLayout());
                displayHistory = new JTextArea(10,10);
                JScrollPane scrollPane = new JScrollPane(displayHistory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                add(scrollPane, BorderLayout.CENTER);

                JButton refresh = new JButton("Refresh");
                refresh.setActionCommand("refresh");
                refresh.addActionListener(this);
                JButton clear =new JButton("Clear");
                clear.setActionCommand("clear");
                clear.addActionListener(this);

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
            }
            if(cmd.equals("clear")) {
                displayHistory.setText("");
                history.clear();
                try {
                    PrintWriter writer = new PrintWriter("history.txt");
                    writer.print("");
                    writer.close();
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
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
                        handleFileChange(word, definition, 0);
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
                            handleFileChange(word,definition,1);
                        }
                        //if user choice is rewrite, clear definition list and add new definition
                        if(n == JOptionPane.NO_OPTION){
                            dictionary.get(word).clear();
                            dictionary.get(word).add(definition);
                            handleFileChange(word, definition, 2);
                            JOptionPane.showMessageDialog(new JFrame(), "Rewrite successfully", "Add slang word", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            });
            inputField.add(addBtn,constraints);

            add(inputField);
        }
    }

    public class editSlangWord extends JPanel{
        public editSlangWord(){
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
            JLabel addDef = new JLabel("Input new definition: ");
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
            JButton editBtn = new JButton("Edit");
            editBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String word = addWordField.getText();
                    if(dictionary.get(word) == null){
                        JOptionPane.showMessageDialog(new JFrame(), "Word not exist", "Edit slang word", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        dictionary.get(word).clear();
                        dictionary.get(word).add(addDefField.getText());
                        handleFileChange(word,addDefField.getText(), 3);
                        JOptionPane.showMessageDialog(new JFrame(), "Edit successfully", "Edit slang word", JOptionPane.INFORMATION_MESSAGE);

                    }
                }
            });
            inputField.add(editBtn,constraints);
            add(inputField);
        }
    }

    public class deleteSlangWord extends JPanel{
        public deleteSlangWord(){
            setLayout(new FlowLayout());
            JPanel inputField = new JPanel();
            inputField.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            //input word label
            constraints.anchor = GridBagConstraints.WEST;
            constraints.gridx = 0; constraints.gridy = 0;
            JLabel deleteWord = new JLabel("Input your word: ");
            inputField.add(deleteWord,constraints);

            //input word field
            constraints.anchor = GridBagConstraints.WEST;
            constraints.gridx = 1; constraints.gridy = 0;
            JTextField deleteWordField = new JTextField(20);
            inputField.add(deleteWordField,constraints);

            constraints.anchor = GridBagConstraints.WEST;
            constraints.gridx = 1; constraints.gridy = 2;
            JButton deleteBtn = new JButton("Delete");
            deleteBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String word = deleteWordField.getText();
                    if(dictionary.get(word) == null){
                        JOptionPane.showMessageDialog(new JFrame(), "Word not exist", "Delete slang word", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        int n = JOptionPane.showConfirmDialog(
                                new JFrame(),
                                "Do you really want to delete this word ?",
                                "Delete slang word",
                                JOptionPane.YES_NO_OPTION);
                        if(n == JOptionPane.YES_OPTION){
                            dictionary.remove(word);
                            handleFileChange("","", 4);
                            JOptionPane.showMessageDialog(new JFrame(), "Delete successfully", "Delete slang word", JOptionPane.INFORMATION_MESSAGE);

                        }
                        else if(n != JOptionPane.NO_OPTION){
                            deleteWordField.setText("");
                        }
                    }
                }
            });
            inputField.add(deleteBtn,constraints);
            add(inputField);
        }
    }

    public class quizUsingWord extends JPanel implements ActionListener{
        int i;
        String word;
        String answer;
        List<JButton> btnList = new ArrayList<JButton>();
        JPanel answerPanel;
        JLabel slangWord;
        public void actionPerformed(ActionEvent e){
            answer = e.getActionCommand();
            System.out.println(dictionary.get(word).toString());
            System.out.println(e.getActionCommand());
            Object[] options = {"Play again", "Finish"};
            if(dictionary.get(word).toString().equals(answer)){
                //find the chosen button and hightlight it green
                for (int j = 0; j < 4; j++) {
                    if(btnList.get(j).getText().equals(e.getActionCommand())){
                        btnList.get(j).setBackground(Color.GREEN);
                    }
                }
                int n = JOptionPane.showOptionDialog(new JFrame(),
                        "Correct answer !!!. Do you want to play again or quit",
                        "Quiz",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,     //do not use a custom Icon
                        options,  //the titles of buttons
                        options[0]); //default button title
                //play again
                if(n == JOptionPane.YES_OPTION){
                    word = randomAWord();
                    slangWord.setText(word);
                    btnList.get(0).setText(dictionary.get(word).toString());
                    btnList.get(0).setBackground(null);
                    btnList.get(1).setText(randomDefinition());
                    btnList.get(1).setBackground(null);
                    btnList.get(2).setText(randomDefinition());
                    btnList.get(2).setBackground(null);
                    btnList.get(3).setText(randomDefinition());
                    btnList.get(3).setBackground(null);
                    Collections.shuffle(btnList);
                    for (int j = 0; j < 4; j++) {
                        btnList.get(j).setActionCommand(btnList.get(j).getText().toString());
                    }
                }
                //if finish do nothing
            }
            //incorrect answer
            else {
                //find the correct button and hightlight it green
                for (int j = 0; j < 4; j++) {
                    if(btnList.get(j).getText().equals(dictionary.get(word).toString())){
                        btnList.get(j).setBackground(Color.GREEN);
                    }
                }
                //find the chosen button and hightlight it red
                for (int j = 0; j < 4; j++) {
                    if(btnList.get(j).getText().equals(e.getActionCommand())){
                        btnList.get(j).setBackground(Color.RED);
                    }
                }
                int n = JOptionPane.showOptionDialog(new JFrame(),
                        "Incorrect answer :((((. Do you want to play again or quit",
                        "Quiz",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,     //do not use a custom Icon
                        options,  //the titles of buttons
                        options[0]); //default button title
                //play again
                if(n == JOptionPane.YES_OPTION){
                    word = randomAWord();
                    slangWord.setText(word);
                    btnList.get(0).setText(dictionary.get(word).toString());
                    btnList.get(0).setBackground(null);
                    btnList.get(1).setText(randomDefinition());
                    btnList.get(1).setBackground(null);
                    btnList.get(2).setText(randomDefinition());
                    btnList.get(2).setBackground(null);
                    btnList.get(3).setText(randomDefinition());
                    btnList.get(3).setBackground(null);
                    Collections.shuffle(btnList);
                    for (int j = 0; j < 4; j++) {
                        btnList.get(j).setActionCommand(btnList.get(j).getText().toString());
                    }
                }
            }
        }

        public quizUsingWord(){
            setSize(500,500);
            answerPanel = new JPanel();
            //quiz case
            word = randomAWord();
            answer = "";
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            slangWord = new JLabel(word);
            slangWord.setFont(new Font("Calibri", Font.BOLD, 25));
            add(slangWord);


            btnList.add(new JButton(dictionary.get(word).toString()));
            btnList.add(new JButton(randomDefinition()));
            btnList.add(new JButton(randomDefinition()));
            btnList.add(new JButton(randomDefinition()));
            Collections.shuffle(btnList);

            for (i = 0; i < 4; i++) {
                btnList.get(i).setActionCommand(btnList.get(i).getText().toString());
                btnList.get(i).addActionListener(this);
            }


            for (int j = 0; j < 4; j++) {
                answerPanel.add(btnList.get(j));
            }
            answerPanel.setLayout(new GridLayout(2,2));
            add(answerPanel);
        }
    }

    public class quizUsingDefinition extends JPanel implements ActionListener{
        String definition;
        String answerWord;
        List<JButton> btnList = new ArrayList<JButton>();
        JPanel answerPanel;
        JLabel definitionLabel;

        public void actionPerformed(ActionEvent e){
            String word = e.getActionCommand();
            Object[] options = {"Play again", "Finish"};
            if(dictionary.get(word).toString().equals(definition)) {
                //if answer is correct, find the correct button and highlight it
                for (int j = 0; j < 4; j++) {
                    if(btnList.get(j).getText().equals(e.getActionCommand())){
                        btnList.get(j).setBackground(Color.GREEN);
                    }
                }
                int n = JOptionPane.showOptionDialog(new JFrame(),
                        "Correct answer !!!. Do you want to play again or quit",
                        "Quiz",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,     //do not use a custom Icon
                        options,  //the titles of buttons
                        options[0]); //default button title
                //play again
                if (n == JOptionPane.YES_OPTION) {
                    answerWord = randomAWord();
                    definition = dictionary.get(answerWord).toString();
                    definitionLabel.setText(definition);
                    btnList.get(0).setText(answerWord);
                    btnList.get(0).setBackground(null);
                    btnList.get(1).setText(randomAWord());
                    btnList.get(1).setBackground(null);
                    btnList.get(2).setText(randomAWord());
                    btnList.get(2).setBackground(null);
                    btnList.get(3).setText(randomAWord());
                    btnList.get(3).setBackground(null);
                    Collections.shuffle(btnList);
                    for (int j = 0; j < 4; j++) {
                        btnList.get(j).setActionCommand(btnList.get(j).getText().toString());
                    }
                }
                //if finish do nothing
            }
            //incorrect answer
            else {
                //find the correct button and hightlight it green
                for (int j = 0; j < 4; j++) {
                    if(btnList.get(j).getText().equals(answerWord)){
                        btnList.get(j).setBackground(Color.GREEN);
                    }
                }
                //find the chosen button and hightlight it red
                for (int j = 0; j < 4; j++) {
                    if(btnList.get(j).getText().equals(e.getActionCommand())){
                        btnList.get(j).setBackground(Color.RED);
                    }
                }
                int n = JOptionPane.showOptionDialog(new JFrame(),
                        "Incorrect answer :((((. Do you want to play again or quit",
                        "Quiz",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,     //do not use a custom Icon
                        options,  //the titles of buttons
                        options[0]); //default button title
                //play again
                if(n == JOptionPane.YES_OPTION){
                    answerWord = randomAWord();
                    definition = dictionary.get(answerWord).toString();
                    definitionLabel.setText(definition);
                    btnList.get(0).setText(answerWord);
                    btnList.get(0).setBackground(null);
                    btnList.get(1).setText(randomAWord());
                    btnList.get(1).setBackground(null);
                    btnList.get(2).setText(randomAWord());
                    btnList.get(2).setBackground(null);
                    btnList.get(3).setText(randomAWord());
                    btnList.get(3).setBackground(null);
                    Collections.shuffle(btnList);
                    for (int j = 0; j < 4; j++) {
                        btnList.get(j).setActionCommand(btnList.get(j).getText().toString());
                    }
                }
            }
        }

        public quizUsingDefinition(){
            setSize(500,500);
            answerPanel = new JPanel();
            //quiz case
            answerWord = randomAWord();
            definition = dictionary.get(answerWord).toString();

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            definitionLabel = new JLabel(definition);
            definitionLabel.setFont(new Font("Calibri", Font.BOLD, 25));
            add(definitionLabel);


            btnList.add(new JButton(answerWord));
            btnList.add(new JButton(randomAWord()));
            btnList.add(new JButton(randomAWord()));
            btnList.add(new JButton(randomAWord()));
            Collections.shuffle(btnList);

            for (int i = 0; i < 4; i++) {
                btnList.get(i).setActionCommand(btnList.get(i).getText());
                btnList.get(i).addActionListener(this);
            }


            for (int j = 0; j < 4; j++) {
                answerPanel.add(btnList.get(j));
            }
            answerPanel.setLayout(new GridLayout(2,2));
            add(answerPanel);
        }
    }

    public class randomSlangWord extends JPanel implements ActionListener{
        List<String> randomList = new ArrayList<>();
        JTextArea displayRandomWord;
        public randomSlangWord(){
            setLayout(new BorderLayout());
            displayRandomWord = new JTextArea(10,10);
            JScrollPane scrollPane = new JScrollPane(displayRandomWord, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            JButton random = new JButton("Random");
            random.setActionCommand("random");
            random.addActionListener(this);
            JButton clear =new JButton("Clear");
            clear.setActionCommand("clear");
            clear.addActionListener(this);

            add(scrollPane, BorderLayout.CENTER);
            //add(displayRandomWord, BorderLayout.CENTER);

            JPanel coverFooter = new JPanel();
            coverFooter.setLayout(new BoxLayout(coverFooter,BoxLayout.PAGE_AXIS));

            JPanel footer = new JPanel();
            footer.setLayout(new BoxLayout(footer,BoxLayout.LINE_AXIS));

            footer.add(random);
            footer.add(Box.createRigidArea(new Dimension(20,0)));
            footer.add(clear);
            footer.setAlignmentX(Component.CENTER_ALIGNMENT);
            coverFooter.add(footer);

            add(coverFooter,BorderLayout.PAGE_END);
        }

        public void actionPerformed(ActionEvent e){
            String cmd =e.getActionCommand();
            if(cmd.equals("random")){
                String word = randomAWord();
                String definition = "";
                for (int i = 0; i < dictionary.get(word).size(); i++) {
                    definition += dictionary.get(word).get(i) + ", ";
                }
                displayRandomWord.append(word + " - " + definition + "\n");

            }
            if(cmd.equals("clear")){
                displayRandomWord.setText("");
            }
        }
    }

    public Dict(){
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JLabel label1 = new JLabel("Your choice: ");
        String[] choiceList = {"Search by word ","Search by definition","Add slang word","Edit slang word","Delete slang word","Random a word","Quiz by word","Quiz by definition"};
        JComboBox box = new JComboBox(choiceList);
        box.setEditable(false);
        box.addItemListener(this);
        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetDictionary();
                JOptionPane.showMessageDialog(new JFrame(),
                        "Reset dictionary successfully",
                        "Reset slang word",
                        JOptionPane.INFORMATION_MESSAGE
                        );
            }
        });
        topPanel.add(label1);
        topPanel.add(box);
        topPanel.add(resetBtn);

        //search by word
        searchWord choice1 = new searchWord();

        //search by definition
        searchDefinition choice2 = new searchDefinition();

        //add slang word
        addSlangWord choice3 = new addSlangWord();

        //edit slang word
        editSlangWord choice4 = new editSlangWord();

        //delete slang word
        deleteSlangWord choice5 = new deleteSlangWord();

        //random slang word
        randomSlangWord choice6 = new randomSlangWord();

        //quiz using word
        quizUsingWord choice7 = new quizUsingWord();

        //quiz using definition
        quizUsingDefinition choice8 = new quizUsingDefinition();

        cards = new JPanel(new CardLayout());
        cards.add(choice1,choiceList[0]);
        cards.add(choice2,choiceList[1]);
        cards.add(choice3,choiceList[2]);
        cards.add(choice4,choiceList[3]);
        cards.add(choice5,choiceList[4]);
        cards.add(choice6,choiceList[5]);
        cards.add(choice7, choiceList[6]);
        cards.add(choice8, choiceList[7]);



        add(topPanel, BorderLayout.PAGE_START);
        add(cards, BorderLayout.CENTER);

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
            BufferedReader br = new BufferedReader(new FileReader("slang_changed.txt"));
            String line = br.readLine();
            while (line != null) {
                if(line.contains("`")){
                    String[] word = line.split("`");
                    ArrayList<String> means = new ArrayList<>();
                    dictionary.put(word[0],means);
                    if(word[1].contains("|")){
                        String[] meanings = word[1].split("[|]");
                        for (int i = 0; i < meanings.length; i++) {
                            dictionary.get(word[0]).add(meanings[i].trim());
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
        //load history search
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

