package memo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Memo extends JFrame
{
//    Timer tm = new Timer(3000,new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            System.out.println("TEST timera");
//        }
//    });
   
    ImageIcon icons[] = new ImageIcon[16];
    ImageButton[] buttons= new ImageButton[icons.length];
    ImageIcon def = new ImageIcon("back.png");
    ImageIcon match = new ImageIcon("match.png");
    Boolean waitigForSecondCard=false;
    String matchIconName;
    JButton btn = new JButton("CLICK");
    JPanel panel = new JPanel();
    
    Memo()
    {
        super("test");
        this.setBounds(300,300,600,600);
        this.setDefaultCloseOperation(3);
        initComponents();
       
       
      //  tm.start();
        
        
    }
    
    void initComponents()
    {
        this.getContentPane().add(panel);
        panel.setLayout(new GridLayout(4,4));

       
        
          icons[0] = new ImageIcon("rune1.png");
          icons[1] = new ImageIcon("rune1.png");
          icons[2] = new ImageIcon("rune2.png");
          icons[3] = new ImageIcon("rune2.png");
          icons[4] = new ImageIcon("rune3.png");
          icons[5] = new ImageIcon("rune3.png");
          icons[6] = new ImageIcon("rune4.png");
          icons[7] = new ImageIcon("rune4.png");
          icons[8] = new ImageIcon("rune5.png");
          icons[9] = new ImageIcon("rune5.png");
          icons[10] = new ImageIcon("rune6.png");
          icons[11] = new ImageIcon("rune6.png");
          icons[12] = new ImageIcon("rune7.png");
          icons[13] = new ImageIcon("rune7.png");
          icons[14] = new ImageIcon("rune8.png");
          icons[15] = new ImageIcon("rune8.png");
        
           
          
  
                
          shuffleIcons(icons);
          for(int i=0;i<icons.length;i++)
          {  
              buttons[i]=new ImageButton(icons[i]);
              panel.add(panel.add(buttons[i]));    
               
          }     
          

    }
    
    // track
     static void shuffleIcons(ImageIcon[] ar)
      {
        
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
          int index = rnd.nextInt(i + 1);
          // Simple swap
          ImageIcon a = ar[index];
          ar[index] = ar[i];
          ar[i] = a;
        }
       }
    
    void deleteMatch(String MatchingName)
    {
      for(int i=0;i<buttons.length;i++)
          {  
              System.out.println("SPRAWDZAM");
              if(buttons[i].getName().equals(MatchingName))
              {
                 buttons[i].setIcon(match);
                 buttons[i].setDisabledIcon(buttons[i].getIcon());
                 buttons[i].setEnabled(false);
              }   
               
          }     
    
    }
    
    void blockButtons()
    {
       for(int i=0;i<buttons.length;i++)
          {  
              
              if(buttons[i].getIcon().equals(def))
              {
                 buttons[i].setDisabledIcon(buttons[i].getIcon());
                 buttons[i].setEnabled(false);
              }   
              
               
          }      
        
    
    }
    
     void unblockButtons()
    {
       for(int i=0;i<buttons.length;i++)
          {  
              
              if(buttons[i].getIcon().equals(def))
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
                  if(!buttons[i].getIcon().equals(match))
                     buttons[i].setIcon(def);
              }     
    }
            
    
    
    class ImageButton extends JButton
    {
    
        //String image;
        
        ImageButton(Icon icon )
        {
        
            setIcon(def);
            setName(icon.toString());
            addActionListener(new ActionListener() 
            {
                @Override
                 public void actionPerformed(ActionEvent e) 
                 {
                    System.out.println("sprawdzam czy Waiting");
                    if(!waitigForSecondCard)
                    {

                        setIcon(icon);
                        System.out.println("WAITING FOR SECOND CARD");
                        waitigForSecondCard=true; 
                        matchIconName=icon.toString();
                        System.out.println(matchIconName);
 
                    }
                    else if(waitigForSecondCard)
                    {
                         setText("");
                         setIcon(icon);
                         System.out.println("Checking match");
                         waitigForSecondCard=false; 
                         if(((ImageIcon)icon).getDescription().equals(matchIconName))
                            {
                                Timer tm = new Timer(2000,new ActionListener() 
                            {
                                @Override
                                public void actionPerformed(ActionEvent e) 
                                {
                                    
                                     deleteMatch(matchIconName);   
                                     matchIconName="";
                                      
                                }

                            });
                                tm.start();
                                tm.setRepeats(false);
                               
                            }
                         else if(!getName().equals(matchIconName))
                         {
                            blockButtons();
                            Timer tm = new Timer(2000,new ActionListener() 
                            {
                                @Override
                                public void actionPerformed(ActionEvent e) 
                                {
                                    
                                    revertCard();
                                    unblockButtons();
                                      
                                }

                            });
                            matchIconName="";
                            
                            tm.setRepeats(false);
                            tm.start();
                            
                         
                         }
                    }
                    
                }
            });
            
        }
        
        
    
    }
    
    public static void main(String[] args) {
        
        new Memo().setVisible(true);
        
    }
    
}