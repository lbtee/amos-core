/**
*    Copyright (c) 2020-2030 LiuBoTao [1211265557@qq.com]
*
*    Licensed under the Apache License, Version 2.0 (the "License");
*    you may not use this file except in compliance with the License.
*    You may obtain a copy of the License at
*
*        http://www.apache.org/licenses/LICENSE-2.0
*
*    Unless required by applicable law or agreed to in writing, software
*    distributed under the License is distributed on an "AS IS" BASIS,
*    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*    See the License for the specific language governing permissions and
*    limitations under the License.
*/
package org.amos.core.quartz.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.amos.core.basic.constant.SystemConstant;
import org.amos.core.basic.exception.ServiceException;
import org.amos.core.basic.utils.AmosUtils;
import org.amos.core.basic.utils.crud.WrapperBuilder;
import org.amos.core.quartz.dto.QuartzJobDTO;
import org.amos.core.quartz.entity.QuartzJob;
import org.amos.core.quartz.mapper.QuartzJobMapper;
import org.amos.core.quartz.service.QuartzJobService;
import org.amos.core.quartz.utils.QuartzUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 定时任务 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2021-01-25
 */
@Service
@RequiredArgsConstructor
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements QuartzJobService {

    private final QuartzUtil quartzUtil;

    @Override
    public Boolean addJob(QuartzJobDTO dto) {
        List<QuartzJob> list = this.list(new WrapperBuilder().build(dto));
        if (ObjectUtil.isNotEmpty(list)) {
            throw new ServiceException("该定时任务类名已存在");
        }
        QuartzJob job = AmosUtils.copy(dto, QuartzJob.class);
        quartzUtil.add(job);
        this.save(job);
        return Boolean.TRUE;
    }

    @Override
    public Boolean editJob(QuartzJobDTO dto) {
        QuartzJob job = AmosUtils.copy(dto, QuartzJob.class);
        quartzUtil.delete(job);
        quartzUtil.add(job);
        job.setStatus(SystemConstant.QUARTZ_JOB_STATUS_NORMAL);
        this.updateById(job);
        return Boolean.TRUE;
    }

    @Override
    public Boolean pauseJob(QuartzJobDTO dto) {
        QuartzJob job = AmosUtils.copy(dto, QuartzJob.class);
        quartzUtil.pause(job);
        job.setStatus(SystemConstant.QUARTZ_JOB_STATUS_DISABLE);
        this.updateById(job);
        return Boolean.TRUE;
    }

    @Override
    public Boolean resumeJob(QuartzJobDTO dto) {
        QuartzJob job = AmosUtils.copy(dto, QuartzJob.class);
        quartzUtil.resume(job);
        job.setStatus(SystemConstant.QUARTZ_JOB_STATUS_NORMAL);
        this.updateById(job);
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeJob(Set<Long> ids) {
        List<QuartzJob> jobs = this.listByIds(ids);
        for (QuartzJob job : jobs) {
            quartzUtil.delete(job);
        }
        this.removeByIds(ids);
        return Boolean.TRUE;
    }
}
