package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
																	"classpath:config/context-aspect.xml",
																	"classpath:config/context-mybatis.xml",
																	"classpath:config/context-transaction.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-common.xml" })
public class PurchaseServiceTest {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purService;
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService proService;
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	
	public PurchaseServiceTest() {
		// TODO Auto-generated constructor stub
	}
	
	//@Test
	public void testAddPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(proService.getProduct(123));
		purchase.setBuyer(userService.getUser("user15"));
		purchase.setPaymentOption("1");
		purchase.setReceiverName("����ȣ");
		purchase.setReceiverPhone("12345678");
		purchase.setDivyAddr("�����");
		purchase.setDivyRequest("������̤�");
		purchase.setDivyDate("20181010");
		purService.addPurchase(purchase);
		
		System.out.println(purchase);
		
		purchase = purService.getPurchase2(123);
		//==> API Ȯ��
		Assert.assertEquals(123, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user15", purchase.getBuyer().getUserId());
		Assert.assertEquals("1  ", purchase.getPaymentOption());
		Assert.assertEquals("����ȣ", purchase.getReceiverName());
		Assert.assertEquals("12345678", purchase.getReceiverPhone());
		Assert.assertEquals("�����", purchase.getDivyAddr());
		Assert.assertEquals("������̤�", purchase.getDivyRequest());
	}
	
	//@Test
	public void testGetPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
	
		purchase = purService.getPurchase2(123);
		System.out.println(purchase);
		//==> console Ȯ��
		//System.out.println(user);
		
		Assert.assertEquals(123, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user15", purchase.getBuyer().getUserId());
		Assert.assertEquals("1  ", purchase.getPaymentOption());
		Assert.assertEquals("����ȣ", purchase.getReceiverName());
		Assert.assertEquals("12345678", purchase.getReceiverPhone());
		Assert.assertEquals("�����", purchase.getDivyAddr());
		Assert.assertEquals("������̤�", purchase.getDivyRequest());
	}
	
	//@Test
	public void testUpdatePurchase() throws Exception{
		 
		Purchase purchase = purService.getPurchase2(123);
		Assert.assertNotNull(purchase);
		
		Assert.assertEquals(123, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user15", purchase.getBuyer().getUserId());
		Assert.assertEquals("1  ", purchase.getPaymentOption());
		Assert.assertEquals("����ȣ", purchase.getReceiverName());
		Assert.assertEquals("12345678", purchase.getReceiverPhone());
		Assert.assertEquals("�����", purchase.getDivyAddr());
		Assert.assertEquals("������̤�", purchase.getDivyRequest());
		
		//==> console Ȯ��
		System.out.println("purchase::"+purchase);
		//System.out.println(user);
		
		purchase.setPaymentOption("2");
		purchase.setReceiverName("����ȫ");
		purchase.setReceiverPhone("12121234");
		purchase.setDivyAddr("������");
		purchase.setDivyRequest("������");
		purchase.setDivyDate("20181024");
		
		purService.updatePurcahse(purchase);
		
		purchase=purService.getPurchase2(123);
		System.out.println("�ٲ��� purchase :: "+purchase);
		
		//==> API Ȯ��
		Assert.assertEquals(123, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user15", purchase.getBuyer().getUserId());
		Assert.assertEquals("2  ", purchase.getPaymentOption());
		Assert.assertEquals("����ȫ", purchase.getReceiverName());
		Assert.assertEquals("12121234", purchase.getReceiverPhone());
		Assert.assertEquals("������", purchase.getDivyAddr());
		Assert.assertEquals("������", purchase.getDivyRequest());
	 }
	 
	 //@Test
	 public void testGetPurchaseListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	String buyerId = "user15";
	 	Map<String,Object> map = purService.getPurchaseList(search, buyerId);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = purService.getPurchaseList(search, buyerId);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 public void testUpdateTranCode()throws Exception{
		 Purchase purchase = new Purchase();
		 purchase = purService.getPurchase2(123);
		 
		 System.out.println("::::"+purchase);
		 
		 Assert.assertEquals("1  ", purchase.getTranCode());
		 
		 purchase.setTranCode("2");
		 
		 purService.updateTranCode(purchase);
		 
		 purchase=purService.getPurchase2(123);
		 
		 Assert.assertEquals("2  ", purchase.getTranCode());
	 }
}
