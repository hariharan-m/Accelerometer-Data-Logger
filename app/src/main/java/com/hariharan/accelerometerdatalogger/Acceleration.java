package com.hariharan.accelerometerdatalogger;

/**
 * Created by hariharan on 21/6/15.
 */
public class Acceleration {
    float x;
    float y;
    float z;
    public void setAcc(float x,float y,float z)
    {
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public float getZ()
    {
        return z;
    }

}
