import java.awt.*;
Import StdDraw.java;
public class Album {
private Picture original; // object of Picture class
private Picture collage;
private int collageDimension; // The collage Picture consists of collageDimension X collageDimension tiles
private int tileDimension; // A tile consists of tileDimension X tileDimension pixels

public Album (String filename) {
    this.collageDimension=4;
    this.tileDimension = 100;
    this.original = new Picture(filename);
    this.collage = new Picture((tileDimension*collageDimension), (tileDimension*collageDimension)); 

    for (int col = 0; col < (tileDimension*collageDimension),;col++) {
            for (int row = 0; row < (tileDimension*collageDimension); row++) {
                int originalCol = col * original.width() / (tileDimension*collageDimension);
                int originalRow = row * original.height() / (tileDimension*collageDimension);
                Color color = original.get(originalCol, originalRow);
                collage.set(col, row, color);
            }
     }
}

public int getCollageDimension() {
  return collageDimension;
}

public int getTileDimension() {
  return tileDimension;
}

public Picture getOriginalPicture() {
  return original;
}

public Picture getCollagePicture() {
  return collage;
}

public void showOriginalPicture() {
  original.show();
}

public void showCollagePicture() {
  collage.show();
}

//Replaces the tile at collageCol,collageRow with the image from filename

public void replaceTile (String filename, int collageCol, int collageRow) {
     Picture ori = new Picture(filename);
     Picture newP = new Picture(tileDimension, tileDimension);
     for (int col = 0; col < tileDimension; col++) {
       for (int row = 0; row < tileDimension; row++) {
       int oc = col * ori.width() / tileDimension;
       int or = row * ori.height() /tileDimension;
       Color color = ori.get(oc, or);
       newP.set(col, row, color);
        }
     }
     for (int r = 0; r < collage.height()/tileDimension; r++){
       for (int c =0; c < collage.height()/tileDimension; c++){
          if (c == collageCol && r == collageRow){
             for (int r1 = 0; r1 < newP.height(); r1++) {
                for (int c1 = 0; c1 < newP.height(); c1++) {
                   Color color = newP.get(r1, c1);
                   collage.set(r1+(c*tileDimension), c1+(r*tileDimension), color);
                 }
              }
           }
       }
     }
}
/*
* Makes a collage of tiles from original Picture
* original will have collageDimension x collageDimension tiles, each tile
* has tileDimension X tileDimension pixels
*/
public void makeCollage () {
      Picture scale = new Picture(tileDimension, tileDimension);
      for (int col = 0; col < tileDimension; col++) {
        for (int row = 0; row < tileDimension; row++) {
          int oc = col * original.width() / tileDimension;
          int or = row * original.height() /tileDimension;
          Color color = original.get(oc, or);
          scale.set(col, row, color);
        }
      }
      int w =scale.width();
      int h = scale.height();
      for (int col = 0; col < w; col++) {
        for (int row = 0; row < h; row++) {
          for (int i = 0; i < collageDimension; i++) {
            for (int j = 0; j < collageDimension; j++) {
            collage.set(w*i+col, h*j+row, scale.get(col, row));
            }
          }
        }
      }
}

public void colorizeTile (String component, int collageCol, int collageRow) {
      for (int c = tileDimension*collageCol; c < tileDimension*(collageCol+1); c++) {
        for (int r = tileDimension*collageRow; r < tileDimension*(collageRow+1); r++) {
          Color color1 = collage.get(c,r);
          if (component == "green"){
          int g = color1.getGreen();
          Color color = new Color (0,g, 0);
          collage.set(r,c, color);
          }
          else if (component == "red"){
          int r1 = color1.getRed();
          Color color = new Color (r1,0,0);
          collage.set(r,c, color);
          }
          else if (component == "blue"){
          int b = color1.getBlue();
          Color color = new Color (0,0, b);
          collage.set(c,r, color);
          }
        }
      }
}

public void greyscaleTile (int collageCol, int collageRow) {
      for (int r = tileDimension*collageRow; r < tileDimension*(collageRow+1); r++) {
        for (int c = tileDimension*collageCol; c < tileDimension*(collageCol+1); c++) {
          Color color = collage.get(c, r);
          Color gray = Luminance.toGray(color);
          collage.set(c, r, gray);
         }
       }
}

public static void main (String[] args) {
      ArtCollage art = new ArtCollage("Ariel.jpg", 200, 3);
      art.makeCollage();
      //art.colorizeTile("blue",1,3);
      art.greyscaleTile(2,0);
      art.showCollagePicture()
}
}
