/* 
 * Anagrams GUI program
 * Developed and Designed by Gerret Kubota for StealStreet
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.UIManager.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.util.*;
import java.io.*;

public class Anagrams{
  JFrame jframe;
  JLabel jlab, jlabOutput, jlabInput, jlabCopyright;
  JScrollPane jscp1, jscp2;
  JButton jBtnCheck, jFile;
  JTextField jtfInput;
  JTextArea jtaOutput, jtaInput;
  JPanel jp1, jp2, jp3;
  JFileChooser jfc;
  
  ArrayList<String> list;
  int[] asciiTracker;
  boolean status = true;
  StringBuilder sb, sb2;
  String input;

  Anagrams(){
    // make a JFrame container
    jframe = new JFrame("ANAGRAMS CHECKER");
    jframe.setLayout(new GridLayout(1,3));
    jframe.setSize(890,380);
    // closes the program when 'x' out or if user quits
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // JPanels are made to hold parts of the components of the program
    jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jp1.setPreferredSize(new Dimension(300,300)); // 100
    jp2.setPreferredSize(new Dimension(300,300));
    jp3.setPreferredSize(new Dimension(300,300));

    // create the labels, textfield, buttons, and scrollpanes
    jlab = new JLabel("Enter a list of words:", SwingConstants.LEFT);
    jtfInput = new JTextField(25);
    jlabInput = new JLabel("Input:", SwingConstants.LEFT);
    jlabOutput = new JLabel("Output:", SwingConstants.LEFT);
    jlabCopyright = new JLabel(" Designed and developed by Gerret Kubota. ©2018");
    jBtnCheck = new JButton("Submit");
    jtaOutput = new JTextArea();
    jtaInput = new JTextArea();
    jscp1 = new JScrollPane(jtaInput);
    jscp2 = new JScrollPane(jtaOutput);
    jFile = new JButton("File Directory");

    // set the font, font style, and font size for the labels
    jlab.setFont(new Font("Arial", Font.BOLD, 16));
    jlabOutput.setFont(new Font("Arial", Font.BOLD, 16));
    jlabInput.setFont(new Font("Arial", Font.BOLD, 16));
    jlabCopyright.setFont(new Font("Arial", Font.PLAIN, 10));
    
    // set size of the buttons
    jBtnCheck.setPreferredSize(new Dimension(285, 50));
    jFile.setPreferredSize(new Dimension(285, 50));

    // add the label, textfield input, buttons on first jpanel
    jp1.add(jlab);
    jp1.add(jtfInput);
    jp1.add(jBtnCheck);
    jp1.add(jFile);
    jp1.add(jlabCopyright);
    // add the input "result" and scroll pane/text area to jpanel
    jp2.add(jlabInput);
    jp2.add(jscp1);
    // add the output "result" and scrollpane/text area to jpanel
    jp3.add(jlabOutput);
    jp3.add(jscp2);

    // make the textareas uneditable    
    jtaOutput.setEditable(false);
    jtaInput.setEditable(false);
    // set the size of the scroll panes and enable scrollbars
    jscp1.setPreferredSize(new Dimension(250, 300));
    jscp1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    jscp2.setPreferredSize(new Dimension(250,300));
    jscp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    jscp2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // Event listeners for jBtnCheck and jtfInput, since it has the same functionality
    // contains the algorithm/function that performs the actual functionality of actual program of finding anagrams is called
    // copies the textfield input to the input "result" of the textarea to display.
    // Then get the result from the algorithm/function and display it on the output "result" of the text area
    ActionListener listener = new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        sb2 = new StringBuilder();

        jtaInput.setEditable(true);
        jtaInput.setText(jtfInput.getText());
        jtaInput.setEditable(false);

        splitInput(jtfInput.getText());
        jtaOutput.setEditable(true);
        jtaOutput.setText(sb2.toString());
        jtaOutput.setEditable(false);
      }
    };
    
    jBtnCheck.addActionListener(listener);
    jtfInput.addActionListener(listener);

    // add a actionlistener to the jfile button so that a file directory menu opens up
    // and then the user can choose a text file that contains the "unit tests" or
    // other text files that contain words to check for anagrams.
    jFile.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int result = jfc.showOpenDialog(null);

        if(result == JFileChooser.APPROVE_OPTION){
          File selectedFile = jfc.getSelectedFile();
          // System.out.println(selectedFile.getAbsolutePath());
          try{
            readFile(selectedFile.toString());
          }catch(IOException e){
            e.printStackTrace();
          }
        }
      }
    });

    // Add the components into jframe
    jframe.getContentPane().add(jp1);
    jframe.getContentPane().add(jp2);
    jframe.getContentPane().add(jp3);
    
    // centers window
    jframe.setLocationRelativeTo(null);
    // displays the window
    jframe.setVisible(true);
  }

  // function that reads the text file that is chosen from the jfilechooser
  // it will keep iterating through the text file and appends the string to a stringbuilder
  // and setting the stringbuilder string to the input "result" of the textarea
  public void readFile(String fileName) throws IOException{
    StringBuilder fileSB = new StringBuilder();
    BufferedReader br = new BufferedReader(new FileReader(fileName));

    try{
      String fileInput = br.readLine();
      while(fileInput != null){
        fileSB.append(fileInput);
        fileSB.append(" ");
        fileInput = br.readLine();
      }
      jtfInput.setText(fileSB.toString());
    }catch(IOException e){
      e.printStackTrace();
    }finally{
      br.close();
    }
  }

  // This function splits the string into individual words and it calls the
  // actual algorithm function that checks if the two strings that are being compared to are anagrams
  // (it also removes any duplicates while storing the individual words)
  public void splitInput(String words){
    StringBuilder tempSB = new StringBuilder();

    list = new ArrayList<String>();
    StringTokenizer token = new StringTokenizer(words);
    while(token.hasMoreTokens()){
      String temp = token.nextToken();
      tempSB.append(temp + "\n");
      if(!list.contains(temp))
        list.add(temp);
    }
    // retains all the input from the input textfield, regardless of duplicates
    // stores it into "input" text area to display
    jtaInput.setText(tempSB.toString());

    // Since the elements are being removed from the list as the algorithm starts, 
    // keep looping until the list is empty
    while(list.size() > 0){
      // stringbuilder object to hold the output string
      sb = new StringBuilder();
      // always append the starting index word
      sb.append(list.get(0));
      // compare the starting index word to the rest of the list using a for-loop, start from second element of arraylist
      for(int i = 1; i < list.size(); i++){
        // if the length of the two strings are the  same length check anagram
        if(list.get(0).length() == list.get(i).length()){
          if(checkAnagram(list.get(0), list.get(i))){
            // if it's an anagram, append the secondary string to the stringbuilder
            sb.append(" " + list.get(i));
            // remove the secondary string from the arraylist as it's not necessary to have in the arraylist anymore
            list.remove(i);
            // need to backtrack the index position as the for-loop increments the position and the arraylist list shifts the elements
            // to the "front" (like a queue)
            i--;
          }
        }
      }
      // remove the starting word from the list and print the result
      list.remove(0);
      // sb2.append(sb.toString() + "\n");
      StringTokenizer token2 = new StringTokenizer(sb.toString());
      sb2.append("{");
      while(token2.hasMoreTokens()){
        String temp = token2.nextToken();
        sb2.append(temp + ", ");
      }
      sb2.delete(sb2.toString().length()-2, sb2.toString().length());
      sb2.append("}\n");

    }
  }

  // The function/algorithm that checks if the two strings are an anagram of each other
  public boolean checkAnagram(String s1, String s2){
    // Make an int array size of 128, as the ascii values are from 0 to 127
    // This array will "act" as a placeholder/table for each asciivalue
    asciiTracker = new int[128];
    // Loop through the first string and cast each char in the string to an int
    // this will provide the ascii value of each letter in the string.
    // The resulting ascii value will work as an index for the asciiTracker array
    // Increment the value inside that index placeholder for asciiTracker
    for(int i = 0; i < s1.length(); i++){
      int asciiValue = (int) s1.charAt(i);
      asciiTracker[asciiValue]++;
    }
    // This for-loop decrements the value for the index placeholder of asciiTracker
    // Because when the initialized array starts with the values of 0 for its elements,
    // if at any point the value turns into a number < 0, it means that letter never existed
    // as it was never incremented by the first word
    for(int i = 0; i < s1.length(); i++){
      int asciiValue = (int) s2.charAt(i);
      if(--asciiTracker[asciiValue] < 0)
        return false;
    }
    // Return true if all goes well
    return true;
  }

  public static void main(String[] args){
    // makes it a cleaner/fancy look for the program
    try{
      for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
        if("Nimbus".equals(info.getName())){
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    }catch(Exception e){}

    SwingUtilities.invokeLater(new Runnable() {
      public void run(){
        new Anagrams();
      }
    });
  }
}