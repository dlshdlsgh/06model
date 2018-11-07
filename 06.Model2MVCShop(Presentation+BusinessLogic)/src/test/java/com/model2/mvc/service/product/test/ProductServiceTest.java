package com.model2.mvc.service.product.test;

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
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
																	"classpath:config/context-aspect.xml",
																	"classpath:config/context-mybatis.xml",
																	"classpath:config/context-transaction.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-common.xml" })
public class ProductServiceTest {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService proService;
	
	public ProductServiceTest() {
		// TODO Auto-generated constructor stub
	}
	
	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdNo(123);
		product.setProdName("곡물쿠키");
		product.setProdDetail("맛있어요");
		product.setPrice(8000);
		product.setManuDate("12434124");
		product.setFileName("vkdlfspdla");
		proService.addProduct(product);
		
		System.out.println(product);
		
		product = proService.getProduct(123);
		//==> API 확인
		Assert.assertEquals(123, product.getProdNo());
		Assert.assertEquals("곡물쿠키", product.getProdName());
		Assert.assertEquals("맛있어요", product.getProdDetail());
		Assert.assertEquals(8000, product.getPrice());
		Assert.assertEquals("12434124", product.getManuDate());
		Assert.assertEquals("vkdlfspdla", product.getFileName());
	}
	
	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
	
		product = proService.getProduct(123);
		System.out.println(product);
		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
		Assert.assertEquals(123, product.getProdNo());
		Assert.assertEquals("곡물쿠키", product.getProdName());
		Assert.assertEquals("맛있어요", product.getProdDetail());
		Assert.assertEquals(8000, product.getPrice());
		Assert.assertEquals("12434124", product.getManuDate());
		Assert.assertEquals("vkdlfspdla", product.getFileName());

		Assert.assertNotNull(proService.getProduct(10108));
		Assert.assertNotNull(proService.getProduct(10105));
	}
	
	//@Test
	 public void testUpdateProduct() throws Exception{
		 
		Product product = proService.getProduct(123);
		Assert.assertNotNull(product);
		
		Assert.assertEquals(123, product.getProdNo());
		Assert.assertEquals("곡물쿠키", product.getProdName());
		Assert.assertEquals("맛있어요", product.getProdDetail());
		Assert.assertEquals(8000, product.getPrice());
		Assert.assertEquals("12434124", product.getManuDate());
		Assert.assertEquals("vkdlfspdla", product.getFileName());
		
		product.setProdName("국물쿠키");
		product.setProdDetail("맛없어요");
		product.setPrice(13000);
		product.setManuDate("20181024");
		product.setFileName("파일네임");
		
		proService.updateProduct(product);
		
		product = proService.getProduct(123);
		Assert.assertNotNull(product);
		
		System.out.println(product);
			
		//==> API 확인
		Assert.assertEquals("국물쿠키", product.getProdName());
		Assert.assertEquals("맛없어요", product.getProdDetail());
		Assert.assertEquals(13000, product.getPrice());
		Assert.assertEquals("20181024", product.getManuDate());
		Assert.assertEquals("파일네임", product.getFileName());
	 }
	 
	 //@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = proService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = proService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 public void testGetProductListByProdNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10103");
	 	Map<String,Object> map = proService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = proService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 public void testGetProductListByProdName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("%자전거%");
	 	Map<String,Object> map = proService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = proService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 
	 }
}
