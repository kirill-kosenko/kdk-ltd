/**
 * Created by kkm on 20.02.2017.
 */

$(document).on('ready', function() {

    var i = 0;
    var $phones = $('#phones');

    $phones.append(createFirstPhone(i, ""));

    $('#addPhone').click(function() {
        $phones.append(createPhone(++i));
    });

    $('form#new-partner').on('submit', function(event) {
        event.preventDefault();
        var p = {};
        $('#partner_info').find('input').each(function() {
            p[this.name] = this.value;
        });
        var phones = [];
        $('#phones').find('input').each(function() {
            phones.push(this.value);
        });
        p.phones = phones;
        console.log(JSON.stringify(p));

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/api/partners/",
            data: JSON.stringify(p),
            contentType: "application/json",
            success: function (data) {
                alert(JSON.stringify(data));
            },
            error: function (data) {
                alert("Failed");
            }
        });
    });
    return false;
});
function createPhone(n) {
    var a = "<a href='javascript:void(0)' onclick='deletePhone(" + n + ")'>x</a>";
    return createFirstPhone(n, a);
}
function createFirstPhone(n, a) {
    return $("<div class='phone' id='phone_"+n+"'><input type='text' name='phone[" + n + "]' placeholder='Номер телефона'>" + a + "</div>");
}
function deletePhone(n) {
    $("#phones").find("#phone_" + n).empty();
}
