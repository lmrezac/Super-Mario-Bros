// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import supermario.TextureMap;
import supermario.Utilities;

public class Textures
{
    public boolean validTextures;
    public TextureMap mariomap;

    public TextureMap enemies;
    public ImageIcon title;
    public ImageIcon title2;
    public ImageIcon selector;
    public ImageIcon controller;
    public ImageIcon bubby;
    public ImageIcon fakePrincess;
    public ImageIcon marioStand;
    public ImageIcon marioJump;
    public ImageIcon marioCrouch;
    public ImageIcon marioWalk1;
    public ImageIcon marioWalk2;
    public ImageIcon marioWalk3;
    public ImageIcon marioSkid;
    public ImageIcon marioSmStand;
    public ImageIcon marioSmJump;
    public ImageIcon marioSmWalk1;
    public ImageIcon marioSmWalk2;
    public ImageIcon marioSmWalk3;
    public ImageIcon marioSmSkid;
    public ImageIcon marioGrow;
    public ImageIcon marioSwim1;
    public ImageIcon marioSwim2;
    public ImageIcon marioSwim3;
    public ImageIcon marioSwim4;
    public ImageIcon marioSwim5;
    public ImageIcon marioSwim6;
    public ImageIcon marioSmSwim1;
    public ImageIcon marioSmSwim2;
    public ImageIcon marioSmSwim3;
    public ImageIcon marioSmSwim4;
    public ImageIcon marioSmSwim5;
    public ImageIcon marioSmSwim6;
    public ImageIcon marioFlowerSwim1;
    public ImageIcon marioFlowerSwim2;
    public ImageIcon marioFlowerSwim3;
    public ImageIcon marioFlowerSwim4;
    public ImageIcon marioFlowerSwim5;
    public ImageIcon marioFlowerSwim6;
    public ImageIcon marioDead;
    public ImageIcon luigiStand;
    public ImageIcon luigiJump;
    public ImageIcon luigiCrouch;
    public ImageIcon luigiWalk1;
    public ImageIcon luigiWalk2;
    public ImageIcon luigiWalk3;
    public ImageIcon luigiSkid;
    public ImageIcon luigiSmStand;
    public ImageIcon luigiSmJump;
    public ImageIcon luigiSmWalk1;
    public ImageIcon luigiSmWalk2;
    public ImageIcon luigiSmWalk3;
    public ImageIcon luigiSmSkid;
    public ImageIcon luigiGrow;
    public ImageIcon luigiSwim1;
    public ImageIcon luigiSwim2;
    public ImageIcon luigiSwim3;
    public ImageIcon luigiSwim4;
    public ImageIcon luigiSwim5;
    public ImageIcon luigiSwim6;
    public ImageIcon luigiSmSwim1;
    public ImageIcon luigiSmSwim2;
    public ImageIcon luigiSmSwim3;
    public ImageIcon luigiSmSwim4;
    public ImageIcon luigiSmSwim5;
    public ImageIcon luigiSmSwim6;
    public ImageIcon luigiFlowerSwim1;
    public ImageIcon luigiFlowerSwim2;
    public ImageIcon luigiFlowerSwim3;
    public ImageIcon luigiFlowerSwim4;
    public ImageIcon luigiFlowerSwim5;
    public ImageIcon luigiFlowerSwim6;
    public ImageIcon luigiClimb1;
    public ImageIcon luigiClimb2;
    public ImageIcon luigiSmClimb1;
    public ImageIcon luigiSmClimb2;
    public ImageIcon luigiDead;
    public ImageIcon marioDeadButton;
    public ImageIcon marioShootStandJumpWalk1;
    public ImageIcon marioShootWalk2;
    public ImageIcon marioShootWalk3;
    public ImageIcon marioShootSkid;
    public ImageIcon marioStarBlackCrouch;
    public ImageIcon marioStarBlackGrow;
    public ImageIcon marioStarBlackJump;
    public ImageIcon marioStarBlackShootSkid;
    public ImageIcon marioStarBlackShootStandJumpWalk1;
    public ImageIcon marioStarBlackShootWalk2;
    public ImageIcon marioStarBlackShootWalk3;
    public ImageIcon marioStarBlackSkid;
    public ImageIcon marioStarBlackSmJump;
    public ImageIcon marioStarBlackSmSkid;
    public ImageIcon marioStarBlackSmStand;
    public ImageIcon marioStarBlackSmWalk1;
    public ImageIcon marioStarBlackSmWalk2;
    public ImageIcon marioStarBlackSmWalk3;
    public ImageIcon marioStarBlackStand;
    public ImageIcon marioStarBlackWalk1;
    public ImageIcon marioStarBlackWalk2;
    public ImageIcon marioStarBlackWalk3;
    public ImageIcon marioStarBlackSwim1;
    public ImageIcon marioStarBlackSwim2;
    public ImageIcon marioStarBlackSwim3;
    public ImageIcon marioStarBlackSwim4;
    public ImageIcon marioStarBlackSwim5;
    public ImageIcon marioStarBlackSwim6;
    public ImageIcon marioStarBlackSmSwim1;
    public ImageIcon marioStarBlackSmSwim2;
    public ImageIcon marioStarBlackSmSwim3;
    public ImageIcon marioStarBlackSmSwim4;
    public ImageIcon marioStarBlackSmSwim5;
    public ImageIcon marioStarBlackSmSwim6;
    public ImageIcon marioStarBlackClimb1;
    public ImageIcon marioStarBlackClimb2;
    public ImageIcon marioStarBlackSmClimb1;
    public ImageIcon marioStarBlackSmClimb2;
    public ImageIcon marioStarGreenCrouch;
    public ImageIcon marioStarGreenGrow;
    public ImageIcon marioStarGreenJump;
    public ImageIcon marioStarGreenShootSkid;
    public ImageIcon marioStarGreenShootStandJumpWalk1;
    public ImageIcon marioStarGreenShootWalk2;
    public ImageIcon marioStarGreenShootWalk3;
    public ImageIcon marioStarGreenSkid;
    public ImageIcon marioStarGreenSmJump;
    public ImageIcon marioStarGreenSmSkid;
    public ImageIcon marioStarGreenSmStand;
    public ImageIcon marioStarGreenSmWalk1;
    public ImageIcon marioStarGreenSmWalk2;
    public ImageIcon marioStarGreenSmWalk3;
    public ImageIcon marioStarGreenStand;
    public ImageIcon marioStarGreenWalk1;
    public ImageIcon marioStarGreenWalk2;
    public ImageIcon marioStarGreenWalk3;
    public ImageIcon marioStarGreenSwim1;
    public ImageIcon marioStarGreenSwim2;
    public ImageIcon marioStarGreenSwim3;
    public ImageIcon marioStarGreenSwim4;
    public ImageIcon marioStarGreenSwim5;
    public ImageIcon marioStarGreenSwim6;
    public ImageIcon marioStarGreenSmSwim1;
    public ImageIcon marioStarGreenSmSwim2;
    public ImageIcon marioStarGreenSmSwim3;
    public ImageIcon marioStarGreenSmSwim4;
    public ImageIcon marioStarGreenSmSwim5;
    public ImageIcon marioStarGreenSmSwim6;
    public ImageIcon marioStarGreenClimb1;
    public ImageIcon marioStarGreenClimb2;
    public ImageIcon marioStarGreenSmClimb1;
    public ImageIcon marioStarGreenSmClimb2;
    public ImageIcon marioStarRedCrouch;
    public ImageIcon marioStarRedGrow;
    public ImageIcon marioStarRedJump;
    public ImageIcon marioStarRedShootSkid;
    public ImageIcon marioStarRedShootStandJumpWalk1;
    public ImageIcon marioStarRedShootWalk2;
    public ImageIcon marioStarRedShootWalk3;
    public ImageIcon marioStarRedSkid;
    public ImageIcon marioStarRedSmJump;
    public ImageIcon marioStarRedSmSkid;
    public ImageIcon marioStarRedSmStand;
    public ImageIcon marioStarRedSmWalk1;
    public ImageIcon marioStarRedSmWalk2;
    public ImageIcon marioStarRedSmWalk3;
    public ImageIcon marioStarRedStand;
    public ImageIcon marioStarRedWalk1;
    public ImageIcon marioStarRedWalk2;
    public ImageIcon marioStarRedWalk3;
    public ImageIcon marioStarRedSwim1;
    public ImageIcon marioStarRedSwim2;
    public ImageIcon marioStarRedSwim3;
    public ImageIcon marioStarRedSwim4;
    public ImageIcon marioStarRedSwim5;
    public ImageIcon marioStarRedSwim6;
    public ImageIcon marioStarRedSmSwim1;
    public ImageIcon marioStarRedSmSwim2;
    public ImageIcon marioStarRedSmSwim3;
    public ImageIcon marioStarRedSmSwim4;
    public ImageIcon marioStarRedSmSwim5;
    public ImageIcon marioStarRedSmSwim6;
    public ImageIcon marioStarRedClimb1;
    public ImageIcon marioStarRedClimb2;
    public ImageIcon marioStarRedSmClimb1;
    public ImageIcon marioStarRedSmClimb2;
    public ImageIcon marioFlowerStand;
    public ImageIcon marioFlowerJump;
    public ImageIcon marioFlowerCrouch;
    public ImageIcon marioFlowerWalk1;
    public ImageIcon marioFlowerWalk2;
    public ImageIcon marioFlowerWalk3;
    public ImageIcon marioFlowerSkid;
    public ImageIcon marioClimb1;
    public ImageIcon marioClimb2;
    public ImageIcon marioSmClimb1;
    public ImageIcon marioSmClimb2;
    public ImageIcon marioFlowerClimb1;
    public ImageIcon marioFlowerClimb2;
    public ImageIcon lightBrick;
    public ImageIcon darkBrick;
    public ImageIcon stoneBrick;
    //MARK ground here
    public ImageIcon lightGround;
    public ImageIcon lightGroundTop, lightGroundTopLeft, lightGroundTopRight,
    				lightGroundLeft,lightGroundRight, lightGroundBottomLeft,
    				lightGroundBottom, lightGroundBottomRight;
    public ImageIcon darkGround;
    public ImageIcon darkGroundTop, darkGroundTopLeft, darkGroundTopRight,
					darkGroundLeft,darkGroundRight, darkGroundBottomLeft,
    				darkGroundBottom, darkGroundBottomRight;
    public ImageIcon lightBlock;
    public ImageIcon darkBlock;
    public ImageIcon stoneBlock;
    public ImageIcon cloudGround;
    public ImageIcon cloudGroundTop, cloudGroundTopLeft, cloudGroundTopRight,
    				cloudGroundLeft,cloudGroundRight, cloudGroundBottomLeft,
    				cloudGroundBottom, cloudGroundBottomRight;
    public ImageIcon singleCloud;
    public ImageIcon doubleCloud;
    public ImageIcon tripleCloud;
    public ImageIcon singleBush;
    public ImageIcon doubleBush;
    public ImageIcon tripleBush;
    public HashMap<Character, ImageIcon> symbols;
    public static final char LEFT_ARROW = '\uffff';
    public static final char RIGHT_ARROW = '\ufffe';
    public static final char TIMES = '\ufffd';
    public static final char FAT_EXCLAM = '\ufffc';
    public static final char UNKNOWN = '\ufffb';
    public HashMap<Character, Character> customTextChars;
    public HashMap<Character, Character> customTextCharsInverted;
    public ImageIcon[] greenPipes;
    public ImageIcon[] bluePipes;
    public ImageIcon[] whitePipes;
    public ImageIcon[] orangePipes;
    public ImageIcon lightOverlayCoin1;
    public ImageIcon lightOverlayCoin2;
    public ImageIcon lightOverlayCoin3;
    public ImageIcon darkOverlayCoin1;
    public ImageIcon darkOverlayCoin2;
    public ImageIcon darkOverlayCoin3;
    public ImageIcon stoneOverlayCoin1;
    public ImageIcon stoneOverlayCoin2;
    public ImageIcon stoneOverlayCoin3;
    public ImageIcon darkCoin;
    public ImageIcon lightCoin;
    public ImageIcon stoneCoin;
    public ImageIcon seaCoin;
    public ImageIcon warpZoneMessage;
    public ImageIcon warpZonePipeGreen;
    public ImageIcon warpZonePipeWhite;
    public ImageIcon warpZonePipeBlue;
    public ImageIcon warpZonePipeOrange;
    public ImageIcon smallCastle;
    public ImageIcon largeCastle;
    public ImageIcon bigHill, bigHillDark, bigHillCastle, bigHillSea;
    public ImageIcon smallHill, smallHillDark, smallHillCastle, smallHillSea;
    public ImageIcon flag;
    public ImageIcon castleFlag;
    public ImageIcon flagpoleGreen;
    public ImageIcon flagpoleWhite;
    public ImageIcon castleWall;
    public ImageIcon lightQuestionBox;
    public ImageIcon darkQuestionBox;
    public ImageIcon stoneQuestionBox;
    public ImageIcon questionBoxHidden;
    public ImageIcon darkGoomba1;
    public ImageIcon darkGoomba2;
    public ImageIcon darkGoomba3;
    public ImageIcon darkGoomba4;
    public ImageIcon lightGoomba1;
    public ImageIcon lightGoomba2;
    public ImageIcon lightGoomba3;
    public ImageIcon lightGoomba4;
    public ImageIcon grayGoomba1;
    public ImageIcon grayGoomba2;
    public ImageIcon grayGoomba3;
    public ImageIcon grayGoomba4;
    public ImageIcon darkKoopa1;
    public ImageIcon darkKoopa2;
    public ImageIcon darkKoopa3;
    public ImageIcon darkKoopa4;
    public ImageIcon darkKoopa5;
    public ImageIcon darkKoopa6;
    public ImageIcon darkKoopa7;
    public ImageIcon lightKoopa1;
    public ImageIcon lightKoopa2;
    public ImageIcon lightKoopa3;
    public ImageIcon lightKoopa4;
    public ImageIcon lightKoopa5;
    public ImageIcon lightKoopa6;
    public ImageIcon lightKoopa7;
    public ImageIcon redKoopa1;
    public ImageIcon redKoopa2;
    public ImageIcon redKoopa3;
    public ImageIcon redKoopa4;
    public ImageIcon redKoopa5;
    public ImageIcon redKoopa6;
    public ImageIcon redKoopa7;
    public ImageIcon lightBrickPiece1;
    public ImageIcon lightBrickPiece2;
    public ImageIcon lightBrickPiece3;
    public ImageIcon lightBrickPiece4;
    public ImageIcon darkBrickPiece1;
    public ImageIcon darkBrickPiece2;
    public ImageIcon darkBrickPiece3;
    public ImageIcon darkBrickPiece4;
    public ImageIcon stoneBrickPiece1;
    public ImageIcon stoneBrickPiece2;
    public ImageIcon stoneBrickPiece3;
    public ImageIcon stoneBrickPiece4;
    public ImageIcon coinCollected1;
    public ImageIcon coinCollected2;
    public ImageIcon coinCollected3;
    public ImageIcon coinCollected4;
    public ImageIcon checkPtFlag;
    public ImageIcon checkpoint;
    public ImageIcon oneUp;
    public ImageIcon points100;
    public ImageIcon points200;
    public ImageIcon points400;
    public ImageIcon points500;
    public ImageIcon points800;
    public ImageIcon points1000;
    public ImageIcon points2000;
    public ImageIcon points5000;
    public ImageIcon points8000;
    public ImageIcon growMushroom;
    public ImageIcon poisonMushroom;
    public ImageIcon lightExtraLife;
    public ImageIcon darkExtraLife;
    public ImageIcon beanstalkTopLight;
    public ImageIcon beanstalkSectionLight;
    public ImageIcon entryVineLight;
    public ImageIcon beanstalkTopDark;
    public ImageIcon beanstalkSectionDark;
    public ImageIcon entryVineDark;
    public ImageIcon star1;
    public ImageIcon star2;
    public ImageIcon star3;
    public ImageIcon star4;
    public ImageIcon star5;
    public ImageIcon starCancel;
    public ImageIcon lightFlower1;
    public ImageIcon lightFlower2;
    public ImageIcon lightFlower3;
    public ImageIcon lightFlower4;
    public ImageIcon darkFlower1;
    public ImageIcon darkFlower2;
    public ImageIcon darkFlower3;
    public ImageIcon darkFlower4;
    public ImageIcon treeTopLeftEnd;
    public ImageIcon treeTopMiddle;
    public ImageIcon treeTopRightEnd;
    public ImageIcon treeBark, treeBarkLeft, treeBarkRight;
    public ImageIcon mushroomTreeLeftEnd;
    public ImageIcon mushroomTreeMiddleSection;
    public ImageIcon mushroomTreeRightEnd;
    public ImageIcon mushroomTreeBarkTop;
    public ImageIcon mushroomTreeBark;
    public ImageIcon mushroomTreeBarkBottom;
    public ImageIcon lavaClassic;
    public ImageIcon lavaTop;
    public ImageIcon lavaBottom;
    public ImageIcon waterTop;
    public ImageIcon waterBottom;
    public ImageIcon waves;
    //MARK ground here
    public ImageIcon stoneGround;
    public ImageIcon stoneGroundTop, stoneGroundTopLeft, stoneGroundTopRight,
    				stoneGroundLeft,stoneGroundRight, stoneGroundBottomLeft,
    				stoneGroundBottom, stoneGroundBottomRight;
    public ImageIcon coral;
    public ImageIcon seaBrick;
    public ImageIcon seaGround;
    public ImageIcon seaGroundTop, seaGroundTopLeft, seaGroundTopRight,
    				seaGroundLeft,seaGroundRight, seaGroundBottomLeft,
    				seaGroundBottom, seaGroundBottomRight;
    public ImageIcon seaBlock;
    public ImageIcon stoneMetal;
    public ImageIcon darkMetal;
    public ImageIcon lightMetal;
    public ImageIcon tallTrimmedBush;
    public ImageIcon shortTrimmedBush;
    public ImageIcon picketFence, picketFenceLeft, picketFenceRight;
    public ImageIcon bridge;
    public ImageIcon shortSnowyBush;
    public ImageIcon tallSnowyBush;
    public ImageIcon fireball1;
    public ImageIcon fireball2;
    public ImageIcon fireball3;
    public ImageIcon fireball4;
    public ImageIcon lavaball1;
    public ImageIcon lavaball2;
    public ImageIcon springLight;
    public ImageIcon springDark;
    public ImageIcon springGray;
    public ImageIcon springBaseLight;
    public ImageIcon springBaseDark;
    public ImageIcon springBaseGray;
    public ImageIcon springGreenLight;
    public ImageIcon springGreenDark;
    public ImageIcon springGreenGray;
    public ImageIcon springBaseGreenLight;
    public ImageIcon springBaseGreenDark;
    public ImageIcon springBaseGreenGray;
    public ImageIcon spiny1;
    public ImageIcon spiny2;
    public ImageIcon spiny3;
    public ImageIcon spiny4;
    public ImageIcon grayBeetle1;
    public ImageIcon grayBeetle2;
    public ImageIcon grayBeetle3;
    public ImageIcon grayBeetle4;
    public ImageIcon lightBeetle1;
    public ImageIcon lightBeetle2;
    public ImageIcon lightBeetle3;
    public ImageIcon lightBeetle4;
    public ImageIcon darkBeetle1;
    public ImageIcon darkBeetle2;
    public ImageIcon darkBeetle3;
    public ImageIcon darkBeetle4;
    public ImageIcon spinyThrower1;
    public ImageIcon spinyThrower2;
    public ImageIcon spinyThrower3;
    public ImageIcon squid1;
    public ImageIcon squid2;
    public ImageIcon squid3;
    public ImageIcon redFish1;
    public ImageIcon redFish2;
    public ImageIcon redFish3;
    public ImageIcon grayFish1;
    public ImageIcon grayFish2;
    public ImageIcon grayFish3;
    public ImageIcon lightChomper1;
    public ImageIcon lightChomper2;
    public ImageIcon lightChomper3;
    public ImageIcon lightChomper4;
    public ImageIcon darkChomper1;
    public ImageIcon darkChomper2;
    public ImageIcon darkChomper3;
    public ImageIcon darkChomper4;
    public ImageIcon redChomper1;
    public ImageIcon redChomper2;
    public ImageIcon redChomper3;
    public ImageIcon redChomper4;
    public ImageIcon lightHammerBro1;
    public ImageIcon lightHammerBro2;
    public ImageIcon lightHammerBro3;
    public ImageIcon lightHammerBro4;
    public ImageIcon lightHammerBro5;
    public ImageIcon darkHammerBro1;
    public ImageIcon darkHammerBro2;
    public ImageIcon darkHammerBro3;
    public ImageIcon darkHammerBro4;
    public ImageIcon darkHammerBro5;
    public ImageIcon lightTallCannon;
    public ImageIcon lightShortCannon;
    public ImageIcon lightCannonBase;
    public ImageIcon lightBullet;
    public ImageIcon darkTallCannon;
    public ImageIcon darkShortCannon;
    public ImageIcon darkCannonBase;
    public ImageIcon darkBullet;
    public ImageIcon stoneTallCannon;
    public ImageIcon stoneShortCannon;
    public ImageIcon stoneCannonBase;
    public ImageIcon stoneBullet;
    public ImageIcon hammerBlack;
    public ImageIcon hammerGray;
    public ImageIcon pulley;
    public ImageIcon platformLong;
    public ImageIcon platformShort;
    public ImageIcon platformExtraShort;
    public ImageIcon cloudCarrierLong;
    public ImageIcon cloudCarrierShort;
    public ImageIcon firework1;
    public ImageIcon firework2;
    public ImageIcon firework3;
    public ImageIcon flame1;
    public ImageIcon flame2;
    public ImageIcon airBubble;
    public ImageIcon bowserBridge;
    public ImageIcon bowserBridgeSection;
    public ImageIcon bowserAxe;
    public ImageIcon bowserChain;
    public ImageIcon bowser1;
    public ImageIcon bowser2;
    public ImageIcon bowser3;
    public ImageIcon bowser4;
    public ImageIcon bowser5;
    public ImageIcon infiniteCorridorSizer;
    public ImageIcon solidTestTile;
    public ImageIcon waterBackground = null;
    public Color skyBlue;
    public Color black;
    public Color waterBlue;
    public Color pink;
    public Color brickRed;
    public Color brickBlue;
    public Color brickGray;
    public Color offBlack;
    public Color lavaColor;
    public Color waterColor;
    public Color superSpringGreen;
    
    public Textures() {
        this.symbols = new HashMap<Character, ImageIcon>();
        this.customTextChars = new HashMap<Character, Character>();
        this.customTextCharsInverted = new HashMap<Character, Character>();
        this.validTextures = true;
        try {
            this.init();
        }
        catch (Exception e) {
            this.validTextures = false;
            e.printStackTrace();
        }
    }
    
    public ImageIcon[] getOverlayCoinTextures() {
        final ImageIcon[] overlayCoinImages = { this.lightOverlayCoin1, this.lightOverlayCoin2, this.lightOverlayCoin3, this.darkOverlayCoin1, this.darkOverlayCoin2, this.darkOverlayCoin3, this.stoneOverlayCoin1, this.stoneOverlayCoin2, this.stoneOverlayCoin3 };
        return overlayCoinImages;
    }
    
    public ImageIcon[] getMarioTextures() {
        final ImageIcon[] marioImages = { this.marioStand, this.marioJump, this.marioWalk1, this.marioWalk2, this.marioWalk3, this.marioSwim1, this.marioSwim2, this.marioSwim3, this.marioSwim4, this.marioSwim5, this.marioSwim6, this.marioSkid, this.marioClimb1, this.marioClimb2, this.marioCrouch, this.marioSmStand, this.marioSmJump, this.marioSmWalk1, this.marioSmWalk2, this.marioSmWalk3, this.marioSmSwim1, this.marioSmSwim2, this.marioSmSwim3, this.marioSmSwim4, this.marioSmSwim5, this.marioSmSwim6, this.marioSmSkid, this.marioSmClimb1, this.marioSmClimb2, this.marioGrow, this.marioDead, this.marioFlowerStand, this.marioFlowerJump, this.marioFlowerWalk1, this.marioFlowerWalk2, this.marioFlowerWalk3, this.marioFlowerSwim1, this.marioFlowerSwim2, this.marioFlowerSwim3, this.marioFlowerSwim4, this.marioFlowerSwim5, this.marioFlowerSwim6, this.marioFlowerSkid, this.marioFlowerClimb1, this.marioFlowerClimb2, this.marioFlowerCrouch, this.marioShootStandJumpWalk1, this.marioShootWalk2, this.marioShootWalk3, this.marioShootSkid, this.marioStarBlackStand, this.marioStarBlackJump, this.marioStarBlackWalk1, this.marioStarBlackWalk2, this.marioStarBlackWalk3, this.marioStarBlackSwim1, this.marioStarBlackSwim2, this.marioStarBlackSwim3, this.marioStarBlackSwim4, this.marioStarBlackSwim5, this.marioStarBlackSwim6, this.marioStarBlackSkid, this.marioStarBlackClimb1, this.marioStarBlackClimb2, this.marioStarBlackCrouch, this.marioStarBlackShootStandJumpWalk1, this.marioStarBlackShootWalk2, this.marioStarBlackShootWalk3, this.marioStarBlackShootSkid, this.marioStarBlackSmStand, this.marioStarBlackSmJump, this.marioStarBlackSmWalk1, this.marioStarBlackSmWalk2, this.marioStarBlackSmWalk3, this.marioStarBlackSmSwim1, this.marioStarBlackSmSwim2, this.marioStarBlackSmSwim3, this.marioStarBlackSmSwim4, this.marioStarBlackSmSwim5, this.marioStarBlackSmSwim6, this.marioStarBlackSmSkid, this.marioStarBlackSmClimb1, this.marioStarBlackSmClimb2, this.marioStarBlackGrow, this.marioStarGreenStand, this.marioStarGreenJump, this.marioStarGreenWalk1, this.marioStarGreenWalk2, this.marioStarGreenWalk3, this.marioStarGreenSwim1, this.marioStarGreenSwim2, this.marioStarGreenSwim3, this.marioStarGreenSwim4, this.marioStarGreenSwim5, this.marioStarGreenSwim6, this.marioStarGreenSkid, this.marioStarGreenClimb1, this.marioStarGreenClimb2, this.marioStarGreenCrouch, this.marioStarGreenShootStandJumpWalk1, this.marioStarGreenShootWalk2, this.marioStarGreenShootWalk3, this.marioStarGreenShootSkid, this.marioStarGreenSmStand, this.marioStarGreenSmJump, this.marioStarGreenSmWalk1, this.marioStarGreenSmWalk2, this.marioStarGreenSmWalk3, this.marioStarGreenSmSwim1, this.marioStarGreenSmSwim2, this.marioStarGreenSmSwim3, this.marioStarGreenSmSwim4, this.marioStarGreenSmSwim5, this.marioStarGreenSmSwim6, this.marioStarGreenSmSkid, this.marioStarGreenSmClimb1, this.marioStarGreenSmClimb2, this.marioStarGreenGrow, this.marioStarRedStand, this.marioStarRedJump, this.marioStarRedWalk1, this.marioStarRedWalk2, this.marioStarRedWalk3, this.marioStarRedSwim1, this.marioStarRedSwim2, this.marioStarRedSwim3, this.marioStarRedSwim4, this.marioStarRedSwim5, this.marioStarRedSwim6, this.marioStarRedSkid, this.marioStarRedClimb1, this.marioStarRedClimb2, this.marioStarRedCrouch, this.marioStarRedShootStandJumpWalk1, this.marioStarRedShootWalk2, this.marioStarRedShootWalk3, this.marioStarRedShootSkid, this.marioStarRedSmStand, this.marioStarRedSmJump, this.marioStarRedSmWalk1, this.marioStarRedSmWalk2, this.marioStarRedSmWalk3, this.marioStarRedSmSwim1, this.marioStarRedSmSwim2, this.marioStarRedSmSwim3, this.marioStarRedSmSwim4, this.marioStarRedSmSwim5, this.marioStarRedSmSwim6, this.marioStarRedSmSkid, this.marioStarRedSmClimb1, this.marioStarRedSmClimb2, this.marioStarRedGrow };
        return marioImages;
    }
    
    public ImageIcon[] getLuigiTextures() {
        final ImageIcon[] luigiImages = { this.luigiStand, this.luigiJump, this.luigiWalk1, this.luigiWalk2, this.luigiWalk3, this.luigiSwim1, this.luigiSwim2, this.luigiSwim3, this.luigiSwim4, this.luigiSwim5, this.luigiSwim6, this.luigiSkid, this.luigiClimb1, this.luigiClimb2, this.luigiCrouch, this.luigiSmStand, this.luigiSmJump, this.luigiSmWalk1, this.luigiSmWalk2, this.luigiSmWalk3, this.luigiSmSwim1, this.luigiSmSwim2, this.luigiSmSwim3, this.luigiSmSwim4, this.luigiSmSwim5, this.luigiSmSwim6, this.luigiSmSkid, this.luigiSmClimb1, this.luigiSmClimb2, this.luigiGrow, this.luigiDead };
        return luigiImages;
    }
    
    public ImageIcon[] getKoopaTextures() {
        final ImageIcon[] koopaImages = { this.lightKoopa1, this.lightKoopa2, this.lightKoopa3, this.lightKoopa4, this.lightKoopa5, this.lightKoopa6, this.lightKoopa7, this.darkKoopa1, this.darkKoopa2, this.darkKoopa3, this.darkKoopa4, this.darkKoopa5, this.darkKoopa6, this.darkKoopa7, this.redKoopa1, this.redKoopa2, this.redKoopa3, this.redKoopa4, this.redKoopa5, this.redKoopa6, this.redKoopa7 };
        return koopaImages;
    }
    
    public ImageIcon[] getQuestionBoxTextures() {
        final ImageIcon[] questionBoxImages = { this.lightQuestionBox, this.darkQuestionBox, this.stoneQuestionBox, this.lightMetal, this.darkMetal, this.stoneMetal, this.questionBoxHidden };
        return questionBoxImages;
    }
    
    public ImageIcon[] getFlowerTextures() {
        final ImageIcon[] flowerImages = { this.lightFlower1, this.lightFlower2, this.lightFlower3, this.lightFlower4, this.darkFlower1, this.darkFlower2, this.darkFlower3, this.darkFlower4 };
        return flowerImages;
    }
    
    public ImageIcon[] getLavaballTextures() {
        return new ImageIcon[] { this.lavaball1, this.lavaball2 };
    }
    
    public ImageIcon[] getFireballTextures() {
        return new ImageIcon[] { this.fireball1, this.fireball2, this.fireball3, this.fireball4 };
    }
    
    public ImageIcon[] getFireworkTextures() {
        return new ImageIcon[] { this.firework1, this.firework2, this.firework3 };
    }
    
    public ImageIcon[] getShortCannonTextures() {
        return new ImageIcon[] { this.lightShortCannon, this.darkShortCannon, this.stoneShortCannon };
    }
    
    public ImageIcon[] getTallCannonTextures() {
        return new ImageIcon[] { this.lightTallCannon, this.darkTallCannon, this.stoneTallCannon };
    }
    
    public ImageIcon[] getBulletTextures() {
        return new ImageIcon[] { this.lightBullet, this.darkBullet, this.stoneBullet };
    }
    
    public ImageIcon[] getChomperTextures(final boolean vertical) {
        if (vertical) {
            return new ImageIcon[] { this.lightChomper1, this.lightChomper2, this.darkChomper1, this.darkChomper2, this.redChomper1, this.redChomper2 };
        }
        return new ImageIcon[] { this.lightChomper3, this.lightChomper4, this.darkChomper3, this.darkChomper4, this.redChomper3, this.redChomper4 };
    }
    
    public ImageIcon[] getBowserTextures() {
        return new ImageIcon[] { this.bowser1, this.bowser2, this.bowser3, this.bowser4, this.bowser5 };
    }
    
    public ImageIcon[] getBrickTextures() {
        return new ImageIcon[] { this.lightBrick, this.darkBrick, this.stoneBrick, this.lightMetal, this.darkMetal, this.stoneMetal };
    }
    
    public ImageIcon[] getGoombaTextures() {
        return new ImageIcon[] { this.lightGoomba1, this.lightGoomba2, this.lightGoomba3, this.lightGoomba4, this.darkGoomba1, this.darkGoomba2, this.darkGoomba3, this.darkGoomba4, this.grayGoomba1, this.grayGoomba2, this.grayGoomba3, this.grayGoomba4 };
    }
    
    public ImageIcon[] getCoinTextures() {
        return new ImageIcon[] { this.darkCoin, this.lightCoin, this.stoneCoin };
    }
    
    public ImageIcon[] getBeetleTextures() {
        return new ImageIcon[] { this.lightBeetle1, this.lightBeetle2, this.lightBeetle3, this.lightBeetle4, this.darkBeetle1, this.darkBeetle2, this.darkBeetle3, this.darkBeetle4, this.grayBeetle1, this.grayBeetle2, this.grayBeetle3, this.grayBeetle4 };
    }
    
    public ImageIcon[] getStarTextures() {
        return new ImageIcon[] { this.star1, this.star2, this.star3, this.star4, this.star5 };
    }
    
    public ImageIcon[] getHammerBroTextures() {
        return new ImageIcon[] { this.lightHammerBro1, this.lightHammerBro2, this.lightHammerBro3, this.lightHammerBro4, this.lightHammerBro5, this.darkHammerBro1, this.darkHammerBro2, this.darkHammerBro3, this.darkHammerBro4, this.darkHammerBro5 };
    }
    
    public ImageIcon[] getSpringTextures() {
        return new ImageIcon[] { this.springBaseLight, this.springBaseDark, this.springBaseGray, this.springLight, this.springDark, this.springGray };
    }
    
    public ImageIcon[] getGreenSpringTextures() {
        return new ImageIcon[] { this.springBaseGreenLight, this.springBaseGreenDark, this.springBaseGreenGray, this.springGreenLight, this.springGreenDark, this.springGreenGray };
    }
    
    public ImageIcon[] getSpinyTextures() {
        return new ImageIcon[] { this.spiny1, this.spiny2, this.spiny3, this.spiny4 };
    }
    
    public ImageIcon[] getSpinyThrowerTextures() {
        return new ImageIcon[] { this.spinyThrower1, this.spinyThrower2, this.spinyThrower3 };
    }
    
    public ImageIcon[] getSquidTextures() {
        return new ImageIcon[] { this.squid1, this.squid2, this.squid3 };
    }
    
    public ImageIcon[] getGrayFishTextures() {
        return new ImageIcon[] { this.grayFish1, this.grayFish2, this.grayFish3 };
    }
    
    public ImageIcon[] getRedFishTextures() {
        return new ImageIcon[] { this.redFish1, this.redFish2, this.redFish3 };
    }
    
    public ImageIcon[] getBeanstalkTextures() {
        return new ImageIcon[] { this.beanstalkTopLight, this.beanstalkSectionLight, this.beanstalkTopDark, this.beanstalkSectionDark };
    }
    public void init() {  
    	this.customTextCharsInit();
    	    	 
    	init("images/");
    }
    private String darkpalette, graypalette;
    public void init(String prefix) {
    	File f = new File(prefix+"tiles/waterBackgroundOverride.png");
    	System.out.println(f.getAbsolutePath());
    	if(f.exists()){
    		try{
    		waterBackground = image(prefix,"tiles/waterBackgroundOverride.png");
    		}catch(MessageException e){
        		e.printStackTrace();
    			System.out.println(e.getMessage());
    			System.exit(0);
        	}
    		System.out.println("waterBackground exists!");
    	}else{
    		waterBackground = null;
    	}
    	darkpalette = prefix+"sprites/darkpalette.png";
    	graypalette = prefix+"sprites/graypalette.png";
    	mariomap = new TextureMap(prefix+"sprites/mario/mario.png");
    	//tiles = new TextureMap(prefix+"tiles/tileset.png");
        this.symbolsInit(prefix);
        this.overlayInit(prefix);
        this.tilesInit(prefix);
        this.colorsInit(prefix);
        this.pipesInit(prefix);
        this.spritesInit(prefix);
        this.effectsInit(prefix);
        this.marioInit(prefix);
        this.luigiInit(prefix);
    }
    private Color getColorFromImage(String prefix,String name){
    	try{
    		BufferedImage img = ImageIO.read(new File(prefix+"tiles/"+name+".png"));
    		return new Color(img.getRGB(0, 0));
    	}catch(IOException ex){
    		if(prefix.equals("images/")){
    			ex.printStackTrace();
    			System.exit(0);
    			return null;
    		}else{
    			try{
    				BufferedImage img = ImageIO.read(new File("images/tiles/"+name+".png"));
    				return new Color(img.getRGB(0, 0));
    			}catch(IOException f){
    				f.printStackTrace();
    				System.exit(0);
    				return null;
    			}
    		}
    	}
    }
    private Color getColorFromImage(ImageIcon icon){
    	BufferedImage img = (BufferedImage)icon.getImage();
    	return new Color(img.getRGB(0,0));
    }
    private void colorsInit(String prefix) {
        this.skyBlue = getColorFromImage(prefix,"skyBlue");//new Color(146, 144, 255);
        this.waterBlue = getColorFromImage(prefix,"waterBlue");//new Color(66, 64, 255);
        this.pink = new Color(255, 204, 197);
        this.black = Color.BLACK;
        this.brickRed = new Color(181, 49, 32);
        this.brickBlue = new Color(0, 123, 140);
        this.brickGray = new Color(166, 166, 166);
        this.superSpringGreen = new Color(13, 147, 0);
        this.offBlack = new Color(20, 20, 20);
        this.lavaColor = getColorFromImage(this.lavaBottom);//new Color(181, 49, 32);
        this.waterColor =getColorFromImage(this.waterBottom);//new Color(100, 176, 255);
    }
    //public void symbolsInit()  { symbolsInit("images/"); }
    public void symbolsInit(String prefix)  {
    	try{
        this.symbols.put('a', image(prefix,"symbols/A.png"));
        this.symbols.put('b', image(prefix,"symbols/B.png"));
        this.symbols.put('c', image(prefix,"symbols/C.png"));
        this.symbols.put('d', image(prefix,"symbols/D.png"));
        this.symbols.put('e', image(prefix,"symbols/E.png"));
        this.symbols.put('f', image(prefix,"symbols/F.png"));
        this.symbols.put('g', image(prefix,"symbols/G.png"));
        this.symbols.put('h', image(prefix,"symbols/H.png"));
        this.symbols.put('i', image(prefix,"symbols/I.png"));
        this.symbols.put('j', image(prefix,"symbols/J.png"));
        this.symbols.put('k', image(prefix,"symbols/K.png"));
        this.symbols.put('l', image(prefix,"symbols/L.png"));
        this.symbols.put('m', image(prefix,"symbols/M.png"));
        this.symbols.put('n', image(prefix,"symbols/N.png"));
        this.symbols.put('o', image(prefix,"symbols/O.png"));
        this.symbols.put('p', image(prefix,"symbols/P.png"));
        this.symbols.put('q', image(prefix,"symbols/Q.png"));
        this.symbols.put('r', image(prefix,"symbols/R.png"));
        this.symbols.put('s', image(prefix,"symbols/S.png"));
        this.symbols.put('t', image(prefix,"symbols/T.png"));
        this.symbols.put('u', image(prefix,"symbols/U.png"));
        this.symbols.put('v', image(prefix,"symbols/V.png"));
        this.symbols.put('w', image(prefix,"symbols/W.png"));
        this.symbols.put('x', image(prefix,"symbols/X.png"));
        this.symbols.put('y', image(prefix,"symbols/Y.png"));
        this.symbols.put('z', image(prefix,"symbols/Z.png"));
        this.symbols.put('1', image(prefix,"symbols/n1.png"));
        this.symbols.put('2', image(prefix,"symbols/n2.png"));
        this.symbols.put('3', image(prefix,"symbols/n3.png"));
        this.symbols.put('4', image(prefix,"symbols/n4.png"));
        this.symbols.put('5', image(prefix,"symbols/n5.png"));
        this.symbols.put('6', image(prefix,"symbols/n6.png"));
        this.symbols.put('7', image(prefix,"symbols/n7.png"));
        this.symbols.put('8', image(prefix,"symbols/n8.png"));
        this.symbols.put('9', image(prefix,"symbols/n9.png"));
        this.symbols.put('0', image(prefix,"symbols/n0.png"));
        this.symbols.put('-', image(prefix,"symbols/dash.png"));
        this.symbols.put(' ', image(prefix,"symbols/space.png"));
        this.symbols.put('(', image(prefix,"symbols/leftParens.png"));
        this.symbols.put(')', image(prefix,"symbols/rightParens.png"));
        this.symbols.put('~', image(prefix,"symbols/tilde.png"));
        this.symbols.put('!', image(prefix,"symbols/exclamation.png"));
        this.symbols.put('@', image(prefix,"symbols/emailAt.png"));
        this.symbols.put('#', image(prefix,"symbols/pound.png"));
        this.symbols.put('$', image(prefix,"symbols/dollarSign.png"));
        this.symbols.put('%', image(prefix,"symbols/percent.png"));
        this.symbols.put('^', image(prefix,"symbols/caret.png"));
        this.symbols.put('&', image(prefix,"symbols/ampersand.png"));
        this.symbols.put('*', image(prefix,"symbols/asterisk.png"));
        this.symbols.put('_', image(prefix,"symbols/underscore.png"));
        this.symbols.put('+', image(prefix,"symbols/plus.png"));
        this.symbols.put('=', image(prefix,"symbols/equals.png"));
        this.symbols.put('[', image(prefix,"symbols/leftBracket.png"));
        this.symbols.put(']', image(prefix,"symbols/rightBracket.png"));
        this.symbols.put('{', image(prefix,"symbols/leftCurly.png"));
        this.symbols.put('}', image(prefix,"symbols/rightCurly.png"));
        this.symbols.put(':', image(prefix,"symbols/colon.png"));
        this.symbols.put(';', image(prefix,"symbols/semiColon.png"));
        this.symbols.put('\"', image(prefix,"symbols/quote.png"));
        this.symbols.put(',', image(prefix,"symbols/comma.png"));
        this.symbols.put('\'', image(prefix,"symbols/apostraphe.png"));
        this.symbols.put('©', image(prefix,"symbols/copyright.png"));
        this.symbols.put('<', image(prefix,"symbols/lessThan.png"));
        this.symbols.put('>', image(prefix,"symbols/greaterThan.png"));
        this.symbols.put('.', image(prefix,"symbols/period.png"));
        this.symbols.put('?', image(prefix,"symbols/questionMark.png"));
        this.symbols.put('/', image(prefix,"symbols/fSlash.png"));
        this.symbols.put('\\', image(prefix,"symbols/bSlash.png"));
        this.symbols.put('|', image(prefix,"symbols/pipe.png"));
        this.symbols.put('\ufffc', image(prefix,"symbols/fatExclamation.png"));
        this.symbols.put('\ufffd', image(prefix,"symbols/times.png"));
        this.symbols.put('\uffff', image(prefix,"symbols/leftArrow.png"));
        this.symbols.put('\ufffe', image(prefix,"symbols/rightArrow.png"));
        this.symbols.put('\ufffb', image(prefix,"symbols/unknown.png"));
	    }catch(MessageException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(0);
		}
    }
    
    private void customTextCharsInit() {
        this.customTextChars.put('\u00ad', 'a');
        this.customTextChars.put('¯', 'b');
        this.customTextChars.put('³', 'c');
        this.customTextChars.put('¹', 'd');
        this.customTextChars.put('¸', 'e');
        this.customTextChars.put('\u00c0', 'f');
        this.customTextChars.put('\u00c1', 'g');
        this.customTextChars.put('\u00c5', 'h');
        this.customTextChars.put('\u00c6', 'i');
        this.customTextChars.put('\u00c7', 'j');
        this.customTextChars.put('\u00c8', 'k');
        this.customTextChars.put('\u00cc', 'l');
        this.customTextChars.put('\u00cd', 'm');
        this.customTextChars.put('\u00d0', 'n');
        this.customTextChars.put('\u00d2', 'o');
        this.customTextChars.put('\u00d3', 'p');
        this.customTextChars.put('\u00d4', 'q');
        this.customTextChars.put('\u00d5', 'r');
        this.customTextChars.put('\u00f3', 's');
        this.customTextChars.put('\u00f8', 't');
        this.customTextChars.put('\u00f5', 'u');
        this.customTextChars.put('\u00d9', 'v');
        this.customTextChars.put('\u00da', 'w');
        this.customTextChars.put('\u00ed', 'x');
        this.customTextChars.put('\u00dc', 'y');
        this.customTextChars.put('\u00dd', 'z');
        this.customTextChars.put('\u00fa', '1');
        this.customTextChars.put('\u00f2', '2');
        this.customTextChars.put('\u00e0', '3');
        this.customTextChars.put('\u00e1', '4');
        this.customTextChars.put('\u00e3', '5');
        this.customTextChars.put('\u00e4', '6');
        this.customTextChars.put('\u00e9', '7');
        this.customTextChars.put('\u00d6', '8');
        this.customTextChars.put('\u00db', '9');
        this.customTextChars.put('\u00e8', '0');
        this.customTextChars.put('\u0001', '!');
        this.customTextChars.put('\u00ea', '@');
        this.customTextChars.put('\u00eb', '#');
        this.customTextChars.put('\u00ec', '$');
        this.customTextChars.put('\u00f9', '%');
        this.customTextChars.put('\u00fb', '^');
        this.customTextChars.put('\u00fc', '&');
        this.customTextChars.put('\u00fe', '*');
        this.customTextChars.put('\u000e', '(');
        this.customTextChars.put('\f', ')');
        this.customTextChars.put('\u000b', '-');
        this.customTextChars.put('\u0002', '+');
        this.customTextChars.put('\u0003', '=');
        this.customTextChars.put('\u0004', '?');
        this.customTextChars.put('\u0005', '.');
        this.customTextChars.put('\u0006', ',');
        this.customTextChars.put('\u0007', ':');
        this.customTextChars.put('\u000f', ';');
        this.customTextChars.put('\u0010', '/');
        this.customTextChars.put('\u0011', '\\');
        this.customTextChars.put('\u0012', '\"');
        this.customTextChars.put('\u0013', '\'');
        this.customTextChars.put('\u0014', '<');
        this.customTextChars.put('\u0015', '>');
        this.customTextChars.put('\u0016', '~');
        this.customTextChars.put('\u0017', '[');
        this.customTextChars.put('\u0018', ']');
        this.customTextChars.put('\0', '{');
        this.customTextChars.put('\u001c', '}');
        this.customTextChars.put('\u001b', '|');
        this.customTextChars.put('\u007f', '_');
        for (final Map.Entry<Character, Character> entry : this.customTextChars.entrySet()) {
            this.customTextCharsInverted.put(entry.getValue(), entry.getKey());
        }
    }
    //public void overlayInit()  { overlayInit("images/"); }
    public void overlayInit(String prefix)  {
    	try{
        this.title = image(prefix,"overlay/Title.png");
        this.title2 = image(prefix,"overlay/Title2.png");
        this.selector = image(prefix,"overlay/Selector.png");
        this.controller = image(prefix,"overlay/controller.png");
        this.lightOverlayCoin1 = image(prefix,"overlay/overlayCoin/lightOverlayCoin1.png");
        this.lightOverlayCoin2 = image(prefix,"overlay/overlayCoin/lightOverlayCoin2.png");
        this.lightOverlayCoin3 = image(prefix,"overlay/overlayCoin/lightOverlayCoin3.png");
        this.darkOverlayCoin1 = image(prefix,"overlay/overlayCoin/darkOverlayCoin1.png");
        this.darkOverlayCoin2 = image(prefix,"overlay/overlayCoin/darkOverlayCoin2.png");
        this.darkOverlayCoin3 = image(prefix,"overlay/overlayCoin/darkOverlayCoin3.png");
        this.stoneOverlayCoin1 = image(prefix,"overlay/overlayCoin/stoneOverlayCoin1.png");
        this.stoneOverlayCoin2 = image(prefix,"overlay/overlayCoin/stoneOverlayCoin2.png");
        this.stoneOverlayCoin3 = image(prefix,"overlay/overlayCoin/stoneOverlayCoin3.png");
    	}catch(MessageException e){
    		e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(0);
    	}
    }
    //public void marioInit() { marioInit("images/"); }
    public void marioInit(String prefix)  {
    	try{
        this.marioDeadButton = image(prefix,"sprites/mario/deadButton.png");
        this.marioSmStand = mariomap.getTallSprite(0, 2);//image(prefix,"sprites/mario/smStand.png");
        this.marioSmJump = mariomap.getTallSprite(5, 2);//image(prefix,"sprites/mario/smJump.png");
        this.marioStand = mariomap.getTallSprite(0, 0);//image(prefix,"sprites/mario/stand.png");
        this.marioJump = mariomap.getTallSprite(5,0);//image(prefix,"sprites/mario/jump.png");
        this.marioCrouch = mariomap.getTallSprite(6,0);//image(prefix,"sprites/mario/crouch.png");
        this.marioWalk1 = mariomap.getTallSprite(1, 0);//image(prefix,"sprites/mario/walk1.png");
        this.marioWalk2 = mariomap.getTallSprite(2, 0);//image(prefix,"sprites/mario/walk2.png");
        this.marioWalk3 = mariomap.getTallSprite(03, 0);//image(prefix,"sprites/mario/walk3.png");
        this.marioSkid = mariomap.getTallSprite(04, 0);//image(prefix,"sprites/mario/skid.png");
        this.marioSmWalk1 = mariomap.getTallSprite(1, 2);//image(prefix,"sprites/mario/smWalk1.png");
        this.marioSmWalk2 = mariomap.getTallSprite(2, 2);//image(prefix,"sprites/mario/smWalk2.png");
        this.marioSmWalk3 = mariomap.getTallSprite(3, 2);//image(prefix,"sprites/mario/smWalk3.png");
        this.marioSmSkid = mariomap.getTallSprite(4, 2);//image(prefix,"sprites/mario/smSkid.png");
        this.marioGrow = mariomap.getTallSprite(15, 0);//image(prefix,"sprites/mario/grow.png");
        this.marioSwim1 = mariomap.getTallSprite(10, 0);//image(prefix,"sprites/mario/swim1.png");
        this.marioSwim2 = mariomap.getTallSprite(9, 0);//image(prefix,"sprites/mario/swim2.png");
        this.marioSwim3 = mariomap.getTallSprite(12, 0);//image(prefix,"sprites/mario/swim3.png");
        this.marioSwim4 = mariomap.getTallSprite(11, 0);//image(prefix,"sprites/mario/swim4.png");
        this.marioSwim5 = mariomap.getTallSprite(14, 0);//image(prefix,"sprites/mario/swim5.png");
        this.marioSwim6 = mariomap.getTallSprite(13, 0);//image(prefix,"sprites/mario/swim6.png");
        this.marioSmSwim1 = mariomap.getTallSprite(10, 2);//image(prefix,"sprites/mario/smSwim1.png");
        this.marioSmSwim2 = mariomap.getTallSprite(9, 2);//image(prefix,"sprites/mario/smSwim2.png");
        this.marioSmSwim3 = mariomap.getTallSprite(12, 2);//image(prefix,"sprites/mario/smSwim3.png");
        this.marioSmSwim4 = mariomap.getTallSprite(11, 2);//image(prefix,"sprites/mario/smSwim4.png");
        this.marioSmSwim5 = mariomap.getTallSprite(12, 2);//image(prefix,"sprites/mario/smSwim5.png");
        this.marioSmSwim6 = mariomap.getTallSprite(13, 2);//image(prefix,"sprites/mario/smSwim6.png");
        this.marioDead = mariomap.getTallSprite(6, 2);//image(prefix,"sprites/mario/dead.png");
        this.marioClimb1 = mariomap.getTallSprite(8,0);//image(prefix,"sprites/mario/climb1.png");
        this.marioClimb2 = mariomap.getTallSprite(7, 0);//image(prefix,"sprites/mario/climb2.png");
        this.marioSmClimb1 = mariomap.getTallSprite(8, 2);//image(prefix,"sprites/mario/smClimb1.png");
        this.marioSmClimb2 = mariomap.getTallSprite(7,2);//image(prefix,"sprites/mario/smClimb2.png");
        if((new File(prefix+"sprites/mario/mariopalette.png")).exists()){
        	 this.marioSmStand = TextureMap.applyPalette(marioSmStand, prefix+"sprites/mario/mariopalette.png");
             this.marioSmJump = TextureMap.applyPalette(marioSmJump,  prefix+"sprites/mario/mariopalette.png");
             this.marioStand = TextureMap.applyPalette(marioStand,  prefix+"sprites/mario/mariopalette.png");
             this.marioJump =TextureMap.applyPalette(marioJump,  prefix+"sprites/mario/mariopalette.png");
             this.marioCrouch = TextureMap.applyPalette(marioCrouch,  prefix+"sprites/mario/mariopalette.png");
             this.marioWalk1 = TextureMap.applyPalette(marioWalk1,  prefix+"sprites/mario/mariopalette.png");
             this.marioWalk2 =TextureMap.applyPalette(marioWalk2,  prefix+"sprites/mario/mariopalette.png");
             this.marioWalk3 =TextureMap.applyPalette(marioWalk3,  prefix+"sprites/mario/mariopalette.png");
             this.marioSkid =TextureMap.applyPalette(marioSkid,  prefix+"sprites/mario/mariopalette.png");
             this.marioSmWalk1 = TextureMap.applyPalette(marioSmWalk1, prefix+"sprites/mario/mariopalette.png");
             this.marioSmWalk2 = TextureMap.applyPalette(marioSmWalk2, prefix+"sprites/mario/mariopalette.png");
             this.marioSmWalk3 = TextureMap.applyPalette(marioSmWalk3, prefix+"sprites/mario/mariopalette.png");
             this.marioSmSkid = TextureMap.applyPalette(marioSmSkid, prefix+"sprites/mario/mariopalette.png");
             this.marioGrow = TextureMap.applyPalette(marioGrow, prefix+"sprites/mario/mariopalette.png");
             this.marioSwim1 = TextureMap.applyPalette(marioSwim1, prefix+"sprites/mario/mariopalette.png");
             this.marioSwim2 = TextureMap.applyPalette(marioSwim2, prefix+"sprites/mario/mariopalette.png");
             this.marioSwim3 = TextureMap.applyPalette(marioSwim3, prefix+"sprites/mario/mariopalette.png");
             this.marioSwim4 =TextureMap.applyPalette(marioSwim4, prefix+"sprites/mario/mariopalette.png");
             this.marioSwim5 = TextureMap.applyPalette(marioSwim5, prefix+"sprites/mario/mariopalette.png");
             this.marioSwim6 = TextureMap.applyPalette(marioSwim6, prefix+"sprites/mario/mariopalette.png");
             this.marioSmSwim1 = TextureMap.applyPalette(marioSmSwim1, prefix+"sprites/mario/mariopalette.png");
             this.marioSmSwim2 = TextureMap.applyPalette(marioSmSwim2, prefix+"sprites/mario/mariopalette.png");
             this.marioSmSwim3 =TextureMap.applyPalette(marioSmSwim3, prefix+"sprites/mario/mariopalette.png");
             this.marioSmSwim4 =TextureMap.applyPalette(marioSmSwim4, prefix+"sprites/mario/mariopalette.png");
             this.marioSmSwim5 = TextureMap.applyPalette(marioSmSwim5, prefix+"sprites/mario/mariopalette.png");
             this.marioSmSwim6 =TextureMap.applyPalette(marioSmSwim6, prefix+"sprites/mario/mariopalette.png");
             this.marioDead = TextureMap.applyPalette(marioDead, prefix+"sprites/mario/mariopalette.png");
             this.marioClimb1 =TextureMap.applyPalette(marioClimb1, prefix+"sprites/mario/mariopalette.png");
             this.marioClimb2 = TextureMap.applyPalette(marioClimb2, prefix+"sprites/mario/mariopalette.png");
             this.marioSmClimb1 = TextureMap.applyPalette(marioSmClimb1, prefix+"sprites/mario/mariopalette.png");
             this.marioSmClimb2 = TextureMap.applyPalette(marioSmClimb2, prefix+"sprites/mario/mariopalette.png");
        }
        this.marioFlowerWalk1 = TextureMap.applyPalette(marioWalk1,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/walk1.png");
        this.marioFlowerWalk2 = TextureMap.applyPalette(marioWalk2,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/walk2.png");
        this.marioFlowerWalk3 = TextureMap.applyPalette(marioWalk3,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/walk3.png");
        this.marioFlowerSwim1 = TextureMap.applyPalette(marioSwim1,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/swim1.png");
        this.marioFlowerSwim2 = TextureMap.applyPalette(marioSwim2,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/swim2.png");
        this.marioFlowerSwim3 = TextureMap.applyPalette(marioSwim3,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/swim3.png");
        this.marioFlowerSwim4 = TextureMap.applyPalette(marioSwim4,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/swim4.png");
        this.marioFlowerSwim5 = TextureMap.applyPalette(marioSwim5,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/swim5.png");
        this.marioFlowerSwim6 = TextureMap.applyPalette(marioSwim6,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/swim6.png");
        this.marioFlowerSkid = TextureMap.applyPalette(marioSkid,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/skid.png");
        this.marioFlowerCrouch = TextureMap.applyPalette(marioCrouch,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/crouch.png");
        this.marioFlowerJump = TextureMap.applyPalette(marioJump,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/jump.png");
        this.marioFlowerStand = TextureMap.applyPalette(marioStand,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/stand.png");
        this.marioFlowerClimb1 = TextureMap.applyPalette(marioClimb1,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/climb1.png");
        this.marioFlowerClimb2 = TextureMap.applyPalette(marioClimb2,prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/climb2.png");
        this.marioShootStandJumpWalk1 = TextureMap.applyPalette(mariomap.getTallSprite(16,0),prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/shootStandJumpWalk1.png");
        this.marioShootWalk2 = TextureMap.applyPalette(mariomap.getTallSprite(17,0),prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/shootWalk2.png");
        this.marioShootWalk3 = TextureMap.applyPalette(mariomap.getTallSprite(18,0),prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/shootWalk3.png");
        this.marioShootSkid = TextureMap.applyPalette(mariomap.getTallSprite(19,0),prefix+"sprites/mario/pizzapalette.png");//image(prefix,"sprites/mario/flower/shootSkid.png");
        this.marioStarBlackCrouch = TextureMap.applyPalette(marioCrouch,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/crouch.png");
        this.marioStarBlackGrow = TextureMap.applyPalette(marioGrow,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/grow.png");
        this.marioStarBlackJump = TextureMap.applyPalette(marioJump,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/jump.png");
        this.marioStarBlackShootSkid = TextureMap.applyPalette(mariomap.getTallSprite(19,0),prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/shootSkid.png");
        this.marioStarBlackShootStandJumpWalk1 = TextureMap.applyPalette(mariomap.getTallSprite(16,0),prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/shootStandJumpWalk1.png");
        this.marioStarBlackShootWalk2 = TextureMap.applyPalette(mariomap.getTallSprite(17,0),prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/shootWalk2.png");
        this.marioStarBlackShootWalk3 = TextureMap.applyPalette(mariomap.getTallSprite(18,0),prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/shootWalk3.png");
        this.marioStarBlackSkid = TextureMap.applyPalette(marioSkid,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/skid.png");
        this.marioStarBlackSmJump = TextureMap.applyPalette(marioSmJump,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smJump.png");
        this.marioStarBlackSmSkid = TextureMap.applyPalette(marioSmSkid,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smSkid.png");
        this.marioStarBlackSmStand = TextureMap.applyPalette(marioSmStand,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smStand.png");
        this.marioStarBlackSmSwim1 = TextureMap.applyPalette(marioSmSwim1,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smSwim1.png");
        this.marioStarBlackSmSwim2 = TextureMap.applyPalette(marioSmSwim2,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smSwim2.png");
        this.marioStarBlackSmSwim3 = TextureMap.applyPalette(marioSmSwim3,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smSwim3.png");
        this.marioStarBlackSmSwim4 = TextureMap.applyPalette(marioSmSwim4,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smSwim4.png");
        this.marioStarBlackSmSwim5 = TextureMap.applyPalette(marioSmSwim5,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smSwim5.png");
        this.marioStarBlackSmSwim6 = TextureMap.applyPalette(marioSmSwim6,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smSwim6.png");
        this.marioStarBlackSmWalk1 = TextureMap.applyPalette(marioSmWalk1,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smWalk1.png");
        this.marioStarBlackSmWalk2 = TextureMap.applyPalette(marioSmWalk2,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smWalk2.png");
        this.marioStarBlackSmWalk3 = TextureMap.applyPalette(marioSmWalk3,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smWalk3.png");
        this.marioStarBlackStand = TextureMap.applyPalette(marioStand,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/stand.png");
        this.marioStarBlackSwim1 = TextureMap.applyPalette(marioSwim1,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/swim1.png");
        this.marioStarBlackSwim2 = TextureMap.applyPalette(marioSwim2,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/swim2.png");
        this.marioStarBlackSwim3 = TextureMap.applyPalette(marioSwim3,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/swim3.png");
        this.marioStarBlackSwim4 = TextureMap.applyPalette(marioSwim4,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/swim4.png");
        this.marioStarBlackSwim5 = TextureMap.applyPalette(marioSwim5,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/swim5.png");
        this.marioStarBlackSwim6 = TextureMap.applyPalette(marioSwim6,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/swim6.png");
        this.marioStarBlackWalk1 = TextureMap.applyPalette(marioWalk1,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/walk1.png");
        this.marioStarBlackWalk2 = TextureMap.applyPalette(marioWalk2,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/walk2.png");
        this.marioStarBlackWalk3 = TextureMap.applyPalette(marioWalk3,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/walk3.png");
        this.marioStarBlackClimb1 = TextureMap.applyPalette(marioClimb1,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/climb1.png");
        this.marioStarBlackClimb2 = TextureMap.applyPalette(marioClimb2,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/climb2.png");
        this.marioStarBlackSmClimb1 = TextureMap.applyPalette(marioSmClimb1,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smClimb1.png");
        this.marioStarBlackSmClimb2 = TextureMap.applyPalette(marioSmClimb2,prefix+"sprites/mario/starblackpalette.png");//image(prefix,"sprites/mario/star/black/smClimb2.png");
        this.marioStarGreenCrouch = TextureMap.applyPalette(marioCrouch,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/crouch.png");
        this.marioStarGreenGrow = TextureMap.applyPalette(marioGrow,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/grow.png");
        this.marioStarGreenJump = TextureMap.applyPalette(marioJump,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/jump.png");
        this.marioStarGreenShootStandJumpWalk1 = TextureMap.applyPalette(mariomap.getTallSprite(16,0),prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/shootStandJumpWalk1.png");
        this.marioStarGreenShootWalk2 = TextureMap.applyPalette(mariomap.getTallSprite(17,0),prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/flower/shootWalk2.png");
        this.marioStarGreenShootWalk3 = TextureMap.applyPalette(mariomap.getTallSprite(18,0),prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/flower/shootWalk3.png");
        this.marioStarGreenShootSkid = TextureMap.applyPalette(mariomap.getTallSprite(19,0),prefix+"sprites/mario/stargreenpalette.png");
        this.marioStarGreenSkid = TextureMap.applyPalette(marioSkid,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/skid.png");
        this.marioStarGreenSmJump = TextureMap.applyPalette(marioSmJump,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smJump.png");
        this.marioStarGreenSmSkid = TextureMap.applyPalette(marioSmSkid,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smSkid.png");
        this.marioStarGreenSmStand = TextureMap.applyPalette(marioSmStand,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smStand.png");
        this.marioStarGreenSmSwim1 = TextureMap.applyPalette(marioSmSwim1,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smSwim1.png");
        this.marioStarGreenSmSwim2 = TextureMap.applyPalette(marioSmSwim2,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smSwim2.png");
        this.marioStarGreenSmSwim3 = TextureMap.applyPalette(marioSmSwim3,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smSwim3.png");
        this.marioStarGreenSmSwim4 = TextureMap.applyPalette(marioSmSwim4,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smSwim4.png");
        this.marioStarGreenSmSwim5 = TextureMap.applyPalette(marioSmSwim5,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smSwim5.png");
        this.marioStarGreenSmSwim6 = TextureMap.applyPalette(marioSmSwim6,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smSwim6.png");
        this.marioStarGreenSmWalk1 = TextureMap.applyPalette(marioSmWalk1,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smWalk1.png");
        this.marioStarGreenSmWalk2 = TextureMap.applyPalette(marioSmWalk2,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smWalk2.png");
        this.marioStarGreenSmWalk3 = TextureMap.applyPalette(marioSmWalk3,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smWalk3.png");
        this.marioStarGreenStand = TextureMap.applyPalette(marioStand,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/stand.png");
        this.marioStarGreenSwim1 = TextureMap.applyPalette(marioSwim1,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/swim1.png");
        this.marioStarGreenSwim2 = TextureMap.applyPalette(marioSwim2,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/swim2.png");
        this.marioStarGreenSwim3 = TextureMap.applyPalette(marioSwim3,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/swim3.png");
        this.marioStarGreenSwim4 = TextureMap.applyPalette(marioSwim4,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/swim4.png");
        this.marioStarGreenSwim5 = TextureMap.applyPalette(marioSwim5,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/swim5.png");
        this.marioStarGreenSwim6 = TextureMap.applyPalette(marioSwim6,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/swim6.png");
        this.marioStarGreenWalk1 = TextureMap.applyPalette(marioWalk1,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/walk1.png");
        this.marioStarGreenWalk2 = TextureMap.applyPalette(marioWalk2,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/walk2.png");
        this.marioStarGreenWalk3 = TextureMap.applyPalette(marioWalk3,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/walk3.png");
        this.marioStarGreenClimb1 = TextureMap.applyPalette(marioClimb1,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/climb1.png");
        this.marioStarGreenClimb2 = TextureMap.applyPalette(marioClimb2,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/climb2.png");
        this.marioStarGreenSmClimb1 = TextureMap.applyPalette(marioSmClimb1,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smClimb1.png");
        this.marioStarGreenSmClimb2 = TextureMap.applyPalette(marioSmClimb2,prefix+"sprites/mario/stargreenpalette.png");//image(prefix,"sprites/mario/star/green/smClimb2.png");
        this.marioStarRedCrouch = TextureMap.applyPalette(marioCrouch,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/crouch.png");
        this.marioStarRedGrow = TextureMap.applyPalette(marioGrow,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/grow.png");
        this.marioStarRedJump = TextureMap.applyPalette(marioJump,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/jump.png");
        this.marioStarRedShootStandJumpWalk1 = TextureMap.applyPalette(mariomap.getTallSprite(16,0),prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/shootStandJumpWalk1.png");
        this.marioStarRedShootWalk2 = TextureMap.applyPalette(mariomap.getTallSprite(17,0),prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/flower/shootWalk2.png");
        this.marioStarRedShootWalk3 = TextureMap.applyPalette(mariomap.getTallSprite(18,0),prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/flower/shootWalk3.png");
        this.marioStarRedShootSkid = TextureMap.applyPalette(mariomap.getTallSprite(19,0),prefix+"sprites/mario/starredpalette.png");
        this.marioStarRedSkid = TextureMap.applyPalette(marioSkid,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/skid.png");
        this.marioStarRedSmJump = TextureMap.applyPalette(marioSmJump,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smJump.png");
        this.marioStarRedSmSkid = TextureMap.applyPalette(marioSmSkid,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smSkid.png");
        this.marioStarRedSmStand = TextureMap.applyPalette(marioSmStand,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smStand.png");
        this.marioStarRedSmSwim1 = TextureMap.applyPalette(marioSmSwim1,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smSwim1.png");
        this.marioStarRedSmSwim2 = TextureMap.applyPalette(marioSmSwim2,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smSwim2.png");
        this.marioStarRedSmSwim3 = TextureMap.applyPalette(marioSmSwim3,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smSwim3.png");
        this.marioStarRedSmSwim4 = TextureMap.applyPalette(marioSmSwim4,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smSwim4.png");
        this.marioStarRedSmSwim5 = TextureMap.applyPalette(marioSmSwim5,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smSwim5.png");
        this.marioStarRedSmSwim6 = TextureMap.applyPalette(marioSmSwim6,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smSwim6.png");
        this.marioStarRedSmWalk1 = TextureMap.applyPalette(marioSmWalk1,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smWalk1.png");
        this.marioStarRedSmWalk2 = TextureMap.applyPalette(marioSmWalk2,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smWalk2.png");
        this.marioStarRedSmWalk3 = TextureMap.applyPalette(marioSmWalk3,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smWalk3.png");
        this.marioStarRedStand = TextureMap.applyPalette(marioStand,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/stand.png");
        this.marioStarRedSwim1 = TextureMap.applyPalette(marioSwim1,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/swim1.png");
        this.marioStarRedSwim2 = TextureMap.applyPalette(marioSwim2,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/swim2.png");
        this.marioStarRedSwim3 = TextureMap.applyPalette(marioSwim3,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/swim3.png");
        this.marioStarRedSwim4 = TextureMap.applyPalette(marioSwim4,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/swim4.png");
        this.marioStarRedSwim5 = TextureMap.applyPalette(marioSwim5,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/swim5.png");
        this.marioStarRedSwim6 =TextureMap.applyPalette(marioSwim6,prefix+"sprites/mario/starredpalette.png");// image(prefix,"sprites/mario/star/red/swim6.png");
        this.marioStarRedWalk1 = TextureMap.applyPalette(marioWalk1,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/walk1.png");
        this.marioStarRedWalk2 = TextureMap.applyPalette(marioWalk2,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/walk2.png");
        this.marioStarRedWalk3 = TextureMap.applyPalette(marioWalk3,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/walk3.png");
        this.marioStarRedClimb1 = TextureMap.applyPalette(marioClimb1,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/climb1.png");
        this.marioStarRedClimb2 = TextureMap.applyPalette(marioClimb2,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/climb2.png");
        this.marioStarRedSmClimb1 = TextureMap.applyPalette(marioSmClimb1,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smClimb1.png");
        this.marioStarRedSmClimb2 = TextureMap.applyPalette(marioSmClimb2,prefix+"sprites/mario/starredpalette.png");//image(prefix,"sprites/mario/star/red/smClimb2.png");
    	}catch(MessageException e){
    		e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(0);
    	}
    }
    //public void luigiInit()  { luigiInit("images/"); }
    public void luigiInit(String prefix)  {
        this.luigiSmStand = TextureMap.applyPalette(marioSmStand,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smStand.png");
        this.luigiSmJump = TextureMap.applyPalette(marioSmJump,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smJump.png");
        this.luigiStand = TextureMap.applyPalette(marioStand,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/stand.png");
        this.luigiJump = TextureMap.applyPalette(marioJump,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/jump.png");
        this.luigiCrouch = TextureMap.applyPalette(marioCrouch,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/crouch.png");
        this.luigiWalk1 = TextureMap.applyPalette(marioWalk1,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/walk1.png");
        this.luigiWalk2 = TextureMap.applyPalette(marioWalk2,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/walk2.png");
        this.luigiWalk3 = TextureMap.applyPalette(marioWalk3,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/walk3.png");
        this.luigiSkid = TextureMap.applyPalette(marioSkid,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/skid.png");
        this.luigiSmWalk1 = TextureMap.applyPalette(marioSmWalk1,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smWalk1.png");
        this.luigiSmWalk2 = TextureMap.applyPalette(marioSmWalk2,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smWalk2.png");
        this.luigiSmWalk3 = TextureMap.applyPalette(marioSmWalk3,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smWalk3.png");
        this.luigiSmSkid = TextureMap.applyPalette(marioSmSkid,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smSkid.png");
        this.luigiGrow = TextureMap.applyPalette(marioGrow,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/grow.png");
        this.luigiSwim1 = TextureMap.applyPalette(marioSwim1,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/swim1.png");
        this.luigiSwim2 = TextureMap.applyPalette(marioSwim2,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/swim2.png");
        this.luigiSwim3 = TextureMap.applyPalette(marioSwim3,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/swim3.png");
        this.luigiSwim4 = TextureMap.applyPalette(marioSwim4,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/swim4.png");
        this.luigiSwim5 = TextureMap.applyPalette(marioSwim5,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/swim5.png");
        this.luigiSwim6 = TextureMap.applyPalette(marioSwim6,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/swim6.png");
        this.luigiSmSwim1 = TextureMap.applyPalette(marioSmSwim1,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smSwim1.png");
        this.luigiSmSwim2 = TextureMap.applyPalette(marioSmSwim2,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smSwim2.png");
        this.luigiSmSwim3 = TextureMap.applyPalette(marioSmSwim3,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smSwim3.png");
        this.luigiSmSwim4 = TextureMap.applyPalette(marioSmSwim4,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smSwim4.png");
        this.luigiSmSwim5 = TextureMap.applyPalette(marioSmSwim5,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smSwim5.png");
        this.luigiSmSwim6 = TextureMap.applyPalette(marioSmSwim6,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smSwim6.png");
        this.luigiDead = TextureMap.applyPalette(marioDead,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/dead.png");
        this.luigiClimb1 = TextureMap.applyPalette(marioClimb1,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/climb1.png");
        this.luigiClimb2 = TextureMap.applyPalette(marioClimb2,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/climb2.png");
        this.luigiSmClimb1 = TextureMap.applyPalette(marioSmClimb1,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smClimb1.png");
        this.luigiSmClimb2 = TextureMap.applyPalette(marioSmClimb2,prefix+"sprites/mario/luigipalette.png");//image(prefix,"sprites/luigi/smClimb2.png");
    }
    //public void tilesInit()  { tilesInit("images/"); }
    public void tilesInit(String prefix)  {   	
    	//normal images
    	try{
        this.bubby = image(prefix,"tiles/princess.png",24);
        this.fakePrincess = image(prefix,"tiles/toad.png",24);
        this.lightBrick = image(prefix,"tiles/overworld/brick.png",16);
        this.darkBrick = image(prefix,"tiles/underground/brick.png",16);
        this.stoneBrick = image(prefix,"tiles/castle/brick.png",16);
        System.out.println("stoneBrick is bric? "+isBrick(stoneBrick));
        TextureMap ground = new TextureMap(prefix+"tiles/overworld/ground.png");
        this.lightGround = ground.getTile(1, 1);
        this.lightGroundTopLeft = ground.getTile(0, 0);
        this.lightGroundTop = ground.getTile(1, 0);
        this.lightGroundTopRight = ground.getTile(2,0);
        this.lightGroundLeft = ground.getTile(0,1);
        this.lightGroundRight = ground.getTile(2,1);
        this.lightGroundBottomLeft = ground.getTile(0,2);
        this.lightGroundBottom = ground.getTile(1,2);
        this.lightGroundBottomRight = ground.getTile(2,2);
        ground = new TextureMap(prefix+"tiles/underground/ground.png");
        this.darkGround = ground.getTile(1, 1);
        this.darkGroundTopLeft = ground.getTile(0, 0);
        this.darkGroundTop = ground.getTile(1, 0);
        this.darkGroundTopRight = ground.getTile(2,0);
        this.darkGroundLeft = ground.getTile(0,1);
        this.darkGroundRight = ground.getTile(2,1);
        this.darkGroundBottomLeft = ground.getTile(0,2);
        this.darkGroundBottom = ground.getTile(1,2);
        this.darkGroundBottomRight = ground.getTile(2,2);
        this.lightBlock = image(prefix,"tiles/overworld/block.png",16);//tiles.get16xSprite(96,0);//image(prefix,"tiles/lightBlock.png");
        this.darkBlock = image(prefix,"tiles/underground/block.png",16);//tiles.get16xSprite(112,0);//image(prefix,"tiles/darkBlock.png");
        this.stoneBlock = image(prefix,"tiles/castle/block.png",16);//tiles.get16xSprite(128,0);//image(prefix,"tiles/stoneBlock.png");
       
        ground = new TextureMap(prefix+"tiles/overworld/cloud.png");
        this.cloudGround = ground.getTile(1, 1);
        this.cloudGroundTopLeft = ground.getTile(0, 0);
        this.cloudGroundTop = ground.getTile(1, 0);
        this.cloudGroundTopRight = ground.getTile(2,0);
        this.cloudGroundLeft = ground.getTile(0,1);
        this.cloudGroundRight = ground.getTile(2,1);
        this.cloudGroundBottomLeft = ground.getTile(0,2);
        this.cloudGroundBottom = ground.getTile(1,2);
        this.cloudGroundBottomRight = ground.getTile(2,2);
        
        this.lightMetal = image(prefix,"tiles/overworld/metal.png",16);//tiles.get16xSprite(144,0);//image(prefix,"tiles/lightMetal.png");
        this.darkMetal = image(prefix,"tiles/underground/metal.png",16);//tiles.get16xSprite(160,0);//image(prefix,"tiles/darkMetal.png");
        this.bigHill = image(prefix,"tiles/overworld/hill_lg.png");//tiles.getSubImage(192,0,80,40);//image(prefix,"tiles/bigHill.png");
        this.smallHill = image(prefix,"tiles/overworld/hill_sm.png");//tiles.getSubImage(224,40,48,24);//image(prefix,"tiles/smallHill.png");
        this.singleBush = image(prefix,"tiles/overworld/bush_sm.png",16);//tiles.getSubImage(144,16,32,16);//image(prefix,"tiles/singleBush.png");
        this.doubleBush = image(prefix,"tiles/overworld/bush_md.png",16);//tiles.getSubImage(273,0,48,16);//image(prefix,"tiles/doubleBush.png");
        this.tripleBush = image(prefix,"tiles/overworld/bush_lg.png",16);//tiles.getSubImage(144,56,64,16);//image(prefix,"tiles/tripleBush.png");
        this.singleCloud = image(prefix,"tiles/overworld/cloud_sm.png",24);//tiles.getSubImage(144,32,32,24);//image(prefix,"tiles/singleCloud.png");
        this.doubleCloud = image(prefix,"tiles/overworld/cloud_md.png",24);//tiles.getSubImage(273,16,48,24);//image(prefix,"tiles/doubleCloud.png");
        this.tripleCloud = image(prefix,"tiles/overworld/cloud_lg.png",24);//tiles.getSubImage(144,72,64,24);//image(prefix,"tiles/tripleCloud.png");
        this.smallCastle = image(prefix,"tiles/overworld/castle_sm.png");//tiles.getSubImage(0,112,80,80);//image(prefix,"tiles/smallCastle.png");
        this.largeCastle = image(prefix,"tiles/overworld/castle_lg.png");//tiles.getSubImage(208,96,152,176);//image(prefix,"tiles/largeCastle.png");
        this.castleWall = image(prefix,"tiles/overworld/castleWall.png");//tiles.getSubImage(128,16,16,96);//image(prefix,"tiles/castleWall.png");
        /*this.lightQuestionBox1 = tiles.get16xSprite(0,193);//image(prefix,"tiles/questionBox/lightQuestionBox1.png");
        this.lightQuestionBox2 = tiles.get16xSprite(16,193);//image(prefix,"tiles/questionBox/lightQuestionBox2.png");
        this.lightQuestionBox3 = tiles.get16xSprite(32,193);//image(prefix,"tiles/questionBox/lightQuestionBox3.png");
        this.darkQuestionBox1 = tiles.get16xSprite(0,209);//image(prefix,"tiles/questionBox/darkQuestionBox1.png");
        this.darkQuestionBox2 = tiles.get16xSprite(16,209);//image(prefix,"tiles/questionBox/darkQuestionBox2.png");
        this.darkQuestionBox3 = tiles.get16xSprite(32,209);//image(prefix,"tiles/questionBox/darkQuestionBox3.png");
        this.stoneQuestionBox1 = tiles.get16xSprite(0,225);//image(prefix,"tiles/questionBox/stoneQuestionBox1.png");
        this.stoneQuestionBox2 = tiles.get16xSprite(16,225);//image(prefix,"tiles/questionBox/stoneQuestionBox2.png");
        this.stoneQuestionBox3 = tiles.get16xSprite(32,225);//image(prefix,"tiles/questionBox/stoneQuestionBox3.png");
       */
        this.lightQuestionBox = image(prefix,"tiles/overworld/questionblock.png",16);
        this.darkQuestionBox = image(prefix,"tiles/underground/questionblock.png",16);
        this.stoneQuestionBox = image(prefix,"tiles/castle/questionblock.png",16);
        this.questionBoxHidden = image("images/","tiles/null.png");//tiles.get16xSprite(81,176);//image(prefix,"tiles/questionBox/questionBoxHidden.png");
        this.treeTopLeftEnd = image(prefix,"tiles/overworld/tree_l.png",16);//tiles.get16xSprite(48,16);//image(prefix,"tiles/tree/treeTopLeftEnd.png");
        this.treeTopMiddle = image(prefix,"tiles/overworld/tree_m.png",16);//tiles.getSubImage(64,16,8,16);//image(prefix,"tiles/tree/treeTopMiddle.png");
        this.treeTopRightEnd = image(prefix,"tiles/overworld/tree_r.png",16);//tiles.get16xSprite(80,16);//image(prefix,"tiles/tree/treeTopRightEnd.png");
        this.treeBark = image(prefix,"tiles/overworld/bark_m.png",8);//tiles.getSubImage(72,16,8,8);//image(prefix,"tiles/tree/treeBark.png");
        this.treeBarkLeft = image(prefix,"tiles/overworld/bark_l.png",8);//
        this.treeBarkRight = image(prefix,"tiles/overworld/bark_r.png",8);//
        this.mushroomTreeLeftEnd = image(prefix,"tiles/overworld/mushroom_l.png",16);//tiles.get16xSprite(80,32);//image(prefix,"tiles/mushroomTree/mushroomTreeLeftEnd.png");
        this.mushroomTreeMiddleSection = image(prefix,"tiles/overworld/mushroom_m.png",16);//tiles.get16xSprite(92,32);//image(prefix,"tiles/mushroomTree/mushroomTreeMiddleSection.png");
        this.mushroomTreeRightEnd = image(prefix,"tiles/overworld/mushroom_r.png",16);//tiles.get16xSprite(112,32);//image(prefix,"tiles/mushroomTree/mushroomTreeRightEnd.png");
        this.mushroomTreeBarkTop = image(prefix,"tiles/overworld/mushroomStem_t.png",8);//tiles.getSubImage(32,16,16,8);//image(prefix,"tiles/mushroomTree/mushroomBarkTop.png");
        this.mushroomTreeBark = image(prefix,"tiles/overworld/mushroomStem_m.png",8);//tiles.getSubImage(32,24,16,8);
        this.mushroomTreeBarkBottom = image(prefix,"tiles/overworld/mushroomStem_b.png",8);//
        this.lavaClassic = image(prefix,"tiles/overworld/lava.png",40);//tiles.getSubImage(84,121,8,40);//image(prefix,"tiles/liquids/lava.png");
        this.lavaTop = image(prefix,"tiles/overworld/lava_t.png",8);//tiles.getSubImage(0,48,8,8);//image(prefix,"tiles/liquids/lavaTop.png");
        this.lavaBottom = image(prefix,"tiles/overworld/lava_b.png",8);//tiles.getSubImage(0,56,8,8);//image(prefix,"tiles/liquids/lavaBottom.png");
        this.waterTop = image(prefix,"tiles/overworld/water_t.png",8);//tiles.getSubImage(8,48,8,8);//image(prefix,"tiles/liquids/waterTop.png");
        this.waterBottom = image(prefix,"tiles/overworld/water_b.png",8);//tiles.getSubImage(8,56,8,8);//image(prefix,"tiles/liquids/waterBottom.png");
        this.waves = image(prefix,"tiles/overworld/waves.png",8);//tiles.getSubImage(72,24,8,8);//image(prefix,"tiles/waves.png");
        ground = new TextureMap(prefix+"tiles/castle/ground.png");
        this.stoneGround = ground.getTile(1, 1);
        this.stoneGroundTopLeft = ground.getTile(0, 0);
        this.stoneGroundTop = ground.getTile(1, 0);
        this.stoneGroundTopRight = ground.getTile(2,0);
        this.stoneGroundLeft = ground.getTile(0,1);
        this.stoneGroundRight = ground.getTile(2,1);
        this.stoneGroundBottomLeft = ground.getTile(0,2);
        this.stoneGroundBottom = ground.getTile(1,2);
        this.stoneGroundBottomRight = ground.getTile(2,2);
        
        //this.stoneGround = tiles.get16xSprite(32,0);//image(prefix,"tiles/stoneGround.png");
        this.flagpoleGreen = Utilities.addImages(image(prefix,"tiles/overworld/flagpole_green.png"), lightBlock, "down");//tiles.getSubImage(128,112,16,168);//image(prefix,"tiles/flagpoleGreen.png");
        this.flagpoleWhite = Utilities.addImages(image(prefix,"tiles/overworld/flagpole_white.png"), lightBlock, "down");//tiles.getSubImage(145,112,16,168);//image(prefix,"tiles/flagpoleWhite.png");
        this.coral = image(prefix,"tiles/underwater/coral.png",16);//tiles.get16xSprite(64,48);//image(prefix,"tiles/coral.png");
        ground = new TextureMap(prefix+"tiles/underwater/ground.png");
        this.seaGround = ground.getTile(1, 1);
        this.seaGroundTopLeft = ground.getTile(0, 0);
        this.seaGroundTop = ground.getTile(1, 0);
        this.seaGroundTopRight = ground.getTile(2,0);
        this.seaGroundLeft = ground.getTile(0,1);
        this.seaGroundRight = ground.getTile(2,1);
        this.seaGroundBottomLeft = ground.getTile(0,2);
        this.seaGroundBottom = ground.getTile(1,2);
        this.seaGroundBottomRight = ground.getTile(2,2);
        
        //this.seaGround = tiles.get16xSprite(0,16);//image(prefix,"tiles/seaGround.png");
        this.seaBlock = image(prefix,"tiles/underwater/block.png",16);
        this.stoneMetal = image(prefix,"tiles/castle/metal.png",16);//tiles.get16xSprite(176,0);//image(prefix,"tiles/stoneMetal.png");
        this.tallTrimmedBush = image(prefix,"tiles/overworld/shrub_lg.png",48);//tiles.getSubImage(96,80,16,48);//image(prefix,"tiles/tallTrimmedBush.png");
        this.shortTrimmedBush = image(prefix,"tiles/overworld/shrub_sm.png",32);//tiles.getSubImage(96,48,16,32);//image(prefix,"tiles/shortTrimmedBush.png");
        this.shortSnowyBush = image(prefix,"tiles/castle/shrub_sm.png",32);//tiles.getSubImage(112,48,16,32);//image(prefix,"tiles/shortSnowyBush.png");
        this.tallSnowyBush = image(prefix,"tiles/castle/shrub_lg.png",48);//tiles.getSubImage(112,80,16,48);//image(prefix,"tiles/tallSnowyBush.png");
        this.picketFence = image(prefix,"tiles/overworld/fence_m.png",16);//tiles.get16xSprite(32,48);//image(prefix,"tiles/picketFence.png");
        this.picketFenceLeft = image(prefix,"tiles/overworld/fence_l.png",16);//
        this.picketFenceRight = image(prefix,"tiles/overworld/fence_r.png",16);//
        this.bridge = image(prefix,"tiles/overworld/bridge.png",16);// tiles.getSubImage(16,48,8,16);//image(prefix,"tiles/bridge.png");
        this.bowserBridge = image(prefix,"tiles/castle/bowserBridge.png",16);//tiles.get16xSprite(48,32);//image(prefix,"tiles/bowserBattle/bowserBridge.png");
        this.bowserBridgeSection = Utilities.cropIcon(bowserBridge, 0, 0, 8, 16);
        //this.bowserBridgeSection = tiles.getSubImage(48,32,8,16);//image(prefix,"tiles/bowserBridge.png");
        /*this.bowserAxe1 = tiles.get16xSprite(0,32);//image(prefix,"tiles/bowserBattle/bowserAxe1.png");
        this.bowserAxe2 = tiles.get16xSprite(16,32);//image(prefix,"tiles/bowserBattle/bowserAxe2.png");
        this.bowserAxe3 = tiles.get16xSprite(32,32);//image(prefix,"tiles/bowserBattle/bowserAxe3.png");
        */
        this.bowserAxe = image(prefix,"tiles/castle/axe.png",16);
        this.bowserChain = image(prefix,"tiles/castle/chain.png",16);//tiles.get16xSprite(64,32);//image(prefix,"tiles/bowserBattle/bowserChain.png");
        this.warpZoneMessage = image(prefix,"tiles/warpZoneMessage.png");
        this.warpZonePipeGreen = image(prefix,"tiles/pipes/warpPipeGreen.png");
        this.warpZonePipeWhite = TextureMap.applyPalette(warpZonePipeGreen, prefix+"tiles/pipes/whitepalette.png");
        this.warpZonePipeBlue = TextureMap.applyPalette(warpZonePipeGreen, prefix+"tiles/pipes/bluepalette.png");
        this.warpZonePipeOrange = TextureMap.applyPalette(warpZonePipeGreen, prefix+"tiles/pipes/orangepalette.png");
        this.infiniteCorridorSizer = new ImageIcon(new BufferedImage(64, 32, 2));
        this.solidTestTile = image(prefix,"tiles/solidTestTile.png");
    	}catch(MessageException ex){
    		ex.printStackTrace();
			System.out.println(ex.getMessage());
			System.exit(0);
    	}
        altsInit(prefix);
    }
    //inits alternate level types of tiles
    public void altsInit(String prefix){
    	//light
    	try{this.seaBrick = image(prefix,"tiles/underwater/bricks.png",16);}
    	catch(MessageException ex){this.seaBrick = this.lightBrick;}
    	try{this.seaCoin = image(prefix,"tiles/underwater/coin.png",16);}
    	catch(MessageException ex){this.seaCoin = this.lightCoin;}
    	
    	//dark
    	try{this.bigHillDark = image(prefix,"tiles/underground/hill_lg.png",16);}
    	catch(MessageException ex){this.bigHillDark = this.bigHill;}
    	try{this.smallHillDark = image(prefix,"tiles/underground/hill_sm.png",16);}
    	catch(MessageException ex){this.smallHillDark = this.smallHill;}
    	
    	//castle
    	try{this.bigHillCastle = image(prefix,"tiles/castle/hill_lg.png",16);}
    	catch(MessageException ex){this.bigHillCastle = this.bigHill;}
    	try{this.smallHillCastle = image(prefix,"tiles/castle/hill_sm.png",16);}
    	catch(MessageException ex){this.smallHillCastle = this.smallHill;}
    	
    	//sea
    	try{this.bigHillSea = image(prefix,"tiles/underwater/hill_lg.png",16);}
    	catch(MessageException ex){this.bigHillSea = this.bigHill;}
    	try{this.smallHillSea = image(prefix,"tiles/underwater/hill_sm.png",16);}
    	catch(MessageException ex){this.smallHillSea = this.smallHill;}
    }
    public void pipesInit(String prefix)  {
        this.greenPipes = this.getGreenPipeImages(prefix);
        this.bluePipes = this.getColorPipeImages(prefix+"tiles/pipes/bluepalette.png");
        this.whitePipes = this.getColorPipeImages(prefix+"tiles/pipes/whitepalette.png");
        this.orangePipes = this.getColorPipeImages(prefix+"tiles/pipes/orangepalette.png");
    }
    public ImageIcon[] getColorPipeImages(String palette){
    	ImageIcon[] pipeSet = new ImageIcon[11];
    	for(int i = 0; i < 11; i++){
    		pipeSet[i] = TextureMap.applyPalette(greenPipes[i], palette);
    	}
    	return pipeSet;
    }
    public ImageIcon[] getGreenPipeImages(String prefix)  {
       
        final ImageIcon[] pipeSet = new ImageIcon[11];
        try{
        pipeSet[0] = image(prefix,"tiles/pipes/pipeSideLeftGreen.png");
        pipeSet[1] = image(prefix,"tiles/pipes/pipeSideRightGreen.png");
        pipeSet[2] = image(prefix,"tiles/pipes/pipeTopGreen.png");
        pipeSet[3] = image(prefix,"tiles/pipes/pipeBottomGreen.png");
        pipeSet[4] = image(prefix,"tiles/pipes/pipeTopSectionGreen.png"); 
        pipeSet[5] = image(prefix,"tiles/pipes/pipeSideSectionGreen.png");
        pipeSet[6] = image(prefix,"tiles/pipes/pipeSideConnectorLeftGreen.png");
        pipeSet[7] = image(prefix,"tiles/pipes/pipeSideConnectorRightGreen.png");
        pipeSet[8] = image(prefix,"tiles/pipes/pipeTopConnectorGreen.png");
        pipeSet[9] = image(prefix,"tiles/pipes/pipeBottomConnectorGreen.png");
        pipeSet[10] = image(prefix,"tiles/pipes/pipeSideConnectorDoubleGreen.png");
        }catch(MessageException e){
        	e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(0);
        }
       
        return pipeSet;
    }
   
    public void spritesInit(String prefix)  {
    	try{
        this.flag = image(prefix,"tiles/overworld/flag_green.png",16);//tiles.get16xSprite(112,16);//image(prefix,"tiles/flag.png");
        this.checkPtFlag = image(prefix,"sprites/flag.png");
        this.castleFlag = image(prefix,"tiles/overworld/flag_red.png",16);//tiles.get16xSprite(96,16);//image(prefix,"tiles/castleFlag.png");
        this.darkCoin = image(prefix,"tiles/underground/coin.png",16);
        this.lightCoin = image(prefix,"tiles/overworld/coin.png",16);
        this.stoneCoin = image(prefix,"tiles/castle/coin.png",16);
        TextureMap mushrooms = new TextureMap(prefix+"sprites/mushroom.png");
        this.growMushroom = mushrooms.getTile(0, 0);//image(prefix,"sprites/growMushroom.png");
        this.poisonMushroom = mushrooms.getTile(2,0);//image(prefix,"sprites/poisonMushroom.png");
        TextureMap starmap = new TextureMap(prefix+"sprites/star.png");
        this.star1 = starmap.getTile(0, 0);//image(prefix,"sprites/star/star1.png");
        this.star2 = starmap.getTile(1,0);//image(prefix,"sprites/star/star2.png");
        this.star3 = starmap.getTile(2,0);//image(prefix,"sprites/star/star3.png");
        this.star4 = starmap.getTile(3,0);//image(prefix,"sprites/star/star4.png");
        this.star5 = starmap.getTile(4,0);//image(prefix,"sprites/star/star5.png");
        this.starCancel = starmap.getTile(5,0);//image(prefix,"sprites/star/starCancel.png");
        this.lightExtraLife = mushrooms.getTile(1, 0);//mage(prefix,"sprites/1up.png");
        this.darkExtraLife = TextureMap.applyPalette(lightExtraLife,darkpalette);//image(prefix,"sprites/extraLife/darkExtraLife.png");
        TextureMap flowermap = new TextureMap(prefix+"sprites/flower.png");
        this.lightFlower1 = flowermap.getTile(0, 0);//image(prefix,"sprites/flower/lightFlower1.png");
        this.lightFlower2 = flowermap.getTile(1, 0);//image(prefix,"sprites/flower/lightFlower2.png");
        this.lightFlower3 = flowermap.getTile(2, 0);//image(prefix,"sprites/flower/lightFlower3.png");
        this.lightFlower4 = flowermap.getTile(3, 0);//image(prefix,"sprites/flower/lightFlower4.png");
        this.darkFlower1 = flowermap.getTile(0, 1);//image(prefix,"sprites/flower/darkFlower1.png");
        this.darkFlower2 = flowermap.getTile(1, 1);//image(prefix,"sprites/flower/darkFlower2.png");
        this.darkFlower3 = flowermap.getTile(2, 1);//image(prefix,"sprites/flower/darkFlower3.png");
        this.darkFlower4 = flowermap.getTile(3, 1);//image(prefix,"sprites/flower/darkFlower4.png");
        TextureMap goombas = new TextureMap(prefix+"sprites/goomba.png");
        this.lightGoomba1 = goombas.getTile(0, 0);//image(prefix,"sprites/goomba/lightMushroom1.png");
        this.lightGoomba2 = goombas.getTile(1, 0);//image(prefix,"sprites/goomba/lightMushroom2.png");
        this.lightGoomba3 = goombas.getTile(2, 0);//image(prefix,"sprites/goomba/lightMushroom3.png");
        this.lightGoomba4 = goombas.getTile(3, 0);//image(prefix,"sprites/goomba/lightMushroom4.png");
        this.darkGoomba1 = TextureMap.applyPalette(lightGoomba1, darkpalette);//image(prefix,"sprites/goomba/darkMushroom1.png");
        this.darkGoomba2 = TextureMap.applyPalette(lightGoomba2, darkpalette);//image(prefix,"sprites/goomba/darkMushroom2.png");
        this.darkGoomba3 = TextureMap.applyPalette(lightGoomba3, darkpalette);//image(prefix,"sprites/goomba/darkMushroom3.png");
        this.darkGoomba4 = TextureMap.applyPalette(lightGoomba4, darkpalette);//image(prefix,"sprites/goomba/darkMushroom4.png");
        this.grayGoomba1 = TextureMap.applyPalette(lightGoomba1, graypalette);//image(prefix,"sprites/goomba/grayMushroom1.png");
        this.grayGoomba2 = TextureMap.applyPalette(lightGoomba2, graypalette);//image(prefix,"sprites/goomba/grayMushroom2.png");
        this.grayGoomba3 = TextureMap.applyPalette(lightGoomba3, graypalette);//image(prefix,"sprites/goomba/grayMushroom3.png");
        this.grayGoomba4 = TextureMap.applyPalette(lightGoomba4, graypalette);//image(prefix,"sprites/goomba/grayMushroom4.png");
        TextureMap koopas = new TextureMap(prefix+"sprites/koopa.png");
        this.lightKoopa1 = koopas.getSubImage(0, 0, 16, 24);//image(prefix,"sprites/koopa/lightTurtle1.png");
        this.lightKoopa2 = koopas.getSubImage(1*16, 0, 16, 24);//image(prefix,"sprites/koopa/lightTurtle2.png");
        this.lightKoopa3 = koopas.getSubImage(2*16, 0, 16, 24);//image(prefix,"sprites/koopa/lightTurtle3.png");
        this.lightKoopa4 = koopas.getSubImage(3*16, 0, 16, 24);//image(prefix,"sprites/koopa/lightTurtle4.png");
        this.lightKoopa5 = koopas.getSubImage(4*16, 0, 16, 24);//image(prefix,"sprites/koopa/lightTurtle5.png");
        this.lightKoopa6 = koopas.getSubImage(5*16, 0, 16, 24);//image(prefix,"sprites/koopa/lightTurtle6.png");
        this.lightKoopa7 = koopas.getSubImage(6*16, 0, 16, 24);//image(prefix,"sprites/koopa/lightTurtle7.png");
        this.darkKoopa1 = TextureMap.applyPalette(lightKoopa1, darkpalette);//image(prefix,"sprites/koopa/darkTurtle1.png");
        this.darkKoopa2 = TextureMap.applyPalette(lightKoopa2, darkpalette);//image(prefix,"sprites/koopa/darkTurtle2.png");
        this.darkKoopa3 = TextureMap.applyPalette(lightKoopa3, darkpalette);//image(prefix,"sprites/koopa/darkTurtle3.png");
        this.darkKoopa4 = TextureMap.applyPalette(lightKoopa4, darkpalette);//image(prefix,"sprites/koopa/darkTurtle4.png");
        this.darkKoopa5 = TextureMap.applyPalette(lightKoopa5, darkpalette);//image(prefix,"sprites/koopa/darkTurtle5.png");
        this.darkKoopa6 = TextureMap.applyPalette(lightKoopa6, darkpalette);//image(prefix,"sprites/koopa/darkTurtle6.png");
        this.darkKoopa7 = TextureMap.applyPalette(lightKoopa7, darkpalette);//image(prefix,"sprites/koopa/darkTurtle7.png");
        this.redKoopa1 = TextureMap.applyPalette(lightKoopa1, prefix+"sprites/redkoopapalette.png");//image(prefix,"sprites/koopa/redTurtle1.png");
        this.redKoopa2 = TextureMap.applyPalette(lightKoopa2, prefix+"sprites/redkoopapalette.png");//image(prefix,"sprites/koopa/redTurtle2.png");
        this.redKoopa3 = TextureMap.applyPalette(lightKoopa3, prefix+"sprites/redkoopapalette.png");//image(prefix,"sprites/koopa/redTurtle3.png");
        this.redKoopa4 = TextureMap.applyPalette(lightKoopa4, prefix+"sprites/redkoopapalette.png");//image(prefix,"sprites/koopa/redTurtle4.png");
        this.redKoopa5 = TextureMap.applyPalette(lightKoopa5, prefix+"sprites/redkoopapalette.png");//image(prefix,"sprites/koopa/redTurtle5.png");
        this.redKoopa6 = TextureMap.applyPalette(lightKoopa6, prefix+"sprites/redkoopapalette.png");//image(prefix,"sprites/koopa/redTurtle6.png");
        this.redKoopa7 = TextureMap.applyPalette(lightKoopa7, prefix+"sprites/redkoopapalette.png");//image(prefix,"sprites/koopa/redTurtle7.png");
        TextureMap fireball = new TextureMap(prefix+"sprites/fireball.png");
        this.fireball1 = fireball.getSubImage(0*8,0,8,8);//image(prefix,"sprites/fireball/fireball1.png");
        this.fireball2 = fireball.getSubImage(1*8,0,8,8);//image(prefix,"sprites/fireball/fireball2.png");
        this.fireball3 = fireball.getSubImage(2*8,0,8,8);//image(prefix,"sprites/fireball/fireball3.png");
        this.fireball4 = fireball.getSubImage(3*8,0,8,8);//image(prefix,"sprites/fireball/fireball4.png");
        TextureMap lavaball = new TextureMap(prefix+"sprites/lavaball.png");
        this.lavaball1 = lavaball.getTile(0,0);//image(prefix,"sprites/lavaball/lavaball1.png");
        this.lavaball2 = lavaball.getTile(1,0);//image(prefix,"sprites/lavaball/lavaball2.png");
        this.springBaseLight = image(prefix,"sprites/springBase.png");
        this.springBaseDark = TextureMap.applyPalette(springBaseLight,darkpalette);//image(prefix,"sprites/spring/springBaseBlue.png");
        this.springBaseGray = TextureMap.applyPalette(springBaseLight,graypalette);//image(prefix,"sprites/spring/springBaseGray.png");
        this.springLight = image(prefix,"sprites/spring.png");
        this.springDark = TextureMap.applyPalette(springLight,darkpalette);//image(prefix,"sprites/spring/springBlue.png");
        this.springGray = TextureMap.applyPalette(springLight,graypalette);//image(prefix,"sprites/spring/springGray.png");
        this.springGreenLight = springLight;//image(prefix,"sprites/spring/springGreenLight.png");
        this.springGreenDark = springDark;//image(prefix,"sprites/spring/springGreenDark.png");
        this.springGreenGray = springGray;//image(prefix,"sprites/spring/springGreenStone.png");
        this.springBaseGreenLight = springBaseLight;//image(prefix,"sprites/spring/springBaseGreenLight.png");
        this.springBaseGreenDark = springBaseDark;//image(prefix,"sprites/spring/springBaseGreenDark.png");
        this.springBaseGreenGray = springBaseGray;//image(prefix,"sprites/spring/springBaseGreenStone.png");
        TextureMap spineys = new TextureMap(prefix+"sprites/spiny.png");
        this.spiny1 = spineys.getTile(0,0);//image(prefix,"sprites/spiny/spiny1.png");
        this.spiny2 = spineys.getTile(1,0);//image(prefix,"sprites/spiny/spiny2.png");
        this.spiny3 = spineys.getTile(2,0);//image(prefix,"sprites/spiny/spiny3.png");
        this.spiny4 = spineys.getTile(3,0);//image(prefix,"sprites/spiny/spiny4.png");
        TextureMap lakitu = new TextureMap(prefix+"sprites/lakitu.png");
        this.spinyThrower1 = lakitu.getSubImage(0, 0, 16, 24);//image(prefix,"sprites/spinyThrower/spinyThrower1.png");
        this.spinyThrower2 = lakitu.getSubImage(16, 0, 16, 24);//image(prefix,"sprites/spinyThrower/spinyThrower2.png");
        this.spinyThrower3 = lakitu.getSubImage(32, 0, 16, 24);//image(prefix,"sprites/spinyThrower/spinyThrower3.png");
        TextureMap beetles = new TextureMap(prefix+"sprites/beetle.png");
        this.lightBeetle1 = beetles.getTile(0,0);//image(prefix,"sprites/beetle/lightBeetle1.png");
        this.lightBeetle2 = beetles.getTile(1,0);//image(prefix,"sprites/beetle/lightBeetle2.png");
        this.lightBeetle3 = beetles.getTile(2,0);//image(prefix,"sprites/beetle/lightBeetle3.png");
        this.lightBeetle4 = beetles.getTile(3,0);//image(prefix,"sprites/beetle/lightBeetle4.png");
        this.darkBeetle1 = TextureMap.applyPalette(lightBeetle1, darkpalette);//image(prefix,"sprites/beetle/darkBeetle1.png");
        this.darkBeetle2 = TextureMap.applyPalette(lightBeetle2, darkpalette);//image(prefix,"sprites/beetle/darkBeetle2.png");
        this.darkBeetle3 = TextureMap.applyPalette(lightBeetle3, darkpalette);//image(prefix,"sprites/beetle/darkBeetle3.png");
        this.darkBeetle4 = TextureMap.applyPalette(lightBeetle4, darkpalette);//image(prefix,"sprites/beetle/darkBeetle4.png");
        this.grayBeetle1 = TextureMap.applyPalette(lightBeetle1, graypalette);//image(prefix,"sprites/beetle/grayBeetle1.png");
        this.grayBeetle2 = TextureMap.applyPalette(lightBeetle2, graypalette);//image(prefix,"sprites/beetle/grayBeetle2.png");
        this.grayBeetle3 = TextureMap.applyPalette(lightBeetle3, graypalette);//image(prefix,"sprites/beetle/grayBeetle3.png");
        this.grayBeetle4 = TextureMap.applyPalette(lightBeetle4, graypalette);//image(prefix,"sprites/beetle/grayBeetle4.png");
        this.lightChomper1 = image(prefix,"sprites/chomper1.png");
        this.lightChomper2 = image(prefix,"sprites/chomper2.png");
        this.lightChomper3 = image(prefix,"sprites/chomper3.png");
        this.lightChomper4 = image(prefix,"sprites/chomper4.png");
        this.darkChomper1 = TextureMap.applyPalette(lightChomper1, darkpalette);//image(prefix,"sprites/chomper/darkChomper1.png");
        this.darkChomper2 = TextureMap.applyPalette(lightChomper2, darkpalette);//image(prefix,"sprites/chomper/darkChomper2.png");
        this.darkChomper3 = TextureMap.applyPalette(lightChomper3, darkpalette);//image(prefix,"sprites/chomper/darkChomper3.png");
        this.darkChomper4 = TextureMap.applyPalette(lightChomper4, darkpalette);//image(prefix,"sprites/chomper/darkChomper4.png");
        this.redChomper1 = TextureMap.applyPalette(lightChomper1, graypalette);//image(prefix,"sprites/chomper/redChomper1.png");
        this.redChomper2 = TextureMap.applyPalette(lightChomper2, graypalette);//image(prefix,"sprites/chomper/redChomper2.png");
        this.redChomper3 = TextureMap.applyPalette(lightChomper3, graypalette);//image(prefix,"sprites/chomper/redChomper3.png");
        this.redChomper4 = TextureMap.applyPalette(lightChomper4, graypalette);//image(prefix,"sprites/chomper/redChomper4.png");
        this.lightHammerBro1 = image(prefix,"sprites/hammerBro/hammerBro1.png");
        this.lightHammerBro2 = image(prefix,"sprites/hammerBro/hammerBro2.png");
        this.lightHammerBro3 = image(prefix,"sprites/hammerBro/hammerBro3.png");
        this.lightHammerBro4 = image(prefix,"sprites/hammerBro/hammerBro4.png");
        this.lightHammerBro5 = image(prefix,"sprites/hammerBro/hammerBro5.png");
        this.darkHammerBro1 = TextureMap.applyPalette(lightHammerBro1, darkpalette);//image(prefix,"sprites/hammerBro/darkHammerBro1.png");
        this.darkHammerBro2 = TextureMap.applyPalette(lightHammerBro2, darkpalette);//image(prefix,"sprites/hammerBro/darkHammerBro2.png");
        this.darkHammerBro3 = TextureMap.applyPalette(lightHammerBro3, darkpalette);//image(prefix,"sprites/hammerBro/darkHammerBro3.png");
        this.darkHammerBro4 = TextureMap.applyPalette(lightHammerBro4, darkpalette);//image(prefix,"sprites/hammerBro/darkHammerBro4.png");
        this.darkHammerBro5 = TextureMap.applyPalette(lightHammerBro5, darkpalette);//image(prefix,"sprites/hammerBro/darkHammerBro5.png");
        TextureMap squid = new TextureMap(prefix+"sprites/squid.png");
        this.squid1 = squid.getSubImage(0,0,16,24);//image(prefix,"sprites/squid/squid1.png");
        this.squid2 = squid.getSubImage(16,0,16,24);//image(prefix,"sprites/squid/squid2.png");
        this.squid3 = squid.getSubImage(32,0,16,24);//image(prefix,"sprites/squid/squid3.png");
        TextureMap fishies = new TextureMap(prefix+"sprites/fish.png");
        this.redFish1 = fishies.getTile(0,0);//image(prefix,"sprites/redFish/redFish1.png");
        this.redFish2 = fishies.getTile(1,0);//image(prefix,"sprites/redFish/redFish2.png");
        this.redFish3 = fishies.getTile(2,0);//image(prefix,"sprites/redFish/redFish3.png");
        this.grayFish1 = TextureMap.applyPalette(redFish1, prefix+"sprites/grayfishpalette.png");//image(prefix,"sprites/grayFish/grayFish1.png");
        this.grayFish2 = TextureMap.applyPalette(redFish2, prefix+"sprites/grayfishpalette.png");//image(prefix,"sprites/grayFish/grayFish2.png");
        this.grayFish3 = TextureMap.applyPalette(redFish3, prefix+"sprites/grayfishpalette.png");//image(prefix,"sprites/grayFish/grayFish3.png");
        this.lightTallCannon = image(prefix,"sprites/tallCannon.png");
        this.lightCannonBase = image(prefix,"sprites/cannonBase.png");
        this.lightShortCannon = image(prefix,"sprites/shortCannon.png");
        this.lightBullet = image(prefix,"sprites/bullet.png");
        this.darkTallCannon = TextureMap.applyPalette(lightTallCannon,darkpalette);//image(prefix,"sprites/cannon/darkTallCannon.png");
        this.darkCannonBase = TextureMap.applyPalette(lightCannonBase,darkpalette);//image(prefix,"sprites/cannon/darkCannonBase.png");
        this.darkShortCannon = TextureMap.applyPalette(lightShortCannon,darkpalette);//image(prefix,"sprites/cannon/darkShortCannon.png");
        this.darkBullet = TextureMap.applyPalette(lightBullet,darkpalette);//image(prefix,"sprites/cannon/darkBullet.png");
        this.stoneTallCannon = TextureMap.applyPalette(lightTallCannon,graypalette);//image(prefix,"sprites/cannon/stoneTallCannon.png");
        this.stoneCannonBase = TextureMap.applyPalette(lightCannonBase,graypalette);//image(prefix,"sprites/cannon/stoneCannonBase.png");
        this.stoneShortCannon = TextureMap.applyPalette(lightShortCannon,graypalette);//image(prefix,"sprites/cannon/stoneShortCannon.png");
        this.stoneBullet = TextureMap.applyPalette(lightBullet,graypalette);//image(prefix,"sprites/cannon/stoneBullet.png");
        this.hammerBlack = image(prefix,"sprites/hammer.png");
        this.hammerGray = TextureMap.applyPalette(hammerBlack,graypalette);//;//image(prefix,"sprites/hammerGray.png");
        this.platformLong = image(prefix,"sprites/platforms/long.png");
        this.platformShort = image(prefix,"sprites/platforms/short.png");
        this.platformExtraShort = image(prefix,"sprites/platforms/extraShort.png");
        this.cloudCarrierLong = image(prefix,"sprites/platforms/cloudCarrierLong.png");
        this.cloudCarrierShort = image(prefix,"sprites/platforms/cloudCarrierShort.png");
        this.bowser1 = image(prefix,"sprites/bowser/bowser1.png");
        this.bowser2 = image(prefix,"sprites/bowser/bowser2.png");
        this.bowser3 = image(prefix,"sprites/bowser/bowser3.png");
        this.bowser4 = image(prefix,"sprites/bowser/bowser4.png");
        this.bowser5 = image(prefix,"sprites/bowser/bowser5.png");
        TextureMap flame = new TextureMap(prefix+"sprites/flame.png");
        this.flame1 = flame.getSubImage(0, 0, 24,8);//image(prefix,"sprites/flame/flame1.png");
        this.flame2 = flame.getSubImage(0, 8, 24,8);//image(prefix,"sprites/flame/flame2.png");
        this.pulley = image(prefix,"sprites/pulley.png");
        this.beanstalkTopLight = image(prefix,"sprites/beanstalkTop.png");
        this.beanstalkSectionLight = image(prefix,"sprites/beanstalkSection.png");
        this.entryVineLight = image(prefix,"sprites/entryVine.png");
        this.beanstalkTopDark = TextureMap.applyPalette(beanstalkTopLight, darkpalette);//image(prefix,"sprites/beanstalk/beanstalkTopDark.png");
        this.beanstalkSectionDark = TextureMap.applyPalette(beanstalkSectionLight, darkpalette);//image(prefix,"sprites/beanstalk/beanstalkSectionDark.png");
        this.entryVineDark = TextureMap.applyPalette(entryVineLight, darkpalette);//image(prefix,"sprites/beanstalk/entryVineDark.png");
    	}catch(MessageException e){
    		e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(0);
    	}
    }
    
    public void effectsInit(String prefix)  {
    	try{
        this.lightBrickPiece1 = image(prefix,"effects/brickPieces/lightBrickPiece1.png");
        this.lightBrickPiece2 = image(prefix,"effects/brickPieces/lightBrickPiece2.png");
        this.lightBrickPiece3 = image(prefix,"effects/brickPieces/lightBrickPiece3.png");
        this.lightBrickPiece4 = image(prefix,"effects/brickPieces/lightBrickPiece4.png");
        this.darkBrickPiece1 = image(prefix,"effects/brickPieces/darkBrickPiece1.png");
        this.darkBrickPiece2 = image(prefix,"effects/brickPieces/darkBrickPiece2.png");
        this.darkBrickPiece3 = image(prefix,"effects/brickPieces/darkBrickPiece3.png");
        this.darkBrickPiece4 = image(prefix,"effects/brickPieces/darkBrickPiece4.png");
        this.stoneBrickPiece1 = image(prefix,"effects/brickPieces/stoneBrickPiece1.png");
        this.stoneBrickPiece2 = image(prefix,"effects/brickPieces/stoneBrickPiece2.png");
        this.stoneBrickPiece3 = image(prefix,"effects/brickPieces/stoneBrickPiece3.png");
        this.stoneBrickPiece4 = image(prefix,"effects/brickPieces/stoneBrickPiece4.png");
        this.coinCollected1 = image(prefix,"effects/coinCollected/coinCollected1.png");
        this.coinCollected2 = image(prefix,"effects/coinCollected/coinCollected2.png");
        this.coinCollected3 = image(prefix,"effects/coinCollected/coinCollected3.png");
        this.coinCollected4 = image(prefix,"effects/coinCollected/coinCollected4.png");
        this.points100 = image(prefix,"effects/points/score100.png");
        this.points200 = image(prefix,"effects/points/score200.png");
        this.points400 = image(prefix,"effects/points/score400.png");
        this.points500 = image(prefix,"effects/points/score500.png");
        this.points800 = image(prefix,"effects/points/score800.png");
        this.points1000 = image(prefix,"effects/points/score1000.png");
        this.points2000 = image(prefix,"effects/points/score2000.png");
        this.points5000 = image(prefix,"effects/points/score5000.png");
        this.points8000 = image(prefix,"effects/points/score8000.png");
        this.checkpoint = image(prefix,"effects/points/checkPoint.png");
        this.oneUp = image(prefix,"effects/points/oneUp.png");
        this.firework1 = image(prefix,"effects/firework/firework1.png");
        this.firework2 = image(prefix,"effects/firework/firework2.png");
        this.firework3 = image(prefix,"effects/firework/firework3.png");
        this.airBubble = image(prefix,"effects/airBubble.png");
    	}catch(MessageException e){
    		e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(0);
    	}
    }
  
    private ImageIcon image(String prefix, String imageName)throws MessageException  {
      
    	BufferedImage img = null;
    	try{
    		img = ImageIO.read(new File(prefix+imageName));
    	}catch(IOException e){
    		if(prefix.equals("images/")){
    			throw new MessageException(e,"error reading images/"+imageName);
    		}else{
    			try{
    				img = ImageIO.read(new File("images/"+imageName));
    			}catch(IOException f){
    				f.printStackTrace();
    				throw new MessageException(f,"error reading images/"+imageName);
    			
    			}
    		}
    	}
    	//
    	ImageIcon image = new ImageIcon((BufferedImage)img);
    	if (image.getIconWidth() == -1) {
    		image = new ImageIcon("images/"+imageName);
    		if(image.getIconWidth() == -1){
    			this.validTextures = false;
    			throw new RuntimeException("Invalid image: " + imageName);
    		}
        }
    	return image;
    }
    private ImageIcon image(String prefix, String imageName, int expectedHeight) throws MessageException{
    	BufferedImage img = null;
    	try{
    		img = ImageIO.read(new File(prefix+imageName));
    	}catch(IOException e){
    		if(prefix.equals("images/")){
    			
    			throw new MessageException(e,"error reading images/"+imageName);
    			
    		}else{
    			try{
    				img = ImageIO.read(new File("images/"+imageName));
    			}catch(IOException f){
    				throw new MessageException(f,"error reading images/"+imageName);
    				
    			}
    		}
    	}
    	
    	ImageIcon image;
    	if(img.getHeight()>expectedHeight)
    		image = new AnimatedIcon(img,expectedHeight);
    	else
    		image = new ImageIcon(img);
    	if (image.getIconWidth() == -1) {
    		image = new ImageIcon("images/"+imageName);
    		if(image.getIconWidth() == -1){
    			this.validTextures = false;
    			throw new RuntimeException("Invalid image: " + imageName);
    		}
        }
    	return image;
    }
   
    public ImageIcon getLevelTypeAlt(int leveltype,ImageIcon icon){
    	
    	switch(leveltype){
    	case Level.LEVEL_TYPE_CASTLE:
    		return getCastleVariant(icon);
    	case Level.LEVEL_TYPE_OUTSIDE:case Level.LEVEL_TYPE_OUTSIDE_NIGHT:
    		return getLightVariant(icon);
    	case Level.LEVEL_TYPE_COIN_ZONE_DAY:case Level.LEVEL_TYPE_COIN_ZONE_NIGHT:
    		return getCoinZoneVariant(icon);
    	case Level.LEVEL_TYPE_UNDERGROUND:
    		return getDarkVariant(icon);
    	case Level.LEVEL_TYPE_UNDER_WATER:
    		return getUnderwaterVariant(icon);
    	default:
    		return icon;
    	}
    }
    public ImageIcon getGroundSide(ImageIcon imgicon,String side){
    	if(imgicon == this.lightGround){
    		switch(side){
    		case "topLeft": return this.lightGroundTopLeft;
    		case "topRight": return this.lightGroundTopRight;
    		case "top": return this.lightGroundTop;
    		case "left": return this.lightGroundLeft;
    		case "right": return this.lightGroundRight;
    		case "bottomLeft": return this.lightGroundBottomLeft;
    		case "bottomRight": return this.lightGroundBottomRight;
    		case "bottom": return this.lightGroundBottom;
    		default: return imgicon;
    		}
    	}else if(imgicon == this.darkGround){
    		switch(side){
    		case "topLeft": return this.darkGroundTopLeft;
    		case "topRight": return this.darkGroundTopRight;
    		case "top": return this.darkGroundTop;
    		case "left": return this.darkGroundLeft;
    		case "right": return this.darkGroundRight;
    		case "bottomLeft": return this.darkGroundBottomLeft;
    		case "bottomRight": return this.darkGroundBottomRight;
    		case "bottom": return this.darkGroundBottom;
    		default: return imgicon;
    		}
    	}else if(imgicon == this.stoneGround){
    		switch(side){
    		case "topLeft": return this.stoneGroundTopLeft;
    		case "topRight": return this.stoneGroundTopRight;
    		case "top": return this.stoneGroundTop;
    		case "left": return this.stoneGroundLeft;
    		case "right": return this.stoneGroundRight;
    		case "bottomLeft": return this.stoneGroundBottomLeft;
    		case "bottomRight": return this.stoneGroundBottomRight;
    		case "bottom": return this.stoneGroundBottom;
    		default: return imgicon;
    		}
    	}else if(imgicon == this.seaGround){
    		switch(side){
    		case "topLeft": return this.seaGroundTopLeft;
    		case "topRight": return this.seaGroundTopRight;
    		case "top": return this.seaGroundTop;
    		case "left": return this.seaGroundLeft;
    		case "right": return this.seaGroundRight;
    		case "bottomLeft": return this.seaGroundBottomLeft;
    		case "bottomRight": return this.seaGroundBottomRight;
    		case "bottom": return this.seaGroundBottom;
    		default: return imgicon;
    		}
    	}else if(imgicon == this.cloudGround){
    		switch(side){
    		case "topLeft": return this.cloudGroundTopLeft;
    		case "topRight": return this.cloudGroundTopRight;
    		case "top": return this.cloudGroundTop;
    		case "left": return this.cloudGroundLeft;
    		case "right": return this.cloudGroundRight;
    		case "bottomLeft": return this.cloudGroundBottomLeft;
    		case "bottomRight": return this.cloudGroundBottomRight;
    		case "bottom": return this.cloudGroundBottom;
    		default: return imgicon;
    		}
    	}else return imgicon;
    }
    public boolean isGroundTile(ImageIcon icon){
    	return isGroundTile(icon,"middle");
    }
    public boolean isGroundTile(ImageIcon icon,String side){
    	switch(side){
    	case "middle":return icon == this.lightGround || icon == this.darkGround || icon == this.seaGround || icon == this.stoneGround || icon == this.cloudGround;
    	case "topLeft": return icon == this.lightGroundTopLeft||icon == this.darkGroundTopLeft||icon == this.seaGroundTopLeft||icon == this.stoneGroundTopLeft||icon == this.cloudGroundTopLeft;
    	case "topRight": return icon == this.lightGroundTopRight||icon == this.darkGroundTopRight||icon == this.seaGroundTopRight||icon == this.stoneGroundTopRight||icon == this.cloudGroundTopRight;
    	case "top": return icon == this.lightGroundTop||icon == this.darkGroundTop||icon == this.seaGroundTop||icon == this.cloudGroundTop||icon == this.stoneGroundTop;
    	case "left": return icon == this.lightGroundLeft||icon == this.darkGroundLeft||icon == this.seaGroundLeft||icon == this.cloudGroundLeft||icon == this.stoneGroundLeft;
    	case "right": return icon == this.lightGroundRight||icon == this.darkGroundRight||icon == this.seaGroundRight||icon == this.cloudGroundRight||icon == this.stoneGroundRight;
    	case "bottomLeft": return icon == this.lightGroundBottomLeft||icon == this.darkGroundBottomLeft||icon == this.seaGroundBottomLeft||icon == this.stoneGroundBottomLeft||icon == this.cloudGroundBottomLeft;
    	case "bottomRight": return icon == this.lightGroundBottomRight||icon == this.darkGroundBottomRight||icon == this.seaGroundBottomRight||icon == this.stoneGroundBottomRight||icon == this.cloudGroundBottomRight;
    	case "bottom": return icon == this.lightGroundBottom||icon == this.darkGroundBottom||icon == this.seaGroundBottom||icon == this.stoneGroundBottom||icon == this.cloudGroundBottom;
    	default: return false;
    	}
    }
    public boolean isCoin(ImageIcon icon){
    	return icon == lightCoin || icon == darkCoin || icon == stoneCoin || icon == seaCoin;
    }
    public boolean isBrick(ImageIcon icon){
    	return icon == this.lightBrick || icon == this.darkBrick|| icon == this.stoneBrick || icon == this.seaBrick;
    }
    public boolean isBlock(ImageIcon icon){
    	return icon == this.lightBlock || icon == this.darkBlock || icon == this.stoneBlock || icon == this.seaBlock;
    }
    public ImageIcon getLightVariant(ImageIcon icon){
    	if(icon == this.entryVineDark)return this.entryVineLight;
    	if(isCoin(icon))return this.lightCoin;
    	if(isBrick(icon))return this.lightBrick;
    	if(isBlock(icon))return this.lightBlock;
    	if(isGroundTile(icon))return this.lightGround;
    	if(isGroundTile(icon,"topLeft"))return this.lightGroundTopLeft;
    	if(isGroundTile(icon,"topRight"))return this.lightGroundTopRight;
    	if(isGroundTile(icon,"top"))return this.lightGroundTop;
    	if(isGroundTile(icon,"left"))return this.lightGroundLeft;
    	if(isGroundTile(icon,"right"))return this.lightGroundRight;
    	if(isGroundTile(icon,"bottomLeft"))return this.lightGroundBottomLeft;
    	if(isGroundTile(icon,"bottomRight"))return this.lightGroundBottomRight;
    	if(isGroundTile(icon,"bottom"))return this.lightGroundBottom;
    	return icon;
    }
    public ImageIcon getDarkVariant(ImageIcon icon){
    	if(icon == this.entryVineLight)return this.entryVineDark;
    	if(isCoin(icon))return this.darkCoin;
    	if(isBrick(icon))return this.darkBrick;
    	if(isBlock(icon))return this.darkBlock;
    	if(isGroundTile(icon))return this.darkGround;
    	if(isGroundTile(icon,"topLeft"))return this.darkGroundTopLeft;
    	if(isGroundTile(icon,"topRight"))return this.darkGroundTopRight;
    	if(isGroundTile(icon,"top"))return this.darkGroundTop;
    	if(isGroundTile(icon,"left"))return this.darkGroundLeft;
    	if(isGroundTile(icon,"right"))return this.darkGroundRight;
    	if(isGroundTile(icon,"bottomLeft"))return this.darkGroundBottomLeft;
    	if(isGroundTile(icon,"bottomRight"))return this.darkGroundBottomRight;
    	if(isGroundTile(icon,"bottom"))return this.darkGroundBottom;
    	return icon;
    }
    public ImageIcon getCastleVariant(ImageIcon icon){
    	if(icon == this.entryVineLight)return this.entryVineDark;
    	if(isCoin(icon))return this.stoneCoin;
    	if(isBrick(icon))return this.stoneBrick;
    	if(isBlock(icon))return this.stoneBlock;
    	if(isGroundTile(icon))return this.stoneGround;
    	if(isGroundTile(icon,"topLeft"))return this.stoneGroundTopLeft;
    	if(isGroundTile(icon,"topRight"))return this.stoneGroundTopRight;
    	if(isGroundTile(icon,"top"))return this.stoneGroundTop;
    	if(isGroundTile(icon,"left"))return this.stoneGroundLeft;
    	if(isGroundTile(icon,"right"))return this.stoneGroundRight;
    	if(isGroundTile(icon,"bottomLeft"))return this.stoneGroundBottomLeft;
    	if(isGroundTile(icon,"bottomRight"))return this.stoneGroundBottomRight;
    	if(isGroundTile(icon,"bottom"))return this.stoneGroundBottom;
    	return icon;
    }
    public ImageIcon getUnderwaterVariant(ImageIcon icon){
    	if(isCoin(icon))return this.seaCoin;
    	if(isBrick(icon))return this.seaBrick;
    	if(isBlock(icon))return this.seaBlock;
    	if(isGroundTile(icon))return this.seaGround;
    	if(isGroundTile(icon,"topLeft"))return this.seaGroundTopLeft;
    	if(isGroundTile(icon,"topRight"))return this.seaGroundTopRight;
    	if(isGroundTile(icon,"top"))return this.seaGroundTop;
    	if(isGroundTile(icon,"left"))return this.seaGroundLeft;
    	if(isGroundTile(icon,"right"))return this.seaGroundRight;
    	if(isGroundTile(icon,"bottomLeft"))return this.seaGroundBottomLeft;
    	if(isGroundTile(icon,"bottomRight"))return this.seaGroundBottomRight;
    	if(isGroundTile(icon,"bottom"))return this.seaGroundBottom;
    	return getLightVariant(icon);
    }
    public ImageIcon getCoinZoneVariant(ImageIcon icon){
    	if(isGroundTile(icon))return this.cloudGround;
    	if(isGroundTile(icon,"topLeft"))return this.cloudGroundTopLeft;
    	if(isGroundTile(icon,"topRight"))return this.cloudGroundTopRight;
    	if(isGroundTile(icon,"top"))return this.cloudGroundTop;
    	if(isGroundTile(icon,"left"))return this.cloudGroundLeft;
    	if(isGroundTile(icon,"right"))return this.cloudGroundRight;
    	if(isGroundTile(icon,"bottomLeft"))return this.cloudGroundBottomLeft;
    	if(isGroundTile(icon,"bottomRight"))return this.cloudGroundBottomRight;
    	if(isGroundTile(icon,"bottom"))return this.cloudGroundBottom;
    	return getLightVariant(icon);
    }
    public ImageIcon getGhostHouseVariant(ImageIcon icon){
    	
    }
}

class MessageException extends Exception{
	private static final long serialVersionUID = 3248878419786597659L;
	private Exception ex;
	private String message;
	public MessageException(Exception e, String msg){
		this.ex = e;
		this.message = msg;
	}
	/*public Throwable fillInStackTrace(){
		return ex.fillInStackTrace();
	}*/
	public Throwable getCause(){
		return ex.getCause();
	}
	public String getLocalizedMessage(){
		return ex.getLocalizedMessage();
	}
	public String getMessage(){
		return message;
	}
	public Exception getException(){
		return ex;
	}
	public StackTraceElement[] getStackTrace(){
		return ex.getStackTrace();
	}
	public Throwable initCause(Throwable cause){
		return ex.initCause(cause);
	}
	public void printStackTrace(){
		ex.printStackTrace();
	}
	public void printStackTrace(PrintStream s){
		ex.printStackTrace(s);
	}
	public void printStackTrace(PrintWriter s){
		ex.printStackTrace(s);
	}
	public void setStackTrace(StackTraceElement[] trace){
		ex.setStackTrace(trace);
	}
	public String toString(){
		return message+" :: "+ex.toString();
	}
}