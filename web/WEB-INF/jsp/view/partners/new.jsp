<template:main htmlTitle="Add Partner" bodyTitle="Add Partner">
    <jsp:attribute name="content">
        <%@ include file="form1.jspf" %>
    </jsp:attribute>
    <jsp:attribute name="footerContent">
        <script type="text/javascript">
            $(document).ready(function() {
                var phones = $('#phones');
                var phonesSize = phones.find('.phone').size();

                var delivery = $('#delivery');
                var deliverySize = delivery.find('.address').size();

                $('#addPhone').click(function() {
                    var codeId = 'phoneForms' + phonesSize + '.code';
                    var codeName = 'phoneForms[' + phonesSize + '].code';
                    var numberId = 'phoneForms' + phonesSize + '.number';
                    var numberName = 'phoneForms[' + phonesSize + '].number';
                    var phone = '<div class="phone"><label for="' + codeId + '">Код:</label><input id="' + codeId + '" name="' + codeName + '" type="text" value=""/><label for="' + numberId + '">Номер:</label><input id="' + numberId + '" name="' + numberName + '" type="text" value=""/></div></div>';
                    phones.append(phone);
                    phonesSize++;
                });

                $('#addAddress').click(function() {
                    var newAddress = '<div class="address"><label for="deliveryForms' + deliverySize + '.city">Город</label><input id="deliveryForms' + deliverySize + '.city" name="deliveryForms['+ deliverySize + '].city" type="text" value=""/><label for="deliveryForms' + deliverySize + '.deliveryCompany">Служба доставки</label><input id="deliveryForms' + deliverySize + '.deliveryCompany" name="deliveryForms[' + deliverySize + '].deliveryCompany" type="text" value=""/><label for="deliveryForms' + deliverySize + '.numberOfDepartment">Номер отделения</label><input id="deliveryForms' + deliverySize + '.numberOfDepartment" name="deliveryForms[' + deliverySize + '].numberOfDepartment" type="text" value=""/></div>';
                    delivery.append(newAddress);
                    deliverySize++;
                })
            })



         /*   var docForm = {}
            var doc = {};
            var docDetails = [];

            $('#submit').click(createDocument);



            function createDocument() {
                $('#docHead').find('input, select').each(function() {
                    doc[$(this).attr('name')] = $(this).val();
                });
                $('.data').each(function() {
                    var o = {};
                    $(this).find('input, select').each(function() {
                        o[$(this).attr('name')] = $(this).val();
                    });
                    docDetails.push(o);
                });
                //   doc['details'] = docDetails;
                docForm["document"] = doc;
                docForm["details"] = docDetails;
                // alert(JSON.stringify(docForm));

                $.ajax({
                    url: "<c:url value='/orders/new'/>",
                    type: "POST",
                    contentType: 'application/json',
                    data: JSON.stringify(docForm),
                    success: function(data) {
                        alert(data);
                    },
                    error: function(msg) {
                        alert(msg);
                    }
                });
            } */

        </script>
    </jsp:attribute>
</template:main>
