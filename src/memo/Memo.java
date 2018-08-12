package memo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;




public class Memo extends JFrame 
{

    JPanel panelBoard = new JPanel();
    ImageIcon icons[] = new ImageIcon[16];
    ImageButton[] cards= new ImageButton[icons.length];
    ImageIcon coveredCardImage = new ImageIcon("resources/back.png");
    ImageIcon matchedCardImage = new ImageIcon("resources/match.png");
    
    ImageIcon avatars[] = new ImageIcon[6];
    int avatarChooser=0;
    
    ImageIcon logoImage = new ImageIcon("resources/memologo.png");
    JLabel logo = new JLabel(); 
     
    JPanel panelTop = new JPanel();
    JButton btnStart = new JButton("Start");
    NameField name1 = new NameField("<Player 1 name>");
    NameField name2 = new NameField("<Player 1 name>");
    Avatar playerAvatar1 = new Avatar("Choose Avatar");
    Avatar playerAvatar2 = new Avatar("Choose Avatar");
    JLabel scorePlayer1 = new JLabel("  ");
    JLabel scorePlayer2 = new JLabel("  ");
      
    String matchIconName;
    Boolean waitigForSecondCard=false;
    int pointsPlayer1=0;
    int pointsPlayer2=0;
    int matchedCards=0;
    boolean playerOneTurn = true;
            
    
    Memo()
    {
        super("Memo");
        this.setBounds(300,300,750,828);
        this.setDefaultCloseOperation(3);
        initComponents();  
        initTopPanel();
    }
    
    /**
     * Inner class of Avatar Button
     */
    class Avatar extends JButton
    {
        Avatar(String text)
        {
            super(text);
            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(35, 88, 33), 3, true));
            setPreferredSize(new Dimension(128,128));
            setBackground(Color.white);         
            this.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent ae)
                {
                    chooseAvatar(ae);
                }
            });         
        }
    }
    /**
     * Inner class of Player name textArea
     */
    class NameField extends JTextField
    {
        String defaultText;
        NameField thisNameField = this;
        NameField(String text)
        {   
            super(text,10);
            defaultText=text;         
            setFont(new Font("Lucida", 0, 12));
            setDisabledTextColor(Color.BLACK);     
            this.addMouseListener(new MouseAdapter() 
            {
                public void mouseClicked(MouseEvent me)
                {
                   if (thisNameField.isEnabled())
                   thisNameField.setText("");
                }
            });  
        }
        
        void resetNameFields()
        {
            this.setText(defaultText);     
        }
        
        void setActivePlayerName()
        {
            setFont(new Font("Lucida", 1, 12));  
        }
        
        void setWaitingPlayerName()
        {   
            setFont(new Font("Lucida", 0, 12));    
        }  
    }
    
    /**
     * Inner class of cards 
     */
    class ImageButton extends JButton
    {
        ImageButton(Icon icon )
        {    
            setIcon(coveredCardImage);     
            setBackground(Color.white);
            setName(icon.toString());
            setFocusable(false);
            addActionListener(new ActionListener()            
            {
                @Override
                 public void actionPerformed(ActionEvent e) 
                 { 
                    revealCard((JButton)e.getSource(),icon);    
                 }
            });    
        }               
    }
    

    void initComponents()
    {
        this.getContentPane().add(panelBoard);
        this.getContentPane().add(panelTop,BorderLayout.NORTH);
        
        panelBoard.setBackground(Color.white);
        panelBoard.add(logo,BorderLayout.CENTER);
        logo.setIcon(logoImage);
        
        avatars[0] = new ImageIcon("resources/boy1.png");
        avatars[1] = new ImageIcon("resources/boy2.png");
        avatars[2] = new ImageIcon("resources/girl1.png");
        avatars[3] = new ImageIcon("resources/girl2.png");
        avatars[4] = new ImageIcon("resources/girl2.png");
        avatars[5] = new ImageIcon("resources/girl4.png");

    }
      
    void initTopPanel()
    {
        panelTop.setBackground(Color.white);
        panelTop.add(playerAvatar1);
        panelTop.add(name1);
        panelTop.add(scorePlayer1);
        panelTop.add(Box.createHorizontalStrut(50));
        panelTop.add(btnStart);
        panelTop.add(Box.createHorizontalStrut(50));
        panelTop.add(scorePlayer2);
        panelTop.add(name2);
        panelTop.add(playerAvatar2);
        
        btnStart.setBackground(Color.white);
      
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                name1.setEnabled(false);
                name2.setEnabled(false);
               
                playerAvatar1.setDisabledIcon(playerAvatar1.getIcon());
                playerAvatar2.setDisabledIcon(playerAvatar2.getIcon());
              
                playerAvatar1.setEnabled(false);
                playerAvatar2.setEnabled(false);
                createBoard();
                panelBoard.revalidate();
                btnStart.setEnabled(false);
                name1.setActivePlayerName();  //Player one first (BOLD font)
        
            }
        });   
    }

    void createBoard()
    {
          panelBoard.remove(logo);
          panelBoard.setLayout(new GridLayout(4,4));
          icons[0] = new ImageIcon("resources/rune1.png");
          icons[1] = new ImageIcon("resources/rune1.png");
          icons[2] = new ImageIcon("resources/rune2.png");
          icons[3] = new ImageIcon("resources/rune2.png");
          icons[4] = new ImageIcon("resources/rune3.png");
          icons[5] = new ImageIcon("resources/rune3.png");
          icons[6] = new ImageIcon("resources/rune4.png");
          icons[7] = new ImageIcon("resources/rune4.png");
          icons[8] = new ImageIcon("resources/rune5.png");
          icons[9] = new ImageIcon("resources/rune5.png");
          icons[10] = new ImageIcon("resources/rune6.png");
          icons[11] = new ImageIcon("resources/rune6.png");
          icons[12] = new ImageIcon("resources/rune7.png");
          icons[13] = new ImageIcon("resources/rune7.png");
          icons[14] = new ImageIcon("resources/rune8.png");
          icons[15] = new ImageIcon("resources/rune8.png");
                                    
          shuffleIcons(icons);
          for(int i=0;i<icons.length;i++)
          {  
              cards[i]=new ImageButton(icons[i]);
              panelBoard.add(panelBoard.add(cards[i]));                 
          }     
    }
  
    static void shuffleIcons(ImageIcon[] icons)
    {

        Random rnd = ThreadLocalRandom.current();
        for (int i = icons.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            ImageIcon a = icons[index];
            icons[index] = icons[i];
            icons[i] = a;
        }
    }
      
    public void chooseAvatar(ActionEvent ae) 
    {
         if (avatarChooser==5)
             avatarChooser=0;
         else
             avatarChooser++;

         ((JButton)ae.getSource()).setText("");
         ((JButton)ae.getSource()).setIcon(avatars[avatarChooser]);            
    }
    
    /** 
     * Player chooses card
     * 
     */
    
    void revealCard(JButton btn,Icon icon)
    {       
       if(!waitigForSecondCard)
        {
           firstCardReveal(btn,icon);
        }
        else if(waitigForSecondCard)
        {
           secondCardReveal(btn,icon);
        }     
    }
    
    void firstCardReveal(JButton btn,Icon icon)
    {   
        showChard(btn,icon);      
        waitigForSecondCard=true; 
        matchIconName=icon.toString();        
    }
    void secondCardReveal(JButton btn,Icon icon)
    {
          showChard(btn,icon);
          waitigForSecondCard=false;  
          checkMatch(btn,icon);

    }
    
    void showChard(JButton btn,Icon icon)
    {
        btn.setIcon(icon); 
        btn.setDisabledIcon(icon);
        btn.setEnabled(false);
        System.out.println(icon.toString());
    }
    
    /** 
     * Checking of selected card matches
     * 
     */   
    void checkMatch(JButton btn,Icon icon)
    {
        /**
         * selected cards match
         */
        if(((ImageIcon)icon).getDescription().equals(matchIconName))
            {   
                disableCards(btn);
                Timer tm = new Timer(2000,new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {               
                     processMatchedCards(matchIconName);   
                     matchIconName="";
                     enableCards(btn);
                }
            });                
                tm.start();
                tm.setRepeats(false);
            }      
        /**
         * selected cards do not match
         */
         else if(!getName().equals(matchIconName))
         {           
            disableCards(btn);
            Timer tm = new Timer(2000,new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {                 
                    coverCard();
                    enableCards(btn);
                    matchIconName="";
                    changeTurn();
                }
            });                                
            tm.setRepeats(false);
            tm.start();                       
         }  
    }
    
    /** 
     * Disables all card from clicking for the time of checking if the selected card match each other
     * 
     */
    
    void disableCards(JButton secondbtn)
    {  
       for(int i=0;i<cards.length;i++)
          {      
              if(cards[i].getIcon().equals(coveredCardImage))
              {
                 cards[i].setDisabledIcon(cards[i].getIcon());
                 cards[i].setEnabled(false);
              }            
          }       
    }
    
    /** 
     * Enables cards that left in play
     * 
     */
     void enableCards(JButton secondbtn)
    {
       for(int i=0;i<cards.length;i++)
          {  
              if(cards[i].getIcon().equals(coveredCardImage))
              {
                 cards[i].setDisabledIcon(cards[i].getIcon());
                 cards[i].setEnabled(true);
              }        
          }      
    }

    void processMatchedCards(String MatchingName)
    {
      for(int i=0;i<cards.length;i++)
          {  
              if(cards[i].getName().equals(MatchingName))
              {
                  coverMatchedCard(cards[i]);      
                  setPoints();
              }                 
          }      
    }
    /**
     * 
     * Covering and disabling guessed cards
     * 
     */
    void coverMatchedCard(ImageButton card)
    {
         card.setIcon(matchedCardImage);
         card.setDisabledIcon(card.getIcon());
         card.setEnabled(false);
    }
    
    void setPoints()
    {
        if (playerOneTurn)
        {
            pointsPlayer1++;
            scorePlayer1.setText(Integer.toString(pointsPlayer1));
        }
        else if(!playerOneTurn)
        {
            pointsPlayer2++;
            scorePlayer2.setText(Integer.toString(pointsPlayer2));
        }    
        matchedCards=matchedCards+1;
        checkIfGameOver();     
    }
    
    /**
     * 
     * Covering unguessed cards that are still in play
     * 
     */
    void coverCard()
    {
        for(int i=0;i<cards.length;i++)
              {   
                  if(!cards[i].getIcon().equals(matchedCardImage))
                     cards[i].setIcon(coveredCardImage);
              }     
    }
    
    void changeTurn()
    {
       if(playerOneTurn)
       {
           playerOneTurn=false;
           name1.setWaitingPlayerName();
           name2.setActivePlayerName();
       }
       else
       {
           playerOneTurn=true;
           name1.setActivePlayerName();
           name2.setWaitingPlayerName();
       }
    }
    
    /**
     * Change (matchedCards==2) if you want to end game after first match
     */
    void checkIfGameOver()
    {
        if (matchedCards==16) 
              endGame();  
    }
    
    void endGame()
    {
         if(pointsPlayer1>pointsPlayer2)
         {
            JOptionPane.showMessageDialog(null, name1.getText()+" wins! Congratulations", "Game Over!", JOptionPane.INFORMATION_MESSAGE, playerAvatar1.getIcon());   
         }   
         else if(pointsPlayer1<pointsPlayer2)
         {
            JOptionPane.showMessageDialog(null, name2.getText()+" wins! Congratulations!", "Game Over!", JOptionPane.INFORMATION_MESSAGE, playerAvatar2.getIcon());
         }
         else
         {
            JOptionPane.showMessageDialog(null, "Its a draw! Congratulations!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
         }
         
         resetTopPanelFields();
    }
    
    void resetTopPanelFields()
    {    
         name1.resetNameFields();
         name2.resetNameFields();
         name1.setWaitingPlayerName();
         name2.setWaitingPlayerName();
         scorePlayer1.setText(" ");
         scorePlayer2.setText(" ");
         playerAvatar1.setIcon(null);
         playerAvatar1.setText("Choose avatar");
         playerAvatar2.setIcon(null);
         playerAvatar2.setText("Choose avatar");
         name1.setEnabled(true);
         name2.setEnabled(true);
         playerAvatar1.setEnabled(true);
         playerAvatar2.setEnabled(true);
         pointsPlayer1=0;
         pointsPlayer2=0;
         panelBoard.removeAll();
         panelBoard.repaint();
         btnStart.setEnabled(true);
         matchedCards=0; 
    }
    

    public static void main(String[] args) 
    {
        new Memo().setVisible(true);
    }
}

