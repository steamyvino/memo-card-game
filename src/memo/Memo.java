package memo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Memo extends JFrame implements MouseListener
{

    JPanel panel = new JPanel();
    ImageIcon icons[] = new ImageIcon[16];
    ImageButton[] buttons= new ImageButton[icons.length];
    ImageIcon coveredCardImage = new ImageIcon("resources/back.png");
    ImageIcon matchedCardImage = new ImageIcon("resources/match.png");
    
    ImageIcon avatars[] = new ImageIcon[6];
    int avatarChooser=0;
    
    ImageIcon logoImage = new ImageIcon("resources/memologo.png");
    JLabel logo = new JLabel(); 
     
    JPanel panelTop = new JPanel();
    JButton btnStart = new JButton("Start");
    JTextField name1 = new JTextField("<Player 1 name>",10);
    JTextField name2 = new JTextField("<Player 1 name>",10);
    JButton playerChoice1 = new JButton("Choose Avatar");
    JButton playerChoice2 = new JButton("Choose Avatar");
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
    }
    
    void initComponents()
    {
        this.getContentPane().add(panel);
        this.getContentPane().add(panelTop,BorderLayout.NORTH);
        
        panel.setBackground(Color.white);
        panel.add(logo,BorderLayout.CENTER);
        logo.setIcon(logoImage);
        
        avatars[0] = new ImageIcon("resources/boy1.png");
        avatars[1] = new ImageIcon("resources/boy2.png");
        avatars[2] = new ImageIcon("resources/girl1.png");
        avatars[3] = new ImageIcon("resources/girl2.png");
        avatars[4] = new ImageIcon("resources/girl2.png");
        avatars[5] = new ImageIcon("resources/girl4.png");
                                   
        panelTop.setBackground(Color.white);
        panelTop.add(playerChoice1);
        panelTop.add(name1);
        panelTop.add(scorePlayer1);
        panelTop.add(Box.createHorizontalStrut(50));
        panelTop.add(btnStart);
        panelTop.add(Box.createHorizontalStrut(50));
        panelTop.add(scorePlayer2);
        panelTop.add(name2);
        panelTop.add(playerChoice2);
        
        name1.setMinimumSize(new Dimension(150,10));
        name2.setMinimumSize(new Dimension(150,10));
        name1.setFont(new Font("Lucida", 0, 12));
        name2.setFont(new Font("Lucida", 0, 12));
                
        playerChoice1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(35, 88, 33), 3, true));
        playerChoice2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(35, 88, 33), 3, true));
               
        playerChoice1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chooseAvatar(ae);
            }
        });
        playerChoice2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chooseAvatar(ae);
            }
        });
      
        btnStart.setBackground(Color.white);
        playerChoice1.setPreferredSize(new Dimension(128,128));
        playerChoice1.setBackground(Color.white);
        playerChoice2.setPreferredSize(new Dimension(128,128));
        playerChoice2.setBackground(Color.white);
                   
        name1.addMouseListener(this);
        name2.addMouseListener(this);
                
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                playerChoice1.setDisabledIcon(playerChoice1.getIcon());
                playerChoice2.setDisabledIcon(playerChoice2.getIcon());
              
                playerChoice1.setEnabled(false);
                playerChoice2.setEnabled(false);
                createBoard();
                panel.revalidate();
                btnStart.setEnabled(false);
                name1.setFont(new Font("Lucida", 1, 12));  //Player one first (BOLD font)
        
            }
        });

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
    
    }
    
    void createBoard()
    {
          panel.remove(logo);
          panel.setLayout(new GridLayout(4,4));
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
              buttons[i]=new ImageButton(icons[i]);
              panel.add(panel.add(buttons[i]));    
               
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
    
    void firstCardReveal(JButton btn,Icon icon)
    {   
        System.out.println("PIERWSZA");
        btn.setEnabled(false);
        btn.setIcon(icon);   
        waitigForSecondCard=true; 
        matchIconName=icon.toString();
        btn.setDisabledIcon(icon);
       
        System.out.println(icon.toString());
        
    }
    void secondCardReveal(JButton btn,Icon icon)
    {
    
        btn.setText("");
        btn.setIcon(icon); 
        btn.setDisabledIcon(icon);
        btn.setEnabled(false);
        System.out.println("DRUGA");
        System.out.println(icon.toString());
        
        waitigForSecondCard=false; 
        if(((ImageIcon)icon).getDescription().equals(matchIconName))
            {   
                blockButtons(btn);
                Timer tm = new Timer(2000,new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                   
                     processMatchedCards(matchIconName);   
                     matchIconName="";
                     unblockButtons(btn);


                }

            });
                
                tm.start();
                tm.setRepeats(false);

            }
         else if(!getName().equals(matchIconName))
         {
           
            blockButtons(btn);
            Timer tm = new Timer(2000,new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                   
                    revertCard();
                    unblockButtons(btn);
                    matchIconName="";
                    changeTurn();

                }

            });
                                
            tm.setRepeats(false);
            tm.start();                       
         }
    
    }
    
    void checkMatch(JButton btn,Icon icon)
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
    
    void processMatchedCards(String MatchingName)
    {
      for(int i=0;i<buttons.length;i++)
          {  
              if(buttons[i].getName().equals(MatchingName))
              {
                 matchedCards=matchedCards+1;
                 buttons[i].setIcon(matchedCardImage);
                 buttons[i].setDisabledIcon(buttons[i].getIcon());
                 buttons[i].setEnabled(false);
                 setPoints();
              }                 
          }     
      if (matchedCards==16) 
          endGame();
   
    }
    
    void endGame()
    {
         if(pointsPlayer1>pointsPlayer2)
         {
            JOptionPane.showMessageDialog(null, name1.getText()+" Wygrywa!Gratulacje!", "Koniec Gry", JOptionPane.INFORMATION_MESSAGE, playerChoice1.getIcon());   
         }   
         else if(pointsPlayer1<pointsPlayer2)
         {
            JOptionPane.showMessageDialog(null, name2.getText()+" Wygrywa! Gratulacje!", "Koniec Gry", JOptionPane.INFORMATION_MESSAGE, playerChoice2.getIcon());
         }
         else
         {
            JOptionPane.showMessageDialog(null, "REMIS! Gratulacje!", "Koniec Gry", JOptionPane.INFORMATION_MESSAGE);
         }
         
         name1.setText("<Player 1 name>");
         name1.setText("<Player 2 name>");
         pointsPlayer1=0;
         pointsPlayer2=0;
         scorePlayer1.setText(" ");
         scorePlayer2.setText(" ");
         playerChoice1.setIcon(null);
         playerChoice1.setText("Choose avatar");
         playerChoice2.setIcon(null);
         playerChoice2.setText("Choose avatar");
         panel.removeAll();
         panel.repaint();
         btnStart.setEnabled(true);
    }
    
    void changeTurn()
    {
       if(playerOneTurn)
       {
           playerOneTurn=false;
           name1.setFont(new Font("Lucida", 0, 12));
           name2.setFont(new Font("Lucida", 1, 12));
       }
       else
       {
           playerOneTurn=true;
           name1.setFont(new Font("Lucida", 1, 12));
           name2.setFont(new Font("Lucida", 0, 12));
       }
    }
    
    void blockButtons(JButton secondbtn)
    {
     
       for(int i=0;i<buttons.length;i++)
          {  
              
              if(buttons[i].getIcon().equals(coveredCardImage))
              {
                 buttons[i].setDisabledIcon(buttons[i].getIcon());
                 buttons[i].setEnabled(false);
              }   
              
               
          }      
        
    
    }
    
     void unblockButtons(JButton secondbtn)
    {
      // secondbtn.setEnabled(false);
       for(int i=0;i<buttons.length;i++)
          {  
              
              if(buttons[i].getIcon().equals(coveredCardImage))
              {
                 buttons[i].setDisabledIcon(buttons[i].getIcon());
                 buttons[i].setEnabled(true);
              }   
              
               
          }      
        
    
    }
    
    void revertCard()
    {

        for(int i=0;i<buttons.length;i++)
              {   
                  if(!buttons[i].getIcon().equals(matchedCardImage))
                     buttons[i].setIcon(coveredCardImage);
              }     
    }

   
    class ImageButton extends JButton
    {
    
        //String image;
        
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
                    
                    checkMatch((JButton)e.getSource(),icon);
                    
                }
            });
            
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
    
    @Override
    public void mouseClicked(MouseEvent me) {
       ((JTextField)me.getSource()).setEditable(true);
       ((JTextField)me.getSource()).setText(" ");
       ((JTextField)me.getSource()).setCaretColor(Color.BLACK);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
       
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        ((JTextField)me.getSource()).getCaret().setVisible(false);
        ((JTextField)me.getSource()).setEditable(false);
        
    }
    
    public static void main(String[] args) {
        
        new Memo().setVisible(true);
   
    }
    
}

