/**
 * Created by monkey_d_asce on 16-4-24.
 */

function msg(str, type)
{
    if (type == undefined) type = 0;
    alert(str);
}


function emptyCallBack()
{
    return;
}

/**
 * callback function demo for successful ajax
 * @param json : response information
 */
function defaultSuccess(json)
{
    msg("success!  " + (typeof json == "string" ? json : JSON.stringify(json)));
}


/**
 * callback function demo for bad ajax
 * @param error : response information
 */
function defaultError(error)
{
    msg("error " + error.responseText);
}


function ajax(url, type, data, goodCallBack, errorCallBack, isAsync)
{
    if (goodCallBack == undefined) goodCallBack = defaultSuccess;
    if (errorCallBack == undefined) errorCallBack = defaultError;
    if (isAsync == undefined) isAsync = true;

    $.ajax({
        type: type,
        url: url,
        //dataType: "json",
        data: data,
        async: isAsync,
        success: goodCallBack,  //set success callback function
        error: errorCallBack
    });
}


function isExist(array, item)
{
    for (var i = 0; i < array.length; i++)
    {
        if (array[i] == item)
            return true;
    }
    return false;

}


//设置cookie
//eg setCookie("name","hayden","s20","/"); key,value,存活时间,存储路径
function setCookie(name, value, time, path)
{
    var strsec = getSec(time);
    var exp = new Date();
    exp.setTime(exp.getTime() + strsec * 1);

    if (typeof value != 'string')
        value = JSON.stringify(value);

    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=" + path;
}

//读取cookies
function getCookie(name)
{
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");  //or indexof


    if (arr = document.cookie.match(reg))
        return (unescape(arr[2]));
    else
        return null;
}


function getSec(str)
{
    var str1 = str.substring(1, str.length) * 1;
    var str2 = str.substring(0, 1);
    if (str2 == "s")
    {
        return str1 * 1000;
    }
    else if (str2 == "h")
    {
        return str1 * 60 * 60 * 1000;
    }
    else if (str2 == "d")
    {
        return str1 * 24 * 60 * 60 * 1000;
    }
}


