package com.github.woozoo73.srs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.woozoo73.srs.base.BaseController;
import com.github.woozoo73.srs.domain.Member;
import com.github.woozoo73.srs.domain.MemberList;
import com.github.woozoo73.srs.exception.NoContentException;
import com.github.woozoo73.srs.service.MemberService;

@Controller
@RequestMapping("/members")
public class MemberController extends BaseController {

	@Autowired
	private MemberService memberService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void create(@RequestBody Member member) {
		memberService.create(member);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@PathVariable String id, @RequestBody Member member) {
		Assert.notNull(member);
		Assert.isTrue(member.getId() == null || id.equals(member.getId()));

		member.setId(id);
		memberService.update(member);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable String id) {
		memberService.delete(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Member get(@PathVariable String id) {
		return memberService.get(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public MemberList getAllList() {
		List<Member> memberList = memberService.getAllList();

		if (memberList.isEmpty()) {
			throw new NoContentException("no member.");
		}

		return new MemberList(memberList);
	}

}
