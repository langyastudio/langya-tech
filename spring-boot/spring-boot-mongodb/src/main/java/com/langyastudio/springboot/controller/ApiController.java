package com.langyastudio.springboot.controller;

import com.langyastudio.springboot.bean.MemberReadHistory;
import com.langyastudio.springboot.common.data.EC;
import com.langyastudio.springboot.common.data.ResultInfo;
import com.langyastudio.springboot.service.MemberReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Restful web
 */
@RestController
@RequestMapping("/api/member/readHistory")
@Validated
public class ApiController
{
    @Autowired
    private MemberReadHistoryService memberReadHistoryService;

    @PostMapping(value = "/create")
    public ResultInfo create(@RequestBody MemberReadHistory memberReadHistory)
    {
        int count = memberReadHistoryService.create(memberReadHistory);
        if (count > 0)
        {
            return ResultInfo.data(count);
        }
        else
        {
            return ResultInfo.data(EC.ERROR_DATA_SAVE_FAILURE);
        }
    }

    @PostMapping(value = "/delete")
    public ResultInfo delete(@RequestParam("ids") List<String> ids)
    {
        int count = memberReadHistoryService.delete(ids);
        if (count > 0)
        {
            return ResultInfo.data(count);
        }
        else
        {
            return ResultInfo.data(EC.ERROR_DATA_DELETE_FAILURE);
        }
    }

    @GetMapping(value = "/list")
    public ResultInfo list(Long memberId,
                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize)
    {
        Page<MemberReadHistory> memberReadHistoryList = memberReadHistoryService.list(memberId, pageNum, pageSize);
        return ResultInfo.data(memberReadHistoryList);
    }
}
