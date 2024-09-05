package project.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {

	private int seq;
	private String userId;
	private String subject;
	private String content;
	private int hit;
	private Date logtime;

}