package com.github.woozoo73.srs.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "memberList")
@XmlType(propOrder = { "member" })
public class MemberList implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Member> member;

	public MemberList() {
	}

	public MemberList(List<Member> member) {
		this.member = member;
	}

	public List<Member> getMember() {
		return member;
	}

	public void setMember(List<Member> member) {
		this.member = member;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MemberList [member=");
		builder.append(member);
		builder.append("]");
		return builder.toString();
	}

}
