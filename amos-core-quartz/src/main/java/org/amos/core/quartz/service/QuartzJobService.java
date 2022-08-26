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
package org.amos.core.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.amos.core.quartz.dto.QuartzJobDTO;
import org.amos.core.quartz.entity.QuartzJob;

import java.util.Set;

/**
 * <p>
 * 定时任务 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2021-01-25
 */
public interface QuartzJobService extends IService<QuartzJob> {

    /**
     * 新增定时任务
     * @param dto
     * @return
     */
    Boolean addJob(QuartzJobDTO dto);

    /**
     * 编辑任务
     * @param dto
     * @return
     */
    Boolean editJob(QuartzJobDTO dto);

    /**
     * 暂停任务
     * @param dto
     * @return
     */
    Boolean pauseJob(QuartzJobDTO dto);

    /**
     * 恢复任务
     * @param dto
     * @return
     */
    Boolean resumeJob(QuartzJobDTO dto);

    /**
     * 删除任务
     * @param ids
     * @return
     */
    Boolean removeJob(Set<Long> ids);

}
