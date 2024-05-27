public class Color {

    int red, green, blue, rgb;
    public Color() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    public void setRGB(int rgb) {
        this.rgb = rgb;
    }
    public int getRed() {
        return red;
    }
    public void setRed(int red) {
        this.red = red;
    }
    public int getGreen() {
        return green;
    }
    public void setGreen(int green) {
        this.green = green;
    }
    public int getBlue() {
        return blue;
    }
    public void setBlue(int blue) {
        this.blue = blue;
    }
    public int rgbToInteger(int red, int green, int blue){
        int rgbval = (red << 16) | (green << 8) | blue;
        return rgbval;
    }
    public int getRGB(){
        return rgb;
    }
    public void setRGB(){
        this.rgb = (red << 16) | (green << 8) | blue;
    }
    public int darken(int factor){
        int newred = Math.max(0, red - factor);
        int newgreen = Math.max(0, green - factor);
        int newblue = Math.max(0, blue - factor);
        return (newred << 16) | (newgreen << 8) | newblue;

    }
    public int lighten(int factor){
        int newred = Math.min(255, red + factor);
        int newgreen = Math.min(255, green + factor);
        int newblue = Math.min(255, blue + factor);
        return (newred << 16) | (newgreen << 8) | newblue;
    }
}
