/*
 * Decompiled with CFR 0_102.
 */
package supermario.builder;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import supermario.builder.Textures;

public class ImageBuilder {
    public static supermario.game.Textures textures;
    public static Textures bTextures;
    private static final int BLOCK_SIZE = 16;
    private static final int TILE_SIZE = 8;
    public static final int LIGHT = 0;
    public static final int DARK = 1;
    public static final int GREY = 2;
    public static final int LIGHT_BRICK = 0;
    public static final int DARK_BRICK = 1;
    public static final int GREY_BRICK = 2;
    public static final int QUESTION = 3;
    public static final int INVISIBLE = 4;
    public static final int POWERUP = 0;
    public static final int LIFE = 1;
    public static final int STAR = 2;
    public static final int COIN = 3;
    public static final int COINS = 4;
    public static final int BEANSTALK = 5;

    public static ImageIcon createBlockImage(int container, int shadowColor, int contents) {
        boolean tall = contents == 0 || contents == 1 || contents == 2;
        int height = tall ? 32 : 16;
        int solidStart = tall ? 16 : 0;
        BufferedImage image = new BufferedImage(16, height, 6);
        Graphics2D g2D = (Graphics2D)image.getGraphics();
        Image solidPart = ImageBuilder.getSolidPart(container, shadowColor);
        Image contentsPart = ImageBuilder.getContentsPart(container, contents, shadowColor);
        g2D.drawImage(solidPart, 0, solidStart, 16, height, 0, 0, 16, 16, null);
        g2D.drawImage(contentsPart, 0, 0, 16, 16, 0, 0, 16, 16, null);
        return new ImageIcon(image);
    }

    public static ImageIcon createPipeImage(int pipeType, int pipeColor, int shadowType, boolean redPiranha) {
        boolean square = pipeType == 2 || pipeType == 3;
        BufferedImage image = new BufferedImage(square ? 32 : 24, 32, 6);
        Graphics2D g2D = (Graphics2D)image.getGraphics();
        ImageIcon pipe = null;
        if (pipeColor == 1) {
            pipe = ImageBuilder.textures.greenPipes[pipeType];
        } else if (pipeColor == 3) {
            pipe = ImageBuilder.textures.bluePipes[pipeType];
        } else if (pipeColor == 4) {
            pipe = ImageBuilder.textures.orangePipes[pipeType];
        } else if (pipeColor == 2) {
            pipe = ImageBuilder.textures.whitePipes[pipeType];
        } else {
            throw new IllegalStateException("Pipe color or type is not valid. Pipe Color = " + pipeColor + ", Pipe Type = " + pipeType);
        }
        g2D.drawImage(pipe.getImage(), 0, 0, null);
        ImageIcon piranha = null;
        if (redPiranha) {
            piranha = ImageBuilder.bTextures.redPiranhas[pipeType];
        } else if (shadowType == 0) {
            piranha = ImageBuilder.bTextures.lightPiranhas[pipeType];
        } else if (shadowType == 1 || shadowType == 2) {
            piranha = ImageBuilder.bTextures.darkPiranhas[pipeType];
        } else {
            throw new IllegalStateException("Pipe shadow type is not valid. Shadow Type = " + shadowType);
        }
        int y = 0;
        int x = 0;
        if (pipeType == 2) {
            x = image.getWidth() / 2 - piranha.getIconWidth() / 2;
            y = 0;
        } else if (pipeType == 0 || pipeType == 1) {
            x = 0;
            y = image.getHeight() / 2 - piranha.getIconHeight() / 2;
        } else if (pipeType == 3) {
            x = image.getWidth() / 2 - piranha.getIconWidth() / 2;
            y = image.getHeight() - piranha.getIconHeight();
        } else {
            throw new IllegalStateException("Pipe type must be a piranha kind. Pipe Type = " + pipeType);
        }
        g2D.drawImage(piranha.getImage(), x, y, null);
        return new ImageIcon(image);
    }

    private static Image getSolidPart(int container, int shadowColor) {
        if (container == 0||container == 1||container == 2) {
            return ((supermario.game.MultiIcon)ImageBuilder.textures.lightBrick).getImage(shadowColor);
        }
        /*if (container == 1) {
            return ImageBuilder.textures.darkBrick.getImage();
        }
        if (container == 2) {
            return ImageBuilder.textures.stoneBrick.getImage();
        }*/
        if (container == 3) {
           // if (shadowColor == 0) {
                return ((supermario.game.MultiIcon)ImageBuilder.textures.lightQuestionBox).getImage(shadowColor);
            /*}
            if (shadowColor == 1) {
                return ImageBuilder.textures.darkQuestionBox.getImage();
            }
            if (shadowColor == 2) {
                return ImageBuilder.textures.stoneQuestionBox.getImage();
            }*/
        } else if (container == 4) {
            return ImageBuilder.bTextures.questionBoxInvisible.getImage();
        }
        throw new RuntimeException("Solid part of image not found: container=" + container + ", shadowColor=" + shadowColor);
    }

    private static Image getContentsPart(int container, int contents, int shadowColor) {
        if (contents == 0) {
            return ImageBuilder.getPowerupSplice(shadowColor);
        }
        if (contents == 1) {
            if (shadowColor == 0) {
                return ImageBuilder.textures.lightExtraLife.getImage();
            }
            if (shadowColor == 1) {
                return ImageBuilder.textures.darkExtraLife.getImage();
            }
            if (shadowColor == 2) {
                return ImageBuilder.textures.lightExtraLife.getImage();
            }
        } else {
            if (contents == 2) {
                return ImageBuilder.textures.star1.getImage();
            }
            if (contents == 3) {
                if (shadowColor == 0) {
                    if (container == 3) {
                        return ImageBuilder.bTextures.lightCoinBorderLeft.getImage();
                    }
                    return ImageBuilder.bTextures.lightCoin.getImage();
                }
                if (shadowColor == 1) {
                    if (container == 1) {
                        return ImageBuilder.bTextures.darkCoinBorder.getImage();
                    }
                    if (container == 3) {
                        return ImageBuilder.bTextures.darkCoinBorderLeft.getImage();
                    }
                    return ImageBuilder.bTextures.darkCoin.getImage();
                }
                if (shadowColor == 2) {
                    if (container == 3) {
                        return ImageBuilder.bTextures.stoneCoinBorderLeft.getImage();
                    }
                    return ImageBuilder.bTextures.stoneCoin.getImage();
                }
            } else if (contents == 4) {
                if (shadowColor == 0) {
                    return ImageBuilder.bTextures.coinsLight.getImage();
                }
                if (shadowColor == 1 || shadowColor == 2) {
                    if (container == 1) {
                        return ImageBuilder.bTextures.coinsDarkBorder.getImage();
                    }
                    return ImageBuilder.bTextures.coinsDark.getImage();
                }
            } else if (contents == 5) {
                if (shadowColor == 0) {
                    return ImageBuilder.bTextures.beanstalkLightIcon.getImage();
                }
                if (shadowColor == 1 || shadowColor == 2) {
                    if (container == 1) {
                        return ImageBuilder.bTextures.beanstalkDarkIconBorder.getImage();
                    }
                    return ImageBuilder.bTextures.beanstalkDarkIcon.getImage();
                }
            }
        }
        throw new RuntimeException("Contents part of image not found: contents=" + contents + ", shadowColor=" + shadowColor);
    }

    private static Image getPowerupSplice(int shadowColor) {
        BufferedImage image = new BufferedImage(16, 16, 6);
        Graphics2D g2D = (Graphics2D)image.getGraphics();
        if (shadowColor == 0) {
            ImageBuilder.renderSplice(g2D, ImageBuilder.textures.lightFlower1.getImage());
        } else if (shadowColor == 1) {
            ImageBuilder.renderSplice(g2D, ImageBuilder.textures.darkFlower1.getImage());
        } else if (shadowColor == 2) {
            ImageBuilder.renderSplice(g2D, ImageBuilder.textures.lightFlower1.getImage());
        }
        return image;
    }

    private static void renderSplice(Graphics2D g2D, Image flowerImage) {
        g2D.drawImage(ImageBuilder.textures.growMushroom.getImage(), 0, 0, 8, 16, 0, 0, 8, 16, null);
        g2D.drawImage(flowerImage, 8, 0, 16, 16, 8, 0, 16, 16, null);
    }
}

