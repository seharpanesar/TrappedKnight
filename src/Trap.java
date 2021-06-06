public class Trap {
    int trapValue = 0;
    int trapDepth = 0;

    public Trap(int trapValue, int trapDepth) {
        this.trapValue = trapValue;
        this.trapDepth = trapDepth;
    }

    @Override
    public String toString() {
        return "Value: " + trapValue + " + Depth: " + trapDepth;
    }
}
