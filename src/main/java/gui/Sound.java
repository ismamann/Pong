package gui;
import java.io.File; 
import java.io.IOException; 

import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Clip;  
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.application.Application;
import javafx.stage.Stage; 

/* ------------------------------------------------------------------------------------------------------*/

//Test pour avoir un son lors du lancement du jeu. 

public class Sound extends Application { 

    
	//define storage for start position
	Long nowFrame; 
	Clip clip; 
	
	// get the clip status 
	String thestatus; 
	
	AudioInputStream audioStream; 
	 

	// initialize both the clip and streams 
	public Sound(String thePath) 
		throws UnsupportedAudioFileException, 
		IOException, LineUnavailableException 
	{ 
		// the input stream object 
		audioStream = 
				AudioSystem.getAudioInputStream(
				    new File(thePath)
				    .getAbsoluteFile()); 
		
		// the reference to the clip 
		clip = AudioSystem.getClip(); 
 
		clip.open(audioStream); 
		
		clip.loop(Clip.LOOP_CONTINUOUSLY); 
	} 

	
	
	
	// play 
	public void play() 
	{ 
		//start the clip 
		clip.start(); 
		
		thestatus = "play"; 
	} 
	
	// Pause audio 
	public void pause() 
	{ 
		if (thestatus.equals("paused")) 
		{ 
			System.out.println("audio is already paused"); 
			return; 
		} 
		this.nowFrame = 
		this.clip.getMicrosecondPosition(); 
		clip.stop(); 
		thestatus = "paused"; 
	} 
	
	
	
	// stop audio 
	public void stop() throws UnsupportedAudioFileException, 
	IOException, LineUnavailableException 
	{ 
		nowFrame = 0L; 
		clip.stop(); 
		clip.close(); 
	} 
	
	
	


    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        
    } 

} 