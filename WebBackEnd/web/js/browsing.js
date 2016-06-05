/**
 * Created by lyn on 16-6-1.
 */
/**
 * browsing.html展示futures
 * @param jsonArr
 */
function showProduct(jsonArr) {
    var len=jsonArr.length;
    var futures=$(".future-section");
    var grid_html=$(".single-section").html();
    var tmp="<div class='future-grid1'>";
    var grid1_html=tmp;
    for(var i=0;i<3;i++)
        grid1_html+=grid_html;
    grid1_html+="</div>";
    futures.html("");

    for(var i=0;i<parseInt(len/3);i++)
        futures.append(grid1_html);

    for(var i=0;i<len%3;i++)
    {
        tmp+=grid_html;
    }
    tmp+="</div>";
    futures.append(tmp);
    for(var i=0;i<len;i++)
    {
        var html=futures.find(".product").eq(i);
        html.find(".name").html(jsonArr[i].name);
        html.find(".kind").html("kind: "+jsonArr[i].kind);
        html.find(".broker").html("broker: "+jsonArr[i].broker);
        var price=jsonArr[i].marketPrice;
        if(price==-1) price ="Off-Market";
        else price+="&euro;";
        html.find(".price").html(price);
        html.find("a").attr("href","single.html?productId="+jsonArr[i].id);
    }

}
/**
 * 从url中获取id
 * @param name
 * @returns {null}
 */
function getQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
/**
 * showDetail
 * @param jsonArr
 */
function showDoingDetail(jsonArr) {
    var len=jsonArr.length;
    var tbody=$(".table-responsive").find("tbody");
    tbody.html("");
    $(".pricespan").html(jsonArr[0].price);
    var tmpStr="";
    for(var i=len-1;i>=0;i--)
    {
        tmpStr+="<tr><td></td><td></td><td>"+jsonArr[i].price+"</td><td>"
        +jsonArr[i].surplusVol+"</td><td>"+(i+1)+"</td></tr>"

    }
    tbody.append(tmpStr);
}
function showDoneDetail(jsonArr) {
    var len=jsonArr.length;
    var tbody=$(".table-responsive").find("tbody");
    var tmpStr="";
    for(var i=0;i<len;i++)
    {
        tmpStr+="<tr><td>"+(i+1)+"</td><td>"+jsonArr[i].quantity+"</td><td>"+jsonArr[i].price+"</td><td></td><td></td></tr>"

    }
    tbody.append(tmpStr);
}
function showAllOrders(jsonArr) {
    var buyArr=new Array();
    var sellArr=new Array();
    var len=jsonArr.length;
    for(var i=0;i<len;i++)
    {
        if(jsonArr[i].isSell==1)
            sellArr.push(jsonArr[i]);
        else buyArr.push(jsonArr[i]);
    }
    showOrder(sellArr,"sellProducts");
    showOrder(buyArr,"buyProducts");
    
}
function showOrder(jsonArr,str) {
    var len=jsonArr.length;
    var section=$("#"+str);
    var tbody=section.find("tbody");
    tbody.html("");

    var tmpStr="";
    for(var i=0;i<len;i++)
    {
        tmpStr+="<tr><td>"+jsonArr[i].id+"</td><td>"+jsonArr[i].product+"</td><td>"+jsonArr[i].type
            +"</td><td>"+jsonArr[i].broker+"</td><td>"+jsonArr[i].price+"</td><td>"+jsonArr[i].surplusVol+"/"+jsonArr[i].expectedVol
            +"</td><td><a onclick='cancelOrder("+jsonArr[i].id+")'><span class='glyphicon glyphicon-remove'></span></a></td></tr>";

    }
    tbody.append(tmpStr);
}

function cancelOrder(orderid){
    ajax("Logic","post",{
        action : "cancelOrder",
        orderId: orderid
    })
}
function showTrade(jsonArr) {
    var tbody=$(".table-responsive").find("tbody");
    //tbody.html("");
    var len=jsonArr.length;
    var tmpStr="";
    for(var i=0;i<len;i++)
    {
        tmpStr+="<tr><td>"+jsonArr[i].id+"</td><td>"+jsonArr[i].broker+"</td><td>"+jsonArr[i].product+"</td><td>"
        +jsonArr[i].price+"</td><td>"+jsonArr[i].quantity+"</td><td>"+jsonArr[i].seller+"</td><td>"+jsonArr[i].buyer+"</td></tr>"
    }
    tbody.html(tmpStr);
}
function modiBrokerSelect(jsonArr) {
    var len=jsonArr.length;
    var tmp="";
    for(var i=0;i<len;i++)
    {
        tmp+="<option value="+jsonArr[i].id+">"+jsonArr[i].username+"</option>";
    }
    var broker=$("#broker-select");
    broker.html(tmp);
}
function modiProductSelect() {
    var brokerid=$("#broker-select").val();
    ajax("Query","get",{
        action:"productList",
        filter:JSON.stringify({
            brokerId:brokerid
        })
    },function (productStr) {
        var jsonArr = JSON.parse(productStr);
        var len = jsonArr.length;
        var tmp = "";
        for (var i = 0; i < len; i++) {
            tmp += "<option value=" + jsonArr[i].id + ">" + jsonArr[i].name + "</option>";
        }
        var broker = $("#product-select");
        broker.html(tmp);
    });
}
/**
 * 根据用户选择的order type和相关信息，生成condition
 */
function modiCondition() {
    var type=$(":radio[name='type']:checked").val();
    var fill="<li class='text-info'>Condition:</li><li style='width:60%'>";
    if(type=="MARKET")
        fill+=" Activated at the current market price</li>";
    else if(type=="LIMIT")
        fill+=" Activated at price better than the market price</li>";
    else if(type=="STOP")
        fill+=" Activated once price reaches the specified price: </li><li style='width: 12%'><input type='text' value='' name='fixprice' style='margin-top: 10px '></li>";
    $(".condition").html(fill);

}
/**
 * 展示筛选后的futures
 * @param con
 * @param str
 */
function showFilterProduct(con,str) {
    var filter="{}";
    var username=getCookie("username");
    ajax("Query", "get", {
        action: "userList",
        filter: JSON.stringify({
            username: username
        })
    }, function (data) {
        var user = JSON.parse(data);
        var userid = user[0].id;
        var userRole = user[0].role;
        if(con=="kind")
        {

            filter=JSON.stringify({
                kind:str,
                brokerId:userid
            });
            if(userRole=="TRADER")
                filter=JSON.stringify({
                    kind:str,
                });
        }
        else if(con=="broker")
        {
            filter=JSON.stringify({

                brokerId:userid
            });
            if(userRole=="TRADER")
                filter=JSON.stringify({

                    brokerId:str
                });

        }
        else if(con=="name")
        {
            filter=JSON.stringify({
                name:str,
                brokerId:userid
            });
            if(userRole=="TRADER")
                filter=JSON.stringify({
                    name:str,
                });

        }
        else;
        ajax("Query","get",{
            action:"productList",
            filter:filter
        },function (tt) {
            //alert(tt);
            var jsonArr=JSON.parse(tt);
            showProduct(jsonArr);


        });

    });



}
/**
 * 展示筛选后的trade
 * @param con
 * @param str
 */
function showFilterTrade(con,str) {
    var filter={
    };
    var username=getCookie("username");
    ajax("Query", "get", {
        action: "userList",
        filter: JSON.stringify({
            username: username
        })
    }, function (data) {
        var user = JSON.parse(data);
        var userid = user[0].id;
        var userRole = user[0].role;
        if(userRole=="BROKER")
        {
            filter["brokerId"]=userid;

        }
        else if(userRole=="TRADER")
        {
            var tmp={
                sellerId:userid,
                buyerId:userid
            }
            filter["or"]=tmp;

        }
        filter[con+"Id"]=str;
        ajax("Query","get",{
            action:"tradeList",
            filter:JSON.stringify(filter)

        },function (res) {
            //msg(res);
            var jsonArr=JSON.parse(res);
            showTrade(jsonArr);
        })


    });


}
function showFilterOrder(con,str) {
    var filter={
        status: ["DOING","TODO"]

    };
    var username=getCookie("username");
    ajax("Query", "get", {
        action: "userList",
        filter: JSON.stringify({
            username: username
        })
    }, function (data) {
        var user = JSON.parse(data);
        var userid = user[0].id;
        var userRole = user[0].role;
        if(userRole=="BROKER")
        {
            filter["brokerId"]=userid;

        }
        else if(userRole=="TRADER")
        {
            filter["userId"]=userid;
        }
        else;
        if(con=="type")
            filter["type"]=str;
        else
            filter[con+"Id"]=str;
        ajax("Query","get",{
            action:"orderList",
            filter:JSON.stringify(filter)
        },function (res) {
            var resArr=JSON.parse(res);
            showAllOrders(resArr);

        });
    });



}
/**
 * 新建订单
 * @returns {boolean}
 */
function addOrder() {
    var username=getCookie("username");
    ajax("Query","get", {
        action: "userList",
        filter: JSON.stringify({
            username: username
        })
    },function (data) {
        var condition = ",";
        var user = JSON.parse(data);
        var userid = user[0].id;
        var orderForm = $("#orderForm").serializeObject();
        if(orderForm.price==null || orderForm.quantity==null)
        {
            msg("please input price and quantity!");
            return false;
        }

        ajax("Query", "get", {
            action: "productList",
            filter: JSON.stringify({
                id: orderForm.productId,
                brokerId: orderForm.brokerId
            })
        }, function (str) {
            //alert(str);
            var jsonArr = JSON.parse(str);
            if (jsonArr.length != 1)
                msg("error");
            var marketprice = jsonArr[0].marketPrice;
            if (orderForm.type == "LIMIT") {
                if(marketprice<0)
                {
                    msg("Off-Market Sales");
                    return false;
                }
                condition = orderForm.isSell == 1
                    ? (marketprice + ",")
                    : ("," + marketprice);
            }
            else if (orderForm.type == "STOP")
            {
                if(orderForm.fixprice==null)
                {
                    msg("please input the specified price");
                    return false;
                }
                condition = orderForm.isSell == 1
                    ? (orderForm.fixprice + ",")
                    : ("," + orderForm.fixprice);
            }
            ajax("Logic", "post", {
                action: "createOrder",
                orderData: JSON.stringify({
                    productId: orderForm.productId,
                    userId: userid,
                    brokerId: orderForm.brokerId,
                    isSell: orderForm.isSell,
                    type: orderForm.type,
                    expectedVol: orderForm.quantity,
                    condition: condition,
                    price:orderForm.price


                })
            });
        });
    });
    return false;
}
/**
 * 筛选栏
 * @param str
 * @param con
 * @param jsonArr
 */
function showFilter(str,con,jsonArr) {
    var title={
        kind:"CATEGORIES",
        broker:"BROKERS",
        seller:"SELLERS",
        buyer:"BUYERS",
        product:"PRODUCTS",
        type:"TYPES"
    };
    var set = {

    };

    var len=jsonArr.length;
    if(str=="type")
    {
        set={
            MARKET:"MARKET",
            LIMIT:"LIMIT",
            STOP:"STOP"

        };
    }
    else
    {
        for(var i=0;i<len;i++)
        {
            if(str=="kind")
                set[jsonArr[i].kind]=jsonArr[i].kind;
            else if(str=="broker")
                set[jsonArr[i].broker]=jsonArr[i].brokerId;
            else if(str=="product")
                set[jsonArr[i].product]=jsonArr[i].productId;
            else if(str=="buyer")
                set[jsonArr[i].buyer]=jsonArr[i].buyerId;
            else if(str=="seller")
                set[jsonArr[i].seller]=jsonArr[i].sellerId;

        }
    }

    var fill=$("#filter");
    var tmp="<div class='categories'><h3>"+title[str]+"</h3><ul>";
    for(var key in set)
    {

        tmp+="<li><a onclick='showFilter"+con+"(\""+str+"\",\""+set[key]+"\")'>"+key+"</a></li>";
    }
    fill.append(tmp+"</ul></div><br>");

}