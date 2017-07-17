<template:main htmlTitle="Новый заказ" bodyTitle="Новый заказ">
    <jsp:attribute name="content">
    <div class="container">
        <form role="form">
            <div id="docHead">
            <div class="row">
                <div class="col-lg-4 col-xs-8">
                <label for="operation">Тип операции:</label>
                    <select class="form-control" id="operation" name="documentType">
                        <option>Тип документа</option>
                        <c:forEach items="${dealTypes}" var="documentType">
                            <option value="${documentType}">${documentType}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-4 col-xs-8">
                <label for="partner">Контрагент:</label>
                    <select class="form-control" id="partner" name="partnerId">
                        <c:forEach items="${partners}" var="partner">
                            <option value="${partner.id}"><c:out value="${partner.firstname} ${partner.lastname}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            </div>
            <hr>
                <div id="records">
                <div class="row">
                    <div class="col-lg-4 col-xs-4">
                        <strong>Товар</strong>
                    </div>
                    <div class="col-lg-4 col-xs-4">
                        <span style="font-weight: bold;">Количество</span>
                    </div>
                    <div class="col-lg-4 col-xs-4">Цена</div>
                </div>
                <div class="row data" id="dataRow">
                    <div class="col-lg-4 col-xs-4">
                        <select class="form-control" name="productId">
                            <option>Выберите товар...</option>
                            <c:forEach items="${products}" var="product">
                                <option value="${product.id}">${product.name}</option>
                            </c:forEach>
                        </select>
         <!--               <input class="productInput" type="text" name="productId" value="" />     -->
                    </div>
                    <div class="col-lg-4 col-xs-4">
                        <input class="form-control quantityInput" type="text" name="quantity" value="" />
                    </div>
                    <div class="col-lg-3 col-xs-3">
                        <input class="form-control priceInput" type="text" name="price"  value="0"/>
                    </div>
                </div>
                </div>

            <a href="javascript:void(0)" id="add" style="float: right"><span class="glyphicon glyphicon-plus-sign"></span></a><br>
            <a href="#" id="submit">Сохранить</a>
        </form>
    </div>
    </jsp:attribute>
    <jsp:attribute name="footerContent">


    <script type="text/javascript">
        var records = $('#records');
        var rTableRow = $('#dataRow');



        $('#add').click(function() {

            records.append('<div class="row data">' + rTableRow.html() + '<div class="col-xs-1"><a href="javascript:void(0)" onclick="delRecord();" style="float: right"><span class="glyphicon glyphicon-remove"></span></a><br></div></div>');
        });
        var docForm = {}
        var doc = {};
        var docDetails = [];

        $('#submit').click(createDocument);

        alert($(".data").html());

        function delRecord() {
            alert($(this).closest(".data").html());
        }

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
        }

    </script>
    </jsp:attribute>
</template:main>