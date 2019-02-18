/**
 * Copyright © 2018 TaoYu (tracy5546@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baomidou.kaptcha;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 验证码组件接口
 *
 * @author TaoYu
 */
public interface Kaptcha {

    /**
     * 初始化参数
     *
     * @param request
     * @param response
     */
    public void init(ServletRequest request, ServletResponse response);

    /**
     * 渲染验证码
     *
     * @return 验证码内容
     */
    String render();

    /**
     * 校对验证码,默认超时15分钟（900s）
     *
     * @param code 需要验证的字符串
     * @return 是否验证成功
     * @see com.baomidou.kaptcha.exception.KaptchaException
     */
    boolean validate(String code);

    /**
     * 校对验证码
     *
     * @param code   需要验证的字符串
     * @param second 超时时间（秒）
     * @return 是否验证成功
     * @see com.baomidou.kaptcha.exception.KaptchaException
     */
    boolean validate(String code, long second);

}
