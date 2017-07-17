<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form:form method="post" modelAttribute="partnerForm">
    <form:label path="firstname">First name:</form:label><br />
    <form:input path="firstname" /><br />
    <br />
    <form:label path="lastname">Last name:</form:label><br />
    <form:input path="lastname" /><br />
    <br/>
    <form:label path="fathername">Father name:</form:label><br />
    <form:input path="fathername" /><br />
    <br />

    <div id="phones">
        <h2>Телефон</h2>
        <c:forEach items="${partnerForm.phoneForms}" var="phone" varStatus="i">
            <div class="phone">
                <form:label path="phoneForms[${i.index}].code">Код:</form:label>
                <form:input path="phoneForms[${i.index}].code" />
                <form:label path="phoneForms[${i.index}].number">Номер:</form:label>
                <form:input path="phoneForms[${i.index}].number" />
            </div>

        </c:forEach>
    </div>
    <a href="javascript:void(0)" id="addPhone" style="float: right"><span class="glyphicon glyphicon-plus-sign"></span></a><br />
    <br />

    <div id="delivery">
        <h2>Доставка</h2>
        <c:forEach items="${partnerForm.deliveryForms}" var="delivery" varStatus="i">
            <div class="address">
                <form:select id="delivery-company"  path="deliveryForms[${i.index}].deliveryCompany" >
                    <option value="none">...Служба...</option>
                    <c:forEach items="${deliveryServices}" var="service">
                        <option value="${service.name}"><c:out value="${service.name}" /></option>
                    </c:forEach>
                </form:select>
                &nbsp
                <form:select path="deliveryForms[${i.index}].city">
                    <option value="none">...Город...</option>

                </form:select>
                &nbsp
                <form:select path="deliveryForms[${i.index}].numberOfDepartment">
                    <option value="0">Номер отделения</option>

                </form:select>
            </div>
        </c:forEach>
    </div>
    <a href="javascript:void(0)" id="addAddress" style="float: right"><span class="glyphicon glyphicon-plus-sign"></span></a><br />

    <input type="submit" value="Save" />
</form:form>



