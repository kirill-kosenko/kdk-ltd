<template:main htmlTitle="Add Partner" bodyTitle="Add Partner">
    <jsp:attribute name="content">
        <%@ include file="formSelective.jsp" %>
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

                var xhr;
                var select_company, $select_company;
                var select_city, $select_city;
                var select_department, $select_department;

                $select_company = $('#deliveryForms0.deliveryCompany').selectize({
                    onChange: function(value) {
                        if (!value.length) return;
                        select_city.disable();
                        select_city.clearOptions();
                        select_city.load(function(callback) {
                            xhr && xhr.abort();
                            xhr = $.ajax({
                                url: '<c:url value="/delivery/' + value + '/cities" context="/"/>',
                                success: function(results) {
                                    select_city.enable();
                                    callback(results);
                                },
                                error: function() {
                                    callback();
                                }
                            })
                        });
                    }
                });

                $select_city = $('#select-city').selectize({
                    valueField: 'name',
                    labelField: 'name',
                    searchField: ['name']
                });

                select_city  = $select_city[0].selectize;
                select_company = $select_company[0].selectize;

                select_city.disable();
            })

        </script>
    </jsp:attribute>
</template:main>
