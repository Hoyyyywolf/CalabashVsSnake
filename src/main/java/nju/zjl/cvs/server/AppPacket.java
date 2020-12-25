package nju.zjl.cvs.server;

import java.io.Serializable;

import nju.zjl.cvs.game.Operation;

public class AppPacket implements Serializable{
    public AppPacket(int type, int logicFrame, Operation[] payload1, Operation[] payload2, Operation[] payload3) {
        this.type = type;
        this.logicFrame = logicFrame;
        this.payload1 = payload1;
        this.payload2 = payload2;
        this.payload3 = payload3;
    }

    protected AppPacket(){
        type = -1;
        logicFrame = -1;
        payload1 = null;
        payload2 = null;
        payload3 = null;
    }

    public final int type;
    public final int logicFrame;
    public final Operation[] payload1;
    public final Operation[] payload2;
    public final Operation[] payload3;

    private static final long serialVersionUID = -2585837168358002521L;
}
