package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

    //接下来一些冗余变量是为了查询优化
    private Integer productId;
    private Integer buyerId;
    private Integer sellerId;
    private Integer brokerId;

    public void init(Order buyOrder, Order sellOrder,Product product ,Integer quantity)
    {
        this.buyOrderId = buyOrder.getId();
        this.sellOrderId = sellOrder.getId();
        this.quantity = quantity;
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.productId = product.getId();
        this.buyerId = buyOrder.getUserId();
        this.sellerId = sellOrder.getUserId();
        this.brokerId = product.getBrokerId();
        this.price = sellOrder.getPrice();
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
    @Column(name = "buyerId", nullable = true)
    public Integer getBuyerId()
    {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId)
    {
        this.buyerId = buyerId;
    }

    @Basic
    @Column(name = "sellerId", nullable = true)
    public Integer getSellerId()
    {
        return sellerId;
    }

    public void setSellerId(Integer sellerId)
    {
        this.sellerId = sellerId;
    }

    @Basic
    @Column(name = "brokerId", nullable = true)
    public Integer getBrokerId()
    {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId)
    {
        this.brokerId = brokerId;
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
        if (productId != null ? !productId.equals(trade.productId) : trade.productId != null) return false;
        if (buyerId != null ? !buyerId.equals(trade.buyerId) : trade.buyerId != null) return false;
        if (sellerId != null ? !sellerId.equals(trade.sellerId) : trade.sellerId != null) return false;

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
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (buyerId != null ? buyerId.hashCode() : 0);
        result = 31 * result + (sellerId != null ? sellerId.hashCode() : 0);
        return result;
    }


    private Double price;

    @Basic
    @Column(name = "price", nullable = true)
    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }
}
