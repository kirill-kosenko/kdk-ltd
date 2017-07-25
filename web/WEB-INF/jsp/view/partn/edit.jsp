<h2>Partner form</h2>
<form id="new-partner">
  <div id="partner_info">
    <label for="firstname">First name:</label><br />
    <input id="firstname" type="text" name="firstname"/>${partner.firstname}<br />
    <br />
    <label for="lastname">Last name:</label><br />
    <input id="lastname" type="text" name="lastname"/>${partner.lastname}<br />
    <br/>
    <label for="fathername">Father name:</label><br />
    <input id="fathername" type="text" name="fathername"/>${partner.fathername}<br />
    <br />
  </div>
  <div>
    <h2>Телефон</h2>
    <div id="phones">
      <c:forEach items="${partner.phones}" var="phone" varStatus="i">
        <div class='phone' id='phone_"${i.index}"'>
          <input type='text' name='phone[${i.index}]' value="${phone}">
          <a href='javascript:void(0)' onclick='deletePhone(${i.index})'>x</a>
        </div>
      </c:forEach>
    </div>
  </div>
  <a href="javascript:void(0)" id="addPhone" style="float: right"><span class="glyphicon glyphicon-plus-sign"></span></a><br />
  <br />
  <input type="submit" value="Save" />
</form>
<script>
//  $(deal).on('ready', function() {

    var i = ${fn:length(partner.phones)};
    var $phones = $('#phones');

    if (i == 0) {
      $phones.append(createFirstPhone(i, ""));
    }

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
//    return false;
//  });
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
</script>