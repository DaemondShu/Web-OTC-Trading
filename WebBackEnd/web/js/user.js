/**
 * Created by lyn on 16-6-1.
 */
$.fn.serializeObject = function ()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function ()
    {
        if (o[this.name])
        {
            if (!o[this.name].push)
            {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else
        {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

function register()
{
    var regForm=$("#regForm");
    var formData=regForm.serializeObject();
    if(formData.password1.toString() != formData.password2.toString())
        msg("Please confirm password!");
    else if(formData.role==undefined)
        msg("Please choose role!");
    else
    {
        ajax("User","get",
            {
                action: "register",
                userData: JSON.stringify({
                    username: formData.username,
                    company: formData.company,
                    password: formData.password1,
                    role:formData.role
                })
            },function (data) {
                alert(data);

            });

    }
    return false;
    
}
function login() {
    var loginForm=$("#loginForm");
    var formData=loginForm.serializeObject();
    formData.action="login";
    ajax("User","get",formData,function (data) {
        setCookie("username",formData.username,"d1","/");
    });
    return false;
}