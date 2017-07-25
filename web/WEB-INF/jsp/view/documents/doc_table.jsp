

<template:main htmlTitle="${title}" bodyTitle="${title}">
    <jsp:attribute name="content">
        <div class="row">
            <div class="col-xs-3">
              <div class="panel panel-default">
                <div class="panel-heading">Форма поиска</div>
                <div class="panel-body">
                  <form:form modelAttribute="searchCriteria" action="${action}" method="get">
                    <div class="row">
                      <div class="col-xs-10">
                        <div class="form-group">
                          <div class="input-group date" id="datetimepicker1">
                            <form:input path="documentCriteria.from" class="form-control" placeholder="Дата, начало"/>
                                            <span class="input-group-addon">
                                              <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-xs-10">
                        <div class="form-group">
                          <div class="input-group date" id="datetimepicker2">
                            <form:input path="documentCriteria.to" type="text" cssClass="form-control" placeholder="Дата, конец"/>
                                            <span class="input-group-addon">
                                              <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-xs-10">
                        <div class="form-group">
                          <c:forEach items="${dealTypes}" var="documentType">
                            <div class="checkbox">
                              <label>

                                <form:checkbox path="documentCriteria.dealTypes" value="${documentType.key}" checked="true"/>


                                <c:out value="${documentType.value}" />
                              </label>
                            </div>

                          </c:forEach>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-xs-10">
                        <div class="checkbox">
                          <label>
                            <form:checkbox path="documentCriteria.order" />Заказы
                          </label>

                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-xs-10">
                        <div class="form-group">
                          <form:select path="documentCriteria.partnerIdList" cssClass="form-control">
                            <form:option value="-1" label="--- Все ---" />
                            <form:options items="${partners}"  itemValue="id" itemLabel="fullname" />
                          </form:select>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-xs-10">
                        <div class="form-group">
                          <form:select path="detailCriteria.productIdList" cssClass="form-control">
                            <form:option value="-1" label="--- Все ---" />
                            <form:options items="${products}"  itemValue="id" itemLabel="name" />
                          </form:select>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-xs-10">
                        <div class="form-group">
                          <form:select path="detailCriteria.storageIdList" cssClass="form-control">
                            <form:option value="-1" label="--- Все ---" />
                            <form:options items="${storages}"  itemValue="id" itemLabel="name" />
                          </form:select>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-xs-10">
                        <div class="form-group">
                          <form:input path="detailCriteria.priceMin" cssClass="form-control" placeholder="Цена, min"/>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-xs-10">
                        <div class="form-group">
                          <form:input path="detailCriteria.priceMax" cssClass="form-control" placeholder="Цена, max"/>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-xs-10">
                        <div class="form-group">

                          <form:input path="detailCriteria.quantityMin" cssClass="form-control" placeholder="Количество, min"/>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-xs-10">
                        <div class="form-group">
                          <form:input path="detailCriteria.quantityMax" cssClass="form-control" placeholder="Количество, max"/>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-xs-offset-4 col-xs-2">
                        <button type="submit" class="btn btn-primary">Поиск</button>
                      </div>
                    </div>
                  </form:form>
                </div>
              </div>
            </div>
            <div class="col-xs-9">
              <div class="row">
                <c:choose>
                  <c:when test="${not empty documents}" >
                    <h3>Найдено <c:out value="${documents.size()}" /> документов</h3>
                    <!--        <div class="row">
                    <div class="col-lg-6">
                    <div class="row">
                    <div class="col-lg-3 center-block">Контрагент</div>
                    </div>
                    </div>
                    <div class="col-lg-4">
                    <div class="row">
                    <div class="col-lg-4 center-block">Товар</div>
                    <div class="col-lg-2 center-block">Кол.,<br>шт</div>
                    <div class="col-lg-3 center-block">Сумма</div>
                    <div class="col-lg-3 center-block">Склад</div>
                    </div>
                    </div>
                    <div class="col-lg-2">Итого</div>
                    </div>        -->

                    <c:set var="date" value="${documents.get(0).dateOfDocument}"/>

                    <div class="panel panel-default">
                    <div class="panel-heading"><c:out value="${date}"/></div>
                    <div class="panel-body">

                    <c:forEach items="${documents}" var="deal" varStatus="i">
                      <c:if test="${!date.equals(deal.dateOfDocument)}">
                        <c:set var="date" value="${deal.dateOfDocument}"/>
                        </div></div>
                        <div class="panel panel-default">
                        <div class="panel-heading"><c:out value="${date}" /></div>
                        <div class="panel-body">
                      </c:if>

                      <div class="row">
                        <div class="col-xs-4">
                          <div class="row">
                            <div class="col-xs-3 center-block"><c:out value="${deal.partner.fullname}"/></div>
                            <div class="col-xs-2 center-block"><c:out value="${deal.documentType}" /> </div>
                          </div>
                        </div>
                        <div class="col-xs-4">
                          <c:set var="sum" value="${0}"/>
                          <c:forEach items="${deal.details}" var="detail">
                            <div class="row">
                              <div class="col-lg-4 center-block"><c:out value="${detail.product.name}"/></div>
                              <div class="col-lg-2 center-block"><c:out value="${detail.quantity}"/></div>
                              <div class="col-lg-3 center-block"><c:out value="${detail.sum}"/></div>
                              <div class="col-lg-3 center-block"><c:out value="${detail.storage.name}"/></div>
                            </div>
                            <c:set var="sum" value="${sum + detail.sum}"/>
                          </c:forEach>
                        </div>
                        <div class="col-xs-2"><c:out value="${sum}"/></div>
                        <div class="col-xs-1">
                          <c:if test="${!deal.status}">
                            <form action="<c:out value="${deal.id}" />/todocument" method="post">
                              <button class="btn btn-primary">Провести</button>
                            </form>
                          </c:if>
                        </div>
                      </div>

                    </c:forEach>
                    </div></div>
                  </c:when>
                  <c:otherwise>
                    <h3>Найдено 0 документов</h3>
                  </c:otherwise>
                </c:choose>
              </div>
            </div>
        <script type="text/javascript">
          $(function () {
            //Инициализация datetimepicker8 и datetimepicker9
            $("#datetimepicker1").datetimepicker(
                    {pickTime: false, language: 'ru'}
            );
            $("#datetimepicker2").datetimepicker(
                    {pickTime: false, language: 'ru'}
            );
            //При изменении даты в 8 datetimepicker, она устанавливается как минимальная для 9 datetimepicker
            $("#datetimepicker1").on("dp.change",function (e) {
              $("#datetimepicker2").data("DateTimePicker").setMinDate(e.date);
            });
            //При изменении даты в 9 datetimepicker, она устанавливается как максимальная для 8 datetimepicker
            $("#datetimepicker2").on("dp.change",function (e) {
              $("#datetimepicker1").data("DateTimePicker").setMaxDate(e.date);
            });
          });
        </script>
    </jsp:attribute>
</template:main>
