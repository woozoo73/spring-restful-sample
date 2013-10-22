package com.github.woozoo73.srs.service;

import java.util.List;

import com.github.woozoo73.srs.domain.Member;

public interface MemberService {

	public void create(Member member);

	public int update(Member member);

	public int delete(String id);

	public Member get(String id);

	public List<Member> getAllList();

}
