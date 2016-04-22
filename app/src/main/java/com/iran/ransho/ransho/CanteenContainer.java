package com.iran.ransho.ransho;

/**
 * Created by Admin on 19/04/2016.
 */
public class CanteenContainer {
    private String name;
    private int rate;
    public CanteenContainer(String _name, int _rate)
    {
        name = _name;
        rate = _rate;
    }

    public String getName()
    {
        return name;
    }

    public int getRatingStar()
    {
        return rate;
    }

    public void setName(String _name)
    {
        name = _name;
    }

    public void setRatingStar(int _rate)
    {
        rate = _rate;
    }
}
