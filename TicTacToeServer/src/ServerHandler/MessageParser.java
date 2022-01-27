/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerHandler;

/**
 *
 * @author START
 */
public class MessageParser {
    
    
    
    
    public static void checkClientMsg(String msg)
    {
        String[] arrString=msg.split("::");
        //System.out.println(msg);
      //  System.out.println(arrString[0]);
        //System.out.println(arrString[1]);
        
        if("status".equals(arrString[0]))
        {
            if("Online".equals(arrString[1]))
            {
                System.out.println("My test succeded");
            }
        }
        
        
        switch (arrString[0])
        {
            case "login": /*-------------------login::username::password----------------------*/
                
                if("Username from database".equals(arrString[1]))
                {
                    if("Password from database".equals(arrString[2]))
                    {
                        System.out.println("My test succeded");
                    }
                }
                
                break;
                
            case "signup":/*-------------------signup::username::password----------------------*/
                
         /*------------Insert user data from database----------*/
                
                break; 
                
                
           case "playing": /*-------------------playing::username::turn::indexPlaymove----------------------*/
                
         /*------------In a game----------*/
               
                break;
                
           case "finished_playing": /*-------------------playing::username::winnerStatus----------------------*/
                
         /*------------In a game----------*/
               
                break; 
           
           case "paused_playing": /*-------------------playing::username::winnerStatus----------------------*/
                
         /*------------In a game----------*/
               
                break;     
                
                
           
           case "notplaying": /*-------------------notplaying::username::waitinginqueue/notwaiting--------------------*/
                
         /*------------Not doing anything----------*/
               
                break;  
                
                                                            //sender1
           case "message":  /*-------------------message::username1::username2/all chat----------------------*/
    
               
         /*------------Writting a message----------*/
               
                break;     
               
                
        }
}
}    
