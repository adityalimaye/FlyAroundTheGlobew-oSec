package com.niit.shoppingmallfe.controller;

import java.io.File;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.niit.shoppingmall.dao.CategoryMallDAO;
import com.niit.shoppingmall.dao.ProductMallDAO;
import com.niit.shoppingmall.dao.SupplierMallDAO;
import com.niit.shoppingmall.model.CategoryMall;
import com.niit.shoppingmall.model.ProductMall;
import com.niit.shoppingmall.model.SupplierMall;

@Controller
public class ProductController {
	
	@Autowired 
	ProductMallDAO productMallDAO;
	@Autowired
	ProductMall productMall;
	
	@Autowired
	CategoryMallDAO categoryMallDAO;
	@Autowired
	CategoryMall categoryMall;
	
	@Autowired
	SupplierMallDAO supplierMallDAO;
	@Autowired 
	SupplierMall supplierMall;
	
	public ProductMallDAO getProductMallDAO() {
		return productMallDAO;
	}

	public void setProductMallDAO(ProductMallDAO productMallDAO) {
		this.productMallDAO = productMallDAO;
	}
	
	public CategoryMallDAO getCategoryMallDAO() {
		return categoryMallDAO;
	}

	public void setCategoryMallDAO(CategoryMallDAO categoryMallDAO) {
		this.categoryMallDAO = categoryMallDAO;
	}

	public SupplierMallDAO getSupplierMallDAO() {
		return supplierMallDAO;
	}

	public void setSupplierMallDAO(SupplierMallDAO supplierMallDAO) {
		this.supplierMallDAO = supplierMallDAO;
	}



	private String saveDirectory = "E:/Images/";
	
	@RequestMapping("/AddProduct")
	public ModelAndView AddProduct() {

		System.out.println("Adding Product");
		
		ModelAndView mv = new ModelAndView("AddProduct");
		mv.addObject("productMall", new ProductMall());
		
		List<ProductMall> productMallList = productMallDAO.getList();
		mv.addObject("productMallList",productMallList);
		mv.addObject("AddProduct", true);
		return mv;
	}	
	
	
	
	@RequestMapping("/saveproduct")
	public ModelAndView SaveProduct(@ModelAttribute("productMall") ProductMall productMall, BindingResult result, HttpServletRequest request, @RequestParam CommonsMultipartFile[] fileUpload)throws Exception {

		
		ModelAndView mv = new ModelAndView("AddProduct");
		mv.addObject("productMall", true);
		productMallDAO.save(productMall);
		System.out.println("Save Product Successfull");
		List<ProductMall> productMallList = productMallDAO.getList();
		mv.addObject("productMallList",productMallList);
		
		 System.out.println("description: " + request.getParameter("description"));
         
	        if (fileUpload != null && fileUpload.length > 0) {
	            for (CommonsMultipartFile aFile : fileUpload){
	                 
	                System.out.println("Saving file: " + aFile.getOriginalFilename());
	                 
	                if (!aFile.getOriginalFilename().equals("")) {
	                    aFile.transferTo(new File(saveDirectory + aFile.getOriginalFilename()));
	                }
	            }
	        }
	 
	        // returns to the view "Result"
	    return new ModelAndView("redirect:AddProduct");
	}
	
	@RequestMapping("/Delete")
	 public ModelAndView DeleteProduct(@RequestParam int id) {
	  int productMallDelete= productMallDAO.deleteRow(id);
	  return new ModelAndView("redirect:AddProduct");
	 }
	
	
	@RequestMapping("/Edit")
	public ModelAndView EditProduct(@RequestParam int id,@ModelAttribute("productMall") ProductMall productMall){
		ModelAndView mv = new ModelAndView("AddProduct");
		List<ProductMall> productMallList = productMallDAO.getList();
		mv.addObject("productMallList",productMallList);
		ProductMall productMallEdit = productMallDAO.getRowById(id);
		mv.addObject("productMallEdit",productMallEdit);
		return mv;
	}
	
	
	/*@RequestMapping("/Edit")
	 public ModelAndView editUser(@RequestParam String id,@ModelAttribute("productMall") String productMall) {
		ModelAndView mv = new ModelAndView("AddProduct");
		List<ProductMall> productMallList = productMallDAO.getList();
		mv.addObject("productMallList",productMallList);
		ProductMall productMallEdit = productMallDAO.getRowById(id);
		mv.addObject("productMallEdit", productMallEdit);
		return new ModelAndView("redirect:AddProduct");
	 }*/

	@RequestMapping("/Update_Product")
	 public ModelAndView updateProduct(@ModelAttribute("productMall") ProductMall productMall, BindingResult result) {
	  ModelAndView mv = new ModelAndView("AddProduct");	
	 
	  List<ProductMall> productMallList = (List<ProductMall>) productMallDAO.getList();
	  mv.addObject("productMallList",productMallList);
	  productMallDAO.updateRow(productMall);
	  return new ModelAndView("redirect:AddProduct");
	 }

}
