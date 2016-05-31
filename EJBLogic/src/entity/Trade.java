package entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by monkey_d_asce on 16-6-1.
 */
@Entity
public class Trade
{
    private int id;
    private Integer buyOrderId;
    private Integer sellOrderId;
    private String time;
    private Integer quantity;



    public void init(Integer buyOrderId, Integer sellOrderId, Integer quantity)
    {
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.quantity = quantity;
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

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
    @Column(name = "buyOrderId", nullable = true)
    public Integer getBuyOrderId()
    {
        return buyOrderId;
    }

    public void setBuyOrderId(Integer buyOrderId)
    {
        this.buyOrderId = buyOrderId;
    }

    @Basic
    @Column(name = "sellOrderId", nullable = true)
    public Integer getSellOrderId()
    {
        return sellOrderId;
    }

    public void setSellOrderId(Integer sellOrderId)
    {
        this.sellOrderId = sellOrderId;
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
    @Column(name = "quantity", nullable = true)
    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (id != trade.id) return false;
        if (buyOrderId != null ? !buyOrderId.equals(trade.buyOrderId) : trade.buyOrderId != null) return false;
        if (sellOrderId != null ? !sellOrderId.equals(trade.sellOrderId) : trade.sellOrderId != null) return false;
        if (time != null ? !time.equals(trade.time) : trade.time != null) return false;
        if (quantity != null ? !quantity.equals(trade.quantity) : trade.quantity != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (buyOrderId != null ? buyOrderId.hashCode() : 0);
        result = 31 * result + (sellOrderId != null ? sellOrderId.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }
}
