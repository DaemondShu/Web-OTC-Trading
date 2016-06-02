/**
 * Created by lyn on 16-6-1.
 */
function showProduct(jsonArr) {
    var len=jsonArr.length;
    var futures=$(".future-section");
    var grid1_html=futures.html();
    var grid_html=$(".single-section").html();
    futures.html("");
    for(var i=0;i<len/3-1;i++)
        futures.append(grid1_html);
    var tmp="<div class='future-grid1'>";
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
        html.find(".price").html(jsonArr[i].marketPrice);
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

function showDetail(json) {
    
}
function showSellOrder(jsonArr) {
    var len=jsonArr.length;
    var tbody=$("tbody").first().html();

    var tmpStr="";
    for(var i=0;i<len;i++)
    {
        tmpStr+="<tr><td>"+jsonArr[i].id+"</td><td>"+jsonArr[i].name+"</td><td>"+jsonArr[i].type
            +"</td><td>+jsonArr[i].broker+</td><td>"+jsonArr[i].price+"</td><td>"+jsonArr[i].quantity
            +"</td><td><a><span class='glyphicon glyphicon-remove'></span></a></td></tr>";

    }
}
function cancelOrder(orderid){

}