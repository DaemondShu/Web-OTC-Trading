package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by monkey_d_asce on 16-5-30.
 */
@Entity
public class Order implements Serializable
{
    private int id;
    private Integer productId;
    private Integer userId;
    private Byte isSell;
    private String status;
    private String type;
    private String time;
    private String condition;
    private Integer expectedVol;
    private Integer surplusVol;
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
    @Column(name = "productId", nullable = true)
    public Integer getProductId()
    {
        return productId;
    }

    public void setProductId(Integer productId)
    {
        this.productId = productId;
    }

    @Basic
    @Column(name = "userId", nullable = true)
    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    @Basic
    @Column(name = "isSell", nullable = true)
    public Byte getIsSell()
    {
        return isSell;
    }

    public void setIsSell(Byte isSell)
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
    @Column(name = "time", nullable = false, length = 45)
    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    @Basic
    @Column(name = "condition", nullable = false, length = 45)
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
    @Column(name = "surplusVol", nullable = true)
    public Integer getSurplusVol()
    {
        return surplusVol;
    }

    public void setSurplusVol(Integer surplusVol)
    {
        this.surplusVol = surplusVol;
        if (this.surplusVol == 0)
            this.status = "DONE";
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
        if (productId != null ? !productId.equals(order.productId) : order.productId != null) return false;
        if (userId != null ? !userId.equals(order.userId) : order.userId != null) return false;
        if (isSell != null ? !isSell.equals(order.isSell) : order.isSell != null) return false;
        if (status != null ? !status.equals(order.status) : order.status != null) return false;
        if (type != null ? !type.equals(order.type) : order.type != null) return false;
        if (time != null ? !time.equals(order.time) : order.time != null) return false;
        if (condition != null ? !condition.equals(order.condition) : order.condition != null) return false;
        if (expectedVol != null ? !expectedVol.equals(order.expectedVol) : order.expectedVol != null) return false;
        if (surplusVol != null ? !surplusVol.equals(order.surplusVol) : order.surplusVol != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (isSell != null ? isSell.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        result = 31 * result + (expectedVol != null ? expectedVol.hashCode() : 0);
        result = 31 * result + (surplusVol != null ? surplusVol.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    public void init() throws Exception
    {
        if (this.expectedVol ==null || this.expectedVol <=0)
            throw new Exception("bad attributes");
        this.surplusVol = this.expectedVol;

        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if (this.type.equals("MARKET")) //市场订单和购买订单直接进入执行状态
        {
            this.setStatus("DOING");
            this.setCondition(",");
        } else if(this.isSell == 0)
        {
            this.setStatus("DOING");

        }
        else
            this.setStatus("TODO");
    }

    @Override
    public String toString()
    {
        return "Order{" +
                "id=" + id +
                ", productId=" + productId +
                ", userId=" + userId +
                ", isSell=" + isSell +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", condition='" + condition + '\'' +
                ", expectedVol=" + expectedVol +
                ", surplusVol=" + surplusVol +
                ", price=" + price +
                '}';
    }
}
