package com.github.woozoo73.srs.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.woozoo73.srs.AbstractTest;
import com.github.woozoo73.srs.dao.MemberDao;
import com.github.woozoo73.srs.domain.Member;
import com.github.woozoo73.srs.exception.AlreadyExistException;
import com.github.woozoo73.srs.exception.NotFoundException;

public class MemberServiceTest extends AbstractTest {

	@Autowired
	private MemberService memberService;

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
		memberService.create(member);

		Member loaded = memberService.get(member.getId());
		assertEquals(member.getId(), loaded.getId());
		assertEquals(member.getName(), loaded.getName());
		assertEquals(member.getAge(), loaded.getAge());
	}

	@Test(expected = AlreadyExistException.class)
	public void testCreateDuplicateMember() {
		memberService.create(member);
		memberService.create(member);
	}

	@Test
	public void testUpdate() {
		memberService.create(member);

		String newName = "foo";
		Integer newAge = 20;
		member.setName(newName);
		member.setAge(newAge);

		int updated = memberService.update(member);
		assertEquals(1, updated);

		Member loaded = memberService.get(member.getId());
		assertEquals(member.getId(), loaded.getId());
		assertEquals(newName, loaded.getName());
		assertEquals(newAge, loaded.getAge());
	}

	@Test(expected = NotFoundException.class)
	public void testUpdateNotExist() {
		memberService.update(member);
	}

	@Test
	public void testDelete() {
		memberService.create(member);

		int deleted = memberService.delete(member.getId());
		assertEquals(1, deleted);

		try {
			memberService.get(member.getId());
			fail();
		} catch (Exception e) {
		}
	}

	@Test(expected = NotFoundException.class)
	public void testDeleteNotExist() {
		memberService.delete(member.getId());
	}

	@Test
	public void testGet() {
		memberService.create(member);

		Member loaded = memberService.get(member.getId());
		assertEquals(member.getId(), loaded.getId());
		assertEquals(member.getName(), loaded.getName());
		assertEquals(member.getAge(), loaded.getAge());
	}

	@Test(expected = NotFoundException.class)
	public void testGetNotExist() {
		memberService.get(member.getId());
	}

	@Test
	public void testGetAllList() {
		memberService.create(member);

		List<Member> list = memberService.getAllList();
		assertEquals(1, list.size());

		Member loaded = list.get(0);
		assertEquals(member.getId(), loaded.getId());
		assertEquals(member.getName(), loaded.getName());
		assertEquals(member.getAge(), loaded.getAge());
	}

	@Test
	public void testGetAllListEmpty() {
		List<Member> list = memberService.getAllList();
		assertTrue(list.isEmpty());
	}

}
