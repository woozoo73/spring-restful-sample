package com.github.woozoo73.srs.controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.woozoo73.srs.AbstractTest;
import com.github.woozoo73.srs.dao.MemberDao;
import com.github.woozoo73.srs.domain.Member;
import com.github.woozoo73.srs.domain.MemberList;
import com.github.woozoo73.srs.exception.NoContentException;

public class MemberControllerTest extends AbstractTest {

	@Autowired
	private MemberController memberController;

	@Autowired
	private MemberDao memberDao;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();

		List<Member> list = memberDao.getAllList();
		for (Member member : list) {
			memberDao.delete(member.getId());
		}
	}

	@Test
	public void testCreate() {
		memberController.create(member);

		Member loaded = memberController.get(member.getId());
		assertEquals(member.getId(), loaded.getId());
		assertEquals(member.getName(), loaded.getName());
		assertEquals(member.getAge(), loaded.getAge());
	}

	@Test
	public void testUpdate() {
		memberController.create(member);

		String newName = "foo";
		Integer newAge = 20;
		member.setName(newName);
		member.setAge(newAge);

		memberController.update(member.getId(), member);

		Member loaded = memberController.get(member.getId());
		assertEquals(member.getId(), loaded.getId());
		assertEquals(newName, loaded.getName());
		assertEquals(newAge, loaded.getAge());
	}

	@Test
	public void testDelete() {
		memberController.create(member);

		memberController.delete(member.getId());

		try {
			memberController.get(member.getId());
			fail();
		} catch (Exception e) {
		}
	}

	@Test
	public void testGet() {
		memberController.create(member);

		Member loaded = memberController.get(member.getId());
		assertEquals(member.getId(), loaded.getId());
		assertEquals(member.getName(), loaded.getName());
		assertEquals(member.getAge(), loaded.getAge());
	}

	@Test
	public void testGetAllList() {
		memberController.create(member);

		MemberList memberList = memberController.getAllList();
		List<Member> list = memberList.getMember();
		assertEquals(1, list.size());

		Member loaded = list.get(0);
		assertEquals(member.getId(), loaded.getId());
		assertEquals(member.getName(), loaded.getName());
		assertEquals(member.getAge(), loaded.getAge());
	}

	@Test(expected = NoContentException.class)
	public void testGetAllListNoContent() {
		memberController.getAllList();
	}

}
