package enums;

public enum TokenType {
    RED((short)1), YELLOW((short)2), NONE((short)0);
    
    short val;
    
    TokenType(short i) { this.val = i; }
    
    public short getVal() { return val; }
}
