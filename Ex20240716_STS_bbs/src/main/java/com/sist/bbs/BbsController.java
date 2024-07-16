package com.sist.bbs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	
	int nowPage;
	int numPerPage = 3;
	int pagePerBlock = 3;
	int totalRecord;

	@RequestMapping("/list")
	public ModelAndView bbs_list(String cPage, String bname) {
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
	
	
	
	
	
	
	
	
	
	
}
