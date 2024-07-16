package mybatis.vo;

public class CommentVO {
	private String c_idx, writer, content, pwd, write_date, ip, b_idx;

	
	public void setC_idx(String c_idx) {
		this.c_idx = c_idx;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setB_idx(String b_idx) {
		this.b_idx = b_idx;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	
	
	
	public String getC_idx() {
		return c_idx;
	}

	public String getWriter() {
		return writer;
	}

	public String getContent() {
		return content;
	}

	public String getWrite_date() {
		return write_date;
	}

	public String getIp() {
		return ip;
	}

	public String getB_idx() {
		return b_idx;
	}

	public String getPwd() {
		return pwd;
	}
	
	
}
