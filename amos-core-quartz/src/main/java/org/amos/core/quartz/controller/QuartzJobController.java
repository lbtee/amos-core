package org.amos.core.quartz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amos.core.basic.base.BaseController;
import org.amos.core.basic.utils.AmosUtils;
import org.amos.core.basic.vo.R;
import org.amos.core.quartz.dto.QuartzJobDTO;
import org.amos.core.quartz.entity.QuartzJob;
import org.amos.core.quartz.vo.QuartzJobVO;
import org.amos.core.quartz.service.QuartzJobService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Slf4j
@RestController
@Api(tags = "定时任务管理")
@RequestMapping("/quartz/job")
@RequiredArgsConstructor
public class QuartzJobController extends BaseController {

    private final QuartzJobService quartzJobService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取所有定时任务")
    public R getAllByPage(QuartzJobDTO dto) {
        IPage<QuartzJob> page = quartzJobService.page(initPage(dto), buildWrapper(dto));
        IPage<QuartzJobVO> res = AmosUtils.pageCopy(page, QuartzJobVO.class);
        return R.ok(res);
    }

    @PostMapping(value = "/create")
    @ApiOperation(value = "添加定时任务")
    public R create(@RequestBody QuartzJobDTO dto) {
        quartzJobService.addJob(dto);
        return R.ok("创建定时任务成功");
    }

    @PostMapping(value = "/update")
    @ApiOperation(value = "更新定时任务")
    public R update(@RequestBody QuartzJobDTO dto) {
        quartzJobService.editJob(dto);
        return R.ok("更新定时任务成功");
    }

    @PostMapping(value = "/pause")
    @ApiOperation(value = "暂停定时任务")
    public R pause(@RequestBody QuartzJobDTO dto) {
        quartzJobService.pauseJob(dto);
        return R.ok("暂停定时任务成功");
    }

    @PostMapping(value = "/resume")
    @ApiOperation(value = "恢复定时任务")
    public R resume(@RequestBody QuartzJobDTO dto) {
        quartzJobService.resumeJob(dto);
        return R.ok("恢复定时任务成功");
    }

    @PostMapping(value = "/remove")
    @ApiOperation(value = "删除定时任务")
    public R remove(@RequestParam Set<Long> ids) {
        quartzJobService.removeJob(ids);
        return R.ok("删除定时任务成功");
    }
}
