package com.sist.bbs;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import bbs.util.FileRenameUtil;
import bbs.util.Paging;
import bbs.util.Paging2;
import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BbsController {
	
	@Autowired
	private BbsDAO b_dao;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ServletContext application;
	
	private String editor_img = "/resources/editor_img"; // 썸머노트 이미지 추가할 때 저장할 위치

	private String upload= "/resources/upload"; // 글쓰기 첨부파일 저장할 위치
	
	int nowPage;
	int numPerPage = 3;
	int pagePerBlock = 3;
	int totalRecord;

	@RequestMapping("/list")
	public ModelAndView list(String cPage, String bname) {
		ModelAndView mv = new ModelAndView();
		
		if(bname == null) {
			bname = "bbs";
		}
		
		
		// 인자로 넘어온 파라미터는 현재 페이지 값이며
		// 이 값이 null이라면 기본적으로 첫 페이지가 지정되어야 한다.
		
		if(cPage == null) {
			nowPage = 1;
		} else {
			nowPage = Integer.parseInt(cPage);
		}
		
		// 페이징 기법을 위한 페이지 객체 생성
		Paging b_page = new Paging(numPerPage, pagePerBlock);
		
		// 전체 게시물 수
		totalRecord = b_dao.getCount(bname);
		b_page.setTotalRecord(totalRecord);
		
		// 현재 페이지
		b_page.setNowPage(nowPage); // begin, end, startPage, endPage 가 자동으로 계산됨
		
		// 현재페이지에 표시할 게시물 가져오기
		Paging2 b_page2 = new Paging2(numPerPage, pagePerBlock, totalRecord, nowPage, bname);
		

		int begin = b_page2.getBegin();
		int end = b_page2.getEnd();
		
		BbsVO[] b_ar = b_dao.getList(bname, begin, end);
		
		// 뷰페이지에서 표현할 수 있도록 mv에 저장
		mv.addObject("bname", bname);
		mv.addObject("b_ar", b_ar);
		mv.addObject("b_page", b_page2);

		mv.addObject("totalRecord", totalRecord);
		mv.addObject("nowPage", nowPage);
		mv.addObject("numPerPage", numPerPage);
		mv.addObject("pageCode", b_page2.getSb().toString());
		
		
		mv.setViewName(bname+"/list");
		
		return mv;
	}
	
	@RequestMapping("/write")
	public String write(String bname) {
		
		return bname+"/write";
	}
	
	@RequestMapping(value="/write", method = RequestMethod.POST)
	public ModelAndView write(BbsVO bvo) {
		 
		// 폼 양식에서 첨부파일이 전달될 때 enctype이 지정된다.
		String c_type = request.getContentType();
		if(c_type.startsWith("multipart")) {
			
			String fname = "";
			String oname = "";
		
			MultipartFile file = bvo.getFile();
			if(file != null && file.getSize()>0) {
				
				String realPath = application.getRealPath(upload);
				
				try {
					fname = file.getOriginalFilename();
					bvo.setOri_name(fname);
					
					fname = FileRenameUtil.checkSameFileName(fname, realPath);
					
					file.transferTo(new File(realPath, fname));
					bvo.setFile_name(fname);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} 
			
			String ip = request.getRemoteAddr(); // 접속자의 IP
			
			bvo.setIp(ip);
			
			b_dao.add(bvo);
			
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:list?bname"+bvo.getBname());
		return mv;
	}
	

	@RequestMapping(value="/saveImg", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> saveImg(MultipartFile s_file) {
		Map<String, String> i_map = new HashMap<String, String>();
		String fname = null;
		
		if(s_file != null && s_file.getSize()>0) {
			// 파일이 있는 경우
			// 파일을 저장할 위치, 절대 경로를 만들자	
			
			String realPath = application.getRealPath(editor_img);
			fname = s_file.getOriginalFilename();
			
			// 동일한 파일명이 있다면 파일명을 변경해준다.
			fname = FileRenameUtil.checkSameFileName(fname, realPath);
			
			try {
				s_file.transferTo(new File(realPath, fname));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String c_path = request.getContextPath();
		i_map.put("url", c_path+editor_img);
		i_map.put("fname",fname);
		
		
		
		return i_map;
		//요청한 곳으로 보내진다.
		// 이때, JSON으로 보내기 위해 @ResponseBody으로 지정해준다.
	}
	
	
	
	
	
	
	
}
