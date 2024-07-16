package mybatis.vo;

import java.util.List;

public class BbsVO {

	private String rnum, b_idx, subject, writer, content, file_name, ori_name, pwd, write_date, ip, hit, bname, status;

	private List<CommentVO> c_list;
	
	
	
	public void setRnum(String rnum) {
		
		int r_num = (int) Double.parseDouble(rnum);
		this.rnum = String.valueOf(r_num);
	}

	public void setB_idx(String b_idx) {
		this.b_idx = b_idx;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public void setOri_name(String ori_name) {
		this.ori_name = ori_name;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setHit(String hit) {
		this.hit = hit;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setC_list(List<CommentVO> c_list) {
		this.c_list = c_list;
	}

	
	
	
	public String getRnum() {
		return rnum;
	}

	public String getB_idx() {
		return b_idx;
	}

	public String getSubject() {
		return subject;
	}

	public String getWriter() {
		return writer;
	}

	public String getContent() {
		return content;
	}

	public String getFile_name() {
		return file_name;
	}

	public String getOri_name() {
		return ori_name;
	}

	public String getPwd() {
		return pwd;
	}

	public String getWrite_date() {
		return write_date;
	}

	public String getIp() {
		return ip;
	}

	public String getHit() {
		return hit;
	}

	public String getBname() {
		return bname;
	}

	public String getStatus() {
		return status;
	}

	public List<CommentVO> getC_list() {
		return c_list;
	}
	
	
	
	
}
