package org.stormroboticsnj.stormappmaster2019.db;

public class DeepSpace {
    private int teamNum = 0; //string
    private int matchNum = 0; //string
    private int alliance = 0; //0 = red; 1 =blue

    //autonomous
    private int startingPosition = 1;
    private int passAutoLine = 0;
    private int autoHatches = 0;
    private int autoCargo = 0;

    //cargo
    private int cargoRT = 0;
    private int cargoRD = 0;
    private int cargoRU = 0;
    private int cargoShip = 0;
    private int cargoPlayer = 0;
    private int cargoGround = 0;

    //hatch
    private int hatchRT = 0;
    private int hatchRD = 0;
    private int hatchRU = 0;
    private int hatchShip = 0;
    private int hatchPlayer = 0;
    private int hatchGround = 0;

    //endgame
    private int selfLevel = 0;
    private int assistLevel = 0;
    private int assistTwoLevel = 0;
    private int specialCases = 0;

    private String data = ""; //makes some fragments easier; may not have to included in future

    protected DeepSpace(){};

    //team number, match number, alliance
    public DeepSpace(int p_teamNum, int p_matchNum, int p_alliance){
        this.teamNum = p_teamNum;
        this.matchNum = p_matchNum;
        this.alliance = p_alliance;
    }
    public DeepSpace (String[] temp){
        this.teamNum = Integer.parseInt(temp[0]);
        this.matchNum = Integer.parseInt(temp[1]);
        this.alliance = Integer.parseInt(temp[2]);
        this.startingPosition = Integer.parseInt(temp[3]);
        this.passAutoLine = Integer.parseInt(temp[4]);
        this.autoHatches = Integer.parseInt(temp[5]);
        this.autoCargo = Integer.parseInt(temp[6]);
        this.cargoRT = Integer.parseInt(temp[7]);
        this.cargoRD = Integer.parseInt(temp[8]);
        this.cargoRU = Integer.parseInt(temp[9]);
        this.cargoShip = Integer.parseInt(temp[10]);
        this.cargoPlayer = Integer.parseInt(temp[11]);
        this.cargoGround = Integer.parseInt(temp[12]);
        this.hatchRT = Integer.parseInt(temp[13]);
        this.hatchRD = Integer.parseInt(temp[14]);
        this.hatchRU = Integer.parseInt(temp[15]);
        this.hatchShip = Integer.parseInt(temp[16]);
        this.hatchPlayer = Integer.parseInt(temp[17]);
        this.hatchGround = Integer.parseInt(temp[18]);
        this.selfLevel = Integer.parseInt(temp[19]);
        this.assistLevel = Integer.parseInt(temp[20]);
        this.assistTwoLevel = Integer.parseInt(temp[21]);
        this.specialCases = Integer.parseInt(temp[22]);
    }

    //general information
    public int getTeamNum() {
        return teamNum;
    }
    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    public int getMatchNum() {
        return matchNum;
    }
    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }

    public int getAlliance() {return alliance;}
    public void setAlliance(int alliance) {
        this.alliance = alliance;
    }

    //autonomous
    public int getStartingPosition() {return startingPosition;}
    public void setStartingPosition(int startingPosition) {this.startingPosition = startingPosition;}

    public int getPassAutoLine() {return passAutoLine;}
    public void setPassAutoLine(int passAutoLine) {this.passAutoLine = passAutoLine;}

    public int getAutoHatches() {return autoHatches;}
    public void setAutoHatches(int autoHatches) {this.autoHatches = autoHatches;}

    public int getAutoCargo() {return autoCargo;}
    public void setAutoCargo(int autoCargo) {this.autoCargo = autoCargo;}

    //cargo
    public int getCargoRT() {return cargoRT;}
    public void setCargoRT(int cargoRT) {this.cargoRT = cargoRT;}

    public int getCargoRD() {return cargoRD;}
    public void setCargoRD(int cargoRD) {this.cargoRD = cargoRD;}

    public int getCargoRU() {return cargoRU;}
    public void setCargoRU(int cargoRU) {this.cargoRU = cargoRU;}

    public int getCargoShip() {return cargoShip;}
    public void setCargoShip(int cargoShip) {this.cargoShip = cargoShip;}

    public int getCargoPlayer() {return cargoPlayer;}
    public void setCargoPlayer(int cargoPlayer) {this.cargoPlayer = cargoPlayer;}

    public int getCargoGround() {return cargoGround;}
    public void setCargoGround(int cargoGround) {this.cargoGround = cargoGround;}

    //hatch
    public int getHatchRT() {return hatchRT;}
    public void setHatchRT(int hatchRT) {this.hatchRT = hatchRT;}

    public int getHatchRD() {return hatchRD;}
    public void setHatchRD(int hatchRD) {this.hatchRD = hatchRD;}

    public int getHatchRU() {return hatchRU;}
    public void setHatchRU(int hatchRU) {this.hatchRU = hatchRU;}

    public int getHatchShip() {return hatchShip;}
    public void setHatchShip(int hatchShip) {this.hatchShip = hatchShip;}

    public int getHatchPlayer() {return hatchPlayer;}
    public void setHatchPlayer(int hatchPlayer) {this.hatchPlayer = hatchPlayer;}

    public int getHatchGround() {return hatchGround;}
    public void setHatchGround(int hatchGround) {this.hatchGround = hatchGround;}

    //endgame
    public int getSelfLevel() {return selfLevel;}
    public void setSelfLevel(int selfLevel) {this.selfLevel = selfLevel;}

    public int getAssistLevel() {return assistLevel;}
    public void setAssistLevel(int assistLevel) {this.assistLevel = assistLevel;}

    public int getAssistTwoLevel() {return assistTwoLevel;}
    public void setAssistTwoLevel(int assistTwoLevel) {this.assistTwoLevel = assistTwoLevel;}

    public int getSpecialCases() {return specialCases;}
    public void setSpecialCases(int specialCases) {this.specialCases = specialCases;}

    public void setData(String data){
        this.data = data;
    }
    public String getData() {return data;}
    //output data as string
    public String toString() {
        return teamNum +
                "\t" + matchNum +
                "\t" + alliance +
                "\t" + startingPosition +
                "\t" + passAutoLine +
                "\t" + autoHatches +
                "\t" + autoCargo +
                "\t" + cargoRT +
                "\t" + cargoRD +
                "\t" + cargoRU +
                "\t" + cargoShip +
                "\t" + cargoPlayer +
                "\t" + cargoGround +
                "\t" + hatchRT +
                "\t" + hatchRD +
                "\t" + hatchRU +
                "\t" + hatchShip +
                "\t" + hatchPlayer +
                "\t" + hatchGround +
                "\t" + selfLevel +
                "\t" + assistLevel +
                "\t" + assistTwoLevel +
                "\t" + specialCases +
                '|';
    }
}