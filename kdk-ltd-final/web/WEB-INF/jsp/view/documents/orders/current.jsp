
    <div class="container">
        <div class="panel-group" id="collapse-group">
            <c:forEach items="${orders}" var="order" varStatus="i">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h4 class="panel-title">

                            <div class="row">
                                <div class="col-lg-1 col-xs-1">
                                    <a data-toggle="collapse" data-parent="#collapse-group" href="#el${i.index}">
                                        <span class="glyphicon glyphicon-chevron-down"></span>
                                    </a>
                                </div>
                                <div class="col-lg-2 col-xs-2">
                                    <c:out value="${order.insertTimestamp}"/>
                                </div>
                                <div class="col-lg-2 col-xs-2">
                                    <c:out value="${order.documentType}"/>
                                </div>
                                <div class="col-lg-2 col-xs-2">
                                    <a href="/partners/${order.partner.id}">
                                        <c:out value="${order.partner.firstname}  ${order.partner.lastname}"/>
                                    </a>
                                </div>
                                <div class="col-lg-2 col-xs-2">
                                    <c:out value="${order.sum}"/>
                                </div>
                                <div class="col-lg-3 col-xs-2">
                                    <a href="#"><span class="glyphicon glyphicon-floppy-disk"></span></a>
                                </div>
                            </div>

                    </h4>
                </div>
                <div id="el${i.index}" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-3 col-xs-3"><h4>Товар</h4></div>
                            <div class="col-lg-3 col-xs-3"><h4>Количество</h4></div>
                            <div class="col-lg-3 col-xs-3"><h4>Цена</h4></div>
                            <div class="col-lg-3 col-xs-3"><h4>Сумма</h4></div>
                        </div>
                        <c:forEach items="${order.details}" var="detail">
                            <div class="row">
                                <div class="col-lg-3 col-xs-3">
                                    <c:out value="${detail.product.name}"/>
                                </div>
                                <div class="col-lg-3 col-xs-3">
                                    <c:out value="${detail.quantity}"/>
                                </div>
                                <div class="col-lg-3 col-xs-3">
                                    <c:out value="${detail.price}"/>
                                </div>
                                <div class="col-lg-3 col-xs-3">
                                    <c:out value="${detail.sum}"/>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            </c:forEach>
        </div>
    </div>
    <script src="<c:url value='/resources/jquery-2.1.4.js'/>"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>