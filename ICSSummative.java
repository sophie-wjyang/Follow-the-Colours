/*
ICS SUMMATIVE PROJECT: Follow the Colours
Ryan Chang, Jenny Wu, David Walji, Sophie Yang
TOPS Class of 2021
January 23, 2019
ICS203-03
*/

import hsa.Console;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import sun.audio.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class ICSSummative extends Console
{
    private Canvas canvas = graphicsCanvas;
    private static Graphics2D graphics;
    private static Random rand = new Random ();
    public Random random = new Random ();
    private Rectangle rect = new Rectangle (0, 0, 100, 100);
    public static BufferedImage image;
    public static ICSSummative ICSSummative;
    static Boolean active = false;
    static Boolean gameOn = false;
    static Boolean mouseOn = true;

    //******************************AUDIO METHOD*********************************
    public static void playAudio (String file) throws Exception //for .wav only
    {
	InputStream in = new FileInputStream (file);
	AudioStream as = new AudioStream (in);
	AudioPlayer.player.start (as);
    }


    //***************************LOAD IMAGE METHOD*******************************
    public static BufferedImage loadImage (String name) throws Exception
    {
	BufferedImage img = ImageIO.read (new File (name));
	return img;
    }


    //****************************MOUSE LISTENER*********************************
    public ICSSummative ()
    {
	canvas.addMouseListener (new TestMouseListener ());
    }


    //**************Random Number Method**************************************
    // random pick a number between 1 and x
    public static int rando1 (int x)
    {
	int rando = (int) ((Math.random () * x) + 1);

	return rando;
    }


    //**************************DRAW IMAGE*******************************
    // draw picture imageName at (positionX, positionY)
    public static void drawimage (String imageName, int positionX, int positionY) throws Exception
    {
	Image imageTemp = loadImage (imageName);
	ICSSummative.drawImage (imageTemp, positionX, positionY, null);
    }


    //************DRAW A SQUARE (1, 2, 3, 4) WITH COLOUR*****************
    public static void drawsquare (int squareNum, Color col)
    {
	int[] points1 = {147, 312, 312, 147, 116, 116, 281, 281};
	int[] points2 = {318, 483, 483, 318, 116, 116, 281, 281};
	int[] points3 = {147, 312, 312, 147, 287, 287, 452, 452};
	int[] points4 = {318, 483, 483, 318, 287, 287, 452, 452};

	int i = 0;

	int[] points;
	if (squareNum == 1)
	    points = points1;
	else if (squareNum == 2)
	    points = points2;
	else if (squareNum == 3)
	    points = points3;
	else
	    //if (squareNum == 4)
	    points = points4;

	ICSSummative.setColor (col);
	// read 4 numbers and put into a array as horizontal
	int[] Horizontal;
	Horizontal = new int [4];
	for (int j = 0 ; j < 4 ; j++)
	{
	    Horizontal [j] = points [i + j];
	}
	i = i + 4;

	// read x numbers and put into a array as vertical
	int[] Vertical = new int [4];
	for (int j = 0 ; j < 4 ; j++)
	{
	    Vertical [j] = points [i + j];
	}

	ICSSummative.fillPolygon (Horizontal, Vertical, 4);
    }


    //****************GETTING THE ORIGINAL COLOUR*************************
    public static Color getOriginalColour (int template, int squareNum)
    {
	Color t1s1 = new Color (92, 228, 232);
	Color t1s2 = new Color (255, 87, 87);
	Color t1s3 = new Color (126, 217, 87);
	Color t1s4 = new Color (255, 222, 89);
	// if (template == 1)
	// {
	if (squareNum == 1)
	{
	    return t1s1;
	}
	else if (squareNum == 2)
	{
	    return t1s2;
	}
	else if (squareNum == 3)
	{
	    return t1s3;
	}
	else
	    return t1s4;
    }


    //****************GETTING THE HIGHLIGHTED COLOUR**************************
    public static Color getHighlightColour (int template, int squareNum)
    {
	Color h1s1 = new Color (145, 252, 255);
	Color h1s2 = new Color (255, 126, 126);
	Color h1s3 = new Color (158, 255, 116);
	Color h1s4 = new Color (255, 234, 151);
	// if(template == 1)
	// {
	if (squareNum == 1)
	{
	    return h1s1;
	}
	else if (squareNum == 2)
	{
	    return h1s2;
	}
	else if (squareNum == 3)
	{
	    return h1s3;
	}
	else
	    return h1s4;
    }


    //***********************HIGHLIGHT SQUARES IN GAME*******************************
    public static void highlightSquare (int template, int squareNum) throws Exception
    {

	playAudio ("square" + squareNum + ".wav");
	drawsquare (squareNum, getHighlightColour (template, squareNum));

	Thread.sleep (250);

	drawsquare (squareNum, getOriginalColour (template, squareNum));

    }


    //**********************NEXT LEVEL SEQUENCE METHOD*************************
    public static int[] nextLevelSequence (int[] sequence)
    {
	int[] newSequence = new int [sequence.length + 1];
	for (int i = 0 ; i < sequence.length ; i++)
	{
	    newSequence [i] = sequence [i];
	}
	newSequence [sequence.length] = rando1 (4);
	return newSequence;
    }


    //***********************DRAW TEMPLATE METHOD*******************************
    public static void drawtemplate (int templateNum) throws Exception
    {
	drawsquare (1, getOriginalColour (templateNum, 1));
	drawsquare (2, getOriginalColour (templateNum, 2));
	drawsquare (3, getOriginalColour (templateNum, 3));
	drawsquare (4, getOriginalColour (templateNum, 4));
    }


    //**********************NORMAL GAME MODE*************************************
    public static void playGame () throws Exception
    {
	boolean succeed = true;

	graphics = image.createGraphics (); //Graphics2D

	// draw background
	drawimage ("gamebg.png", 0, 0);

	// current level, actual level starts at 1
	int level = 0;

	//choose random template
	int template = rando1 (5);

	// array to keep the sequence
	int[] sequence = {};
	//draw template
	drawtemplate (template);
	//1drawtemplate(1);

	do
	{
	    if (level == 0)
	    {
		//showInfo ("New game");
		Thread.sleep (1500);
	    }
	    else
	    {
		//showInfo ("Next level");
	    }
	    level = level + 1;
	    // generate template
	    sequence = nextLevelSequence (sequence);

	    for (int i = 0 ; i < level ; i++)
	    {
		highlightSquare (template, sequence [i]);
		Thread.sleep (200);
	    }
	    for (int j = 0 ; j < sequence.length ; j++)
	    {
		int k = ICSSummative.getChar ();
		int entered = k - '1' + 1;
		if (entered == sequence [j])
		{
		    playAudio ("square" + entered + ".wav");
		    highlightSquare (template, entered);
		    //to-do: show mark+1
		}
		else
		{
		    //showInfo ("Game over");
		    playAudio ("wrongsound.wav");
		    //level = 0;
		    //sequence = new int [0];
		    //break;
		    Thread.sleep (300);
		    goback ();
		}
	    }
	    Thread.sleep (1000);
	}
	while (succeed);
    }


    //**********************LIGHTNING GAME MODE*************************************
    public static void playLightning () throws Exception
    {
	boolean succeed = true;

	graphics = image.createGraphics (); //Graphics2D

	// draw background
	drawimage ("gamebg.png", 0, 0);

	// current level, actual level starts at 1
	int level = 0;

	//choose random template
	int template = rando1 (5);

	// array to keep the sequence
	int[] sequence = {};
	//draw template
	drawtemplate (template);
	//1drawtemplate(1);

	do
	{
	    if (level == 0)
	    {
		//showInfo ("New game");
		Thread.sleep (700);
	    }
	    else
	    {
		//showInfo ("Next level");
	    }
	    level = level + 1;
	    // generate template
	    sequence = nextLevelSequence (sequence);

	    for (int i = 0 ; i < level ; i++)
	    {
		highlightSquare (template, sequence [i]);
		Thread.sleep (1);
	    }
	    //display text

	    for (int j = 0 ; j < sequence.length ; j++)
	    {
		int k = ICSSummative.getChar ();
		int entered = k - '1' + 1;
		//showSequenceAndNum (sequence, entered);
		if (entered == 5)
		    goback ();
		if (entered == sequence [j])
		{
		    playAudio ("square" + entered + ".wav");
		    highlightSquare (template, entered);
		    //to-do: show mark+1
		}
		else
		{
		    //showInfo ("Game over");
		    playAudio ("wrongsound.wav");
		    //level = 0;
		    //sequence = new int [0];
		    //break;
		    Thread.sleep (1);
		    goback ();
		}
	    }
	    Thread.sleep (700);
	}
	while (succeed);
    }


    //**************************MOUSE LISTENER OUTPUTS*****************************
    public static void mouseListening (int x, int y) throws Exception
    {
	BufferedImage img = null;
	/*if (x > 418 && y > 35 && x < 418 + 190 && y < 35 + 83) {
	    try {
		if (mouseOn)
		gameOn = true;
		playGame();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}*/
	/*if (x > 418 && y > 145 && x < 418 + 190 && y < 145 + 83) {
	    try {
		if (mouseOn)
		playLightning();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}*/
	if (x > 418 && y > 255 && x < 418 + 190 && y < 255 + 83)
	{
	    try
	    {
		if (mouseOn)
		{
		    active = true;
		    Thread.sleep (300);
		    loadgame ();
		}
	    }
	    catch (Exception e)
	    {
		e.printStackTrace ();
	    }
	}
	if (x > 418 && y > 365 && x < 418 + 190 && y < 365 + 83)
	{
	    try
	    {
		if (mouseOn)
		    rollcredits ();
	    }
	    catch (Exception e)
	    {
		e.printStackTrace ();
	    }
	}
	if (x < 170 + 155 && y < 170 + 118 && x > 155 && y > 118)
	{
	    try
	    {
		if (active)
		    blueglow ();
	    }
	    catch (Exception e)
	    {
		e.printStackTrace ();
	    }
	}
	if (x > 343 && y < 170 + 118 && x < 343 + 170 & y > 118)
	{
	    try
	    {
		if (active)
		    redglow ();
		//if (gameOn)
		//z = 2;
	    }
	    catch (Exception e)
	    {
		e.printStackTrace ();
	    }
	}
	if (x < 170 + 155 && y > 298 && x > 155 && y < 298 + 170)
	    try
	    {
		if (active)
		    greenglow ();
		//if (gameOn)
		//z = 3;
	    }
	catch (Exception e)
	{
	    e.printStackTrace ();
	}
	if (x > 343 && y > 298 && x < 343 + 170 && y < 298 + 170)
	{
	    try
	    {
		if (active)
		    yellowglow ();
		//if (gameOn)
		//z = 4;
	    }
	    catch (Exception e)
	    {
		e.printStackTrace ();
	    }
	}
    }


    //***********************LOADING FREEPLAY MODE************************************
    public static void loadgame () throws Exception
    {
	BufferedImage game = loadImage ("game.png");
	graphics.drawImage (game, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
    }


    //************************RETURNING TO THE MENU************************************
    public static void goback () throws Exception
    {
	mouseOn = true;
	image = new BufferedImage (640, 480, BufferedImage.TYPE_INT_ARGB); //BufferedImage
	graphics = image.createGraphics (); //Graphic
	BufferedImage intro = loadImage ("intro.png");
	BufferedImage menu = loadImage ("menu.png");
	BufferedImage game = loadImage ("game.png");
	BufferedImage yellowglow = loadImage ("yellowglow.png");
	BufferedImage blueglow = loadImage ("blueglow.png");
	BufferedImage redglow = loadImage ("redglow.png");
	BufferedImage greenglow = loadImage ("greenglow.png");
	graphics.drawImage (menu, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
	//Menu Prompt
	int num = ICSSummative.getChar ();
	int newnum = num - '1' + 1;
	if (newnum == 1)
	    playGame ();
	active = true;
	if (newnum == 2)
	    playLightning ();
	if (newnum == 3)
	    active = true;
	loadgame ();
	if (newnum == 4)
	    rollcredits ();
	if (newnum == 5)
	    goback ();
    }


    //**********************CREDIT SCREEN**************************
    public static void rollcredits () throws Exception
    {
	BufferedImage credits = loadImage ("Credits.png");
	graphics.drawImage (credits, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
	int num = ICSSummative.getChar ();
	int newnum = num - '1' + 1;
	if (newnum == 5)
	    goback ();
    }


    //******************BLUE SQUARE LIGHT-UP + SFX************************
    public static void blueglow () throws Exception
    {
	playAudio ("highcnote.wav");
	BufferedImage blueglow = loadImage ("blueglow.png");
	BufferedImage original = loadImage ("game.png");
	graphics.drawImage (blueglow, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
	Thread.sleep (200);
	graphics.drawImage (original, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
    }


    //******************RED SQUARE LIGHT-UP + SFX************************
    public static void redglow () throws Exception
    {
	playAudio ("gnote.wav");
	BufferedImage redglow = loadImage ("redglow.png");
	BufferedImage original = loadImage ("game.png");
	graphics.drawImage (redglow, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
	Thread.sleep (200);
	graphics.drawImage (original, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
    }


    //******************GREEN SQUARE LIGHT-UP + SFX************************
    public static void greenglow () throws Exception
    {
	playAudio ("enote.wav");
	BufferedImage greenglow = loadImage ("greenglow.png");
	BufferedImage original = loadImage ("game.png");
	graphics.drawImage (greenglow, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
	Thread.sleep (200);
	graphics.drawImage (original, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
    }


    //**************YELLOW SQUARE LIGHT-UP + SFX************************
    public static void yellowglow () throws Exception
    {
	playAudio ("cnote.wav");
	BufferedImage yellowglow = loadImage ("yellowglow.png");
	BufferedImage original = loadImage ("game.png");
	graphics.drawImage (yellowglow, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
	Thread.sleep (200);
	graphics.drawImage (original, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
    }


    //*****************************MAIN METHOD***********************************
    public static void main (String. . .args) throws Exception
    {
	ICSSummative = new ICSSummative ();
	image = new BufferedImage (640, 480, BufferedImage.TYPE_INT_ARGB); //BufferedImage
	graphics = image.createGraphics (); //Graphic
	BufferedImage intro = loadImage ("intro.png");
	BufferedImage menu = loadImage ("menu.png");
	BufferedImage game = loadImage ("game.png");
	BufferedImage yellowglow = loadImage ("yellowglow.png");
	BufferedImage blueglow = loadImage ("blueglow.png");
	BufferedImage redglow = loadImage ("redglow.png");
	BufferedImage greenglow = loadImage ("greenglow.png");
	graphics.drawImage (intro, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
	ICSSummative.getChar ();
	graphics.drawImage (menu, 0, 0, null);
	ICSSummative.drawImage (image, 0, 0, null);
	//Menu Prompt
	int num = ICSSummative.getChar ();
	int newnum = num - '1' + 1;
	if (newnum == 1)
	    mouseOn = false;
	gameOn = true;
	playGame ();
	if (newnum == 2)
	    playLightning ();
	if (newnum == 3)
	    active = true;
	loadgame ();
	if (newnum == 4)
	    rollcredits ();
	if (newnum == 5)
	    goback ();
    }


    //****************************MOUSE LISTENER CLASS*************************************
    class TestMouseListener implements MouseListener
    {

	@ Override
	    public void mouseClicked (MouseEvent e)
	{
	    //e is your "mouse event": what happens when this event is invoked (in this case, a click)
	    try
	    {
		mouseListening (e.getX (), e.getY ()); //e.getX() returns the x coordinate of the click, e.getY() returns the y coordinate of the click
	    }
	    catch (Exception e1)
	    {
		e1.printStackTrace ();
	    }
	    //System.out.println(e.getX()+ " " +e.getY()); //for debugging lol
	}

	@ Override
	    public void mousePressed (MouseEvent e)
	{

	}

	@ Override
	    public void mouseReleased (MouseEvent e)
	{

	}

	@ Override
	    public void mouseEntered (MouseEvent e)
	{

	}

	@ Override
	    public void mouseExited (MouseEvent e)
	{

	}
    }
}
