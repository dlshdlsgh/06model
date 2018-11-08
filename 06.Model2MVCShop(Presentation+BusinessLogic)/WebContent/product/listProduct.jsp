<%@page import="com.model2.mvc.service.domain.Product"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
<%@page import="java.util.List"%>

<%@page import="com.model2.mvc.service.domain.Product"%>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>
<%@page import="com.model2.mvc.common.Search" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
List<Product> list= (List<Product>)request.getAttribute("list");
Page resultPage=(Page)request.getAttribute("resultPage");

Search search = (Search)request.getAttribute("search");
//==> null �� ""(nullString)���� ����
String searchCondition = CommonUtil.null2str(search.getSearchCondition());
String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
%>
--%>

<%
	List<Product> list= (List<Product>)request.getAttribute("list");
	System.out.println(list);
%>
<html>
<head>
<title>��ǰ ��� ��ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetUserList(currentPage){
	document.getElementById("currentPage").value = currentPage;
	document.detailForm.submit();
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=${param.menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
		
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				<%--
				<%if(request.getParameter("menu").equals("search")){ %>
					<td width="93%" class="ct_ttl01">��ǰ �����ȸ</td>
					<%}else if(request.getParameter("menu").equals("manage")){ %>
					<td width="93%" class="ct_ttl01">��ǰ �������</td>
					<%} %>
				--%>
				<c:if test="${!empty param.menu && param.menu == 'search'}">
				<td width="93%" class="ct_ttl01">��ǰ �����ȸ</td>
				</c:if>
				<c:if test="${!empty param.menu && param.menu == 'manage'}">
				<td width="93%" class="ct_ttl01">��ǰ �������</td>
				</c:if>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px" >
				<option value="0" ${!empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>��ǰ��ȣ</option>
				<option value="1" ${!empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>��ǰ��</option>
				<option value="2" ${!empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>��ǰ����</option>
			</select>
			<input 	type="text" name="searchKeyword"  value="${search.searchKeyword}" 
							class="ct_input_g" style="width:200px; height:19px"/>
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetUserList('1');">�˻�</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >��ü ${resultPage.totalCount} �Ǽ�, ���� ${resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">�����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">���� ����</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<%--
	<% 	
		for(int i=0; i<list.size(); i++) {
			Product vo = (Product)list.get(i);
	%>
	--%>
	<c:set var="i" value="0" />
	 <c:forEach var="product" items="${list}">
	 <c:set var="i" value="${i+1}" />
	<tr class="ct_list_pop">
		<td align="center">${i+1}</td>
		<td></td>
		<td align="left">
		<c:if test="${!empty param.menu && param.menu =='search'}">
		<a href="/getProduct.do?prodNo=${product.prodNo}">${product.prodName}</a>
		</c:if>
		<c:if test="${!empty param.menu && param.menu =='manage'}">
		<a href="/updateProductView.do?prodNo=${product.prodNo}">${product.prodName}</a>
		</c:if>
		</td>
		<td></td>
		<td align="left">${product.price}</td>
		<td></td>
		<td align="left">${product.regDate}
		</td>
		<td></td>
		<td align="left">
		<c:if test="${!empty param.menu && param.menu == 'manage'}">
			<c:if test="${product.proTranCode == null}">�Ǹ���</c:if>
			<c:if test="${!empty product.proTranCode && product.proTranCode == 1}">
			���ſϷ�<a href="/updateTranCodeByProd.do?prodNo=${product.prodNo}&tranCode=2">����ϱ�</a>
			</c:if>
			<c:if test="${!empty product.proTranCode && product.proTranCode == 2}">-�����-</c:if>
			<c:if test="${!empty product.proTranCode && product.proTranCode == 3}">��ۿϷ�</c:if>
		</c:if>
		<c:if test="${!empty param.menu && param.menu == 'search'}">
			<c:if test="${product.proTranCode == null}">�������</c:if>
			<c:if test="${!empty product.proTranCode && product.proTranCode != null}">(�ǸſϷ��ǰ)</c:if>
		</c:if>
		</td>		
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
		<%--
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					�� ����
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getCurrentPage()-1%>')">�� ����</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetProductList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					���� ��
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage()+1%>')">���� ��</a>
			<% } %>
		--%>
		<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->
</form>
</div>
</body>
</html>