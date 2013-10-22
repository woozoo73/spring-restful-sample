package com.github.woozoo73.srs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.woozoo73.srs.base.BaseService;
import com.github.woozoo73.srs.dao.MemberDao;
import com.github.woozoo73.srs.domain.Member;
import com.github.woozoo73.srs.exception.AlreadyExistException;
import com.github.woozoo73.srs.exception.NotFoundException;

@Service
public class MemberServiceImpl extends BaseService implements MemberService {

	@Autowired
	private MemberDao memberDao;

	public void create(Member member) {
		Assert.notNull(member, "member can't be null");
		Assert.hasText(member.getId(), "member's id can't be null");

		Member oldMember = memberDao.get(member.getId());
		if (oldMember != null) {
			throw new AlreadyExistException("already exist member. id=" + member.getId());
		}

		memberDao.create(member);
	}

	public int update(Member member) {
		Assert.notNull(member, "member can't be null");
		Assert.hasText(member.getId(), "member's id can't be null");

		Member oldMember = memberDao.get(member.getId());
		if (oldMember == null) {
			throw new NotFoundException("not exist member. id=" + member.getId());
		}

		int result = memberDao.update(member);

		return result;
	}

	public int delete(String id) {
		Assert.hasText(id, "member's id can't be null");

		Member member = memberDao.get(id);
		if (member == null) {
			throw new NotFoundException("not exist member. id=" + id);
		}

		int result = memberDao.delete(id);

		return result;
	}

	public Member get(String id) {
		Assert.hasText(id, "member's id can't be null");

		Member member = memberDao.get(id);
		if (member == null) {
			throw new NotFoundException("not exist member. id=" + id);
		}

		return member;
	}

	public List<Member> getAllList() {
		List<Member> memberList = memberDao.getAllList();

		return memberList;
	}

}
