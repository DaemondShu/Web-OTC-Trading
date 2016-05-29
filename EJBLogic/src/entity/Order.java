package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by monkey_d_asce on 16-5-29.
 */
@Entity
public class Order
{
    private int id;
    private byte isSell;
    private String status;
    private String type;
    private String time;
    private String condition;
    private Integer expectedVol;
    private int surplusVol;
    private Double price;

    @Id
    @Column(name = "id", nullable = false)
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "isSell", nullable = false)
    public byte getIsSell()
    {
        return isSell;
    }

    public void setIsSell(byte isSell)
    {
        this.isSell = isSell;
    }

    @Basic
    @Column(name = "status", nullable = false, length = 45)
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 45)
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Basic
    @Column(name = "time", nullable = true, length = 45)
    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    @Basic
    @Column(name = "condition", nullable = true, length = 45)
    public String getCondition()
    {
        return condition;
    }

    public void setCondition(String condition)
    {
        this.condition = condition;
    }

    @Basic
    @Column(name = "expectedVol", nullable = true)
    public Integer getExpectedVol()
    {
        return expectedVol;
    }

    public void setExpectedVol(Integer expectedVol)
    {
        this.expectedVol = expectedVol;
    }

    @Basic
    @Column(name = "surplusVol", nullable = false)
    public int getSurplusVol()
    {
        return surplusVol;
    }

    public void setSurplusVol(int surplusVol)
    {
        this.surplusVol = surplusVol;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 0)
    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (isSell != order.isSell) return false;
        if (surplusVol != order.surplusVol) return false;
        if (status != null ? !status.equals(order.status) : order.status != null) return false;
        if (type != null ? !type.equals(order.type) : order.type != null) return false;
        if (time != null ? !time.equals(order.time) : order.time != null) return false;
        if (condition != null ? !condition.equals(order.condition) : order.condition != null) return false;
        if (expectedVol != null ? !expectedVol.equals(order.expectedVol) : order.expectedVol != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (int) isSell;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        result = 31 * result + (expectedVol != null ? expectedVol.hashCode() : 0);
        result = 31 * result + surplusVol;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
