package com.github.woozoo73.srs.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.github.woozoo73.srs.base.BaseDao;
import com.github.woozoo73.srs.domain.Member;

@Repository
public class MemberDaoImpl extends BaseDao implements MemberDao {

	private Map<String, Member> memberRepo = new LinkedHashMap<String, Member>();

	public void create(Member member) {
		Assert.notNull(member, "member can't be null");
		Assert.hasText(member.getId(), "member's id can't be null");

		String id = member.getId();
		Member matchedMember = memberRepo.get(id);
		if (matchedMember != null) {
			throw new RuntimeException("already exist id. id=" + id);
		}

		memberRepo.put(id, member);
	}

	public int update(Member member) {
		Assert.notNull(member, "member can't be null");
		Assert.hasText(member.getId(), "member's id can't be null");

		String id = member.getId();
		Member matchedMember = memberRepo.get(id);
		if (matchedMember == null) {
			return 0;
		}

		memberRepo.put(id, member);

		return 1;
	}

	public int delete(String id) {
		Assert.hasText(id, "member's id can't be null");

		Member matchedMember = memberRepo.get(id);
		if (matchedMember == null) {
			return 0;
		}

		memberRepo.remove(id);

		return 1;
	}

	public Member get(String id) {
		Assert.hasText(id, "member's id can't be null");

		Member member = memberRepo.get(id);

		return member;
	}

	public List<Member> getAllList() {
		List<Member> memberList = new ArrayList<Member>();
		memberList.addAll(memberRepo.values());

		return memberList;
	}

}
