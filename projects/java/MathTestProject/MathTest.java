import java.awt.*;
import javax.swing.*;
import java.awt.event.*; 
import java.util.Random;
import java.io.*;
import javax.sound.sampled.*;

/**
 * Math Test
 * Tests Basic integer addition and subtraction
 * Haley Yerxa
 * 11/6/17
 */

public class MathTest extends JFrame implements ActionListener, Runnable
{
    private JLabel title = new JLabel("Haley's Amazing Math Quiz!");
    private JLabel question = new JLabel("");
    private JTextField answer = new JTextField(5);
    private JLabel score = new JLabel("Score: 0");
    private JLabel correct = new JLabel("Correct");
    private JButton enter = new JButton("Submit");
    Color pink = new Color(255,182,193);
    Color greenish = new Color(0, 255, 255);
    int playerScore = 0; // keeps track of score
    int playerAnswer; // keeps track of answer
    int numQuestions = 0; // Keeps track of number of questions so that it stops at 10
    int number1; // first random number
    int number2; // second random number
    int clipCorrect; // random integer for which clip gets played when correct
    int clipIncorrect; // same as clipCorrect but for incorrect answers
    boolean operator; // decides whether to add or subtract
    /*boolean sign1; 
    boolean sign2;*/
    int sum; // sum of two random numbers
    boolean gameOver = false; // becomes true when reaches ten questions
    boolean questionGood = false; // flag for asking questions, starts false, becomes true when a valid question is generated
    Clip numberClips[] = new Clip[11]; // array for clips of number sounds
    Clip correctClips[] = new Clip[2];
    Clip incorrectClips[] = new Clip[3];
    Clip equalSound;
    Clip minusSound;
    Clip plusSound;
    AudioInputStream audioEquals;
    AudioInputStream audioMinus;
    AudioInputStream audioPlus;
    AudioInputStream audioNumbers[] = new AudioInputStream[11];
    AudioInputStream audioCorrectClips[] = new AudioInputStream[2];
    AudioInputStream audioIncorrectClips[] = new AudioInputStream[3];
    Thread thread; // creates our thread
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == enter) // if they press the answer button
        {
            String string;
            string = answer.getText(); // gets the text from the text field
            if (string == "") // if field is empty, leave function
            {
                return;
            }
            try // will try to convert the player's inputted value into a number
            {
                playerAnswer = Integer.parseInt(answer.getText());
                enter.setEnabled(false); // turns button off so that they can't keep pressing it
            }
            catch(NumberFormatException e2) //if it is not an integer, it will leave the function
            { 
                return;
            }
            if (playerAnswer == sum) // if the value inputted by the user is the same as the sum of the two numbers generated
            {
                correct.setText("Correct!"); // sets the label to say correct
                if (thread == null)
                {
                    thread = new Thread(this); // construct a new thread
                    thread.start(); // starts the thread
                }
                
                correct.setForeground(Color.black); // changes the colour of the correct label back to black (before it was "invisible" by being the same coour as the background)
                playerScore++; // increases the score
                score.setText("Score: "+""+playerScore); // displays the increase in score
                repaint();
            }
            else // otherwise they weren't correct
            {
                correct.setText("Incorrect. The correct answer was "+""+sum); // tells the user what the right answer was
                if (thread == null) // if the thread is dead
                {
                    thread = new Thread(this); // construct a new thread
                    thread.start(); 
                }
                correct.setForeground(Color.black); // makes the words visible (only important for the first question)
                repaint();
            }
        }
        return;
    }   
    public void run() // called when the thread starts
    {
        Thread thisThread = Thread.currentThread(); // gets the current thread (our only thread)
        if (playerAnswer == sum)// if they're right
        {
            Random randomClip = new Random(); // creates arandom variabele to generate random numbers for clips
            clipCorrect = randomClip.nextInt(2); // there are two clips to choose from
            correctClips[clipCorrect].start(); // plays the clip in the array with the same index as the random number
            try
                {                        
                    Thread.sleep(3000); // pauses for 3 seconds (to allow the clip to play)
                }
                catch(Exception e3) // if there is a problem, display error message
                {
                    JOptionPane.showMessageDialog(null,e3);
                }  
            correctClips[clipCorrect].stop();
            correctClips[clipCorrect].setMicrosecondPosition(0); // puts the clip back to the beginning so that next time it is at the right spot  
        }
        else // they were incorrect (same proccess, just different clips). But it also gives audio for th correct answer
        {
            Random randomClip2 = new Random();
            clipIncorrect = randomClip2.nextInt(3); // this time there are three clips to choose from
            incorrectClips[clipIncorrect].start();
            try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException ie)
                {
                    System.out.println(ie.getMessage()); // print error message if necessary
                }
            incorrectClips[clipIncorrect].stop();
            incorrectClips[clipIncorrect].setMicrosecondPosition(0);
            
            if (number1<0) // if the number is negative, the audio for the minus sign needs to be played
            {
                number1 = number1*-1; // the number needs to be turned into a positive number, because the array of clips only has positive numbers
                minusSound.start(); // plays the audio for the minus sign
                try
                {
                    Thread.sleep(800); // put it to sleep for 1 sec.
                }
                catch (InterruptedException ie)
                {
                    System.out.println(ie.getMessage()); // print error message if necessary
                }
                minusSound.stop();
                minusSound.setMicrosecondPosition(0);
            }
            
            numberClips[number1].start(); // plays the audio for the number (audio clips are indexed n the array in the correct order)
            try
                {
                    Thread.sleep(800); // put it to sleep for .8 sec.
                }
                catch (InterruptedException ie)
                {
                    System.out.println(ie.getMessage()); // print error message if necessary
                }
            numberClips[number1].stop();
            numberClips[number1].setMicrosecondPosition(0);
            
            if (operator == true) // if the numbers are being added together
            {
                plusSound.start();
                try
                {
                    Thread.sleep(800); 
                }
                catch (InterruptedException ie)
                {
                    System.out.println(ie.getMessage()); 
                }
                plusSound.stop();
                plusSound.setMicrosecondPosition(0);
            }
            
            else // otherwise they are being subtracted
            {
                minusSound.start();
                try
                {
                    Thread.sleep(800); 
                }
                catch (InterruptedException ie)
                {
                    System.out.println(ie.getMessage()); 
                }
                minusSound.stop();
                minusSound.setMicrosecondPosition(0);
            }
            
            if (number2<0) // repeats the same process for the second number
            {
                number2 = number2*-1;
                minusSound.start();
                try
                {
                    Thread.sleep(800); 
                }
                catch (InterruptedException ie)
                {
                    System.out.println(ie.getMessage()); 
                }
                minusSound.stop();
                minusSound.setMicrosecondPosition(0);
            }
            
            numberClips[number2].start();
            try
                {
                    Thread.sleep(800); 
                }
                catch (InterruptedException ie)
                {
                    System.out.println(ie.getMessage()); 
                }
            numberClips[number2].stop();
            numberClips[number2].setMicrosecondPosition(0);
            
            equalSound.start(); // plays the audio for "equals"
            try
                {
                    Thread.sleep(800); 
                }
                catch (InterruptedException ie)
                {
                    System.out.println(ie.getMessage()); // print error message if necessary
                }
            equalSound.stop();
            equalSound.setMicrosecondPosition(0);
            
            if (sum<0) // has to play minus sound if answer is negative
            {
                sum = sum*-1;
                minusSound.start();
                try
                {
                    Thread.sleep(800); // put it to sleep for 1 sec.
                }
                catch (InterruptedException ie)
                {
                    System.out.println(ie.getMessage()); // print error message if necessary
                }
                minusSound.stop();
                minusSound.setMicrosecondPosition(0);
            }
            
            numberClips[sum].start();
            try
            {
                Thread.sleep(800); // put it to sleep for 1 sec.
            }
            catch (InterruptedException ie)
            {
                System.out.println(ie.getMessage()); // print error message if necessary
            }
            numberClips[sum].stop();
            numberClips[sum].setMicrosecondPosition(0);
        }
        
        answer.setText(""); // sets the textfield to empty
        questionGood = false; // turns the flag off so a new question can be asked
        repaint();
        thread = null; // deactivates thread
        
        if (gameOver == false) // if the game is not over
        {
            askQuestion(); // asks a new question
            enter.setEnabled(true); // turns the button back on
        }
        return;
    }   

    public void askQuestion() // function to ask the question
    {
        while (questionGood == false) // keeps picking new numbers until the sum is between -10 and 10
        {
            Random random = new Random();
            number1 = random.nextInt(21)-10; // chooses between -10 and 10
            number2 = random.nextInt(21)-10;
            operator = random.nextBoolean(); // operator can only be plus or minus, so boolean is used
            
            if (operator == true) // this will add them together
            {
                if (number1+number2<11 && number1+number2>-11) // as long as the sum will be between -10 and 10
                {
                    question.setText("What is "+""+number1+" + "+""+number2+"?"); // displays the question
                    sum = number1 + number2; // sets the sum variable to the sum
                    numQuestions++; // increases the number of questions asked
                    repaint();
                    questionGood = true; // will break the loop
                }
                else
                {
                   continue; // otherwise the numbers aren't within the correct range, so new ones need to be chosen
                }
            }
            else // they will be subtracted, same process
            {
                if (number1-number2<11 && number1-number2>-11)
                {
                    question.setText("What is "+""+number1+" - "+""+number2+"?");
                    sum = number1 - number2;
                    numQuestions++;
                    repaint();
                    questionGood = true;
                }
                else
                {
                    continue;
                }
            }
        }
        
        if (numQuestions > 10) // once the number of questions has exceeded 10, it needs to stop the game
        {
            gameOver = true; // sets flag so that it won't continue asking questions
            question.setText("Game over. Your score was "+""+playerScore);
            answer.setText("");
            correct.setText(""); 
            score.setText("");
            enter.setEnabled(false); // turns off the submit button
            repaint();
        }
        return;
    }

    public MathTest() // contructor
    {
        super("Math Test"); // names the window
        Container pane = getContentPane(); // Make a window
        pane.setLayout(new BorderLayout()); // Set the layout of the window
        
        // creates panels on each section of the border layout
        JPanel titlePanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel centerPanel = new JPanel();

        // sets all the panels to be the desired colour
        titlePanel.setBackground(pink);
        rightPanel.setBackground(pink);
        leftPanel.setBackground(pink);
        bottomPanel.setBackground(pink);
        centerPanel.setBackground(greenish);
        correct.setForeground(greenish);
        
        // sets all of the labels and text items to the same font
        Font font1 = new Font("sansserif", Font.PLAIN, 30);
        score.setFont(font1);
        title.setFont(font1);
        question.setFont(font1);
        answer.setFont(font1);
        correct.setFont(font1);
        enter.setFont(font1);
        
        enter.addActionListener(this); // tells the program to listen to the button
        
        pane.add(titlePanel, BorderLayout.NORTH); // Put it on the top
        pane.add(rightPanel, BorderLayout.EAST); // Put it on the right
        pane.add(leftPanel, BorderLayout.WEST); // Put it on the left
        pane.add(bottomPanel, BorderLayout.SOUTH); // Put it on the bottom
        pane.add(centerPanel, BorderLayout.CENTER); // Put it in the middle
        
        titlePanel.add(title, BorderLayout.CENTER); // Put the title in the middle of the title panel
        
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS)); // Centre panel gets box layout
        // sets dimensions for a filler
        Dimension minSize = new Dimension(5, 75);
        Dimension prefSize = new Dimension(5, 75);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 75);
        
        centerPanel.add(new Box.Filler(minSize, prefSize, maxSize)); // filler is a blank spot in the box layout
        
        centerPanel.add(question); // Puts question on centre panel
        question.setHorizontalAlignment(JLabel.CENTER); // displays text in middle of label
        question.setAlignmentX(Component.CENTER_ALIGNMENT); // aligns the label in the center of the screen
        
        centerPanel.add(new Box.Filler(minSize, prefSize, maxSize)); // adds a filler below the question
        centerPanel.add(answer); // Puts answer on centre panel
        answer.setAlignmentX(Component.CENTER_ALIGNMENT);
        answer.setHorizontalAlignment(JTextField.CENTER);
        
        centerPanel.add(new Box.Filler(minSize, prefSize, maxSize));
        centerPanel.add(enter); // puts the enter button below the answer field
        enter.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(new Box.Filler(minSize, prefSize, maxSize));
        centerPanel.add(correct);
        correct.setAlignmentX(Component.CENTER_ALIGNMENT);
        correct.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(new Box.Filler(minSize, prefSize, maxSize));
        
        bottomPanel.add(score);
        score.setForeground(Color.black); // displays the score right away
        
        askQuestion(); // Asks the question right away
        
        /* the next section is where the audio is imported into the program.
           The audio files were named in order (the audio file for zero was named 0)
           to make it easy to import them using a for loop and an array. The audio
           files for correct and incorrect sounds were also given intefer names 
           in different folders for the same reason. The sounds for plus, minus,
           and equals were all imported idividually.*/
        try
        {
            for (int i=0; i<11; i++) // for all of the number audio files
            {
                File soundFile = new File("Audio/Math Audio/"+""+i+".wav");
                audioNumbers[i] = AudioSystem.getAudioInputStream(soundFile);
                numberClips[i] = AudioSystem.getClip();
                numberClips[i].open(audioNumbers[i]);
            }
            
            for (int i=0; i<2; i++) // for the audio files for correct answers
            {
                File soundFile = new File("Audio/Correct/"+""+i+".wav");
                audioCorrectClips[i] = AudioSystem.getAudioInputStream(soundFile);
                correctClips[i] = AudioSystem.getClip();
                correctClips[i].open(audioCorrectClips[i]);
            }
            
            for (int i=0; i<3; i++) // for the audio files for incorrect answers
            {
                File soundFile = new File("Audio/Incorrect/"+""+i+".wav");
                audioIncorrectClips[i] = AudioSystem.getAudioInputStream(soundFile);
                incorrectClips[i] = AudioSystem.getClip();
                incorrectClips[i].open(audioIncorrectClips[i]);
            }
            
            File soundFileEquals = new File("Audio/Math Audio/equals.wav"); // for equal sound
            audioEquals = AudioSystem.getAudioInputStream(soundFileEquals);
            equalSound = AudioSystem.getClip();
            equalSound.open(audioEquals);
            
            File soundFileMinus = new File("Audio/Math Audio/minus.wav"); // for minus sound
            audioMinus = AudioSystem.getAudioInputStream(soundFileMinus);
            minusSound = AudioSystem.getClip();
            minusSound.open(audioMinus);
            
            File soundFilePlus = new File("Audio/Math Audio/plus.wav"); // for plus sound
            audioPlus = AudioSystem.getAudioInputStream(soundFilePlus);
            plusSound = AudioSystem.getClip();
            plusSound.open(audioPlus);
        }
        catch(Exception e) // if there is a problem, display error message
        {
            JOptionPane.showMessageDialog(null,e);
        }

        setSize(900,600); // Set the size of the window
        setVisible(true); //make it visible
        
        repaint();
        return;
        
    }

    /* This is the main program
    * In this section, we call the class constructor, which constructs the window.
    * The second line tells the window how to close (using the "X" in the top right corner)
    */
   public static void main(String args[])
   {
       MathTest mt = new MathTest();      
       mt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       return;
   }
}
