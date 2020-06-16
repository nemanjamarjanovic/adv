package view.chart;

import java.util.Date;

public class AxisStackValue
{

    private final Double ylb, yub;
    private final Date xlb, xub;

    public AxisStackValue(Double ylb, Double yub, Date xlb, Date xub)
    {
        this.ylb = ylb;
        this.yub = yub;
        this.xlb = xlb;
        this.xub = xub;
    }

    public Double getYlb()
    {
        return ylb;
    }

    public Double getYub()
    {
        return yub;
    }

    public Date getXlb()
    {
        return xlb;
    }

    public Date getXub()
    {
        return xub;
    }

}
