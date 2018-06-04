<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<h2><spring:message code="subscription.mandatory" /> </h2>


<form:form action="subscription/student/edit.do" modelAttribute="subscription">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="course" />
	<form:hidden path="student" />
	
	
<form:label path="subscriptionType">
		<spring:message code="subscription.subscriptionType" />:
	</form:label>
	<form:radiobutton path="subscriptionType" value="FREE" onclick="hideCreditCard()"/><spring:message code="subscription.subscriptionType.free" />
	<form:radiobutton path="subscriptionType" value="STANDARD" onclick="clickStandard()" checked="checked"/><spring:message code="subscription.subscriptionType.standard" />
	<form:radiobutton path="subscriptionType" value="PREMIUM" onclick="clickPremium()"/><spring:message code="subscription.subscriptionType.premium" />
	<br>
	<br>	

<div class="creditCard-form">
<fieldset>
 <legend><spring:message code="subscription.creditCard" /></legend>

<acme:textbox code="subscription.creditCard.holder" path="creditCard.holderName"/>

<spring:message code= "subscription.creditCard.placeholder" var="creditCardPlaceholder"/>
<spring:message code= "subscription.creditCard.pattern" var="creditCardPattern"/>
<acme:textbox code="subscription.creditCard.brand" pattern="${creditCardPattern}" placeholder="${creditCardPlaceholder}" path="creditCard.brandName"/>

<acme:textbox code="subscription.creditCard.number" path="creditCard.number"/>

<acme:textbox code="subscription.creditCard.expirationMonth" path="creditCard.expirationMonth"/>

<acme:textbox code="subscription.creditCard.expirationYear" path="creditCard.expirationYear"/>

<acme:textbox code="subscription.creditCard.CVV" path="creditCard.CVV"/>


</fieldset>
</div>
	<br>
	<br>
<div class="precioStandard">
<h4 class="titles"> <spring:message code="subscription.subscriptionType.price.base" /> : ${customisation.standardPrice } <spring:message code="subscription.subscriptionType.coin" /></h4>
<h4 class="redText"><spring:message code="subscription.subscriptionType.points" /> : ${principal.score}</h4>
<h3 class="greenText"><spring:message code="subscription.subscriptionType.total" /> : ${customisation.standardPrice - (customisation.conversionRate*principal.score)} <spring:message code="subscription.subscriptionType.coin" /> </h3>
</div>

<div class="precioPremium">
<h4 class="titles"> <spring:message code="subscription.subscriptionType.price.base" /> : ${customisation.premiumPrice } <spring:message code="subscription.subscriptionType.coin" /></h4>
<h4 class="redText"><spring:message code="subscription.subscriptionType.points" /> : ${principal.score}</h4>
<h3 class="greenText"><spring:message code="subscription.subscriptionType.total" /> : ${customisation.premiumPrice - (customisation.conversionRate*principal.score)} <spring:message code="subscription.subscriptionType.coin" /> </h3>
</div>

	<spring:message code="subscription.save" var="savesubscription"  />
	<spring:message code="subscription.delete" var="deletesubscription"  />
	<spring:message code="subscription.cancel" var="cancelsubscription"  />
	<spring:message code="subscription.confirm" var="confirmsubscription"  />
	
	
	<input type="submit" name="save"
		value="${savesubscription}" />&nbsp; 

	<input type="button" name="cancel"
		value="${cancelsubscription}"
		onclick="javascript: relativeRedir('course/list.do');" />
	<br />
</form:form>

<script>

$(".precioPremium").hide();
$(".precioStandard").show();

function hideCreditCard(){
	$(".creditCard-form").hide();
	$(".precioPremium").hide();
	$(".precioStandard").hide();
}	
function clickStandard(){
	$(".creditCard-form").show();
	$(".precioPremium").hide();
	$(".precioStandard").show();
}

function clickPremium(){
	$(".creditCard-form").show();
	$(".precioPremium").show();
	$(".precioStandard").hide();
}
</script>