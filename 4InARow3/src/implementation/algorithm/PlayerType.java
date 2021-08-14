package implementation.algorithm;


public enum PlayerType {
    HUMAN(false),
    RANDOM(true),
    MINIMAX(true);
    
    private final boolean isComputer;
    
    PlayerType(boolean isComputer) { this.isComputer = isComputer; }
    
    public boolean isComputerPlayer() { return isComputer; }
}
